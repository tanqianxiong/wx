


$(function(){

	//在进入任何菜单页面后，先判断浏览器中是否已经存在cookie，if no 那么跳转致i账号绑定页面。
	
//	if (getCookie("username")==""){
//		if(window.location.href == "http://mushaf.sinaapp.com/tobind.html"||window.location.href == "http://mushaf.sinaapp.com/binding.html"){
//
//		}else{
//			window.location.href="tobind.html";			
//		}
//
//	}


//用户绑定模块
	//binding 页面 点击绑定按钮后，向服务器发送ajax，
	//服务器判断信息是否存在，若存在，则将cookie写入，同时服务器端做数据库的更新
	$("#bindbtn").click(function(event){
		event.preventDefault();
		var name = $("#username").val();
		var jobnumber = $("#jobnumber").val();
		$.ajax({
		    type: "POST", 	
			url: "binding.do",
			data: {
				openid:"3edrfedew4e454", 
				name:$("#username").val(),
				jobnumber:$("#jobnumber").val()
			},
			dataType: "json",
			success: function(result){
				//var data = JSON.parse(data);
				console.log(result);
				if (result.success) {
					setCookie("username",name,100);
					$("#bindbtn").hide();
					$("<span class='btn btn-lg btn-success btn-block'>绑定成功！<br/>现在开始借阅吧~</span>").appendTo("#bindcontainer");
					setTimeout(function(){
						window.location.href="index.do";
					},1000);
				} else {
					alert("抱歉，信息不匹配，请重新输入");
				}  
			},
			error: function(jqXHR){     
			   alert("发生错误：" + jqXHR.status);  
			},     
		});
	});
	


//图书搜索模块
	//图书搜索按钮，搜索内容不为空，则发起ajax的get方式请求
	$(".search_book_btn").click(function(){
		console.log("go 被按下");
		if($("#user_search").val()==''){
			console.log("搜索内容为空");
			return ;
		}
		var bookName = $("#user_search").val();
		console.log("搜索内容为:" + bookName);
		
		$.ajax({
		    type: "POST", 	
			url: "book/bookSearch.do",
			data: {
				bookName :  $("#user_search").val()
			},
			dataType: "json",
			success: function(result){
				if (result.success) {
					$("#six_category").fadeOut(10);
					$("#show_search_result").fadeIn(200);
					var table = $("#show_search_result table");
					$("#show_search_result table tr:gt(0)").remove();
					var tr=$("<tr/>");
					tr.html('<td>'+result.list[0].name+'</td><td>'+result.list[0].author+'</td><td>+result.publish+</td><td><span class="go-popup detail btn-success">详情</span></td>');
					tr.appendTo(table);

				} else {
					alert("抱歉，您搜的图书不存在");
				}
			},
			error: function(jqXHR){  
			   alert("图书ajax发生错误：" + jqXHR.status);  
			},
		});
	});







	//点击图书中的详情按钮，向服务器请求该书信息的藏书数量信息
	$("table").on('click','.detail',function(){
		$(this).parents("tr").addClass("choosing");
		var tds = $("table tr.choosing").find("td");
		console.log("用户点击了图书搜索的结果中的详情按钮，选中的书名为："+tds.eq(0).text());
		showDetail(tds.eq(0).text(),14,5);
/*
		$.ajax({ 
		    type: "POST", 	
			url: "serverjson.php",
			data: {
				bookname: $("#staffName").val(), 
				author: $("#staffNumber").val(), 
				publish: $("#staffSex").val()
			},
			dataType: "json",
			success: function(data){
				if (data.success) {
					//获得返回数据，作为下面函数的参数
					var total = 0;
					var available = 0;

					//显示书目详情弹窗
					showDetail(total,available);
				} else {
					
				}  
			},
			error: function(jqXHR){     
			   alert("发生错误：" + jqXHR.status);  
			},     
		});
*/

	});

	//点击图书详情按钮中用到的显示弹出层函数
	function showDetail(bookName,total,available){
		$("#bookName").text(bookName);
		$("#totalnumber").text(total);
		$("#availablenumber").text(available);
		$("#masker").fadeIn(100);
		$("#showdetail").fadeIn(100);
	}

	//弹出层交互，点击masker 隐藏 
	$("#masker").click(function(){
		$(this).fadeOut(100);
		$("#showdetail").fadeOut(100);
	});
	//借阅按钮
	$("#borrow").click(function(){
		//ajax 将用户信息传递给后台，使其更新数据库
		
		$.ajax({
		    type: "GET", 	
			url: "ajax_server.php？wo=daanteng",
			data: {
				bookName :  $("#bookName").val()
			},
			dataType: "json",
			success: function(result){
				if (result.success) {
					//借阅成功
					$(this).hide();
					$("<span>借阅成功</span>").appendTo("#showdetail");
					setTimeout(function(){
						window.location.href="index.html";
					},1000);
				} else {
					alert("系统错误");
				}
			},
			error: function(jqXHR){  
			   alert("图书归还ajax发生错误：" + jqXHR.status);  
			},
		});		




		
	});




	//点击还书(escheat点击区域变为整个tr) 弹出层出现,因为个人的借阅图书是从后台取得的，所以该事件应使用动态添加
	$("table.individual-table").on('click','tr',function(){
		//获取被点击的书目
		if($(this).index()==0){return}
		$(this).addClass("choosing");
		$("#masker-ind").fadeIn(100);
		$("#popup-ind").fadeIn(100);
		//console.log($(this));
	});
	//点击遮罩层，隐藏整个弹出层
	$("#masker-ind").click(function(){
		$(this).fadeOut(100);
		$("#popup-ind").fadeOut(100);
	});
	//点击确认还书
	$("#escheat-yes").click(function(){
		//发起ajax 更新借阅
		/*
		$.ajax({ 
		    type: "POST", 	
			url: "ajax_server.php",
			data: {
				bookname: $("#staffName").val(), 
				author: $("#staffNumber").val(), 
				publish: $("#staffSex").val()
			},
			dataType: "json",
			success: function(data){
				if (data.success) {
					//获得返回数据，作为下面函数的参数
       //在这里做些事情，假设返回的json数据里有name这个属性
       //有时候可以直接data.name或者data['name']去访问
       //但有时候，却要通过var jsonData = eval("("+
       data.responseText+")");才可以通过jsonData.name访问，而且这种
       情况下，需要是complete而不是success					
					var total = 0;
					var available = 0;

					//显示书目详情弹窗
					showDetail(total,available);
				} else {
					
				}  
			},
			error: function(jqXHR){     
			   alert("发生错误：" + jqXHR.status);  
			},     
		});*/


		//从表格中删除该书
		var mydata = '';
		var tds = $("table .choosing").find("td");
		tds.each(function(){
			mydata+=$(this).text()+','
		});
		var dataforsend = mydata.split(",");
		dataforsend.pop();
		dataforsend.pop();
		console.log(dataforsend);

		//若归还成功，则
		alert("归还成功");
		$("#masker-ind").fadeOut(100);
		$("#popup-ind").fadeOut(100);
		//删除呗选中的书
		$(".choosing").remove();
	});

	//点击取消还书
	$("#escheat-no").click(function(){
		$("#masker-ind").fadeOut(100);
		$("#popup-ind").fadeOut(100);
		$("tr").removeClass("choosing");		
	});















	//模拟ajax动态添加tr元素
	$("#yumy").click(function(){
		var book="半城烟沙";
		var author="鲁迅";
		var publish="ABC";
		var newli = $("<tr><td>"+book+"</td><td>"+author+"</td><td>"+publish+"</td><td><span class='go-popup escheat btn-success'>归还</span>"+"</tr>");
		newli.appendTo("#borrowing");	
	});
	// 六大类图书浏览页面中的返回按钮，回到搜索页面。
	$("#goback").click(function(){
		window.history.back();
	});
});