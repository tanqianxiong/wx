/*
 * 此文件用于js的公共类，包括：删除、修改、搜索、精简简介、日期类型解析
 */

/*
 * 精简介绍内容，如超过一定数目，则下文以省略号代替
 * @param brief 简介之类的文本内容
 * @param len  截取长度
 * @return 截取长度之内的子串，后面内容用省略号代替
 */
function simplifyBrief(brief,len){
	if(brief.length>len){
		return brief.substring(0,len)+"...";
	} 
	else{
		return brief;
	}
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

function del(id){
	window.Modal.confirm({ msg: "确定删除这条记录？" }).on(function (e) {
        if (e) {
			$.ajax({
				type : "POST",
				url : "delete.do",
				dataType : "json",
				data:{
					id:id
				},
				success : function(result) {
					if (result.success) {
						window.Modal.alert({msg:'删除成功！'});
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
function addOrUpdate(){
	$.ajax({
		type : "POST",
		url : window.url,
		dataType : "json",
		data:$("#auModal form").serialize(),
		success : function(result) {
			if (result.success) {
				window.Modal.alert({msg:'操作成功！'});
				window.location.reload();
			}
		},
		error : function(jqXHR) {
			window.Modal.alert({msg:"发生错误：" + jqXHR.status});
		},
	});
}
//获取当前时间
function getCurrentTime($this){
	$this.html(new Date().toLocaleString());	
}

window.keyword="";
//初始化分页
function initPagination() {
  	var init_num_entries=1;
  	// 在指定区域创建分页,默认每页条数为10
    $("#pagination").pagination(init_num_entries, {
      callback: pageselectCallback,
      //itemsPerPage:$('#itemsPerPage').val(),
     });
 }
//page_index为第几页,第一页为0,jq是分页所在区域的Jquery对象
function pageselectCallback(page_index, jq){
	
	$.ajax({
		type : "POST",
		url : "list.do",
		data : {
			//keyword: $('#keyword').val(),
			keyword:window.keyword,
			pageIndex : page_index,		
			itemsPerPage : $('#itemsPerPage').val(),
			orderProp:$('#order').val(),
		},
		dataType : "json",
		success : function(result) {
			if (result.success) {
				var bookList = result.list;
				if (bookList.length > 0) {
					//重新加载分页
					$("#pagination").pagination(result.count, {
						callback: pageselectCallback,
				        load_first_page:false,
				        current_page:page_index,
				        items_per_page:$('#itemsPerPage').val(),
				     });
					$('#searchInfo').html('共'+result.count+'条记录');
					$("#count").html(result.count);
					$("#count").attr('id','dontAlterMeAgain');
					setData2Table(bookList);
					
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

//初始化需要加载的数据
$(function() {
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
	$('#order').change(function(){
		pageselectCallback(0,$("#pagination"));
	});
	/*$('#keyword').change(function(){
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
	$('#addBtn').click(function(){
		$("#auModal form").find('input').each(function(){
			$(this).val(null);
		});
		$("#auModal form").find('textarea').val(null);
		window.url="add.do";
	});
	$('#saveBtn').click(function(){
		addOrUpdate();
	});
	//获取当前时间
	window.setInterval("getCurrentTime($('#currentTime'));",100);
	//开始向后台请求获取数据
	/*$.ajax({
		type : "POST",
		url : "list.do",
		dataType : "json",
		success : function(result) {
			if (result.success) {
				var bookList = result.list;
				if (bookList.length > 0) {
					setData2Table(bookList);
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
	});*/
	//按关键字搜索			
	$('#searchBtn').click(function() {
		window.keyword=$('#keyword').val();
		initPagination();
		/*$.ajax({
			type : "POST",
			url : "list.do",
			data : {
				keyword : $('#keyword').val(),
				pageIndex:1,
				itemsPerPage:10
			},
			dataType : "json",
			success : function(result) {
				if (result.success) {
					var bookList = result.list;
					if (bookList.length > 0) {
						setData2Table(bookList);
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
		});*/
	});
	
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
});