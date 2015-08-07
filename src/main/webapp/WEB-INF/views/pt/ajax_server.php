<?php
//设置页面内容是html编码格式是utf-8
//header("Content-Type: text/plain;charset=utf-8"); 
header('Access-Control-Allow-Origin:*');
header('Access-Control-Allow-Methods:POST,GET');
header('Access-Control-Allow-Credentials:true'); 
header("Content-Type: application/json;charset=utf-8"); 
//header("Content-Type: text/xml;charset=utf-8"); 
//header("Content-Type: text/html;charset=utf-8"); 
//header("Content-Type: application/javascript;charset=utf-8"); 

//定义一个多维数组，包含员工的信息，每条员工信息为一个数组
$staff = array
	(
		array("name" => "aa", "jobnumber" => "1"),
		array("name" => "bb", "jobnumber" => "2"),
		array("name" => "cc", "jobnumber" => "3"),
		array("name" => "n", "jobnumber" => "j")
	);


$books = array
	(
		array("bookName" => "one", "author" => "1", "publish" => "aBBC"),
		array("bookName" => "two", "author" => "2", "publish" => "bBBC"),
		array("bookName" => "three", "author" => "3", "publish" => "cBBC"),
		array("bookName" => "four", "author" => "4", "publish" => "dBBC"),
		array("bookName" => "five", "author" => "5", "publish" => "eBBC"),
		array("bookName" => "six", "author" => "6", "publish" => "fBBC"),
		array("bookName" => "seven", "author" => "7", "publish" => "gBBC"),
		array("bookName" => "eight", "author" => "8", "publish" => "hBBC")
	);



//判断如果是get请求，则进行搜索；如果是POST请求，则进行新建
//$_SERVER是一个超全局变量，在一个脚本的全部作用域中都可用，不用使用global关键字
//$_SERVER["REQUEST_METHOD"]返回访问页面使用的请求方法

	
if ($_SERVER["REQUEST_METHOD"] == "POST") {
	userBind();
} else{
	searchBook();
}

//searchBook();



//通过员工编号和姓名匹配是否有该员工：
function userBind(){
	//检查是否有员工编号的参数
	//isset检测变量是否设置；empty判断值为否为空
	//超全局变量 $_GET 和 $_POST 用于收集表单数据
	if (!isset($_POST["name"]) || empty($_POST["name"])) {
		echo '{"success":false,"msg":"空检测 参数错误"}';
		return;
	}
	//函数之外声明的变量拥有 Global 作用域，只能在函数以外进行访问。
	//global 关键词用于访问函数内的全局变量
	global $staff;
	//获取number参数
	$name = $_POST["name"];
	$jobnumber = $_POST["jobnumber"];
	$result = '{"success":false,"msg":"没有找到员工。"}';
	
	//遍历$staff多维数组，查找key值为number的员工是否存在，如果存在，则修改返回结果
	foreach ($staff as $value) {
		if ($value["name"] == $name && $value["jobnumber"] == $jobnumber) {
			$result = '{"success":true,"msg":"匹配成功"}';
			break;
		}
	}
    echo $result;
}


function searchBook(){
	//检查是否有员工编号的参数
	//isset检测变量是否设置；empty判断值为否为空
	//超全局变量 $_GET 和 $_POST 用于收集表单数据

	/*因为js已经判断了输入不能为空 所以这里不再判断即可
	if (!isset($_POST["bookName"]) || empty($_POST["bookName"])) {
		echo '{"success":false,"msg":"空检测 参数错误"}';
		return;
	}*/



	//函数之外声明的变量拥有 Global 作用域，只能在函数以外进行访问。
	//global 关键词用于访问函数内的全局变量
	global $books;
	//获取number参数
	$bookName = $_GET["bookName"];
	$result = ' { "success":false, "bookName":"书名","author":"yummy","publish":"pub" } ';
	
	//遍历$staff多维数组，查找key值为number的员工是否存在，如果存在，则修改返回结果
	foreach ($books as $value) {
		if ($value["bookName"] == $bookName) {
			$result = ' { "success":true, "bookName":"' . $value["bookName"] . '","author":"' . $value["author"] . '","publish":"' . $value["publish"] . '" } ';
			break;
		}
	}

    echo $result;
}


?>