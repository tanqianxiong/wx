$(function(){
	
	//
	$(".btn-book-update").click(function(){
		var c =$(this).closest("tr");
		var td=c.find("td");
		
		var form=$("#name").closest("form");
		var input=form.find("input");
		
		
		for(var i=0;i<input.length;i++){
		var val=td.eq(i).text();
		
		input.eq(i).val(val);
		//input.eq(i).attr("value",val);
		}
		$("#brief").text(td.eq(input.length).text());
		
	});
	$(".btn-book-delete").click(function(){
		var tr=$(this).closest("tr");
		tr.attr("class","predelete");
		var td=tr.find("td");
		var id=td.eq(0).text();
		alert(id);
		
	});
	$(".btn-book-delete-confirm").click(function(){
		$(".predelete").remove();
	});
	$(".btn-book-delete-quit").click(function(){
		$(".predelete").attr("class","");
	});
	$("#btn-book-add").click(function(){
		$("#table-book-list").append("<tr>");
		var input=$("#form-book-add").find("input");
		
		for(var i=0;i<input.length;i++){
			var val=input.eq(i).val();
			var temp="<td>"+val+"</td>";
			$("#table-book-list").append(temp);
			//val.val("1");
		}
		$("#table-book-list").append($("#addBrief").val());
		$("#table-book-list").append("</tr>");
	});
});