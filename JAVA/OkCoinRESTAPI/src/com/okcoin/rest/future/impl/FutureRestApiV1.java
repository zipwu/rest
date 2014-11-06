package com.okcoin.rest.future.impl;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

import org.apache.commons.httpclient.HttpException;
import com.okcoin.rest.HttpUtilManager;
import com.okcoin.rest.MD5Util;
import com.okcoin.rest.future.IFutureRestApi;

/**
 * 新版本期货 REST API实现
 * 
 * @author zc
 * 
 */
public class FutureRestApiV1 implements IFutureRestApi {

	// 密钥
	private String secret_key;
	// 唯一用户号
	private String partner;
	
	private String url_prex;

	public FutureRestApiV1(String url_prex,String partner, String secret_key) {
		this.partner = partner;
		this.secret_key = secret_key;
		this.url_prex = url_prex;
	}

	public FutureRestApiV1(String url_prex) {
		this.url_prex = url_prex;

	}

	/**
	 * 期货行情URL
	 */
	private final  String FUTURE_TICKER_URL = "/api/v1/future_ticker.do";
	/**
	 * 期货指数查询URL
	 */
	private final String FUTURE_INDEX_URL = "/api/v1/future_index.do";

	/**
	 * 期货交易记录查询URL
	 */
	private final  String FUTURE_TRADES_URL = "/api/v1/future_trades.do";

	/**
	 * 期货市场深度查询URL
	 */
	private final String FUTURE_DEPTH_URL = "/api/v1/future_depth.do";
	/**
	 * 美元-人民币汇率查询URL
	 */
	private final  String FUTURE_EXCHANGE_RATE_URL = "/api/v1/exchange_rate.do";

	/**
	 * 期货取消订单URL
	 */
	private final  String FUTURE_CANCEL_URL = "/api/v1/future_cancel.do";

	/**
	 * 期货下单URL
	 */
	private final  String FUTURE_TRADE_URL = "/api/v1/future_trade.do";

	/**
	 * 期货账户信息URL
	 */
	private final String FUTURE_USERINFO_URL = "/api/v1/future_userinfo.do";
	
	/**
	 * 逐仓期货账户信息URL
	 */
	private final String FUTURE_USERINFO_4FIX_URL = "/api/v1/future_userinfo_4fix.do";

	/**
	 * 期货持仓查询URL
	 */
	private final String FUTURE_POSITION_URL = "/api/v1/future_position.do";
	
	/**
	 * 期货逐仓持仓查询URL
	 */
	private final String FUTURE_POSITION_4FIX_URL = "/api/v1/future_position_4fix.do"; 

	/**
	 * 用户期货订单信息查询URL
	 */
	private final String FUTURE_ORDER_INFO_URL = "/api/v1/future_order_info.do";

	@Override
	public String future_ticker(String symbol, String contractType)
			throws HttpException, IOException {

		HttpUtilManager httpUtil = HttpUtilManager.getInstance();
		String param = "";
		if (symbol != null) {
			if (!param.equals("")) {
				param += "&";
			}
			param += "symbol=" + symbol;
		}
		if (contractType != null) {
			if (!param.equals("")) {
				param += "&";
			}
			param += "contract_type=" + contractType;

		}
		String result = httpUtil.requestHttpGet(url_prex,FUTURE_TICKER_URL, param);
		// System.out.println(result);
		return result;
	}

	@Override
	public String future_index(String symbol) throws HttpException, IOException {
		HttpUtilManager httpUtil = HttpUtilManager.getInstance();
		String param = "";
		if (symbol != null) {
			if (!param.equals("")) {
				param += "&";
			}
			param += "symbol=" + symbol;
		}
		String result = httpUtil.requestHttpGet(url_prex,FUTURE_INDEX_URL, param);
		// System.out.println(result);
		return result;
	}

