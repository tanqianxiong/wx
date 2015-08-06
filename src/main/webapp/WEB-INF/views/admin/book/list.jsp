<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>

<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%> 

<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>中移物联网员工服务</title>
</head>
<body>
<h2>
图书列表
</h2>
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
				<c:forEach var="item" items="${list}">
					<tr>
						<td>${item.ISBN }</td>
						<td>${item.name }</td>
						<td>${item.author }</td>
						<td>${item.publisher }</td>
						<td>${item.year }</td>
						<td>${item.type }</td>
						<td>${item.totalNum }</td>
						<td>${item.outNum }</td>
						<td>${item.points }</td>
						<td>${item.brief }</td>
						<td><a href="${request.getContextPath() }/wx/admin/book/get.do?id=${item.id }"><button>修改</button></a>
							<a href="${request.getContextPath() }/wx/admin/book/delete.do?id=${item.id }"><button>删除</button></a></td>
					</tr>
				</c:forEach>
			</tbody>
		</table>
	</div>
	
		<form action="${request.getContextPath() }/wx/admin/book/add.do" method="POST">
			<div>
				<h3>>新增图书</h3>
			</div>
			<div>
			 <label>ISBN</label>
			 <input type="text" id="ISBN" name="ISBN">
			</div>
			<div>
			 <label>书名</label>
			 <input type="text" id="name" name="name">
			</div>
			<div>
			 <label>作者</label>
			 <input type="text" id="author" name="author">
			</div>
			<div>
			 <label>出版社</label>
			 <input type="text" id="publisher" name="publisher">
			</div>
			<div>
			 <label>出版年份</label>
			 <input type="text" id="year" name="year">
			</div>
			<div>
			 <label>类型</label>
			 <input type="text" id="type" name="type">
			</div>
			<div>
			 <label>总本数</label>
			 <input type="text" id="totalNum" name="totalNum">
			</div>
			<div>
			 <label>简介</label>
			 <input type="text" id="brief" name="brief">
			</div>
			
			<div><button type="submit">插入</button></div>
		
		</form>
		
		<form action="${request.getContextPath() }/wx/admin/book/search.do" method="get">
			<div>
				<h3><label>书名查找</label></h3>
				<input type="text" id="propertyValue" name="propertyValue">
			</div>
			<div><button type="submit">查找</button></div>
		</form>
	

</body>
</html>