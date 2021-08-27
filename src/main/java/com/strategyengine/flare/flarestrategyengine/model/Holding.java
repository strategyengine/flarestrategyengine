package com.strategyengine.flare.flarestrategyengine.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.NonNull;
import lombok.Setter;
import lombok.ToString;

@Builder
@EqualsAndHashCode
@ToString
@Getter
@NoArgsConstructor
@AllArgsConstructor
@Setter
public class Holding {

	@NonNull
	private String asset;
	@NonNull
	private Double amount;
	@NonNull
	private InvestmentType investmentType;
}