	@Override
	public String future_trades(String symbol, String contractType)
			throws HttpException, IOException {
		HttpUtilManager httpUtil = HttpUtilManager.getInstance();
		String param = "";
		if (symbol != null) {
			if (!param.equals("")) {
				param += "&";
			}
			param += "symbol=" + symbol;
		}
		if (contractType != null) {
			if (!param.equals("")) {
				param += "&";
			}
			param += "contract_type=" + contractType;

		}
		String result = httpUtil.requestHttpGet(url_prex,FUTURE_TRADES_URL, param);
		// System.out.println(result);
		return result;
	}

	@Override
	public String future_depth(String symbol, String contractType)
			throws HttpException, IOException {
		HttpUtilManager httpUtil = HttpUtilManager.getInstance();
		String param = "";
		if (symbol != null) {
			if (!param.equals("")) {
				param += "&";
			}
			param += "symbol=" + symbol;
		}
		if (contractType != null) {
			if (!param.equals("")) {
				param += "&";
			}
			param += "contract_type=" + contractType;

		}
		String result = httpUtil.requestHttpGet(url_prex,FUTURE_DEPTH_URL, param);
		// System.out.println(result);
		return result;
	}

	@Override
	public String exchange_rate() throws HttpException, IOException {
		HttpUtilManager httpUtil = HttpUtilManager.getInstance();
		String result = httpUtil.requestHttpGet(url_prex,FUTURE_EXCHANGE_RATE_URL, null);
		// System.out.println(result);
		return result;
	}

	@Override
	public String future_cancel(String symbol, String contractType,
			String orderId) throws HttpException, IOException {
		// 构造参数签名
		Map<String, String> params = new HashMap<String, String>();
		if (contractType != null) {
			params.put("contract_type", contractType);
		}
		if (orderId != null) {
			params.put("order_id", orderId);
		}
		if (partner != null) {
			params.put("partner", partner);
		}
		if (symbol != null) {
			params.put("symbol", symbol);
		}
		String sign = MD5Util.buildMysignV1(params, secret_key);

		params.put("sign", sign);
		// 发送post请求

		HttpUtilManager httpUtil = HttpUtilManager.getInstance();
		String result = httpUtil.requestHttpPost(url_prex,FUTURE_CANCEL_URL, params);
		// System.out.println(result);
		return result;

	}

	@Override
	public String future_trade(String symbol, String contractType,
			String price, String amount, String type, String matchPrice)
			throws HttpException, IOException {
		// 构造参数签名
		Map<String, String> params = new HashMap<String, String>();
		if (symbol != null) {
			params.put("symbol", symbol);
		}
		if (contractType != null) {
			params.put("contract_type", contractType);
		}
		if (partner != null) {
			params.put("partner", partner);
		}
		if (price != null) {
			params.put("price", price);
		}
		if (amount != null) {
			params.put("amount", amount);
		}
		if (type != null) {
			params.put("type", type);
		}
		if (matchPrice != null) {
			params.put("match_price", matchPrice);
		}
		String sign = MD5Util.buildMysignV1(params, secret_key);
		params.put("sign", sign);
		// 发送post请求

		HttpUtilManager httpUtil = HttpUtilManager.getInstance();
		String result = httpUtil.requestHttpPost(url_prex,this.FUTURE_TRADE_URL, params);
		// System.out.println(result);
		return result;
	}

        @Override
	public String future_batch_trade(String symbol, String contractType,String orders_data)
			throws HttpException, IOException {
		// 构造参数签名
		Map<String, String> params = new HashMap<String, String>();
		if(symbol!=null){
			params.put("symbol", symbol);
		}
		if(contractType!=null){
			params.put("contract_type", contractType);
		}
		if(orders_data!=null){
			params.put("orders_data", orders_data);
		}
		params.put("partner", partner);
		String sign = MD5Util.buildMysignV1(params, secret_key);
		params.put("sign", sign);
		// 发送post请求

		HttpUtilManager httpUtil = HttpUtilManager.getInstance();
		String result = httpUtil.requestHttpPost(url_prex,this.FUTURE_BATCH_TRADE_URL,
				params);
		return result;
	}
	
