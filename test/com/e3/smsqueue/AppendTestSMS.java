package com.e3.smsqueue;

import java.net.HttpURLConnection;
import java.net.URL;
import java.util.Properties;

public class AppendTestSMS {

	/**
	 * @param args
	 * @throws Exception
	 */
	public static void main(String[] args) throws Exception {

		setProxy("www-proxy.ericsson.se", "8080");
//		setProxy("iproxy-bj.cn.ao.ericsson.se", "8080");
		String address = "http://smsqueue2010.appspot.com/service/sms/put?receiver=18626866505&content=happynewyear";
		for (int i = 1; i <= 5; i++) {
			URL url = new URL(address+ i);
			HttpURLConnection urlConnection = (HttpURLConnection) url
					.openConnection();
			urlConnection.setRequestMethod("GET");
			urlConnection.setConnectTimeout(5000);
			urlConnection.setUseCaches(false);
			int statusCode = urlConnection.getResponseCode();
			System.out.println("" + statusCode);
//			Thread.sleep(1000*60);
		}
		System.out.print("done..");

	}

	private static void setProxy(String host, String port) {
		Properties prop = System.getProperties();
		prop.put("proxySet", "true");
		prop.put("proxyHost", host);
		prop.put("proxyPort", port);
		System.setProperties(prop);

	}

}
