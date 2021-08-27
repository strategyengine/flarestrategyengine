package com.strategyengine.flare.flarestrategyengine.learn;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.stream.Collectors;

import org.uncommons.watchmaker.framework.FitnessEvaluator;

import com.google.common.annotations.VisibleForTesting;
import com.google.common.collect.Lists;
import com.strategyengine.flare.flarestrategyengine.model.AssetValue;
import com.strategyengine.flare.flarestrategyengine.model.AssetYield;
import com.strategyengine.flare.flarestrategyengine.model.Holding;
import com.strategyengine.flare.flarestrategyengine.model.Investment;
import com.strategyengine.flare.flarestrategyengine.model.InvestmentType;

public class FlareInvestmentEvaluator implements FitnessEvaluator<FlarePredictorCreature> {

	private static int DAYS_TO_CALCULATE = 30;
	private static int DAILY_YIELD_DIVISOR = 365;

	private Map<String, AssetValue> assetValues;

	public FlareInvestmentEvaluator(Map<String, AssetValue> assetValues) {
		this.assetValues = assetValues;
	}

	@Override
	public double getFitness(FlarePredictorCreature candidate, List<? extends FlarePredictorCreature> population) {

		if (candidate.getValue() > 0) {
			return candidate.getValue();
		}

		Double value = 0d;
		// calculate for 30 days in this investment
		for (int i = 0; i < DAYS_TO_CALCULATE; i++) {

			// reallocate holding according to investment priorities. Need to do this in the
			// loop since new holding keep coming in every day
			List<Holding> reallocatedHoldings = reallocateHoldings(
					candidate.getStrategy().getInvestmentOptionsPriotized(), candidate.getPortfolio().getHoldings());

			candidate.getPortfolio().setHoldings(reallocatedHoldings);

			List<Holding> updatedHoldings = candidate.getPortfolio().getHoldings().stream().map(
					h -> updateHoldingsBasedOnAssetYields(h, candidate.getStrategy().getInvestmentOptionsPriotized()))
					.flatMap(Collection::stream).collect(Collectors.toList());

			candidate.getPortfolio().setHoldings(updatedHoldings);

			// calculate daily value
			value += candidate.getPortfolio().getHoldings().stream()
					.filter(h -> assetValues.get(h.getAsset())!=null)
					.mapToDouble(h -> h.getAmount() * assetValues.get(h.getAsset()).getUsdValue().doubleValue()).sum();
		//	value += candidate.getStrategy().getInvestmentOptionsPriotized().stream()
		//			.mapToDouble(inv -> calculateCuurentValueOfHoldings(inv, candidate.getPortfolio().getHoldings()))
		//			.sum();

			// TODO consolidate holdings with same asset and investment type
			List<Holding> consolidatedHoldings = consolidateHoldings(candidate.getPortfolio().getHoldings());

			candidate.getPortfolio().setHoldings(consolidatedHoldings);
		}

		candidate.setValue(value);

		return value;
	}
	
	@VisibleForTesting
	protected List<Holding> consolidateHoldings(List<Holding> holdings) {

		List<Holding> currentHoldingsCopy = Lists.newArrayList(holdings);
		List<Holding> consolidatedHoldings = new ArrayList<Holding>();

		for (Holding holding : holdings) {

			List<Holding> matchedHoldings = currentHoldingsCopy.stream()
					.filter(h -> holding.getAsset().equals(h.getAsset())
							&& holding.getInvestmentType().equals(h.getInvestmentType()))
					.collect(Collectors.toList());

			if (matchedHoldings.isEmpty()) {
				// this holding was already consolidated with a prior holding in the loop
				continue;
			}
			currentHoldingsCopy.removeAll(matchedHoldings);

			Double sumOfAllHoldingsForAssetInvestment = matchedHoldings.stream().mapToDouble(h -> h.getAmount()).sum();

			holding.setAmount(sumOfAllHoldingsForAssetInvestment);

			consolidatedHoldings.add(holding);
		}
		return consolidatedHoldings;
	}

	@VisibleForTesting
	protected List<Holding> reallocateHoldings(List<Investment> investmentOptionsPriotized, List<Holding> holdings) {

		List<Holding> reallocatedHoldings = new ArrayList<Holding>();

		List<Holding> holdingsCopy = Lists.newArrayList(holdings);
		for (Investment investment : investmentOptionsPriotized) {

			List<Holding> matchedAssetHoldings = holdingsCopy.stream()
					.filter(h -> h.getAsset().equals(investment.getAsset())).collect(Collectors.toList());

			holdingsCopy.removeAll(matchedAssetHoldings);

			//move the matched holdings to the investment type
			List<Holding> reallocatedHoldingForInvestment = matchedAssetHoldings.stream().map(h -> Holding.builder()
					.amount(h.getAmount()).asset(h.getAsset()).investmentType(investment.getType()).build())
					.collect(Collectors.toList());

			reallocatedHoldings.addAll(reallocatedHoldingForInvestment);
		}

		if (!holdingsCopy.isEmpty()) {
			//TODO these holdings need to be swapped into assets with high priority investment options
			reallocatedHoldings.addAll(holdingsCopy);
		}

		return reallocatedHoldings;
	}

	//get all of the holdings that were earned by holding some asset
	@VisibleForTesting
	protected List<Holding> updateHoldingsBasedOnAssetYields(Holding holding, List<Investment> investmentOptions) {

		Optional<Investment> investmentOpt = investmentOptions.stream()
				.filter(i -> i.getAsset().equals(holding.getAsset()) && i.getType().equals(holding.getInvestmentType()))
				.findFirst();

		if (investmentOpt.isEmpty()) {
			//TODO -- this asset has no investment option, it needs to be swapped to the highest priority investment
			return Arrays.asList(holding);
		}

		List<Holding> finalHoldings = investmentOpt.get().getAssetYields().stream()
				.map(yld -> calcEarnedAsset(yld, holding)).collect(Collectors.toList());

		finalHoldings.add(holding);

		return finalHoldings;

	}

	//calculates the new holding that was earned based on a held asset
	@VisibleForTesting
	protected Holding calcEarnedAsset(AssetYield yld, Holding holding) {
		return Holding.builder().asset(yld.getAsset()).investmentType(InvestmentType.UNINVESTED)
				.amount(holding.getAmount() * yld.getYield() / DAILY_YIELD_DIVISOR).build();
	}

	@VisibleForTesting
	protected double calculateCuurentValueOfHoldings(Investment investment, List<Holding> holdings) {

		AssetValue assetValue = assetValues.get(investment.getAsset());
		if (assetValue == null) {
			return 0d;
		}

		Optional<Holding> holdingOpt = holdings.stream().filter(h -> h.getAsset().equals(investment.getAsset()))
				.findFirst();

		if (holdingOpt.isEmpty()) {
			System.out.println("Need to add sample holding for investment " + investment);
			return 0d;
		}

		return holdingOpt.get().getAmount() * assetValue.getUsdValue().doubleValue();
	}

	/**
	 * higher scores are more fit when true.
	 */
	@Override
	public boolean isNatural() {
		return true;
	}

}
