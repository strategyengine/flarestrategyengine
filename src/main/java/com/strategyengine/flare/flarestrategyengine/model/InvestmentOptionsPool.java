package com.strategyengine.flare.flarestrategyengine.model;

import java.util.ArrayList;
import java.util.List;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

/**
 * the current state of investment options available
 * @author barry
 *
 */
@Builder
@EqualsAndHashCode
@ToString
@Getter
@NoArgsConstructor
@AllArgsConstructor
@Setter
public class InvestmentOptionsPool {

	List<Investment> investments;
	
	public List<Investment> getInvestments(){
		
		if(investments == null) {
			investments = new ArrayList<>();
		}
		return investments;
	}
}
