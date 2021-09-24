# Strategy Engine Whitepaper  
- Version 09242021
- Change tracking [https://github.com/strategyengine/flarestrategyengine](https://github.com/strategyengine/flarestrategyengine)

#Token Names
- Strategy Engine Payment Token (Flare Investments)
- Strategy Engine Payment Token (Songbird Investments)

#Currency Codes
- FSE
- SSE

#Supply
- 100,000 FSE
- 100,000 SSE

#Issuing Addresses
- Currency Name: FSE 
    - rs1MKY54miDtMFEGyNuPd3BLsXauFZUSrj
- Currency Name: SSE 
    - rMDQTunsjE32sAkBDbwixpWr8TJdN5YLxu 

#Description
The strategy engine system (strategyengine) is comprised of a set of applications, services and/or decentralized contracts to facilitate automated investments in a ML (machine learning) determined optimal strategy.  The strategyengine components integrate and interoperate such that event streams supply real time data to the machine learning component which runs simulations determining the most fit strategy.  Fitness, weighted by investment yield, referring to the ability of any strategy to be passed on and combined in future generations of strategies.  Less fit strategies are pruned while the most fit strategy at any time can be purchased using the payment tokens.  Strategies previously determined to have high fitness can become unfit and ultimately pruned as investment yields and asset prices change over time.  

Separate instances of the strategyengine will manage a portfolios of investments focused in the Flare ecosystem or Songbird ecosystem.   A portion of payment tokens will be used to prefund the portfolio management components to allow for purchasing the initial strategies for any new lifecycle. Investment allocations within a portfolio are solely based on the latest purchased strategy.  A configured percentage of the investment profit is apportioned to be exchanged for payment tokens for Flare ecosystem strategies or Songbird ecosystem strategies.  A strategyengine lifecycle begins with prefunding of a new investment portfolio and an initial purchase and allocation of funds within that portfolio.  An iteration within a lifecycle is defined as the period that one strategy is applied to a portfolio.  The iteration interval and percent profit allowed for payment token purchases are locked for a given lifecycle but flexible between lifecycles.  An iteration is outdated when a single strategy has been running beyond the iteration interval and becomes spoiled if the strategy runs beyond four intervals.  An outdated interval triggers the need apply a new strategy to the portfolio. When no payment tokens can be acquired by the system and the current strategy is determined to be spoiled, the system will attempt to reallocate the portfolio to XRP.  When the payment token is FSE or SSE, the entire portfolio is distributed to the payment token holders pro rata.



#Distribution

- FSE token distributions
    - July through September 16 - 70000 FSE (multiple drops to trustline holders)
    - September: 10000 FSE (one scheduled drop)
    - October: 5000 FSE (one scheduled drop)
    - November: 3000 FSE (one scheduled drop)
    - December: 2000 FSE (one scheduled drop)
    - January: 1000 FSE (one scheduled drop)
    - February: 1000 FSE (one scheduled drop)
    - 92% of tokens will be distributed to trustline holders by the end of the airdrop schedule.  The remainder will be used for activities including but not limited to initiating new lifecycles of the strategy engine, purchase of promotional media and resources directly related to running the strategyengine (cloud, hardware & software)


- SSE token distributions are triggered by the need for component testing
    - September 19000 SSE (multiple drops)
    - Future - TBD based on QA need of the strategyengine components and evolution of the Songbird ecosystem
    
    
#More information
For more information see [https://www.strategyengine.one](https://www.strategyengine.one)

