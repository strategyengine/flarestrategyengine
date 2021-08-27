package com.strategyengine.flare.flarestrategyengine.rest;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.strategyengine.flare.flarestrategyengine.model.AssetValueEvent;
import com.strategyengine.flare.flarestrategyengine.model.FlareInvestmentEvent;
import com.strategyengine.flare.flarestrategyengine.model.Investment;
import com.strategyengine.flare.flarestrategyengine.model.Portfolio;
import com.strategyengine.flare.flarestrategyengine.service.EventService;
import com.strategyengine.flare.flarestrategyengine.service.impl.InvestmentMachineLearnServiceImpl;

import lombok.extern.log4j.Log4j2;

@Log4j2
@RestController
public class FlareDataController {

	@Autowired
	private EventService eventService;
	
	@Autowired
	private InvestmentMachineLearnServiceImpl investmentMachineLearnServiceImpl;
	
	@RequestMapping(value = "/api/portfolio", method = RequestMethod.POST)
	public String flare(Portfolio portfolio) {
		return "custom";
	}

	@RequestMapping(value = "/api/event/investment/yield", method = RequestMethod.POST)
	public void postInvestmentEvent(@RequestBody FlareInvestmentEvent event) {
		log.debug("Investment received: "+ event);
		eventService.processEvent(event);
	}
	
	@RequestMapping(value = "/api/event/asset/usdvalue", method = RequestMethod.POST)
	public void postAssetValueEvent(@RequestBody AssetValueEvent event) {
		log.debug("AssetValueEvent received: "+ event);
		eventService.processEvent(event);
	}
	
	//get the winning strategy of investment options
	@RequestMapping(value = "/api/strategy/winning", method = RequestMethod.GET)
	public List<Investment> getWinningInvestments() {
		return investmentMachineLearnServiceImpl.getWinningInvestments();
	}
}