package com.strategyengine.flare.flarestrategyengine.cache;

import java.util.Optional;

import com.strategyengine.flare.flarestrategyengine.learn.FlarePredictorCreature;

public interface WinningStrategyCache {

	Optional<FlarePredictorCreature> getWinningStrategy();

	void storeWinningStrategy(FlarePredictorCreature winningStrategy);

}
