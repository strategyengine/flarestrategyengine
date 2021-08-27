package com.strategyengine.flare.flarestrategyengine.cache;

import java.util.Map;

import com.strategyengine.flare.flarestrategyengine.model.AssetValue;
import com.strategyengine.flare.flarestrategyengine.model.AssetValueEvent;

public interface AssetValueCache {

	void addToAssets(AssetValueEvent event);

	Map<String, AssetValue> getAssetValues();

}
