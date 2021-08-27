package com.strategyengine.flare.flarestrategyengine.learn;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import org.uncommons.watchmaker.framework.EvolutionaryOperator;

/**
 * Mutation of individual characters in a string according to some probability.
 */
public class CalculationMutation implements EvolutionaryOperator<FlarePredictorCreature> {
	private final double mutationProbability;

	private final CalculationFactory calculationFactory;

	/**
	 * @param alphabet            The permitted values for each character in a
	 *                            string.
	 * @param mutationProbability The probability that a given character is changed.
	 */
	public CalculationMutation(double mutationProbability, CalculationFactory calculationFactory) {
		if (mutationProbability <= 0 || mutationProbability > 1) {
			throw new IllegalArgumentException(
					"Mutation probability must be greater than " + "zero and less than or equal to one.");
		}
		this.mutationProbability = mutationProbability;
		this.calculationFactory = calculationFactory;
	}

	/**
	 * mutates by taking a target out of somewhere and sticking it on the end
	 * 
	 * @param s
	 * @param rng
	 * @return
	 */
	private FlarePredictorCreature mutateCalculation(FlarePredictorCreature s, Random rng) {
		
		if(rng.nextDouble() < mutationProbability) {
			return s;
		}
		
		FlarePredictorCreature mutant = calculationFactory.generateRandomCandidate(rng);

		CalculationCrossover cross = new CalculationCrossover();
		FlarePredictorCreature c = cross.mate(mutant, s, rng.nextInt(EvolverServiceImpl.MAX_CROSSOVER_ATTRIBUTES), rng).get(0);
		
		return c;

	}

	@Override
	public List<FlarePredictorCreature> apply(
			List<FlarePredictorCreature> selectedCandidates, Random rng) {

		List<FlarePredictorCreature> mutatedPopulation = new ArrayList<FlarePredictorCreature>(
				selectedCandidates.size());
		for (FlarePredictorCreature s : selectedCandidates) {
			mutatedPopulation.add( mutateCalculation(s, rng));
		}
		return mutatedPopulation;
	}
}