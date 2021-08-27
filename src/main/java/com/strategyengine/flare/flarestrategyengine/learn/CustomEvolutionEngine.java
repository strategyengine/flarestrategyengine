package com.strategyengine.flare.flarestrategyengine.learn;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Random;
import java.util.concurrent.Callable;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.Future;
import java.util.concurrent.LinkedBlockingQueue;
import java.util.concurrent.ThreadFactory;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

import org.uncommons.maths.random.MersenneTwisterRNG;
import org.uncommons.util.concurrent.ConfigurableThreadFactory;
import org.uncommons.watchmaker.framework.CandidateFactory;
import org.uncommons.watchmaker.framework.EvaluatedCandidate;
import org.uncommons.watchmaker.framework.EvolutionaryOperator;
import org.uncommons.watchmaker.framework.FitnessEvaluator;
import org.uncommons.watchmaker.framework.GenerationalEvolutionEngine;
import org.uncommons.watchmaker.framework.SelectionStrategy;


/**
 * Generic evolutionary algorithm engine for evolution that runs on a single
 * host. Includes support for parallel fitness evaluations on multi-processor
 * and multi-core machines.
 * 
 * @param <T> The type of entity that is to be evolved.
 * @author Daniel Dyer
 * @see CandidateFactory
 * @see FitnessEvaluator
 * @see SelectionStrategy
 * @see EvolutionaryOperator
 */
public class CustomEvolutionEngine<T> extends GenerationalEvolutionEngine<T> {

//	private static final int PROCESSOR_COUNT = Runtime.getRuntime().availableProcessors()
//			* CrapsEvolver.THREADS_PER_CPU;
	private static final int PROCESSOR_COUNT = 1;
	private FitnessEvaluator<? super T> fitnessEvaluator;

	/**
	 * This thread pool performs concurrent fitness evaluations (on hosts that have
	 * more than one processor).
	 */
	private final ThreadPoolExecutor threadPool;

	/**
	 * Creates a new evolution engine by specifying the various components required
	 * by an evolutionary algorithm.
	 * 
	 * @param candidateFactory  Factory used to create the initial population that
	 *                          is iteratively evolved.
	 * @param evolutionScheme   The combination of evolutionary operators used to
	 *                          evolve the population at each generation.
	 * @param fitnessEvaluator  A function for assigning fitness scores to candidate
	 *                          solutions.
	 * @param selectionStrategy A strategy for selecting which candidates survive to
	 *                          be evolved.
	 * @param rng               The source of randomness used by all stochastic
	 *                          processes (including evolutionary operators and
	 *                          selection strategies).
	 */

	public CustomEvolutionEngine(CandidateFactory<T> candidateFactory, EvolutionaryOperator<T> evolutionScheme,
			FitnessEvaluator<? super T> fitnessEvaluator, SelectionStrategy<? super T> selectionStrategy,
			MersenneTwisterRNG rng) {
		this(candidateFactory, evolutionScheme, fitnessEvaluator, selectionStrategy, rng,
				new ConfigurableThreadFactory("EvolutionEngine", Thread.NORM_PRIORITY, true));
	}

	/**
	 * Creates a new evolution engine by specifying the various components required
	 * by an evolutionary algorithm and a thread factory. Most users will not need a
	 * custom thread factory and should instead use the
	 * {@link #StandaloneEvolutionEngine(CandidateFactory, EvolutionaryOperator, FitnessEvaluator, SelectionStrategy, Random)}
	 * constructor, which provides a sensible default.
	 * 
	 * @param candidateFactory  Factory used to create the initial population that
	 *                          is iteratively evolved.
	 * @param evolutionScheme   The combination of evolutionary operators used to
	 *                          evolve the population at each generation.
	 * @param fitnessEvaluator  A function for assigning fitness scores to candidate
	 *                          solutions.
	 * @param selectionStrategy A strategy for selecting which candidates survive to
	 *                          be evolved.
	 * @param rng               The source of randomness used by all stochastic
	 *                          processes (including evolutionary operators and
	 *                          selection strategies).
	 * @param threadFactory     The factory used to create worker threads for this
	 *                          evolution engine. This allows clients control over
	 *                          priorities and other characteristics. This is
	 *                          particularly useful for fine-tuning resource usage
	 *                          when running embedded inside another application
	 *                          such as a servlet container.
	 */
	private CustomEvolutionEngine(CandidateFactory<T> candidateFactory, EvolutionaryOperator<T> evolutionScheme,
			FitnessEvaluator<? super T> fitnessEvaluator, SelectionStrategy<? super T> selectionStrategy, Random rng,
			ThreadFactory threadFactory) {
		super(candidateFactory, evolutionScheme, fitnessEvaluator, selectionStrategy, rng);

		this.fitnessEvaluator = fitnessEvaluator;

		threadPool = new ThreadPoolExecutor(PROCESSOR_COUNT, PROCESSOR_COUNT, 60, TimeUnit.SECONDS,
				new LinkedBlockingQueue<Runnable>(), threadFactory);
		int noOfThreads = threadPool.prestartAllCoreThreads();
		System.out.println("Custom evolution engine initialised with " + noOfThreads + " threads.");
	}

