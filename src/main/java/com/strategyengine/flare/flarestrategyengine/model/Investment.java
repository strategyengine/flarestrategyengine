package com.strategyengine.flare.flarestrategyengine.model;

import java.util.List;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.NonNull;
import lombok.Setter;
import lombok.ToString;

/**
 * 10000 YFLR = 1 YFIN Supply yFLR 110,000,000 Supply yFIN 11,000
 * 
 * FLR 10% inflation / year
 * 
 * @author barry
 * 
 *         Stage 1: 
 *         1.Convert XRP to fXRP and put into FlareFarm earning yFIN
 *         2.Put all FLR into FlareFarm earning yFIN 
 *         3. Delegate vote to FTSO.EU
 *         and earn rewards in FLR. Put this FLR in FlareFarm earning yFIN 
 *         4. Convert DFLR to YFLR and put in FlareFarm earning yFIN 
 *         5. Put all yFIN into FlareMutual to earn more yFIN, repeat with earned yFIN 
 *         6. Put the share of the fee pool earned into FlareLoans earning yFIN as
 *         well as an APR on the loan 
 *         7. Put the APR earned back into
 *         FlareLoans, repeat with earnings 
 *         8. Put the yFIN earned into
 *         FlareMutual ** All yFIN in this scenario ends up in FlareMutual
 * 
 *         Stage 2 
 *         1. Follow Stage 1, except the fXRP is moved from FlareFarm to
 *         FlareLoans to grow fXRP holdings
 * 
 *         Stage 3 
 *         1. Transfer fXRP back into XRP on XRPL and put in CoinLoan to
 *         earn an APR 
 *         2. Remove FLR from FlareFarm and become an "Agent" on
 *         FlareNetworks, earning more XRP in the process 
 *         3. Depending on
 *         returns available:
 *          --a) yFLR can stay in FlareFarm - with yFIN going
 *         into FlareMutual to earn more yFIN and the fee pool there 
 *         --b) or put yFLR directly in FlareMutual to earn yFIN and a share of the fee
 *         pool. 
 *         
 *       Flare Farm: participate in governance staking and voting to earn APY from APY
		 cloud (earn yFLR, yFIN & f-assets)
		 FlareLoan: loan assets earn f-assets and yFIN
		 Flare Mutual: provide and purchase coverage: Earn F-assets and yFIN
		 APY Cloud - earn FLR, yFLR, yFIN and f-assets
		 During stressful times in the ecosystem you can lock up yFLR for BFLR which
		 is bonded for a time period and you lock in a yield

 */	
	@Builder
	@EqualsAndHashCode
	@ToString
	@Getter
	@NoArgsConstructor
	@AllArgsConstructor
	@Setter
	public class Investment {

		@NonNull
		private String asset;
		//investing in a single asset can return multiple assets
		@NonNull
		private List<AssetYield> assetYields;
		@NonNull
		private InvestmentType type;
}
