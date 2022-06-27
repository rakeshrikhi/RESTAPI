package com.rikhi.experimentation;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.Callable;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;
import java.util.concurrent.TimeUnit;

import org.apache.http.HttpResponse;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.HttpClientBuilder;

public abstract class KrakenAPIWithArgsPerfCollector extends APIPerfCollector{
	 
	protected String[] symbols;//to be passed as params to the API
	
	@Override
	public void collect() {
		ExecutorService executorService = Executors.newFixedThreadPool(MAX_NUM_CONCURRENT_CLIENTS);
		List<Callable<Double>> taskList = new ArrayList<Callable<Double>>(MAX_NUM_CONCURRENT_CLIENTS);
		List<Future<Double>> futures;
		Double avgForAPI=0d;
		int tempNumClients=0;

		for (int i = 1; i <= MAX_NUM_CONCURRENT_CLIENTS; i++) {
			taskList.add(runsymbolDataAPIConcurrently);
		}

		try {
			futures = executorService.invokeAll(taskList);
			for (Future<Double> future : futures) {
				printMessage(future.get().toString());
				tempNumClients++;
				avgForAPI=((avgForAPI*(tempNumClients-1))+future.get().longValue())/tempNumClients;
				
			}

		} catch (InterruptedException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		} catch (ExecutionException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		printMessage("runsymbolDataAPIConcurrently Avg:"+avgForAPI);
		perfMetrices.avgRequestExecTimePerClient=avgForAPI;
		perfMetrices.successfulResponsesPercent=
				(100*(perfMetrices.numberOfSuccessfulResponses.get()))/perfMetrices.numberOfRequests.get();
		
		try {
			executorService.awaitTermination(10, TimeUnit.SECONDS);//wait for 10 seconds
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		executorService.shutdownNow();
		
		printMessage("perfMetrices:"+perfMetrices);

	}
	
	public Callable<Double> runsymbolDataAPIConcurrently = () -> {
		
		
		// Max Time to run the loop
		long endRunTime = System.nanoTime() + TimeUnit.NANOSECONDS.convert(MAX_RUN_TIME_MINUTES, TimeUnit.MINUTES);
		//Total requests from a single client/thread
		int requestCount = 0;
		//Average of response time for a single client/thread
		double avgGetsymbolData = 0L;

		//HttpClient httpClient = HttpClientBuilder.create().build();
		//run for MAX_RUN_TIME_MINUTES minutes
		while (System.nanoTime() < endRunTime) {

			//ToDo: Tried to avoid creating new client every time
			//The current program was hanging after 2 requests from the same client
			HttpClient httpClient = HttpClientBuilder.create().build();
			long start = System.currentTimeMillis();

			//trying with 2 symbols, even-odd positions
			//incrementing the total requests
			String symbol = symbols[requestCount % symbols.length];
			//invoke the service
			int response = processRequest(httpClient, symbol);
			long end = System.currentTimeMillis();
			long timeTaken = end - start;
			perfMetrices.numberOfRequests.incrementAndGet();
			
			if(response==200) { //storing only successful request stats
				++requestCount;
				perfMetrices.numberOfSuccessfulResponses.incrementAndGet();
				avgGetsymbolData = (avgGetsymbolData * (requestCount - 1) + timeTaken) / requestCount;
				printMessage("Total Request Counter:"+perfMetrices.numberOfRequests.get()
							+"this Thread Request Counter:"+requestCount 
							+ "--" + symbol + "--" + response + "--" + (timeTaken) + "ms"
							+ " running average:" + avgGetsymbolData);
			}else {
				perfMetrices.numberOfUnsuccessfulResponses.incrementAndGet();
			}
			
			try {
				//do not overload the server, sleep for 1 sec after every request
				Thread.sleep(WAIT_TIME_BETWEEN_REQUESTS_MS);
			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				printMessage(Thread.currentThread().getName()+":Exception-"+e.getMessage());
			}
		}
		return avgGetsymbolData;
	};
	
	public int processRequest(HttpClient httpClient, String symbol) {

		int statusCode = -1;
		try {
			printMessage("Preparing symbol Request for:" + symbol);
			HttpGet getRequest = new HttpGet(apiString + symbol);
			
			HttpResponse response = httpClient.execute(getRequest);
			printMessage("Sent symbol Request for:" + symbol);

			statusCode = response.getStatusLine().getStatusCode();
			printMessage("Received symbol Response for:" + symbol + " as:" + statusCode);

			if (statusCode != 200) {
				printMessage("Failed : symbol:"+symbol+" Response HTTP error code : " + statusCode);
			}

		} catch (ClientProtocolException e) {
			printMessage("Failed : ClientProtocolException : " + e.getMessage());

		} catch (IOException e) {
			printMessage("Failed : IOException : " + e.getMessage());
		}

		return statusCode;
	}

}
