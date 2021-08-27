package com.strategyengine.flare.flarestrategyengine.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.NonNull;
import lombok.Setter;
import lombok.ToString;

/**
 * example - this returns 4% fXRP or 5% yFLR
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
public class AssetYield {

	@NonNull
	private String asset;
	@NonNull
	private Double yield;
}
