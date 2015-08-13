window.url="";//用于add与update的转换		
function update(id){
	$.ajax({
		type : "GET",
		url : "get.do",
		dataType : "json",
		data:{
			id:id
		},
		success : function(result) {
			if (result.success) {
				var item=result.item;
				var arr=$('#saveModal input');
				arr.each(function(){
					switch($(this).attr('name')){
					case 'name':
						$(this).val(item.name);
						break;
					case 'number':
						$(this).val(item.number);
						break;
					}
					$('#saveModal').removeClass('hide');
					$('#listDiv').addClass('hide');
					window.url="update.do";
				});
			}
		},
		error : function(jqXHR) {
			alert("发生错误：" + jqXHR.status);
		},
	});
}

function setData2Table(appintmentList){
	var appointmentTBody = $('#listTable tbody');
	var j=1;
	appointmentTBody.html('');
	for (var i = 0; i < appintmentList.length; i++) {
		var tr = $("<tr/>");
		tr.html('<td>' +j
				+ '</td><td>' + appintmentList[i].name + '</td><td>'
				     + appintmentList[i].number + '</td><td>'
				     + '<a title="查看详情" href="detail.do?welfareId='
				     + appintmentList[i].welfareId + '" target="_blank">详情</a></td>');
		tr.appendTo(appointmentTBody);
		j++;
	}
}

		
		