package com.e3.smsqueue;

import java.net.HttpURLConnection;
import java.net.URL;
import java.util.Properties;

public class TestSMS {

	/**
	 * @param args
	 * @throws Exception
	 */
	public static void main(String[] args) throws Exception {

		// setProxy("www-proxy.ericsson.se", "8080");
		// setProxy("iproxy-bj.cn.ao.ericsson.se", "8080");
		// String address = "http://147.128.104.240:8250/service/sms/get";
		String getAddress = "http://localhost:8250/service/sms/get&smsBox=inbox";
		String putAddress = "http://localhost:8250/service/sms/put?receiver=18626866505&content=happynewyear&smsBox=inbox";
		while (true) {
			URL putUrl = new URL(putAddress);
			URL getUrl = new URL(getAddress);
			HttpURLConnection putUrlConnection = (HttpURLConnection) putUrl
					.openConnection();
			putUrlConnection.setRequestMethod("GET");
			putUrlConnection.setConnectTimeout(5000);
			putUrlConnection.setUseCaches(false);
			int statusCode = putUrlConnection.getResponseCode();
			 System.out.println("put result = " + statusCode);

			HttpURLConnection getUrlConnection = (HttpURLConnection) putUrl
					.openConnection();
			getUrlConnection.setRequestMethod("GET");
			getUrlConnection.setConnectTimeout(5000);
			getUrlConnection.setUseCaches(false);
			statusCode = getUrlConnection.getResponseCode();
			
			System.out.println("get result= "+ statusCode);

			Thread.sleep(500);
		}

	}

}
