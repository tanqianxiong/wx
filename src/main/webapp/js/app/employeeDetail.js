function setEmployeeInfo(employee){
	var e = $('#employeeInfo');
	e.html('');
	
		e.html('用户名：'+employee.username+'&nbsp;&nbsp;&nbsp;&nbsp;工号：'+employee.userNo+'&nbsp;&nbsp;&nbsp;&nbsp;部门：'+employee.department+'&nbsp;&nbsp;&nbsp;&nbsp;职位：'+employee.position);	

					
		
	
}
function setData2Table(borrowList) {
	var borrowTBody = $('#listTable tbody');
	borrowTBody.html('');
	var j=1;
	for (var i = 0; i < borrowList.length; i++) {
		var tr = $("<tr/>");
		tr.html('<td>' +j
				
				+ '</td><td>' + borrowList[i].book.bookName
				
				+ '</td><td>' + DateFormat(borrowList[i].borrowTime)
				+ '</td><td>' + DateFormat(borrowList[i].returnTime)
				+ '</td>');
				
				
		tr.appendTo(borrowTBody);
		j++;
	}
}

$(function(){
	
	
	$.ajax({
		type : "POST",
		url : "check.do",
		dataType : "json",
		data: {
			userId: sessionStorage.employeeId,
			},
		success : function(result) {
			if (result.success) {
				var borrowList = result.list;
				var employee=result.employee;
				setEmployeeInfo(employee);
				if (borrowList.length > 0) {
					
					setData2Table(borrowList);
					
				} else {
					window.Modal.alert({msg:"没有数据"});
				}
			} else {
				window.Modal.alert({msg:"抱歉，信息不匹配，请重新输入"});
			}
		},
		error : function(jqXHR) {
			window.Modal.alert({msg:"发生错误：" + jqXHR.status});
		},
	});
	
});