	/**
	 * Takes a population, assigns a fitness score to each member and returns the
	 * members with their scores attached, sorted in descending order of fitness
	 * (descending order of fitness score for natural scores, ascending order of
	 * scores for non-natural scores).
	 * 
	 * @param population The population to evaluate (each candidate is assigned a
	 *                   fitness score).
	 * @return The evaluated population (a list of candidates with attached fitness
	 *         scores).
	 */
	protected List<EvaluatedCandidate<T>> evaluatePopulation(List<T> population) {
		List<EvaluatedCandidate<T>> evaluatedPopulation = new ArrayList<EvaluatedCandidate<T>>(population.size());

		// Divide the required number of fitness evaluations equally among the
		// available processors and coordinate the threads so that we do not
		// proceed until all threads have finished processing.
		try {
			// Make sure that we don't try to use more threads than we have candidates.
			int threadUtilisation = Math.min(PROCESSOR_COUNT, population.size());

			int subListSize = (int) Math.round((double) population.size() / threadUtilisation);
			List<T> unmodifiablePopulation = Collections.unmodifiableList(population);
			List<Callable<List<EvaluatedCandidate<T>>>> tasks = new ArrayList<Callable<List<EvaluatedCandidate<T>>>>(
					threadUtilisation);
			for (int i = 0; i < threadUtilisation; i++) {
				int fromIndex = i * subListSize;
				int toIndex = i < threadUtilisation - 1 ? fromIndex + subListSize : population.size();
				List<T> subList = population.subList(fromIndex, toIndex);
				tasks.add(new FitnessEvalutationTask(subList, unmodifiablePopulation));
			}
			// Submit tasks for execution and wait until all threads have finished fitness
			// evaluations.
			List<Future<List<EvaluatedCandidate<T>>>> results = threadPool.invokeAll(tasks);
			for (Future<List<EvaluatedCandidate<T>>> result : results) {
				evaluatedPopulation.addAll(result.get());
			}
			assert evaluatedPopulation.size() == population.size() : "Wrong number of evaluated candidates.";
		} catch (ExecutionException ex) {
			ex.printStackTrace();
			throw (Error) new InternalError("Fitness evaluation task execution failed.").initCause(ex);
		} catch (InterruptedException ex) {
			// Restore the interrupted status, allows methods further up the call-stack
			// to abort processing if appropriate.
			Thread.currentThread().interrupt();
		}

		// Sort candidates in descending order according to fitness.
		if (fitnessEvaluator.isNatural()) // Descending values for natural fitness.
		{
			Collections.sort(evaluatedPopulation, Collections.reverseOrder());
		} else // Ascending values for non-natural fitness.
		{
			Collections.sort(evaluatedPopulation);
		}
		return evaluatedPopulation;
	}

	/**
	 * Callable task for performing parallel fitness evaluations.
	 */
	private final class FitnessEvalutationTask implements Callable<List<EvaluatedCandidate<T>>> {
		private final List<T> candidates;
		private final List<T> population;

		/**
		 * Creates a task for performing fitness evaluations.
		 * 
		 * @param candidates The candidates to evaluate. This is a subset of
		 *                   {@code population}.
		 * @param population The entire current population. This will include all of the
		 *                   candidates to evaluate along with any other individuals
		 *                   that are not being evaluated by this task.
		 */
		public FitnessEvalutationTask(List<T> candidates, List<T> population) {
			this.candidates = candidates;
			this.population = population;
		}

		public List<EvaluatedCandidate<T>> call() {
			List<EvaluatedCandidate<T>> evaluatedCandidates = new ArrayList<EvaluatedCandidate<T>>(candidates.size());
			for (T candidate : candidates) {

				//runSimulation(candidate);
	
				evaluatedCandidates
						.add(new EvaluatedCandidate<T>(candidate, fitnessEvaluator.getFitness(candidate, population)));
			}
			return evaluatedCandidates;
		}


	}
}