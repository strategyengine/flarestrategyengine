package com.strategyengine.flare.flarestrategyengine.learn;

import java.util.List;
import java.util.Random;

import org.uncommons.watchmaker.framework.factories.AbstractCandidateFactory;

import com.google.common.collect.Lists;
import com.strategyengine.flare.flarestrategyengine.model.Investment;

public class CalculationFactory extends AbstractCandidateFactory<FlarePredictorCreature> {

	private List<Investment> investmentOptionsAvailable;

	public CalculationFactory(List<Investment> investmentOptionsAvailable) {

		this.investmentOptionsAvailable = investmentOptionsAvailable;
	}

	public FlarePredictorCreature generateRandomCandidate(Random rng) {
		return new CandidateFactory(Lists.newArrayList(investmentOptionsAvailable)).generateRandomCandidate(rng);
	}

}