package com.rikhi.experimentation;


public class KrakenOHLCAPIPerfCollector extends KrakenAPIWithArgsPerfCollector{
	
	public KrakenOHLCAPIPerfCollector() {
		super();
		perfMetrics=new PerformanceMetricsData();
		perfMetrics.metricsKeyName="KrakenOHLCAPI";
		perfMetrics.numberOfConcurrentClients=MAX_NUM_CONCURRENT_CLIENTS;
		symbols = new String[]{"XBTUSD", "XBTGBP"};
		apiString="https://api.kraken.com/0/public/OHLC?pair=";
		featureName="KrakenOHLCAPI";
	}
	
	@Override
	public void collect() {
		super.collect();
	}
	
}
