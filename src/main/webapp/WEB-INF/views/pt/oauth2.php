<?php


header('Access-Control-Allow-Origin:*');
header('Access-Control-Allow-Methods:POST,GET');
header('Access-Control-Allow-Credentials:true'); 
header("Content-Type: application/json;charset=utf-8"); 



$code = $_GET["code"];//前端传来的code值

$appid = "wx13160138252e5ae8";
$appsecret = "e8b80580169baf028dc4271ef8efc5cb";

//获取openid
$url = "https://api.weixin.qq.com/sns/oauth2/access_token?appid=wx13160138252e5ae8&secret=e8b80580169baf028dc4271ef8efc5cb&code=".$code."&grant_type=authorization_code";

$result = https_request($url);
$jsoninfo = json_decode($result,true);
$openid = $jsoninfo["openid"];//从返回json结果中读出openid
echo    '{"openid":"' . $openid . '"}';
//echo '{}';    //成功 显示为undefined
 

function https_request($url,$data = null){
  $curl = curl_init();
  curl_setopt($curl, CURLOPT_URL, $url);
  curl_setopt($curl, CURLOPT_SSL_VERIFYPEER, FALSE);
  curl_setopt($curl, CURLOPT_SSL_VERIFYHOST, FALSE);
  if (!empty($data)){
    curl_setopt($curl, CURLOPT_POST, 1);
    curl_setopt($curl, CURLOPT_POSTFIELDS, $data);
  }
  curl_setopt($curl, CURLOPT_RETURNTRANSFER, 1);
  $output = curl_exec($curl);
  curl_close($curl);
  return $output;
}

?>