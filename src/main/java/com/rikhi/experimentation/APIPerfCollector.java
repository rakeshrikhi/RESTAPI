package com.rikhi.experimentation;

public abstract class APIPerfCollector implements PerformanceDataCollector{
	
	public String featureName;
	public String apiString;
	public int MAX_NUM_CONCURRENT_CLIENTS = 4;
	public int MAX_RUN_TIME_MINUTES = 1;
	public long WAIT_TIME_BETWEEN_REQUESTS_MS = 1000;//millisec
	
	public boolean isLogging=true;
	

	public PerformanceMetricsData perfMetrics;
	
	
	public void printMessage(String message) {
		if(isLogging)
		System.out.println(Thread.currentThread().getName()+"--"+message);
	}

}
