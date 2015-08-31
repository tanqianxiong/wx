$(function(){
	
	//自定义alert与confirm弹出框
	window.Modal = function () {
	    var divStr='<div id="ycf-alert" class="modal fade" tabindex="-1" role="dialog" >'+
		      '<div class="modal-dialog modal-sm">'+
		      	'<div class="modal-content">'+
		      		'<div class="modal-header">'+
		      			'<button type="button" class="close" data-dismiss="modal"><span aria-hidden="true">×</span><span class="sr-only">关闭</span></button>'+
		         		'<h5 class="modal-title"><i class="fa fa-exclamation-circle"></i> [Title]</h5>'+
		          	'</div>'+
		          	'<div class="modal-body small">'+
		            '<p>[Message]</p>'+
		          '</div>'+
		          '<div class="modal-footer" >'+
		            '<button type="button" class="btn btn-primary ok" data-dismiss="modal">[BtnOk]</button>'+
		            '<button type="button" class="btn btn-default cancel" data-dismiss="modal">[BtnCancel]</button>'+
		          '</div>'+
		        '</div>'+
		      '</div>'+
		    '</div>';
	    var divDom=$("<div/>");
	    divDom.html(divStr);
	    var reg = new RegExp("\\[([^\\[\\]]*?)\\]", 'igm');
	    divDom.appendTo($('body'));
		var alr =$(divDom).find('#ycf-alert');
	    var ahtml = alr.html();
	    var _alert = function (options) {
	        alr.html(ahtml);	// 复原
	        alr.find('.ok').removeClass('btn-success').addClass('btn-primary');
	        alr.find('.cancel').hide();
	        _dialog(options);
	        return {
	            on: function (callback) {
	                if (callback && callback instanceof Function) {
	                    alr.find('.ok').click(function () { callback(true) });
	                }
	            }
	        };
	    };

	    var _confirm = function (options) {
	        alr.html(ahtml); // 复原
	        alr.find('.ok').removeClass('btn-primary').addClass('btn-success');
	        alr.find('.cancel').show();
	        _dialog(options);
	        return {
	            on: function (callback) {
	                if (callback && callback instanceof Function) {
	                    alr.find('.ok').click(function () { callback(true) });
	                    alr.find('.cancel').click(function () { callback(false) });
	                }
	            }
	        };
	    };

	    var _dialog = function (options) {
	        var ops = {
	            msg: "提示内容",
	            title: "操作提示",
	            btnok: "确定",
	            btncl: "取消"
	        };
	        $.extend(ops, options);
	        var html = alr.html().replace(reg, function (node, key) {
	            return {
	                Title: ops.title,
	                Message: ops.msg,
	                BtnOk: ops.btnok,
	                BtnCancel: ops.btncl
	            }[key];
	        });
	        alr.html(html);
	        alr.modal({
	            width: 300,
	            backdrop: 'static'
	        });
	    }
	    return {
	        alert: _alert,
	        confirm: _confirm
	    }
	}();
	
	

	//获取当前时间
	window.setInterval("getCurrentTime($('#currentTime'));",100);
	
	if(sessionStorage.itemsPerPage!=null){
		//alert("session存在");
		$('#itemsPerPage').val(sessionStorage.itemsPerPage);
	}
	$("kbd.count").attr('id','count');
	initPagination();
	$('#itemsPerPage').change(function(){
		sessionStorage.itemsPerPage=$(this).val();
		pageselectCallback(0,$("#pagination"));
	});
	/*$('#order').change(function(){
		pageselectCallback(0,$("#pagination"));
	});
	$('#keyword').change(function(){
		initPagination();
		pageselectCallback(0,$("#pagination"));
	});*/
	$('#getPage').click(function(){
		var page = $('#page').val();
		var re = /^\d+$/g;
		if(re.test(page)){
			if(page!=0)
			pageselectCallback(page-1,$("#pagination"));
			else alert("请输入正整数");
		}
		else alert("请输入正整数");
		$('#page').val("");
	});
});
//获取当前时间
function getCurrentTime($this){
	$this.html(new Date().toLocaleString());	
}

