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
	
	var welfareId = getQueryString("welfareId");	
	//开始向后台请求获取数据
	$.ajax({
		type : "POST",
		url : "detail.do",
		dataType : "json",
		data:{
			welfareId:welfareId
		},
		success : function(result) {
			if (result.success) {
				//加载福利类型到Detail页面
				$('#welfareName').html(result.welfareName);
				var bookList = result.list;
				if (bookList.length > 0) {
					setDataTable(bookList);
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
//获取当前时间
function getCurrentTime($this){
	$this.html(new Date().toLocaleString());	
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