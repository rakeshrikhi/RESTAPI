package com.rikhi.experimentation.test;

import org.junit.Assert.*;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;

import com.rikhi.experimentation.KrakenTickerAPIPerfCollector;

public class KrakenTickerAPIPerfTester {
	
	static KrakenTickerAPIPerfCollector api;
	
	@BeforeClass
	public static void initialize() {
		api=new KrakenTickerAPIPerfCollector();
		api.collect();
	}
	
	
	@Test
	public void testAvgRequestExecTimePerClient() {
		org.junit.Assert.assertTrue(api.perfMetrics.avgRequestExecTimePerClient < api.perfMetrics.permissibleAvgRequestExecTimePerClient);
	}
	
	@Test
	public void testSuccessfulResponsesPercent() {
		org.junit.Assert.assertTrue(api.perfMetrics.successfulResponsesPercent > api.perfMetrics.permissibleSuccessfulResponsesPercent);
	}

}