window.keyword="";
//初始化分页
function initPagination() {
	var init_num_entries=1;
	// 在指定区域创建分页,默认每页条数为10
	if($("#pagination")!=null){
	    $("#pagination").pagination(init_num_entries, {
	      callback: pageselectCallback,
	      //itemsPerPage:$('#itemsPerPage').val(),
	     });
	}
}
//page_index为第几页,第一页为0,jq是分页所在区域的Jquery对象
function pageselectCallback(page_index, jq){
	var welfareId = getQueryString("welfareId");
	$.ajax({
		type : "POST",
		url : "detail.do",
		data : {			
			welfareId:welfareId,			
			pageIndex : page_index,		
			itemsPerPage : $('#itemsPerPage').val(),		
		},
		dataType : "json",
		success : function(result) {
			if (result.success) {
				//加载福利类型到Detail页面
				$('#welfareName').html(result.welfareName);
				var bookList = result.list;
				if (bookList.length > 0) {
					//重新加载分页
					$("#pagination").pagination(result.count2, {
						callback: pageselectCallback,
				        load_first_page:false,
				        current_page:page_index,
				        items_per_page:$('#itemsPerPage').val(),
				     });
					$('#searchInfo').html('共'+result.count2+'条记录');
					$("#count").html(result.count);
					$("#count").attr('id','dontAlterMeAgain');
					setDataTable(bookList);
					
				} else {
					window.Modal.alert({msg:"没有找到相匹配的数据"});
				}
			} else {
				window.Modal.alert({msg:"抱歉，信息不匹配，请重新输入"});
			}
		},
		error : function(jqXHR) {
			window.Modal.alert({msg:"发生错误：" + jqXHR.status});
		},
	});
	return false;
}

function getQueryString(name) {
    var reg = new RegExp("(^|&)" + name + "=([^&]*)(&|$)", "i");
    var r = window.location.search.substr(1).match(reg);
    if (r != null) return unescape(r[2]); return null;
}

//通过预约申请
function agree(id,state){
	window.Modal.confirm({ msg: "确定"+state+"这条预约申请？" }).on(function (e) {
        if (e) {
			$.ajax({
				type : "POST",
				url : "agree.do",
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

//退回预约申请
function disAgree(id,state){
	window.Modal.confirm({ msg: "确定"+state+"这条预约申请？" }).on(function (e) {
        if (e) {
			$.ajax({
				type : "POST",
				url : "disAgree.do",
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

/*
 * 日期类型解析
 * @param d Java中的Date类型
 * @return yyyy-mm-dd
 */
function DateFormat(d)
 { 
	if(d==null){
		return "--";
	}
	var date = new Date(parseInt(d.time, 10));
    //月份得+1，且只有个位数时在前面+0
    var month = date.getMonth() + 1 < 10 ? "0" + (date.getMonth() + 1) : date.getMonth() + 1;
    //日期为个位数时在前面+0
	var currentDate = date.getDate() < 10 ? "0" + date.getDate() : date.getDate();
    //getFullYear得到4位数的年份 ，返回一串字符串
	return date.getFullYear() + "-" + month + "-" + currentDate;
}

function setDataTable(appintmentList){
	var appointmentTBody = $('#listTable tbody');
	var j=1;
	appointmentTBody.html('');
	var le=appintmentList.length;
	for (var i = 0; i < le; i++) {
		var tr = $("<tr/>");
		var ec=eval(appintmentList[i].employee);
		var userNo=ec.userNo;
		tr.html('<td>' +j
				     + '</td><td>' + userNo + '</td><td>'
				     + appintmentList[i].employee.username + '</td><td>'
				 //    + '</td><td>' + appintmentList[i].employee.userPhone + '</td><td>'				     
				     + window.DateFormat(appintmentList[i].applyTime) + '</td><td>'
				     + window.DateFormat(appintmentList[i].checkTime) + '</td><td>'
				     + appintmentList[i].state + '</td><td>'
				     + '&nbsp;&nbsp;<a title="点击通过" href="#" onclick="javascript:agree(\''
					 + appintmentList[i].id +'\',\''+ "通过" +'\');">通过</a>'
					 + '&nbsp;&nbsp;<a title="点击退回" href="#" onclick="javascript:disAgree(\''
					 + appintmentList[i].id +'\',\''+ "退回" + '\');">退回</a></td>');
		tr.appendTo(appointmentTBody);
		j++;
	}
}