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
福利列表
</h2>
	<div>
		<table>
			<thead>
				<tr>
					<th>福利名</th>
					<th>简介</th>
					<th>操作</th>
				</tr>
			</thead>
			<tbody>
				<c:forEach var="item" items="${list}">
					<tr>
						<td>${item.name }</td>
						<td>${item.introduction }</td>
						<td><a href="${request.getContextPath() }/wx/admin/welfare/get.do?id=${item.id }"><button>修改</button></a>
							<a href="${request.getContextPath() }/wx/admin/welfare/delete.do?id=${item.id }"><button>删除</button></a></td>
					</tr>
				</c:forEach>
			</tbody>
		</table>
	</div>
	
		<form action="${request.getContextPath() }/wx/admin/welfare/add.do" method="POST">
			<div>
				<h3>>新增图书</h3>
			</div>
			<div>
			 <label>福利名</label>
			 <input type="text" id="name" name="name">
			</div>
			<div>
			 <label>简介</label>
			 <input type="text" id="introduction" name="introduction">
			</div>
			<div><button type="submit">插入</button></div>
		
		</form>
		
		<form action="${request.getContextPath() }/wx/admin/welfare/search.do" method="get">
			<div>
				<h3><label>福利查找</label></h3>
				<input type="text" id="propertyValue" name="propertyValue">
			</div>
			<div><button type="submit">查找</button></div>
		</form>
	

</body>
</html>