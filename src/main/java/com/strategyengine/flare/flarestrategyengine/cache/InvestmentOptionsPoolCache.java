package com.strategyengine.flare.flarestrategyengine.cache;

import java.util.List;

import com.strategyengine.flare.flarestrategyengine.model.Investment;
import com.strategyengine.flare.flarestrategyengine.model.InvestmentOptionsPool;

public interface InvestmentOptionsPoolCache {

	InvestmentOptionsPool addToPool(Investment investments);

	List<Investment> getInvestmentOptions();

}
