package com.strategyengine.flare.flarestrategyengine.service;

import com.strategyengine.flare.flarestrategyengine.model.AssetValueEvent;
import com.strategyengine.flare.flarestrategyengine.model.FlareInvestmentEvent;


public interface EventService {

	/**
	 * updates the state of the investment pool with the latest assets and yields available to earn $$
	 * @param event
	 */
	void processEvent(FlareInvestmentEvent event);

	/**
	 * updates the current USD value per asset pool
	 * @param event
	 */
	void processEvent(AssetValueEvent event);

}
