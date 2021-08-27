package com.strategyengine.flare.flarestrategyengine.learn;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import com.google.common.collect.ImmutableList;
import com.strategyengine.flare.flarestrategyengine.model.Investment;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

/**
 * This is the strategy that is used by the evolution components and populated with random values to compete in a bucket of 
 *  EvolveStrategies.  Winning EvolveStrategies are bred and tested in order to find the most profitable strategy.
 * 
 * @author bknapp
 *
 */
@Builder
@EqualsAndHashCode
@ToString
@Getter
@NoArgsConstructor
@AllArgsConstructor
@Setter
public class EvolveStrategy {


	private List<Investment> investmentOptionsAvailable;
	private List<Investment> investmentOptionsPriotized;
	

	public EvolveStrategy(List<Investment> investmentOptions) {
		this.investmentOptionsAvailable = investmentOptions;
	}

	public void init(Random rng) {
		
		List<Investment> options = new ArrayList<>();
		while(!investmentOptionsAvailable.isEmpty()) {
			options.add(investmentOptionsAvailable.remove(rng.nextInt(investmentOptionsAvailable.size())));
		}
		investmentOptionsPriotized = ImmutableList.copyOf(options);
	}
}
