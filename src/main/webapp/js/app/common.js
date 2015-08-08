/*
 * 此文件用于js的公共类，包括：删除、修改、搜索
 */

function del(id){
	$.ajax({
		type : "POST",
		url : "delete.do",
		dataType : "json",
		data:{
			id:id
		},
		success : function(result) {
			if (result.success) {
				alert('删除成功！');
				window.location.reload();
			}
		},
		error : function(jqXHR) {
			alert("发生错误：" + jqXHR.status);
		},
	});
}
function addOrUpdate(){
	$.ajax({
		type : "POST",
		url : url,
		dataType : "json",
		data:$("#saveModal").serialize(),
		success : function(result) {
			if (result.success) {
				alert('操作成功！');
				window.location.reload();
			}
		},
		error : function(jqXHR) {
			alert("发生错误：" + jqXHR.status);
		},
	});
}
$(function() {
	$('#addBtn').click(function(){
		$('#saveModal').removeClass('hide');
		$('#listDiv').addClass('hide');
		url="add.do";
	});
	$('#saveBtn').click(function(){
		addOrUpdate();
	});
	
	//开始向后台请求获取数据
	$.ajax({
		type : "POST",
		url : "list.do",
		dataType : "json",
		success : function(result) {
			if (result.success) {
				var bookList = result.list;
				if (bookList.length > 0) {
					setData2Table(bookList);
				} else {
					alert("没有数据");
				}
			} else {
				alert("抱歉，信息不匹配，请重新输入");
			}
		},
		error : function(jqXHR) {
			alert("发生错误：" + jqXHR.status);
		},
	});
	//按关键字搜索			
	$('#searchBtn').click(function() {
		$.ajax({
			type : "POST",
			url : "list.do",
			data : {
				keyword : $('#keyword').val()
			},
			dataType : "json",
			success : function(result) {
				if (result.success) {
					var bookList = result.list;
					if (bookList.length > 0) {
						setData2Table(bookList);
					} else {
						alert("没有找到相匹配的数据");
					}
				} else {
					alert("抱歉，信息不匹配，请重新输入");
				}
			},
			error : function(jqXHR) {
				alert("发生错误：" + jqXHR.status);
			},
		});
	});

});