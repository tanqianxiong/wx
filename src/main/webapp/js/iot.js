//设置cookie
function setCookie(cname, cvalue, exdays) {
    var d = new Date();
    d.setTime(d.getTime() + (exdays*24*60*60*1000));
    var expires = "expires="+d.toUTCString();
    document.cookie = cname + "=" + escape(cvalue) + "; " + expires + + "; path=/";
}
//获取cookie
function getCookie(cname) {
    var name = cname + "=";
    var ca = document.cookie.split(';');
    for(var i=0; i<ca.length; i++) {
        var c = ca[i];
        while (c.charAt(0)==' ') c = c.substring(1);
        if (c.indexOf(name) != -1) return unescape(c.substring(name.length, c.length));
    }
    return "";
}
//清除cookie
function clearCookie(name) {  
    setCookie(name, "", -1);
}
//获取url参数的正则函数
function getQueryString(name) { 
    var reg = new RegExp("(^|&)" + name + "=([^&]*)(&|$)", "i");
    var r = window.location.search.substr(1).match(reg);
    if (r != null) return unescape(r[2]); return null;
}
$(function(){
//用户绑定模块
	//binding 页面 点击绑定按钮后，向服务器发送ajax，
	//服务器判断信息是否存在，若存在，则将cookie写入，同时服务器端做数据库的更新
	$("#bindbtn").click(function(event){
		event.preventDefault();
		var name = $("#username").val();
		var jobNumber = $("#jobNumber").val();
		var reg1 = /^[\u0391-\uFFE5|\w]+$/g;
		var reg2 = /^\d+$/g; 
		if(!reg1.test(name) || !reg2.test(jobNumber)){
			return;
		}
		//var openId = getCookie("openId");
		var openId = getQueryString("openId");
		
		if( name=='' || jobNumber==''){
			return;
		}
		$.ajax({
		    type: "POST",
			url: "binding.do",
			data: {
				openId: openId,
				name:$("#username").val(),
				jobNumber:$("#jobNumber").val()
			},
			dataType: "json",
			success: function(result){
				//console.log(result);
				if (result.success) {
					setCookie("username",name,100);
					setCookie("jobNumber",jobNumber,100);
					$("#bindbtn").hide();
					$("<span class='btn btn-lg btn-success btn-block'>绑定成功！<br/>现在开始借阅吧~</span>").appendTo("#bindcontainer");
					setTimeout(function(){
						window.location.href="/wx/pt/book/index.do?openId="+openId;
					},800);
				} else {
					alert("对不起，信息不匹配，请重输");
				}  
			},
			error: function(jqXHR){     
			   alert("网络错误：" + jqXHR.status);  
			},     
		});
	});
	
//图书搜索、借阅模块
	//图书搜索按钮，搜索内容不为空，则发起ajax的get方式请求
	$(".search_book_btn").click(function(){
		if($("#user_search").val()==''){
			//console.log("搜索内容为空");
			alert("请输入搜索内容");
			return ;
		}
		var keyword = $("#user_search").val();
		var reg = /^[\u0391-\uFFE5|\w]+$/g;
		if(!reg.test(keyword)){
			return ;
		}		
		//console.log("搜索内容为:" + keyword);	
		$.ajax({
		    type: "POST",
			url: "bookSearch.do",
			data: {
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
			   alert("网络错误：" + jqXHR.status);  
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
				//console.log(k1+'  '+v1);
			});
			var tr=$("<tr/>");
			tr.html('<td>'+td_object.bookName+'</td><td>'+td_object.author+'</td><td>'+td_object.publisher+'</td><td><span class="go-popup detail btn-success">详情</span></td><td class="hide_td" >'+td_object.id+'</td><td class="hide_td">'+td_object.publishTime+'</td><td class="hide_td">'+td_object.amount+'</td><td class="hide_td">'+td_object.borrowed+'</td><td class="hide_td">'+td_object.points+'</td><td class="hide_td">'+td_object.brief+'</td>');
			tr.appendTo(table);
		});
	}
	//	详情click：
	//点击图书搜索结果中的详情按钮，显示该书可借信息
	$("table").on('click','.detail',function(){
		$(this).parents("tr").addClass("choosing");
		var tds = $("table tr.choosing").find("td");
		//存图书信息
		var bookName = tds.eq(0).text();
		sessionStorage.bookName=bookName;
		var author = tds.eq(1).text();
		sessionStorage.author=author;		
		var publisher = tds.eq(2).text();
		sessionStorage.publisher=publisher;
		var bookId = tds.eq(4).text();
		sessionStorage.bookId=bookId;		
		var amount = tds.eq(6).text();
		sessionStorage.amount=amount;		
		var borrowed = tds.eq(7).text();
		sessionStorage.borrowed=borrowed;		
		var available = parseInt(tds.eq(6).text()) - parseInt(tds.eq(7).text());
		sessionStorage.available=available;		
		var points = tds.eq(8).text();
		sessionStorage.points=points;		
		var brief = tds.eq(9).text();
		sessionStorage.brief=brief;		
		//设好sessionStorage，跳转到detail页面
	
		
		var openId = getCookie("openId");
		//var openId = getQueryString("openId");
		window.location.href="/wx/pt/book/detail.do?openId="+openId;
		
		
		
	});
	//借阅按钮
	$("#borrow").click(function(){
		var bookId = sessionStorage.bookId;
		//var openId = getCookie("openId");
		var openId = getQueryString("openId");
		
		$.ajax({
		    type: "POST",
			url: "borrow.do",
			data: {
				bookId : bookId,
				openId : openId
			},
			dataType: "json",
			success: function(result){
				if (result.success) {
					//借阅成功
					$(".popup-detail").text("借阅成功");
					$("#masker").fadeIn(200);
					$(".popup-detail").fadeIn(200);
					setTimeout(function(){
						window.location.href="/wx/pt/book/index.do?openId="+openId;
					},1000);
				} else {
					//重复借阅
					$(".popup-detail").text("您已借过这本书了哦");
					$(".popup-detail").css("color","#93ebf5");
					$("#masker").fadeIn(200);
					$(".popup-detail").fadeIn(200);
					setTimeout(function(){
						window.location.href="/wx/pt/book/category.do?openId="+openId;
					},1000);
				}
			},
			error: function(jqXHR){  
			   alert("网络错误：" + jqXHR.status);  
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
		setCookie("selected_category",selected_category,0.005);
	});
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
		var bookId = tds.eq(4).text();
		//var openId = getCookie("openId");
		var openId = getQueryString("openId");
		var point = $("#trackBar").val();
		$.ajax({
		    type: "POST", 	
			url: "escheat.do",
			data: {
				openId: openId,
				bookId: bookId,
				point:point
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
			   alert("网络错误：" + jqXHR.status);  
			},
		});
	});
	function updateRecord(result){
		//从表格中删除该书
		//若归还成功，则
		//alert(result.msg);
		$("#masker-ind").fadeOut(100);
		$("#popup-ind").fadeOut(100);
		//删除选中的书
		$(".choosing td").eq(3).remove();
		$("#borrowed_th").after($(".choosing"));
		$("#borrowing .choosing").remove();	
		$("tr").removeClass("choosing");
		//还书后加分
		var newScore = parseInt($("#score").text())+1;
		$("#score").text(''+newScore+'');
	}
	//点击取消还书
	$("#escheat-no").click(function(){
		$("#masker-ind").fadeOut(100);
		$("#popup-ind").fadeOut(100);
		$("tr").removeClass("choosing");		
	});

//福利办理模块
	//点击办理按钮，发送ajax
	//用户点击提交按钮，发送ajax请求
	$("#freebtn").click(function(){
		var username = $("#getname").find("input").val();
		var jobNumber = $("#getnumber").find("input").val();
		var reg1 = /^[\u0391-\uFFE5|\w]+$/g;
		var reg2 = /^\d+$/g;
		if(!reg1.test(username) || !reg2.test(jobNumber)){
			if(username==''||jobNumber==''){
				alert("请输入姓名和工号");
			}else{
				alert("输入格式有误");
			}
			return;
		}
		//var openId = getCookie("openId");
		var openId = getQueryString("openId");
		
		var welfareIds = [];
		$("input:checked").each(function(){
            welfareIds.push(this.value);
        });
		welfareIds = welfareIds.join(",");
        //console.log(welfareIds);
        //console.log(openId);
        //console.log(username+'  '+jobNumber);     
        if(welfareIds==''){
        	alert("请选择要办理的业务");
        	return;
        }
		$.ajax({
			url:"handle.do",
			type:"POST",
			data:{
				openId: openId,
				welfareIds: welfareIds,
				username: username,
				jobNumber: jobNumber
			},
			dataType:"json",
			success: function(result){
				if(result.success){
					$("#masker-welfare").fadeIn(200);
					$(".popup-welfare").fadeIn(200);
					setTimeout(function(){
						$("#masker-welfare").fadeOut(200);
						$(".popup-welfare").fadeOut(200);
						$('input:text').val("");
						$("input:checked").attr("checked",false);
						$(".alreadyHandle").click();
						$("#collapseTwo").addClass("in");
					},2222);
				}else{
					alert("信息输入有误,请重输");
				}
			},
			error: function(jqXHR){
			   alert("对不起,网络错误：" + jqXHR.status);  
			},
		});
	});
	//用户点击已办理业务
	$(".alreadyHandle").click(function(){
		//var openId = getCookie("openId");
		var openId = getQueryString("openId");
		
		$.ajax({
			url:"hasHandled.do",
			type:"POST",
			data:{
				openId:openId
			},
			dataType:"json",
			success:function(result){
				if(result.success){
					var myhandle = $("#myhandle");
					myhandle.find("p").remove();
					for(var i=0;i<result.list.length;i++){
						var p = $("<p/>");
						welfareName = result.list[i].welfare.name;
						welfareState = result.list[i].state;
						p.html(welfareName + '<span style="float:right;margin-right:10px;">'+ welfareState +'</span>');
						p.prependTo(myhandle);
					}
				}
			},
			error:function(jqXHR){
				alert("对不起，查询失败：" + jqXHR.status);
			}
		});
	});
});



