package com.futureadymedia.alumni.services;


import android.util.Log;

import java.io.BufferedInputStream;
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.net.HttpURLConnection;
import java.net.URL;

/** This service is used for calling  APIs */

public class ServiceCall {

	private int statusCode =0;
	public int getStatusCode() {
		return statusCode;
	}
	public void setStatusCode(int statusCode) {
		this.statusCode = statusCode;
	}
	private String resMsg = null;
	private final static int CONNECTION_TIMEOUT=5000;

	public String getServiceResponse(String requestUrl, String request, String methodName) {
		Log.e("ServiceCall", "URL" + requestUrl);
		Log.e("ServiceCall", "request" + request);

		HttpURLConnection conn=null;
		try{
		URL url = new URL(requestUrl);
		conn = (HttpURLConnection) url.openConnection();
		conn.setConnectTimeout(CONNECTION_TIMEOUT);

        conn.addRequestProperty("Content-Type",RequestURL.APPLICATION_JSON);

		if(methodName.equalsIgnoreCase("POST") || methodName.equalsIgnoreCase("PUT")){
				conn.setRequestMethod(methodName);
				conn.setDoInput(true);
				conn.setDoOutput(true);
				OutputStream os = conn.getOutputStream();
				BufferedWriter writer = new BufferedWriter(new OutputStreamWriter(os, "UTF-8"));
				writer.write(request);
				writer.flush();
				writer.close();
				os.close();
		}else if(methodName.equalsIgnoreCase("GET")) {
			  conn.setRequestMethod("GET");
		} else if(methodName.equalsIgnoreCase("DELETE")){
			  conn.setRequestMethod("DELETE");
		}
		int status = conn.getResponseCode();
		setStatusCode(status);
		InputStream in = new BufferedInputStream(conn.getInputStream());
		String response = readStream(in);
		Log.e("ServiceCall", "response" + response);
		return response;
	} catch (Exception e) {
		e.printStackTrace();

	} finally {
		try {
			conn.disconnect();
		} catch (Exception e) {
			e.printStackTrace(); //If you want further info on failure...

		}
	}
		return null;
	}


	private String readStream(InputStream in) {
		BufferedReader reader = null;
		StringBuffer response = new StringBuffer();
		try {
			reader = new BufferedReader(new InputStreamReader(in,"UTF-8"));
			String line = "";
			while ((line = reader.readLine()) != null) {
				response.append(line);
			}
		} catch (IOException e) {
			e.printStackTrace();
		} finally {
			if (reader != null) {
				try {
					reader.close();
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
		}
		return response.toString();
	}

}