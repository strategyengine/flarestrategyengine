package com.strategyengine.flare.flarestrategyengine.learn;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import org.springframework.stereotype.Service;
import org.uncommons.maths.random.MersenneTwisterRNG;
import org.uncommons.watchmaker.framework.EvolutionEngine;
import org.uncommons.watchmaker.framework.EvolutionaryOperator;
import org.uncommons.watchmaker.framework.operators.EvolutionPipeline;
import org.uncommons.watchmaker.framework.selection.TruncationSelection;
import org.uncommons.watchmaker.framework.termination.Stagnation;

import com.strategyengine.flare.flarestrategyengine.model.AssetValue;
import com.strategyengine.flare.flarestrategyengine.model.Investment;
import com.strategyengine.flare.flarestrategyengine.model.Portfolio;


/**
 * RUN ME
 * Simple evolutionary algorithm that evolves a population of randomly-generated
 * Craps betting strategies until at least the best strategy is selected
 */
@Service
public final class EvolverServiceImpl implements EvolverService {

	//not using this multithreaded approach
	// threads per cpu
	public static int THREADS_PER_CPU = 2;

	private static int CREATURES_IN_POPULATION = 5000;
	// number of creatures that are copied from the total population to the next generation unchanged. ie take
	// the best 5
	private static int ELITISM = 100;
	
	private static double SELECTION_RATIO = Double.valueOf(String.valueOf(ELITISM)) / Double.valueOf(String.valueOf(CREATURES_IN_POPULATION));
	
	
	//number of attributes that can be set in the strategy.  crossover points max
	public final static int MAX_CROSSOVER_ATTRIBUTES = 10;
	
	private final static int NUMBER_OF_GENERATIONS_WITH_NO_IMPROVEMENTS_TRIGGER_TERMINATE = 1000;
	
	@Override
	public FlarePredictorCreature evolveCreature(List<Investment> investmentOptionsAvailable, Map<String, AssetValue> assetValues) {

		List<EvolutionaryOperator<FlarePredictorCreature>> operators = new ArrayList<EvolutionaryOperator<FlarePredictorCreature>>(2);

		CalculationFactory calculationFactory = new CalculationFactory(investmentOptionsAvailable);
		
		operators.add(new CalculationMutation(0.02d, calculationFactory));
		operators.add(new CalculationCrossover());

		EvolutionaryOperator<FlarePredictorCreature> pipeline = new EvolutionPipeline<FlarePredictorCreature>(
				operators);
		
		EvolutionEngine<FlarePredictorCreature> engine =
				new CustomEvolutionEngine<FlarePredictorCreature>(calculationFactory, pipeline, new FlareInvestmentEvaluator(assetValues),
						new TruncationSelection(SELECTION_RATIO), new MersenneTwisterRNG());

		// engine.addEvolutionObserver(new EvolutionLogger());

		FlarePredictorCreature winner = engine.evolve(CREATURES_IN_POPULATION, // 100 individuals in the population.
				ELITISM, // 5% elitism.
				new Stagnation(NUMBER_OF_GENERATIONS_WITH_NO_IMPROVEMENTS_TRIGGER_TERMINATE, true));
		
		List<Investment> prioritizedInvestmentsWithHoldings = winner.getStrategy().getInvestmentOptionsPriotized().stream()
				.filter(i -> holdingExistsForInvestment(i, winner.getPortfolio())).collect(Collectors.toList());
		
		winner.getStrategy().setInvestmentOptionsPriotized(prioritizedInvestmentsWithHoldings);
		
		return winner;
	}

	private boolean holdingExistsForInvestment(Investment i, Portfolio portfolio) {
		return portfolio.getHoldings().stream()
				.filter(h -> h.getAsset().equals(i.getAsset()) 
						&& h.getInvestmentType().equals(i.getType()))
				.findAny().isPresent();
	}


//	/**
//	 * Trivial evolution observer for displaying information at the end of each
//	 * generation.
//	 */
//	private static class EvolutionLogger implements EvolutionObserver<FlarePredictorCreature> {
//		public void populationUpdate(PopulationData<CrapsPredictorCreature> data) {
//			// System.out.println("Generation " + data.getGenerationNumber() + ": " +
//			// data.getBestCandidate());
//		}
//	}
}