	@Override
	public String future_userinfo() throws HttpException, IOException {
		// 构造参数签名
		Map<String, String> params = new HashMap<String, String>();

		    params.put("partner", partner);

		    String sign = MD5Util.buildMysignV1(params, this.secret_key);
		    params.put("sign", sign);
		
		
		// 发送post请求

		HttpUtilManager httpUtil = HttpUtilManager.getInstance();
		String result = httpUtil.requestHttpPost(url_prex,this.FUTURE_USERINFO_URL,
				params);
		// System.out.println(result);
		return result;
	}
	
	@Override
	public String future_userinfo_4fix()
			throws HttpException, IOException {
		// 构造参数签名
		Map<String, String> params = new HashMap<String, String>();
		    params.put("partner", partner);

		    String sign = MD5Util.buildMysignV1(params, this.secret_key);
		    params.put("sign", sign);
		
		
		// 发送post请求

		HttpUtilManager httpUtil = HttpUtilManager.getInstance();
		String result = httpUtil.requestHttpPost(url_prex,this.FUTURE_USERINFO_4FIX_URL,
				params);
		// System.out.println(result);
		return result;
	}

	@Override
	public String future_position(String symbol, String contractType)
			throws HttpException, IOException {
		// 构造参数签名
		Map<String, String> params = new HashMap<String, String>();
		if (symbol != null) {
			params.put("symbol", symbol);
		}
		if (contractType != null) {
			params.put("contract_type", contractType);
		}
		params.put("partner", partner);
		String sign = MD5Util.buildMysignV1(params, secret_key);
		System.out.println("sign  "+sign);
		params.put("sign", sign);
		// 发送post请求

		HttpUtilManager httpUtil = HttpUtilManager.getInstance();
		String result = httpUtil.requestHttpPost(url_prex,this.FUTURE_POSITION_URL,
				params);
		// System.out.println(result);
		return result;

	}
	
	@Override
	public String future_position_4fix(String symbol, String contractType)
			throws HttpException, IOException {
		// 构造参数签名
		Map<String, String> params = new HashMap<String, String>();
		if (symbol != null) {
			params.put("symbol", symbol);
		}
		if (contractType != null) {
			params.put("contract_type", contractType);
		}
		params.put("partner", partner);
		String sign = MD5Util.buildMysignV1(params, secret_key);
		params.put("sign", sign);
		// 发送post请求
		HttpUtilManager httpUtil = HttpUtilManager.getInstance();
		String result = httpUtil.requestHttpPost(url_prex,this.FUTURE_POSITION_4FIX_URL,
				params);
		// System.out.println(result);
		return result;
	}

	@Override
	public String future_order_info(String symbol, String contractType,
			String orderId, String status, String currentPage, String pageLength)
			throws HttpException, IOException {
		// 构造参数签名
		Map<String, String> params = new HashMap<String, String>();
		if (contractType != null) {
			params.put("contract_type", contractType);
		}
		if (currentPage != null) {
			params.put("current_page", currentPage);
		}
		if (orderId != null) {
			params.put("order_id", orderId);
		}
		if (partner != null) {
			params.put("partner", partner);
		}
		if (pageLength != null) {
			params.put("page_length", pageLength);
		}
		if (symbol != null) {
			params.put("symbol", symbol);
		}
		if (status != null) {
			params.put("status", status);
		}
		String sign = MD5Util.buildMysignV1(params, secret_key);
		params.put("sign", sign);
		// 发送post请求

		HttpUtilManager httpUtil = HttpUtilManager.getInstance();
		String result = httpUtil.requestHttpPost(url_prex,this.FUTURE_ORDER_INFO_URL,
				params);
		// System.out.println(result);
		return result;

	}





}
