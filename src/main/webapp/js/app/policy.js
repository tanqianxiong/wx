window.url = "";
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
					case 'name':
						$(this).val(item.name);
						break;
					case 'content':
						$(this).val(item.content);
						break;
					case 'createTime':
						$(this).val(item.createTime);
						break;
					}
					$("#content").text(item.content);
					window.url = "update.do";
				});
			}
		},
		error : function(jqXHR) {
			window.Modal.alert({msg:"发生错误：" + jqXHR.status});
		},
	});
}
function setData2Table(policyList) {
	var policyTBody = $('#listTable tbody');
	policyTBody.html('');
	var j=1;
	for (var i = 0; i < policyList.length; i++) { 
		var tr = $("<tr/>");
		tr.html('<td>' +j
				+ '</td><td>' + policyList[i].name
				+ '</td><td  data-toggle="tooltip" data-placement="right"  title=\"'+policyList[i].content+'\">' + window.simplifyBrief(policyList[i].content,25)
				+ '</td><td>' + window.parse2Date(policyList[i].createTime)
				+ '</td><td>'
				+ '<a title="点击修改" href="#" onclick="javascript:update(\''
				+ policyList[i].id + '\');" data-toggle="modal" data-target="#auModal">修改</a>'
				+ '&nbsp;&nbsp;<a title="点击删除" href="#" onclick="javascript:del(\''
				+ policyList[i].id + '\');">删除</a></td>');
		tr.appendTo(policyTBody);
		j++;
	}
}
function parse2Date(date){
	 var time = new Date(parseInt(date.time, 10));
	 var month = time.getMonth() + 1 < 10 ? "0" + (time.getMonth() + 1) : date.getMonth() + 1;
     var currentDate = time.getDate() < 10 ? "0" + time.getDate() : time.getDate();
     return time.getFullYear() + "-" + month + "-" + currentDate;
}
