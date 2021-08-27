package com.strategyengine.flare.flarestrategyengine.cache.impl;

import java.math.BigDecimal;
import java.util.HashMap;
import java.util.Map;

import javax.annotation.PostConstruct;

import org.springframework.stereotype.Service;

import com.google.common.collect.ImmutableMap;
import com.strategyengine.flare.flarestrategyengine.cache.AssetValueCache;
import com.strategyengine.flare.flarestrategyengine.model.AssetValue;
import com.strategyengine.flare.flarestrategyengine.model.AssetValueEvent;

@Service
public class AssetValueCacheImpl implements AssetValueCache{

	private Map<String, AssetValue> assetValues = new HashMap<>();
	
	@PostConstruct
	public void initialize() {
		
		assetValues.put("FXRP", AssetValue.builder().asset("FXRP").usdValue(new BigDecimal(".00000001")).build());
		assetValues.put("AUR", AssetValue.builder().asset("AUR").usdValue(new BigDecimal(".00000001")).build());
		assetValues.put("FDOGE", AssetValue.builder().asset("FDOGE").usdValue(new BigDecimal(".00000001")).build());
		assetValues.put("FLR", AssetValue.builder().asset("FLR").usdValue(new BigDecimal(".00000001")).build());
		assetValues.put("YFIN", AssetValue.builder().asset("YFIN").usdValue(new BigDecimal(".00000001")).build());
		assetValues.put("YFLR", AssetValue.builder().asset("YFLR").usdValue(new BigDecimal(".00000001")).build());
		assetValues.put("FLTC", AssetValue.builder().asset("FLTC").usdValue(new BigDecimal(".00000001")).build());		
	}
	
	@Override
	public void addToAssets(AssetValueEvent event) {
		event.getAssetValues().stream().forEach(a-> assetValues.put(a.getAsset(), a));
	}
	
	@Override
	public Map<String, AssetValue> getAssetValues(){
		
		return ImmutableMap.copyOf(assetValues);
	}

}
