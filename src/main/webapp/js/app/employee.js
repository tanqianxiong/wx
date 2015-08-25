
/*
 * 员工的私有js代码
 */
window.url = "";// 用于add与update的转换
function update(id) {
	$.ajax({
		type : "GET",
		url : "get.do",
		dataType : "json",
		data : {
			id : id
		},
		success : function(result) {
			if (result.success) {
				var item = result.item;
				var arr = $('#auModal form input');
				arr.each(function() {
					switch ($(this).attr('id')) {
					case 'id':
						$(this).val(item.id);
						break;
					case 'userNo':
						$(this).val(item.userNo);
						break;
					case 'username':
						$(this).val(item.username);
						break;
					case 'gender':
						$(this).val(item.gender);
						break;
					case 'department':
						$(this).val(item.department);
						break;
					case 'position':
						$(this).val(item.position);
						break;
					case 'point':
						$(this).val(item.point);
						break;					
					}					
					window.url = "update.do";
				});
			}
		},
		error : function(jqXHR) {
			window.Modal.alert({msg:"发生错误：" + jqXHR.status});
		},
	});
}
function check(id){
	sessionStorage.employeeId=id;
}
function setData2Table(employeeList) {
	var employeeTBody = $('#listTable tbody');
	employeeTBody.html('');
	var j=1;
	for (var i = 0; i < employeeList.length; i++) {
		var tr = $("<tr/>");
		tr.html('<td>' +j
				+ '</td><td>' + employeeList[i].userNo
				+ '</td><td>' + employeeList[i].username
				+ '</td><td>' + employeeList[i].gender 
				+ '</td><td>' + employeeList[i].department 
				+ '</td><td>' + employeeList[i].position
				+ '</td><td>' + employeeList[i].point 
				+ '</td><td>'
				+ '<a title="点击查询" href="/wx/admin/employee/check.do" onclick="javascript:check(\''
				+ employeeList[i].id + '\');" >查询</a>'+ '</td><td>'
				+ '<a title="点击修改" href="#" onclick="javascript:update(\''
				+ employeeList[i].id + '\');" data-toggle="modal" data-target="#auModal">修改</a>'
				+ '&nbsp;&nbsp;<a title="点击删除" href="#" onclick="javascript:del(\''
				+ employeeList[i].id + '\');">删除</a></td>');
		tr.appendTo(employeeTBody);
		j++;
	}
}

