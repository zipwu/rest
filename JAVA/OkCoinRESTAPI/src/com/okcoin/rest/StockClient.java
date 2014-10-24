package com.okcoin.rest;

import java.io.IOException;

import org.apache.commons.httpclient.HttpException;

import com.alibaba.fastjson.JSONObject;
import com.okcoin.rest.stock.IStockRestApi;
import com.okcoin.rest.stock.impl.StockRestApi;

/**
 * 现货 REST API 客户端请求
 * @author zhangchi
 *
 */
public class StockClient {

	public static void main(String[] args) throws HttpException, IOException{
		
		//OKCoin唯一用户号,每个用户唯一持有一个
		String partner = "";
		
		//用户私钥
		String secret_key = "";
		

		//请求URL 国际站https://www.okcoin.com
		//       国内站https://www.okcoin.cn
		
		String url_prex = "";
		
		
	    //get请求无需发送身份认证,通常用于获取行情，市场深度等公共信息
		IStockRestApi stockGet = new StockRestApi(url_prex);
		
		/**
		 * post请求需发送身份认证，获取用户个人相关信息时，需要指定partner,与secretKey并与参数进行签名，
		 * 此处对构造方法传入partner与secretKey,在请求用户相关方法时则无需再传入，
		 * 发送post请求之前，程序会做自动加密，生成签名。
		 * 
		*/
		IStockRestApi stockPost = new StockRestApi(url_prex,partner,secret_key);
		
		System.out.println("------------------------现货行情----------------------");
		System.out.println(stockGet.ticker("btc_usd"));

		System.out.println("------------------------现货市场深度------------------");
		System.out.println(stockGet.depth("btc_usd"));
		
		System.out.println("------------------------现货OKCoin历史交易信息---------------");
		System.out.println(stockGet.trades("btc_usd", "20"));
		
		System.out.println("------------------------现货用户信息-------------------------");
		System.out.println(stockPost.userinfo());
		
		System.out.println("------------------------现货下单交易------------------------------");
		String tradeResult = stockPost.trade("btc_usd", "buy", "50", "0.02");
		System.out.println(tradeResult);
		JSONObject tradeJSV1 = JSONObject.parseObject(tradeResult);
		String tradeOrderV1 = tradeJSV1.getString("order_id");

		System.out.println("------------------------现货获取用户订单信息------------------------");
		System.out.println(stockPost.order_info("btc_usd", tradeOrderV1));
		
		System.out.println("------------------------现货撤销订单------------------------------");
		System.out.println(stockPost.cancel_order("btc_usd", tradeOrderV1));
		
		System.out.println("------------------------现货批量下单-------------------------------");
		System.out.println(stockPost.batch_trade("btc_usd", "buy", "[{price:50,amount:0.02},{price:50,amount:0.03}]"));

		System.out.println("------------------------批量获取用户订单--------------------------");
		System.out.println(stockPost.orders_info("0", "btc_usd", "125420341,125420342"));
		
		System.out.println("------------------------获取用户历史订单信息，只返回最近七天的信息");
		System.out.println(stockPost.order_history("btc_usd", "0", "1", "20"));
		
		
		
		
	}
}
