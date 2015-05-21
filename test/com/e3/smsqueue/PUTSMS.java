package com.e3.smsqueue;

import java.net.HttpURLConnection;
import java.net.URL;
import java.util.Properties;

public class PUTSMS {

	/**
	 * @param args
	 * @throws Exception
	 */
	public static void main(String[] args) throws Exception {

		// setProxy("www-proxy.ericsson.se", "8080");
		// setProxy("iproxy-bj.cn.ao.ericsson.se", "8080");

		// String address =
		// "http://147.128.104.240:8250/service/sms/put?receiver=18626866505&content=happynewyear";
		String address = "http://localhost:8888/service/sms/put?receiver=18626866505&content=happynewyear&smsBox=inbox";
		while (true) {
			URL url = new URL(address);
			HttpURLConnection urlConnection = (HttpURLConnection) url
					.openConnection();
			urlConnection.setRequestMethod("GET");
			urlConnection.setConnectTimeout(5000);
			urlConnection.setUseCaches(false);
			int statusCode = urlConnection.getResponseCode();
//			 System.out.println("" + statusCode);
			Thread.sleep(100);
		}
	}

}
