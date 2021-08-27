package com.strategyengine.flare.flarestrategyengine.learn;

import java.math.BigDecimal;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import com.google.common.collect.Lists;
import com.strategyengine.flare.flarestrategyengine.model.AssetValue;
import com.strategyengine.flare.flarestrategyengine.model.AssetYield;
import com.strategyengine.flare.flarestrategyengine.model.Investment;
import com.strategyengine.flare.flarestrategyengine.model.InvestmentType;

public class EvolverServiceImplTestRandomAssetYeildsPrices {

	private EvolverServiceImpl sut;
	
	
	@BeforeEach
	public void setup() {
		sut = new EvolverServiceImpl();
	}
	
	@Test
	public void testEvolve() {
		
		FlarePredictorCreature winner = sut.evolveCreature(investmentOptionsAvailable(), assetValues());
		
		System.out.println(winner);
	}

	private Map<String, AssetValue> assetValues() {
		Map<String, AssetValue> m = new HashMap<>();
		m.put("FXRP", AssetValue.builder().asset("FXRP").usdValue(new BigDecimal(".96")).build());
		m.put("AUR", AssetValue.builder().asset("AUR").usdValue(new BigDecimal("1")).build());
		m.put("FDOGE", AssetValue.builder().asset("FDOGE").usdValue(new BigDecimal(".27")).build());
		m.put("FLR", AssetValue.builder().asset("FLR").usdValue(new BigDecimal(".4")).build());
		m.put("YFIN", AssetValue.builder().asset("YFIN").usdValue(new BigDecimal("2")).build());
		m.put("YFLR", AssetValue.builder().asset("YFLR").usdValue(new BigDecimal(".05")).build());
		m.put("FLTC", AssetValue.builder().asset("FLTC").usdValue(new BigDecimal(".03")).build());		
			
		
		//there is a calculation here to determine this value.  Must be a based on a factor of both
		//m.put("LP_FLR_YUSD, AssetValue.builder().asset("LP_YFLR_YUSD).usdValue(new BigDecimal(".96")).build());

		return m;
	}
	
	private Investment liquidityPool(String asset) {
		return investment(asset,  InvestmentType.FLAREX_LIQUIDITY_POOL, 
				Lists.newArrayList(
						AssetYield.builder().asset(asset).yield(Math.random()).build(),
				        AssetYield.builder().asset("YFLR").yield(Math.random()).build()));
	}

	private Investment flareFarm(String asset) {
		return investment(asset,  InvestmentType.FLARE_FARM, 
				Lists.newArrayList(
						AssetYield.builder().asset("YFIN").yield(Math.random()).build()));
	}
	
	private Investment flareLoan(String asset) {
		return investment(asset,  InvestmentType.FLARE_LOAN, 
				Lists.newArrayList(
						AssetYield.builder().asset(asset).yield(Math.random()).build(),
				        AssetYield.builder().asset("YFIN").yield(Math.random()).build()));
	}
	
	private Investment flareMutual(String asset) {
		//When this is released you may earn the asset as well.  Need to double check TODO
		return investment(asset,  InvestmentType.FLARE_MUTUAL, 
				Lists.newArrayList(
				        AssetYield.builder().asset("YFIN").yield(Math.random()).build()));
	}
	
	private Investment flareGovernance(String asset) {
		return investment(asset,  InvestmentType.FLARE_LOAN, 
				Lists.newArrayList(
						AssetYield.builder().asset("YFLR").yield(Math.random()).build(),
						AssetYield.builder().asset("YUSD").yield(Math.random()).build(),
				        AssetYield.builder().asset("YFIN").yield(Math.random()).build()));
	}
	
	private Investment probity(String asset) {
		return investment(asset,  InvestmentType.PROBITY, 
				Lists.newArrayList(
						AssetYield.builder().asset("AUR").yield(Math.random()).build()));
	}
	
	private List<Investment> investmentOptionsAvailable() {
		

		return Lists.newArrayList(

				//FlareX Liquidity Pools
				liquidityPool("FXRP"),
				liquidityPool("FDOGE"),
				liquidityPool("FLTC"),
				liquidityPool("FXLM"),
				liquidityPool("FLR"),
				
				//FlareX LP for YFLR only earns YFLR
				investment("YFLR",  InvestmentType.FLAREX_LIQUIDITY_POOL, 
						Lists.newArrayList(
						        AssetYield.builder().asset("YFLR").yield(Math.random()).build())),
				

				//mint LP token on FlareX liquidity pool and stake in FlareFarm
				flareFarm("LP_FDOGE_YUSD"),
				flareFarm("LP_FLTC_YUSD"),
				flareFarm("LP_FXLM_YUSD"),
				flareFarm("LP_FXRP_YUSD"),
				
				
				//FlareLoan earn f-asset and YFIN
				flareLoan("FDOGE"),
				flareLoan("FLTC"),
				flareLoan("FXLM"),
				flareLoan("FXRP"),
				flareLoan("FLR"),
				flareLoan("YFLR"),
				
				//FlareLoan for YFIN only earns YFIN
				investment("YFIN",  InvestmentType.FLARE_LOAN, 
						Lists.newArrayList(
						        AssetYield.builder().asset("YFIN").yield(Math.random()).build())),
				
				//flareMutual earn 
				flareMutual("FDOGE"),
				flareMutual("FLTC"),
				flareMutual("FXLM"),
				flareMutual("FXRP"),
				flareMutual("FLR"),
				flareMutual("YFLR"),
				
				
				//FlareGovernance 
				flareGovernance("YFIN"),
				flareGovernance("YFLR"),
				
				//Probity earns AUR or TCN
				probity("FLR"),
				probity("FDOGE"),
				probity("FLTC"),
				probity("FXLM"),
				probity("FXRP")
				
				
				);
	}

	private Investment investment(String asset, InvestmentType investmentType, List<AssetYield> assetYields) {
		return Investment.builder().asset(asset).type(investmentType)
				.assetYields(
						Lists.newArrayList(assetYields)).build();
	}
	
}
