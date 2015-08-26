/*
 * 福利的私有js代码
 */
window.url="";//用于add与update的转换
//状态启用按钮
function use(id,state){
	window.Modal.confirm({ msg: "确定"+state+"这条福利？" }).on(function (e) {
        if (e) {
			$.ajax({
				type : "POST",
				url : "use.do",
				dataType : "json",
				data:{
					id:id
				},
				success : function(result) {
					if (result.success) {
						window.location.reload();
					}
				},
				error : function(jqXHR) {
					window.Modal.alert({msg:"发生错误：" + jqXHR.status});
				},
			});
        }
	});
}
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
				var arr=$('#auModal input');
				arr.each(function(){
					switch($(this).attr('id')){
					case 'id':
						$(this).val(item.id);
						break;
					case 'introduction':
						$(this).val(item.introduction);
						break;
					case 'name':
						$(this).val(item.name);
						break;
					case 'state':
						$(this).val(item.state);
						break;
					}
					$("#introduction").text(item.introduction);
					window.url = "update.do";
				});
			}
		},
		error : function(jqXHR) {
			window.Modal.alert({msg:"发生错误：" + jqXHR.status});
		},
	});
}
function setData2Table(welfareList){
	var welfareTBody = $('#listTable tbody');
	welfareTBody.html('');
	var j=1;
	for (var i = 0; i < welfareList.length; i++) {
		var tr = $("<tr/>");
		tr.html('<td>' +j+'</td><td>' + welfareList[i].name + '</td><td  data-toggle="tooltip" data-placement="right"  title=\"'+welfareList[i].introduction+'\">'
				+ window.simplifyBrief(welfareList[i].introduction,20) + '</td><td>'
				+ welfareList[i].state + '</td><td>'
				+ '<a title="点击修改" href="#" onclick="javascript:update(\''
				+ welfareList[i].id + '\');" data-toggle="modal" data-target="#auModal">修改</a>'
				+ '&nbsp;&nbsp;<a title="点击删除" href="#" onclick="javascript:del(\''
		 		+ welfareList[i].id + '\');">删除</a>'
                + '&nbsp;&nbsp;<a title="'+window.transferSwith(welfareList[i].state)+'这条福利" href="#" onclick="javascript:use(\''
		        + welfareList[i].id + '\',\''+window.transferSwith(welfareList[i].state)+'\');">'+window.transferSwith(welfareList[i].state)+'</a></td>');
		j++;
		tr.appendTo(welfareTBody);
	}
}
function transferSwith(state){
	var res="停用";
	if(state=="停用"){
		res="启用";
	}
	return res;
}
		