package com.strategyengine.flare.flarestrategyengine.service;

import java.util.List;

import com.strategyengine.flare.flarestrategyengine.model.Investment;

public interface InvestmentMachineLearnService {

	void determineBestInvestments();

	List<Investment> getWinningInvestments();

}
