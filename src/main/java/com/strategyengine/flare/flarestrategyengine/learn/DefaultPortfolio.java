package com.strategyengine.flare.flarestrategyengine.learn;

import java.util.List;

import com.google.common.collect.Lists;
import com.strategyengine.flare.flarestrategyengine.model.Holding;
import com.strategyengine.flare.flarestrategyengine.model.InvestmentType;
import com.strategyengine.flare.flarestrategyengine.model.Portfolio;

public class DefaultPortfolio {

	
	public static Portfolio create() {

		List<Holding> holdings = Lists.newArrayList(
				Holding.builder().investmentType(InvestmentType.UNINVESTED).asset("AUR").amount(100d).build(),
				Holding.builder().investmentType(InvestmentType.UNINVESTED).asset("FLR").amount(100d).build(),
				Holding.builder().investmentType(InvestmentType.UNINVESTED).asset("YFIN").amount(100d).build(),
				Holding.builder().investmentType(InvestmentType.UNINVESTED).asset("YFLR").amount(100d).build(),
				Holding.builder().investmentType(InvestmentType.UNINVESTED).asset("FLTC").amount(100d).build(),
				Holding.builder().investmentType(InvestmentType.UNINVESTED).asset("FXRP").amount(100d).build(),
				Holding.builder().investmentType(InvestmentType.UNINVESTED).asset("FXLM").amount(100d).build(),
				Holding.builder().investmentType(InvestmentType.UNINVESTED).asset("FDOGE").amount(100d).build(),
				Holding.builder().investmentType(InvestmentType.UNINVESTED).asset("AUR").amount(100d).build());

		return Portfolio.builder().holdings(holdings).build();
	}
}
