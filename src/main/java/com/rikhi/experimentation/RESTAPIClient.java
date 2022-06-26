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

	private static String[] tickers = { "XBTUSD", "XBTGBP" };
	private static final int MAX_NUM_CONCURRENT_CLIENTS = 4;
	private static final int MAX_RUN_TIME_MINUTES = 1;
	
	private static boolean isLogging=true;
	
	public static void main(String[] args) {

		ExecutorService executorService = Executors.newFixedThreadPool(MAX_NUM_CONCURRENT_CLIENTS);
		List<Callable<Long>> taskList = new ArrayList<Callable<Long>>(MAX_NUM_CONCURRENT_CLIENTS);
		List<Future<Long>> futures;
		Long avgForAPI=0L;
		int tempNumClients=1;

		for (int i = 1; i <= MAX_NUM_CONCURRENT_CLIENTS; i++) {
			taskList.add(runTickerDataAPIConcurrently);
		}

		try {
			futures = executorService.invokeAll(taskList);
			for (Future<Long> future : futures) {
				printMessage(future.get().toString());
				avgForAPI=((avgForAPI*tempNumClients)+future.get().longValue())/tempNumClients;
				tempNumClients++;
			}

		} catch (InterruptedException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		} catch (ExecutionException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		printMessage("runTickerDataAPIConcurrently Avg:"+avgForAPI);
		
		//executorService.shutdown();

	}

	private static Callable<Long> runTickerDataAPIConcurrently = () -> {
		// Time to run the loop
		long endRunTime = System.nanoTime() + TimeUnit.NANOSECONDS.convert(MAX_RUN_TIME_MINUTES, TimeUnit.MINUTES);
		//Total requests from a single client/thread
		int requestCount = 0;
		//Average of response time for a single client/thread
		long avgGetTickerData = 0L;

		HttpClient httpClient = HttpClientBuilder.create().build();
		//run for MAX_RUN_TIME_MINUTES minutes
		while (System.nanoTime() < endRunTime) {

			//ToDo: Tried to avoid creating new client every time
			//The current program was hanging after 2 requests from the same client
			//HttpClient httpClient = HttpClientBuilder.create().build();
			long start = System.currentTimeMillis();

			//trying with 2 tickers, even-odd positions
			//incrementing the total requests
			String ticker = tickers[requestCount % tickers.length];
			//invoke the service
			int response = GetTickerData(httpClient, ticker);
			long end = System.currentTimeMillis();
			long timeTaken = end - start;
			
			if(response==200) { //storing only successful request stats
				++requestCount;
				avgGetTickerData = (avgGetTickerData * (requestCount - 1) + timeTaken) / requestCount;
				printMessage(requestCount + "--" + ticker + "--" + response + "--" + (timeTaken) + "ms"
						+ " running average:" + avgGetTickerData);
			}
			
			try {
				//do not overload the server, sleep for 1 sec after everyrequest
				Thread.sleep(1000);
			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				printMessage(Thread.currentThread().getName()+":Exception-"+e.getMessage());
			}
		}
		return avgGetTickerData;
	};
	
	public static void printMessage(String message) {
		if(isLogging)
		System.out.println(Thread.currentThread().getName()+"--"+message);
	}

	public static int GetTickerData(HttpClient httpClient, String ticker) {

		int statusCode = -1;
		try {
			printMessage("Preparing Ticker Request for:" + ticker);
			HttpGet getRequest = new HttpGet("https://api.kraken.com/0/public/Ticker?pair=" + ticker);
			
			HttpResponse response = httpClient.execute(getRequest);
			printMessage("Sent Ticker Request for:" + ticker);

			statusCode = response.getStatusLine().getStatusCode();
			printMessage("Received Ticker Response for:" + ticker + " as:" + statusCode);

			if (statusCode != 200) {
				printMessage("Failed : Ticker:"+ticker+" Response HTTP error code : " + statusCode);
			}

		} catch (ClientProtocolException e) {
			printMessage("Failed : ClientProtocolException : " + e.getMessage());

		} catch (IOException e) {
			printMessage("Failed : IOException : " + e.getMessage());
		}

		return statusCode;
	}
}