<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>中移物联网员工服务</title>
</head>
<body>
	<form action="${request.getContextPath() }/wx/admin/login.do" method="post">
		<div>
			<label>
				用户名：
			</label>
			<input type="text" id="username" name="username"/>
		</div>
	
		<div>
			<label>
				密&nbsp;&nbsp;码：
			</label>
			<input type="password" id="password" name="password"/>
		</div>
		<div>
			<button type="submit">登录</button>
		</div>
	</form>
</body>
</html>