package com.strategyengine.flare.flarestrategyengine.service.impl;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.google.common.collect.Lists;
import com.strategyengine.flare.flarestrategyengine.cache.AssetValueCache;
import com.strategyengine.flare.flarestrategyengine.cache.InvestmentOptionsPoolCache;
import com.strategyengine.flare.flarestrategyengine.cache.WinningStrategyCache;
import com.strategyengine.flare.flarestrategyengine.learn.EvolverService;
import com.strategyengine.flare.flarestrategyengine.learn.FlarePredictorCreature;
import com.strategyengine.flare.flarestrategyengine.model.Investment;
import com.strategyengine.flare.flarestrategyengine.service.InvestmentMachineLearnService;

@Service
public class InvestmentMachineLearnServiceImpl implements InvestmentMachineLearnService {

	@Autowired
	private AssetValueCache assetValueCache;

	@Autowired
	private InvestmentOptionsPoolCache investmentOptionsPoolCache;

	@Autowired
	private WinningStrategyCache winningStrategyCache;

	@Autowired
	private EvolverService evolverService;

	private boolean running = false;
	private boolean rerunPending = false;

	@Override
	public List<Investment> getWinningInvestments() {
		Optional<FlarePredictorCreature> winnerOpt = winningStrategyCache.getWinningStrategy();

		return winnerOpt.isPresent() ? winnerOpt.get().getStrategy().getInvestmentOptionsPriotized()
				: Lists.newArrayList();
	}

	@Override
	public void determineBestInvestments() {

		if (running) {
			rerunPending = true;
			return;
		}
		try {

			FlarePredictorCreature winner = evolverService.evolveCreature(
					investmentOptionsPoolCache.getInvestmentOptions(), assetValueCache.getAssetValues());

			winningStrategyCache.storeWinningStrategy(winner);

		} finally {
			running = false;
			try {
				if (rerunPending) {
					determineBestInvestments();
				}
			} finally {
				rerunPending = false;
			}
		}
	}
}
