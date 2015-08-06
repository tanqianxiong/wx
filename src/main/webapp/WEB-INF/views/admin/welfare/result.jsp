<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>

<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%> 

<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>查询结果</title>
</head>
<body>
<h2>
名字中含有“ ${propertyValue} ”
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
	
<h2>
id中含有“ ${propertyValue} ”
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
				<c:forEach var="item" items="${list2}">
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
		

</body>
</html>