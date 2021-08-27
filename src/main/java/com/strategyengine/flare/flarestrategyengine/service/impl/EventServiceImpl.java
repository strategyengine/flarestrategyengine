package com.strategyengine.flare.flarestrategyengine.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.strategyengine.flare.flarestrategyengine.cache.AssetValueCache;
import com.strategyengine.flare.flarestrategyengine.cache.InvestmentOptionsPoolCache;
import com.strategyengine.flare.flarestrategyengine.model.AssetValueEvent;
import com.strategyengine.flare.flarestrategyengine.model.FlareInvestmentEvent;
import com.strategyengine.flare.flarestrategyengine.service.EventService;
import com.strategyengine.flare.flarestrategyengine.service.InvestmentMachineLearnService;

@Service
public class EventServiceImpl implements EventService {
	
	@Autowired
	private InvestmentOptionsPoolCache investmentOptionsPoolCache;
	
	@Autowired
	private InvestmentMachineLearnService investmentMachineLearnService;
	
	@Autowired
	private AssetValueCache assetValueCache;
	
	@Override
	public void processEvent(FlareInvestmentEvent event) {
		event.getInvestments().stream().forEach(i -> investmentOptionsPoolCache.addToPool(i));
		
		//TODO persist events to something we can chart.  Maybe imply
		
		investmentMachineLearnService.determineBestInvestments();
	}

	@Override
	public void processEvent(AssetValueEvent event) {
		
		assetValueCache.addToAssets(event);
		
		investmentMachineLearnService.determineBestInvestments();
		
	}

}
