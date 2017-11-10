package com.expdatacloud.ty.util;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.net.HttpURLConnection;
import java.net.URL;
import java.nio.charset.StandardCharsets;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.codec.binary.Base64;

import com.atlassian.jira.util.json.JSONException;
import com.atlassian.jira.util.json.JSONObject;

public class RestUrlSend {

	public JSONObject postSend(String path, JSONObject data, HttpServletRequest req, HttpServletResponse resp) {

		String strBackUrl = "http://" + req.getServerName() // 服务器地址
				+ ":" + req.getServerPort() // 端口号
				+ req.getContextPath() // 项目名称
				+ path; // 请求页面或其他地址
		
		JSONObject json=null;
		String result = "";
		StringBuffer sb = new StringBuffer();
		String s = "";
		try {
			URL realURL = new URL(strBackUrl);
			HttpURLConnection connection = (HttpURLConnection) realURL.openConnection();
			connection.setRequestMethod("POST");
			connection.setRequestProperty("Accept", "*/*");
			connection.setRequestProperty("Content-Type", "application/json");
			connection.setDoOutput(true);
			connection.setDoInput(true);
			String userCredentials = "admin:admin";
			String basicAuth = "Basic " + new String(new Base64().encode(userCredentials.getBytes()));
			connection.setRequestProperty("Authorization", basicAuth);
			connection.connect();
			OutputStreamWriter wr = new OutputStreamWriter(connection.getOutputStream(), StandardCharsets.UTF_8);
			wr.write(data.toString());
			wr.flush();
			wr.close();
			BufferedReader br = new BufferedReader(
					new InputStreamReader(connection.getInputStream(), StandardCharsets.UTF_8));
			while ((s = br.readLine()) != null) {
				sb.append(s);
			}
			String str = sb.toString();
			if (str == null || str.equals(""))
				return json;
			if(connection.getResponseCode()==500){
				return null;
			}
			json = new JSONObject(str);

		} catch (IOException e) {
			e.printStackTrace();
		} catch (JSONException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return json;
	}

	public JSONObject getSend(String path, HttpServletRequest req, HttpServletResponse resp) {
		
		String strBackUrl = "http://" + req.getServerName() // 服务器地址
		+ ":" + req.getServerPort() // 端口号
		+ req.getContextPath() // 项目名称
		+ path; // 请求页面或其他地址
		JSONObject json=null;
		StringBuffer sb = new StringBuffer();
		String s = "";
		try {
			URL realURL = new URL(strBackUrl);
			HttpURLConnection connection = (HttpURLConnection) realURL.openConnection();
			connection.setRequestMethod("GET");
			connection.setRequestProperty("Accept", "*/*");
			String userCredentials = "admin:admin";
			String basicAuth = "Basic " + new String(new Base64().encode(userCredentials.getBytes()));
			connection.setRequestProperty("Authorization", basicAuth);
			connection.connect();
			BufferedReader br = new BufferedReader(
					new InputStreamReader(connection.getInputStream(), StandardCharsets.UTF_8));
			while ((s = br.readLine()) != null) {
				sb.append(s);
			}
			String str = sb.toString();
			if (str == null || str.equals(""))
				return json;

			json = new JSONObject(str);

		} catch (IOException e) {
			e.printStackTrace();
		} catch (JSONException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return json;
	}
}
