package com.strategyengine.flare.flarestrategyengine.model;

import java.util.Date;
import java.util.List;

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
public class AssetValueEvent {

	@NonNull
	private String eventId;
	@NonNull
	private Date eventTime;
	@NonNull
	private List<AssetValue> assetValues;
	
}
