<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>

<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%> 

<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>福利修改</title>
</head>
<body>
<h2>
福利信息
</h2>
	<form action="${request.getContextPath() }/wx/admin/welfare/update.do" method="POST">
	<div>
		<table>
			<thead>
				<tr>
					<th>名称</th>
					<th>简介</th>
					<th>操作</th>
				</tr>
			</thead>
			<tbody>
				
					<tr>
						
						<td><input  type="hidden" id="id" name="id" value="${item.id }">
						    <input type="text" id="name" name="name" value="${item.name }"></td>
						<td><input type="text" id="introduction" name="introduction" value="${item.introduction }"></td>
						
						<td><button type="submit">修改</button></td>
					</tr>
				
			</tbody>
		</table>
	</div>
	</form>
		
	

</body>
</html>