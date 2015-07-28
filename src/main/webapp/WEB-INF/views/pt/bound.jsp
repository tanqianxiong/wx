<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>中移物联网员工服务</title>


</head>
<body>
	<h2>普通用户的账号绑定</h2>
	<form action="bound.do" method="post">
		<div>
			<label>
				工&nbsp;&nbsp;号：
			</label>
			<input type="text" id="workID" name="workID"/>
		</div>
	
		<div>
			<label>
				姓&nbsp;&nbsp;名：
			</label>
			<input type="text" id="name" name="name"/>
		</div>
		<div>
			<button type="submit">绑定</button>
		</div>
	</form>
</body>
</html>