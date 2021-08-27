package com.strategyengine.flare.flarestrategyengine.learn;

import com.strategyengine.flare.flarestrategyengine.model.Portfolio;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

/**
 *
 * @author barry
 */
@Builder
@EqualsAndHashCode
@ToString
@Getter
@NoArgsConstructor
@AllArgsConstructor
@Setter
public class FlarePredictorCreature {

	private Portfolio portfolio;
	private EvolveStrategy strategy;
	private Double value;
 //I think we need the assets? maybe a static portfolio with a basket of assets?

}
