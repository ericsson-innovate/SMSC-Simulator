package com.e3.smsqueue.webutil.stripes;

import java.io.StringReader;

import net.sourceforge.stripes.action.StreamingResolution;

public class JsonResolution extends StreamingResolution{

	public JsonResolution(String json) {
		super("application/x-javascript", new StringReader(json));
	}

}
