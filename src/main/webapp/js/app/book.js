/*
 * 图书的私有js代码
 */
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
					switch($(this).attr('id')){
					case 'id':
						$(this).val(item.id);
						break;
					case 'ISBN':
						$(this).val(item.ISBN);
						break;
					case 'name':
						$(this).val(item.name);
						break;
					case 'author':
						$(this).val(item.author);
						break;
					case 'publisher':
						$(this).val(item.publisher);
						break;
					case 'year':
						$(this).val(item.year);
						break;
					case 'type':
						$(this).val(item.type);
						break;
					case 'totalNum':
						$(this).val(item.totalNum);
						break;
					case 'outNum':
						$(this).val(item.outNum);
						break;
					case 'points':
						$(this).val(item.points);
						break;
					case 'brief':
						$(this).val(item.brief);
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
function setData2Table(bookList){
	var bookTBody = $('#bookTable tbody');
	bookTBody.html('');
	for (var i = 0; i < bookList.length; i++) {
		var tr = $("<tr/>");
		tr.html('<td>' + bookList[i].ISBN + '</td><td>'
				+ bookList[i].name + '</td><td>'
				+ bookList[i].author + '</td><td>'
				+ bookList[i].publisher + '</td><td>'
				+ bookList[i].year + '</td><td>'
				+ bookList[i].type + '</td><td>'
				+ bookList[i].totalNum + '</td><td>'
				+ bookList[i].outNum + '</td><td>'
				+ bookList[i].points + '</td><td>'
				+ bookList[i].brief + '</td><td>'
				+ '<a title="点击修改" href="#" onclick="javascript:update(\''
				+ bookList[i].id + '\');">修改</a>'
				+ '<a title="点击删除" href="#" onclick="javascript:del(\''
				+ bookList[i].id + '\');">删除</a></td>');
		tr.appendTo(bookTBody);
	}
}
		
		