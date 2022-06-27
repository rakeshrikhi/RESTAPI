package com.rikhi.experimentation;


public class KrakenAssetsAPIPerfCollector extends KrakenAPINoArgsPerfCollector{
	
	public KrakenAssetsAPIPerfCollector() {
		super();
		perfMetrices=new PerformanceMetricsData();
		perfMetrices.metricsKeyName="KrakenSsetsAPI";
		perfMetrices.numberOfConcurrentClients=MAX_NUM_CONCURRENT_CLIENTS;
		apiString="https://api.kraken.com/0/public/Assets";
	}
	
	@Override
	public void collect() {
		super.collect();
	}
	
}