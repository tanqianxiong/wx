<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>

<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%> 

<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>图书信息修改</title>
</head>
<body>
<h2>
图书信息
</h2>
	<form action="${request.getContextPath() }/wx/admin/book/update.do" method="POST">
	<div>
		<table>
			<thead>
				<tr>
					<th>ISBN</th>
					<th>书名</th>
					<th>作者</th>
					<th>出版社	</th>
					<th>出版年份</th>
					<th>类型</th>
					<th>总本数</th>
					<th>借出数</th>
					<th>评分</th>
					<th>简介</th>
					<th>操作</th>
				</tr>
			</thead>
			<tbody>
				
					<tr>
						
						<td><input  type="hidden" id="id" name="id" value="${item.id }">
						    <input type="text" id="ISBN" name="ISBN" value="${item.ISBN }"></td>
						<td><input type="text" id="name" name="name" value="${item.name }"></td>
						<td><input type="text" id="author" name="author" value="${item.author }"></td>
						<td><input type="text" id="publisher" name="publisher" value="${item.publisher }"></td>
						<td><input type="text" id="year" name="year" value="${item.year }"></td>
						<td><input type="text" id="type" name="type" value="${item.type }"></td>
						<td><input type="text" id="totalNum" name="totalNum" value="${item.totalNum }"></td>
						<td><input type="text" id="outNum" name="outNum" value="${item.outNum }"></td>
						<td><input type="text" id="points" name="points" value="${item.points }"></td>
						<td><input type="text" id="brief" name="brief" value="${item.brief }"></td>
						<td><button type="submit">修改</button></td>
					</tr>
				
			</tbody>
		</table>
	</div>
	</form>
		
	

</body>
</html>