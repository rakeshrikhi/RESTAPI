package com.rikhi.experimentation;

import org.apache.http.HttpResponse;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.HttpClientBuilder;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.Callable;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;
import java.util.concurrent.TimeUnit;

/**
 * @author Rakesh Rikhi
 * 
 */

public class RESTAPIClient {
	public static void main(String[] args) {
		//PerformanceDataCollector tickerCollector=new KrakenTickerAPIPerfCollector();
		//tickerCollector.collect();
		
		//PerformanceDataCollector tickerCollector=new KrakenAssetsAPIPerfCollector();
		//tickerCollector.collect();
		
		PerformanceDataCollector ohlcCollector=new KrakenOHLCAPIPerfCollector();
		ohlcCollector.collect();
	}
}