package com.okcoin.rest;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.apache.commons.httpclient.HttpClient;
import org.apache.commons.httpclient.HttpException;
import org.apache.commons.httpclient.NameValuePair;
import org.apache.commons.httpclient.methods.GetMethod;
import org.apache.commons.httpclient.methods.PostMethod;

/**
 * 封装HTTP get post请求，简化发送http请求
 * @author zhangchi
 *
 */
public class HttpUtilManager {

	private static HttpUtilManager instance = new HttpUtilManager();
	private static HttpClient client;

	private HttpUtilManager() {
		client = new HttpClient();
		client.getHttpConnectionManager().getParams()
				.setConnectionTimeout(30000);
		client.getHttpConnectionManager().getParams().setSoTimeout(30000);
	}

	public static HttpUtilManager getInstance() {
		return instance;
	}

	public HttpClient getHttpClient() {
		return client;
	}

	private PostMethod httpPostMethod(String url) {
		return new PostMethod(url);
	}

	private GetMethod httpGetMethod(String url) {
		return new GetMethod(url);
	}
	
	public String requestHttpGet(String url_prex,String url,String param) throws HttpException, IOException{
		url=url_prex+url;
		if(param!=null && !param.equals("")){
			url = url+"?"+param;
		}
		GetMethod method = this.httpGetMethod(url);
		client.executeMethod(method);
		return method.getResponseBodyAsString();
	}
	
	public String requestHttpPost(String url_prex,String url,Map<String,String> params) throws HttpException, IOException{
		url=url_prex+url;
		PostMethod method = this.httpPostMethod(url);
		NameValuePair[] valuePairs = this.convertMap2PostParams(params);
		method.setRequestBody(valuePairs);
		client.executeMethod(method);
		return method.getResponseBodyAsString();
		
	}
	
	private NameValuePair[] convertMap2PostParams(Map<String,String> params){
		List<String> keys = new ArrayList<String>(params.keySet());
		if(keys.isEmpty()){
			return null;
		}
		int keySize = keys.size();
		NameValuePair[] data = new NameValuePair[keySize];
		for(int i=0;i<keySize;i++){
			String key = keys.get(i);
			String value = params.get(key);
			data[i] = new NameValuePair(key,value);
		}
		return data;
	}

}
