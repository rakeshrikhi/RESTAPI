package com.rikhi.experimentation;

import java.util.concurrent.atomic.AtomicInteger;
public class PerformanceMetricsData{
	
	//ToDo: generate and adopt getters and setters
	public String metricsKeyName;
	public int numberOfConcurrentClients;
	public AtomicInteger numberOfRequests=new AtomicInteger();
	public AtomicInteger numberOfSuccessfulResponses=new AtomicInteger();
	public AtomicInteger numberOfUnsuccessfulResponses=new AtomicInteger();
	public volatile double avgRequestExecTimePerClient;
	public volatile double successfulResponsesPercent;
	
	public double permissibleAvgRequestExecTimePerClient=200;//milliseconds
	public double permissibleSuccessfulResponsesPercent=90;//percentage
	
	public String toString() {
		return "["+metricsKeyName
				+",numberOfConcurrentClients"+numberOfConcurrentClients
				+",numberOfRequests="+numberOfRequests.get()
				+",numberOfSuccessfulResponses="+numberOfSuccessfulResponses.get()
				+",numberOfUnsuccessfulResponses="+numberOfUnsuccessfulResponses.get()
				+",avgRequestExecTimePerClient="+avgRequestExecTimePerClient
				+",successfulResponsesPercent="+successfulResponsesPercent
				+",permissibleAvgRequestExecTimePerClient"+permissibleAvgRequestExecTimePerClient
				+",permissibleSuccessfulResponsesPercent="+permissibleSuccessfulResponsesPercent
				+"]";
	}
	
}
