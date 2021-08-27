package com.strategyengine.flare.flarestrategyengine.learn;

import java.util.List;
import java.util.Map;

import com.strategyengine.flare.flarestrategyengine.model.AssetValue;
import com.strategyengine.flare.flarestrategyengine.model.Investment;

public interface EvolverService {

	FlarePredictorCreature evolveCreature(List<Investment> investmentOptionsAvailable,
			Map<String, AssetValue> assetValues);

}
