package com.strategyengine.flare.flarestrategyengine.learn;

import java.util.List;
import java.util.Random;

import org.uncommons.watchmaker.framework.factories.AbstractCandidateFactory;

import com.strategyengine.flare.flarestrategyengine.model.Investment;

public class CandidateFactory extends AbstractCandidateFactory<FlarePredictorCreature> {

	/**
	 * @param alphabet     The set of characters that can legally occur within a
	 *                     string generated by this factory.
	 * @param stringLength The fixed length of all strings generated by this
	 *                     factory.
	 */

	private List<Investment> investmentOptions;

	public CandidateFactory(List<Investment> investmentOptions) {

		this.investmentOptions = investmentOptions;
	}

	/**
	 * Generates a randomly ordered list of investment options to be applied in
	 * priority order
	 * 
	 * @param rng A source of randomness used to select characters to make up the
	 *            string.
	 * @return A randomly generated string.
	 */
	public FlarePredictorCreature generateRandomCandidate(Random rng) {
		try {
			EvolveStrategy strategy = new EvolveStrategy(investmentOptions);

			strategy.init(rng);
			
			FlarePredictorCreature c =  FlarePredictorCreature.builder().portfolio(DefaultPortfolio.create()).strategy(strategy).value(0d).build();
			
			return c;

		} catch (Exception e) {
			throw new RuntimeException(e);
		}
	}


}