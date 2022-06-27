package com.rikhi.experimentation;


public class KrakenTickerAPIPerfCollector extends KrakenAPIWithArgsPerfCollector{
	
	public KrakenTickerAPIPerfCollector() {
		super();
		perfMetrices=new PerformanceMetricsData();
		perfMetrices.metricsKeyName="KrakenTickerAPI";
		perfMetrices.numberOfConcurrentClients=MAX_NUM_CONCURRENT_CLIENTS;
		symbols = new String[]{"XBTUSD", "XBTGBP"};
		apiString="https://api.kraken.com/0/public/Ticker?pair=";
	}
	
	@Override
	public void collect() {
		super.collect();
	}
	
}
