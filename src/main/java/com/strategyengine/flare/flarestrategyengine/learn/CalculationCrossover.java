package com.strategyengine.flare.flarestrategyengine.learn;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Random;

import org.uncommons.maths.number.NumberGenerator;
import org.uncommons.maths.random.Probability;
import org.uncommons.watchmaker.framework.operators.AbstractCrossover;

import com.strategyengine.flare.flarestrategyengine.model.Investment;

/**
 * Variable-point (fixed or random) cross-over for String candidates. This
 * implementation assumes that all candidate Strings are the same length. If
 * they are not, an exception will be thrown at runtime.
 * 
 * @author Daniel Dyer
 */
public class CalculationCrossover extends AbstractCrossover<FlarePredictorCreature> {
	/**
	 * Default is single-point cross-over, applied to all parents.
	 */
	public CalculationCrossover() {
		this(1);
	}

	/**
	 * Cross-over with a fixed number of cross-over points.
	 * 
	 * @param crossoverPoints The constant number of cross-over points to use for
	 *                        all cross-over operations.
	 */
	public CalculationCrossover(int crossoverPoints) {
		super(crossoverPoints);
	}

	/**
	 * Cross-over with a fixed number of cross-over points. Cross-over may or may
	 * not be applied to a given pair of parents depending on the
	 * {@code crossoverProbability}.
	 * 
	 * @param crossoverPoints      The constant number of cross-over points to use
	 *                             for all cross-over operations.
	 * @param crossoverProbability The probability that, once selected, a pair of
	 *                             parents will be subjected to cross-over rather
	 *                             than being copied, unchanged, into the output
	 *                             population. Must be in the range
	 *                             {@literal 0 < crossoverProbability <= 1}
	 */
	public CalculationCrossover(int crossoverPoints, Probability crossoverProbability) {
		super(crossoverPoints, crossoverProbability);
	}

	/**
	 * Cross-over with a variable number of cross-over points.
	 * 
	 * @param crossoverPointsVariable A random variable that provides a number of
	 *                                cross-over points for each cross-over
	 *                                operation.
	 */
	public CalculationCrossover(NumberGenerator<Integer> crossoverPointsVariable) {
		super(crossoverPointsVariable);
	}

	/**
	 * Cross-over with a variable number of cross-over points. Cross-over may or may
	 * not be applied to a given pair of parents depending on the
	 * {@code crossoverProbability}.
	 * 
	 * @param crossoverPointsVariable A random variable that provides a number of
	 *                                cross-over points for each cross-over
	 *                                operation.
	 * @param crossoverProbability    The probability that, once selected, a pair of
	 *                                parents will be subjected to cross-over rather
	 *                                than being copied, unchanged, into the output
	 *                                population. Must be in the range
	 *                                {@literal 0 < crossoverProbability <= 1}
	 */
	public CalculationCrossover(NumberGenerator<Integer> crossoverPointsVariable,
			NumberGenerator<Probability> crossoverProbability) {
		super(crossoverPointsVariable, crossoverProbability);
	}

	protected List<FlarePredictorCreature> mate(FlarePredictorCreature parent1, FlarePredictorCreature parent2,
			int numberOfCrossoverPoints, Random rng) {

		
		FlarePredictorCreature child = FlarePredictorCreature.builder().value(0d)
				.portfolio(DefaultPortfolio.create())
				.strategy(EvolveStrategy.builder()
						.investmentOptionsPriotized(
								new ArrayList<Investment>(parent1.getStrategy().getInvestmentOptionsPriotized()))
						.build())
				.build();

		
		// the child is based on parent1, so replace the investment at the priority from
		// the value at parent2
		for (int i = 0; i < numberOfCrossoverPoints; i++) {
			int size = parent1.getStrategy().getInvestmentOptionsPriotized().size();
			if(size==0) {
				continue;
			}
			int crossoverPoint = rng.nextInt(size);
			Investment investmentToMoveToPriority = parent2.getStrategy().getInvestmentOptionsPriotized()
					.get(crossoverPoint);

			// remove that investment from its current priority
			child.getStrategy().getInvestmentOptionsPriotized().remove(investmentToMoveToPriority);

			// insert it at the new priority
			child.getStrategy().getInvestmentOptionsPriotized().add(crossoverPoint, investmentToMoveToPriority);

		}
		

		return Arrays.asList(child);
	}

}