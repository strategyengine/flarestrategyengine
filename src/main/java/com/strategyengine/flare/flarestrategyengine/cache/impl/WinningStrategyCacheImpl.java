package com.strategyengine.flare.flarestrategyengine.cache.impl;

import java.util.Optional;

import org.springframework.stereotype.Service;

import com.strategyengine.flare.flarestrategyengine.cache.WinningStrategyCache;
import com.strategyengine.flare.flarestrategyengine.learn.FlarePredictorCreature;

@Service
public class WinningStrategyCacheImpl implements WinningStrategyCache {

	private FlarePredictorCreature winningStrategy;
	
	@Override
	public Optional<FlarePredictorCreature> getWinningStrategy() {
		return Optional.of(winningStrategy);
	}
	
	@Override
	public void storeWinningStrategy(FlarePredictorCreature winningStrategy) {
		this.winningStrategy = winningStrategy;
	}

}
