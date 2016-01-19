package com.utils;

import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.List;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.NameValuePair;
import org.apache.http.ParseException;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.util.EntityUtils;

public class HttpUtils {
	private static CloseableHttpClient httpclient = HttpClients.createDefault();

	/**
	 * 发送 post请求访问本地应用并根据传递参数不同返回不同结果
	 */
	// public String post(String url,String respEncoding) {
	// return post(url,"UTF-8",respEncoding,new ArrayList<NameValuePair>());
	// }

	public static HttpResponse post(String url) {
		return post(url, "UTF-8", "UTF-8", new ArrayList<NameValuePair>());
	}

	public static HttpResponse post(String url, String body)
			throws ClientProtocolException, IOException {
		HttpResponse response = null;
		// 创建httppost
		HttpPost httppost = new HttpPost(url);
		// 创建参数队列
		StringEntity entity = new StringEntity(body, "UTF-8");
		httppost.setEntity(entity);
		response = httpclient.execute(httppost);
		return response;
	}
	
	public static HttpResponse post(String url, List<NameValuePair> param)
			throws ClientProtocolException, IOException {
		return post(url, "UTF-8", "UTF-8", param);
	}

	/**
	 * 发送 post请求访问本地应用并根据传递参数不同返回不同结果
	 */
	public static HttpResponse post(String url, String reqEncoding, String respEncoding, List<NameValuePair> param) {
		HttpResponse response = null;
		// 创建httppost
		HttpPost httppost = new HttpPost(url);
		// 创建参数队列
		List<NameValuePair> formparams = param;
		UrlEncodedFormEntity uefEntity;
		try {
			uefEntity = new UrlEncodedFormEntity(formparams, reqEncoding);
			httppost.setEntity(uefEntity);
			response = httpclient.execute(httppost);
			// HttpEntity entity = response.getEntity();
			// if (entity != null) {
			// resStr = EntityUtils.toString(entity,respEncoding);
			// }
		} catch (ClientProtocolException e) {
			e.printStackTrace();
		} catch (UnsupportedEncodingException e1) {
			e1.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		} finally {
			// 关闭连接,释放资源
			// httpclient.getConnectionManager().shutdown();
		}
		return response;
	}

	/**
	 * 发送 get请求
	 */
	public String get(String url) {
		// httpclient = new DefaultHttpClient();
		String resStr = "";
		try {
			// 创建httpget.
			HttpGet httpget = new HttpGet(url);
			// 执行get请求.
			HttpResponse response = httpclient.execute(httpget);
			// 获取响应实体
			HttpEntity entity = response.getEntity();
			// 打印响应状态
			System.out.println(response.getStatusLine());
			if (entity != null) {
				// 打印响应内容长度
				// System.out.println("Response content length: "
				// + entity.getContentLength());
				// 打印响应内容
				// System.out.println("Response content: "
				// + EntityUtils.toString(entity));
				resStr = EntityUtils.toString(entity);
			}
		} catch (ClientProtocolException e) {
			e.printStackTrace();
		} catch (ParseException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		} finally {
			// 关闭连接,释放资源
			// httpclient.getConnectionManager().shutdown();
		}
		return resStr;
	}
}