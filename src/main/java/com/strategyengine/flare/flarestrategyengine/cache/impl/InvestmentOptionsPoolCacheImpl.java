package com.strategyengine.flare.flarestrategyengine.cache.impl;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.stereotype.Service;

import com.strategyengine.flare.flarestrategyengine.cache.InvestmentOptionsPoolCache;
import com.strategyengine.flare.flarestrategyengine.model.Investment;
import com.strategyengine.flare.flarestrategyengine.model.InvestmentOptionsPool;

@Service
public class InvestmentOptionsPoolCacheImpl implements InvestmentOptionsPoolCache {

	// TODO persist current state out of the app so we can restart with the current
	// state. Maybe imply
	private InvestmentOptionsPool pool = InvestmentOptionsPool.builder().build();

	@Override
	public InvestmentOptionsPool addToPool(Investment investment) {

		List<Investment> matchingInvestmentsInPool = pool.getInvestments().stream()
				.filter(i -> i.getType().equals(investment.getType()) && i.getAsset().equals(investment.getAsset()))
				.collect(Collectors.toList());

		// remove investments that are already in the pool for the same invested assets
		// so we have the latest yield in the current state
		pool.getInvestments().removeAll(matchingInvestmentsInPool);

		pool.getInvestments().add(investment);

		return pool;

	}

	@Override
	public List<Investment> getInvestmentOptions() {
		return pool.getInvestments();
	}
}
