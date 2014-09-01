<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<meta http-equiv="Content-Type" content="text/html; charset=gb2312" />
<title>PHP 测试例子</title>
</head>
<?php
$Ok=new OkAPI ;
//var_dump( $Ok->Fund());
//var_dump( $Ok->CancelOrder(23456));
var_dump( $Ok->Trade(1000, 0.11, "buy"));
//var_dump($Ok->GetOrder("2345"));
class OkAPI {

   var $apiKey = "";
   var $secretKey = "";

   protected function ok_query($parameters, $url){
	   
        $post_data =http_build_query($parameters, '', '&');
	echo  $post_data;
	$sign=md5 ($post_data."&secret_key=".$this->secretKey );
	$sign=strtoupper($sign);
	var_dump($sign);
	$post="api_key=".$this->apiKey."&sign=".$sign."&".$post_data;
	var_dump($post);
	$ch = curl_init();
	curl_setopt($ch, CURLOPT_RETURNTRANSFER, true);
	curl_setopt($ch, CURLOPT_POST, 1);
	curl_setopt($ch, CURLOPT_URL, $url);
	curl_setopt($ch, CURLOPT_POSTFIELDS, $post);
	curl_setopt($ch, CURLOPT_SSL_VERIFYPEER, FALSE);
	$res = curl_exec($ch);
	$res=json_decode ($res, true);
	return $res;

   }

   function MarketDepth($N=5){
   	
	$ch = curl_init();
	curl_setopt($ch, CURLOPT_URL, "https://www.okcoin.com/api/v1/depth.do ");   //国际站
	//curl_setopt($ch, CURLOPT_URL, "https://www.okcoin.cn/api/v1/depth.do "); //中国站
	curl_setopt($ch, CURLOPT_SSL_VERIFYPEER, false);
	curl_setopt($ch, CURLOPT_SSL_VERIFYHOST, false);
	curl_setopt($ch, CURLOPT_RETURNTRANSFER, 1);
	$res= curl_exec($ch);
	$res=json_decode ($res,true);
	$res_ask=array_reverse(array_slice($res["asks"] , -$N, $N));
	$res_bid=array_slice($res["bids"] , 0, $N);
	$ans=array("asks"=>$res_ask, "bids"=>$res_bid);
	return $ans;
	
   }
       
   function Trade($Price, $Amount, $Direction){

	$parameters=array("amount"=>$Amount, "api_key"=>$this->apiKey, "price"=>$Price, "symbol"=>'btc_usd',
	                  "type"=>strtolower($Direction));
         $url= 'https://www.okcoin.com/api/v1/trade.do';//国际站
         //  $url= 'https://www.okcoin.cn/api/v1/trade.do';//中国站
	 $res=$this->ok_query($parameters, $url);
	 return $res;
	 
   }		
		
   function CancelOrder($OrderID){
	
	 $parameters=array("api_key"=>$this->apiKey,"order_id"=>$OrderID,"symbol"=>"btc_usd");  //注意 symbol  国际站：btc_usd/ltc_usd  国内站：btc_cny/ltc_cny
         $url='https://www.okcoin.com/api/v1/cancel_order.do';      //国际站
	 //$url='https://www.okcoin.cn/api/v1/cancel_order.do';  //中国站
	 $res=$this->ok_query($parameters, $url);
	 return $res;
	  
   }
 
   function Fund() {
  	
         $parameters=array("api_key"=>$this->apiKey);
         $url='https://www.okcoin.com/api/v1/userinfo.do';     //国际站
	 //$url='https://www.okcoin.cn/api/v1/userinfo.do';  //中国站
	 $res=$this->ok_query($parameters, $url);
 	 //var_dump($res);
	 if($res["result"] ){
	      $res=array("result"=>true, "Frozen"=>array("BTC"=>$res["info"]["funds"]["freezed"]["btc"], "CNY"=>$res["info"]["funds"]["freezed"]["cny"]),
	      "Free"=>array("BTC"=>$res["info"]["funds"]["free"]["btc"], "CNY"=>$res["info"]["funds"]["free"]["cny"]) ); 
	      return $res;
	  }
	  $res=array("result"=>false);
	  return $res;
	  
   }	

   function GetOrder($OrderID){
   	
         $parameters=array("api_key"=>$this->apiKey,"order_id"=>$OrderID,"symbol"=>"btc_usd"); //注意 symbol  国际站：btc_usd/ltc_usd  国内站：btc_cny/ltc_cny
         $url= 'https://www.okcoin.com/api/v1/order_info.do';   //国际站
         //$url= 'https://www.okcoin.cn/api/v1/order_info.do'; //中国站
         $res=$this->ok_query($parameters, $url);
         return $res;
         
    }
    
}
?>
<body>
</body>
</html>
