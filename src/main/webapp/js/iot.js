


$(function(){

	//在进入任何菜单页面后，先判断浏览器中是否已经存在cookie，if no 那么跳转致i账号绑定页面。
	
	
	//先搞个假的openId 
	setCookie("openId","abcd1234",100);
	//setCookie("username","defaultUserName",100);
	
	if (getCookie("username")==""){
		if(window.location.href == "http://localhost:8080/wx/pt/tobind.do"||window.location.href == "http://localhost:8080/wx/pt/binding.do"){

		}else{
			window.location.href="/wx/pt/tobind.do";			
		}
	}
	


//用户绑定模块
	//binding 页面 点击绑定按钮后，向服务器发送ajax，
	//服务器判断信息是否存在，若存在，则将cookie写入，同时服务器端做数据库的更新
	$("#bindbtn").click(function(event){
		event.preventDefault();
		var name = $("#username").val();
		var jobNumber = $("#jobNumber").val();
		var openId = getCookie("openId");
		if( name=='' || jobNumber==''){
			return ;
		}
		$.ajax({
		    type: "POST", 	
			url: "binding.do",
			data: {
				ajaxid:"bind",
				openId: openId,
				name:$("#username").val(),
				jobNumber:$("#jobNumber").val()
			},
			dataType: "json",
			success: function(result){
				//var data = JSON.parse(data);
				console.log(result);
				if (result.success) {
					setCookie("openId",openId,100);
					setCookie("username",name,100);
					setCookie("jobNumber",jobNumber,100);
					$("#bindbtn").hide();
					$("<span class='btn btn-lg btn-success btn-block'>绑定成功！<br/>现在开始借阅吧~</span>").appendTo("#bindcontainer");
					setTimeout(function(){
						window.location.href="/wx/pt/book/index.do";
					},500);
				} else {
					alert("抱歉，信息不匹配，请重新输入");
				}  
			},
			error: function(jqXHR){     
			   alert("发生错误：" + jqXHR.status);  
			},     
		});
	});
	


//图书搜索、借阅模块
	//图书搜索按钮，搜索内容不为空，则发起ajax的get方式请求
	$(".search_book_btn").click(function(){
		console.log("go 被按下");
		if($("#user_search").val()==''){
			console.log("搜索内容为空");
			return ;
		}
		var keyword = $("#user_search").val();
		console.log("搜索内容为:" + keyword);
		
		$.ajax({
		    type: "POST",
			url: "bookSearch.do",
			data: {
				ajaxid : "searchBook",
				keyword :  $("#user_search").val()
			},
			dataType: "json",
			success: function(result){
				if (result.success) {
					showResult(result);
					$(".tocategory").fadeTo(500,0.85);
				} else {
					alert("抱歉，您搜的图书不存在");
				}
			},
			error: function(jqXHR){  
			   alert("图书ajax发生错误：" + jqXHR.status);  
			},
		});
	});


	//负责搜索结果的展示
	function showResult(result){
		
		$("#six_category").fadeOut(10);
		$("#show_search_result").fadeIn(200);
		//var table = $("#show_search_result table");
		$("#show_search_result table tr:gt(0)").remove();

		dealResult(result);

	}

	//负责把搜索结果，向table中动态添加，即实现 json数据转化为td标签
	function dealResult(result){

		var table = $("#show_search_result table");

		$.each(result.list,function(k,v){
					
			var td_object={};
			$.each(v, function(k1, v1) {
				td_object[''+k1+'']=v1;
				console.log(k1+'  '+v1);
			});
			var tr=$("<tr/>");
			tr.html('<td>'+td_object.bookName+'</td><td>'+td_object.author+'</td><td>'+td_object.publisher+'</td><td><span class="go-popup detail btn-success">详情</span></td><td class="hide_td" >'+td_object.rowId+'</td><td class="hide_td">'+td_object.publishTime+'</td><td class="hide_td">'+td_object.amount+'</td><td class="hide_td">'+td_object.borrowed+'</td><td class="hide_td">'+td_object.points+'</td><td class="hide_td">'+td_object.brief+'</td>');
			tr.appendTo(table);
		});
		
	}





	//	详情click：
	//点击图书搜索结果中的详情按钮，显示该书可借信息
	$("table").on('click','.detail',function(){
		$(this).parents("tr").addClass("choosing");
		var tds = $("table tr.choosing").find("td");

		var bookName = tds.eq(0).text();

		var amount = tds.eq(6).text();
		var borrowed = tds.eq(7).text();
		var available = parseInt(tds.eq(6).text()) - parseInt(tds.eq(7).text());
		var points = tds.eq(8).text();
		var brief = tds.eq(9).text();

		console.log("用户点击了图书搜索的结果中的详情按钮，选中的书名为："+bookName+"，可借数量："+available);

		showDetail(tds.eq(0).text(),amount,available,points,brief);

	});

	//点击图书详情按钮中用到的显示弹出层函数
	function showDetail(bookName,total,available,points,brief){
		$("#bookName").text(bookName);
		$("#points").text(points);
		$("#totalnumber").text(total);
		$("#availablenumber").text(available);
		$("#brief").text(brief);
		$("#masker").fadeIn(100);
		$("#showdetail").fadeIn(100);
	}

	//弹出层交互，点击masker 隐藏 
	$("#masker").click(function(){
		//点击取消后，应该移除tr的choosing类
		$("#show_search_result tr").removeClass("choosing");
		$(this).fadeOut(100);
		$("#showdetail").fadeOut(100);
	});



	//借阅按钮
	$("#borrow").click(function(){
		//ajax 将用户信息传递给后台，使其更新数据库
		var tds = $("table tr.choosing").find("td");
		var bookId = tds.eq(4).text();
		var openId = getCookie("openId");

		$.ajax({
		    type: "POST", 	
			url: "borrow.do",
			data: {
				ajaxid : "borrow",
				bookId : bookId,
				openId : openId
			},
			dataType: "json",
			success: function(result){
				if (result.success) {
					//借阅成功
					//$("#show_search_result tr").removeClass("choosing");
					$("#borrow").hide();
					$("<span>借阅成功</span>").appendTo("#showdetail");
					setTimeout(function(){
						window.location.href="/wx/pt/book/index.do";
					},1000);
				} else {
					alert("系统错误");
				}
			},
			error: function(jqXHR){  
			   alert("图书借阅ajax发生错误：" + jqXHR.status);  
			},
		});	
	});

	//从搜索结果退回到六大类书目
	$(".tocategory").click(function(){
		$("#show_search_result").hide();
		$("#six_category").fadeIn();
		$(this).fadeOut();
	});



	//点击六大类,页面转向category.html,同时通过cookie的方式实现页面间传参
	$(".thumbnail").click(function(){
		var selected_category = $(this).data("category");
		setCookie("selected_category",selected_category,0001);
	});




	//在category页面发起ajax请求：
	if(window.location.href=="http://localhost:8080/wx/pt/book/category.do"){
		var selected_category = getCookie("selected_category");
		$(".category_info").prepend(selected_category);
		$.ajax({
			type:"POST",
			url:"book/category.do",
			data:{
				ajaxid : "searchByCategory",
				selected_category:selected_category
			},
			dataType:"json",
			success: function(result){
				if (result.success) {
					showCategory(result);
				} else {
					alert("抱歉，暂无藏书");
				}
			},
			error: function(jqXHR){
			   alert("分类页面加载ajax发生错误：" + jqXHR.status);  
			},
		});
	}
	function showCategory(result){
		$("#category_table tr:gt(0)").remove();

		var table = $("#category_table");
		
		$.each(result.list,function(k,v){
			var td_object={};
			$.each(v, function(k1, v1) {
				td_object[''+k1+'']=v1;
				console.log(k1+'  '+v1);
			});
			var tr=$("<tr/>");
			tr.html('<td>'+td_object.bookName+'</td><td>'+td_object.author+'</td><td>'+td_object.publisher+'</td><td><span class="go-popup detail btn-success">详情</span></td><td class="hide_td" >'+td_object.rowId+'</td><td class="hide_td">'+td_object.publishTime+'</td><td class="hide_td">'+td_object.amount+'</td><td class="hide_td">'+td_object.borrowed+'</td><td class="hide_td">'+td_object.points+'</td><td class="hide_td">'+td_object.brief+'</td>');
			tr.appendTo(table);
		});
		
	}



















//图书归还模块

	//在individual页面发起ajax请求：
	if(window.location.href=="http://localhost:8080/wx/pt/book/individual.do"){
		var openId = getCookie("openId");
		$.ajax({
			type:"POST",
			url:"individual/individual.do",
			data:{
				ajaxid : "borrowRecord",
				openId:openId
			},
			dataType:"json",
			success: function(result){
				if (result.success) {
					showRecord(result);
				} else {
					alert("抱歉，信息载入失败");
				}
			},
			error: function(jqXHR){
			   alert("个人记录ajax发生错误：" + jqXHR.status);  
			},
		});
	}
	
	function showRecord(result){
		$("#borrowing tr:gt(0)").remove();
		$("#borrowed tr:gt(0)").remove();

		var borrowingTable = $("#borrowing");
		var borrowedTable = $("#borrowed");
	
		//显示积分
		$("#score").text(result.score);
		//显示在借书目列表
		$.each(result.borrowing,function(k,v){
			var td_array=[];
			$.each(v, function(k1, v1) {
				td_array.push(v1);
			});
			console.log(td_array);
			var tr=$("<tr/>");
			tr.html('<td>'+td_array[0]+'</td><td>'+td_array[1]+'</td><td>'+td_array[2]+'</td><td><span class="go-popup escheat btn-success">归还</span></td>');
			tr.appendTo(borrowingTable);
		});
		//显示已经归还书目列表
		$.each(result.borrowed,function(k,v){
			var td_array=[];
			$.each(v, function(k1, v1) {
				td_array.push(v1);
			});
			console.log(td_array);
			var tr=$("<tr/>");
			tr.html('<td>'+td_array[0]+'</td><td>'+td_array[1]+'</td><td>'+td_array[2]+'</td>');
			tr.appendTo(borrowedTable);
		});
	}



	//点击还书(escheat点击区域变为整个tr) 弹出层出现,因为个人的借阅图书是从后台取得的，所以该事件应使用动态添加
	$("table.borrowing_table").on('click','tr',function(){
		//获取被点击的书目
		if($(this).index()==0){return}
		$(this).addClass("choosing");
		$("#masker-ind").fadeIn(100);
		$("#popup-ind").fadeIn(100);
		//console.log($(this));
	});
	//点击遮罩层，隐藏整个弹出层
	$("#masker-ind").click(function(){
		$("table.individual-table tr").removeClass("choosing");
		$(this).fadeOut(100);
		$("#popup-ind").fadeOut(100);
	});
	//点击确认还书
	$("#escheat-yes").click(function(){
		//发起ajax 更新个人的借阅信息
		var tds = $("table.individual-table tr.choosing").find("td");
		var bookName = tds.eq(0).text();
		var openId = getCookie("openId");

		$.ajax({ 
		    type: "POST", 	
			url: "ajax_server.php",
			data: {
				ajaxid: "escheat",
				openId: openId,
				bookName: bookName
			},
			dataType: "json",
			success: function(result){
				if (result.success) {
					updateRecord(result);
				} else {
					alert("归还失败，请稍后再试");
				}  
			},
			error: function(jqXHR){     
			   alert("ajax发生错误：" + jqXHR.status);  
			},
		});

	});


	function updateRecord(result){
		//从表格中删除该书
		//若归还成功，则
		alert(result.msg);
		$("#masker-ind").fadeOut(100);
		$("#popup-ind").fadeOut(100);
		//删除选中的书
		$(".choosing td").eq(3).remove();
		$("#borrowed_th").after($(".choosing"));
		$("#borrowing .choosing").remove();	
		$("tr").removeClass("choosing");	
	}

	//点击取消还书
	$("#escheat-no").click(function(){
		$("#masker-ind").fadeOut(100);
		$("#popup-ind").fadeOut(100);
		$("tr").removeClass("choosing");		
	});

	
	

});