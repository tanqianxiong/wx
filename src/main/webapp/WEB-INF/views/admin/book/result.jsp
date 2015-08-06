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
						<td><a href="${request.getContextPath() }/wx/admin/book/modify.do?id=${item.id }"><button>修改</button></a>
							<a href="${request.getContextPath() }/wx/admin/book/delete.do?id=${item.id }"><button>删除</button></a></td>
					</tr>
				</c:forEach>
			</tbody>
		</table>
	</div>
	
<h2>
作者中含有“ ${propertyValue} ”
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
				<c:forEach var="item" items="${list2}">
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
						<td><a href="${request.getContextPath() }/wx/admin/book/modify.do?id=${item.id }"><button>修改</button></a>
							<a href="${request.getContextPath() }/wx/admin/book/delete.do?id=${item.id }"><button>删除</button></a></td>
					</tr>
				</c:forEach>
			</tbody>
		</table>
	</div>
		

</body>
</html>