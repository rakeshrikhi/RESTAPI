package com.rikhi.experimentation.test;

import org.junit.Assert.*;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;

import com.rikhi.experimentation.KrakenOHLCAPIPerfCollector;

public class KrakenOHLCAPIPerfTester {
	
	static KrakenOHLCAPIPerfCollector api;
	
	@BeforeClass
	public static void initialize() {
		api=new KrakenOHLCAPIPerfCollector();
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
