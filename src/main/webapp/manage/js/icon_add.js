require.config({
	baseUrl : "../assets/js/",
	//urlArgs: "bust=" + (new Date()).getTime(), //开发环境使用。实际环境删除
	paths : {
		"jquery" : "jquery1x",
		"zDialog" : "zDialog",
		"zDrag" : "zDrag",
		"jqtips" : "jquery.tips",
		"jqueryForm": "jquery.form",
		"jqValidate":"jquery.validate.min",
		"datepicker" : "date-time/bootstrap-datepicker",
		"aceEle" : "ace-elements",
		"ace" : "ace",
		"chosen" : "chosen.jquery",
		"tag" : "bootstrap-tag"
	},
	shim : {
		'zDialog' : {
			deps : [ 'jquery','zDrag'],
			exports : 'zDialog'
		},
		'jqtips' : {
			deps : [ 'jquery' ],
			exports : 'jqtips'
		},
		'jqueryForm':{
			deps : [ 'jquery'],
			exports : 'jqueryForm'
		},
		'jqValidate' : {
			deps : [ 'jquery'],
			exports : 'jqValidate'
		},
		'chosen' : {
			deps : [ 'jquery' ],
			exports : 'chosen'
		},
		'datepicker' : {
			deps : [ 'bootstrap' ],
			exports : 'datepicker'
		},
		'ace' : {
			deps : [ 'jquery', 'bootstrap'],
			exports : 'ace'
		},
		'aceEle' : {
			deps : ['ace'],
			exports : 'aceEle'
		},
		'tag' :{
			deps : ['bootstrap'],
			exports : 'tag'
		}
	}
});

require(['aceEle','zDialog','jqtips','jqueryForm','jqValidate','datepicker','tag','chosen'],function() {
	var LocString = String(window.document.location.href); 
	var baseUrl = location.protocol  +"//"+location.host;
    
	$("#cancleButton").click(function () {
		window.parent.Dialog.close();
	});
	
	$('#addForm').validate({
		errorElement: 'div',
		errorClass: 'help-block',
		focusInvalid: false,
		ignore: "",
		rules: {
			
		},
		messages: {
			
		},
		highlight: function (e) {
			$(e).closest('.form-group').removeClass('has-info').addClass('has-error');
		},
		success: function (e) {
			$(e).closest('.form-group').removeClass('has-error');//.addClass('has-info');
			$(e).remove();
		},

		errorPlacement: function (error, element) {
			if(element.is('input[type=checkbox]') || element.is('input[type=radio]')) {
				var controls = element.closest('div[class*="col-"]');
				if(controls.find(':checkbox,:radio').length > 1) controls.append(error);
				else error.insertAfter(element.nextAll('.lbl:eq(0)').eq(0));
			}
			else if(element.is('.chosen-select')) {
				error.insertAfter(element.siblings('[class*="chosen-container"]:eq(0)'));
			}
			else error.insertAfter(element.parent());
		},
		submitHandler: function (form) {
			$("#addForm").ajaxSubmit({
				beforeSerialize : function(form, options) {
					$("#addButton").addClass("disabled");
					$("#save").attr("hidden",true);
					$("#saving").attr("hidden",false);top.showProBar();
					return true;
				},
				type : "post",
				url : "/CF/admin/addIcon", // 提交的URL 
				success : function(data) {
					$("#addButton").removeClass("disabled");
			    	$("#save").attr("hidden",false);
			    	$("#saving").attr("hidden",true);top.hideProBar();
					if (data.status == 0) { // 保存成功
						$("#addButton").tips({
							side : 3,
							msg : '保存成功！',
							bg : '#AE81FF',
							time : 3
						});
						
						window.parent.Dialog.close();
						
					} else {
						var msg = data.message;
						//console.log(msg);
						if(msg.lenght>=13) {
							$("#addButton").tips({
								side : 3,
								msg : msg,
								time : 3
							});
							$("#code").focus();
						} else {
							$("#addButton").tips({
								side : 3,
								msg : '保存失败！请检查数据！',
								time : 3
							});
						}
					}
				}
			});
		}
	});
	
	$("#addButton").click(function () {
		$('#addForm').submit();
		return false;
	});
	
	var setting  = {
		    //style: 'well', //缩略图样式
			no_file: '请选择文件',
			droppable: true, //拖拽功能开启,支持html5的浏览器有效
			//thumbnail: 'small', // 缩略图大小，仅启动style的时候有效，支持html5的浏览器有效
			btn_choose: '请选择',
			btn_change: '更改',
			maxSize: 10000000 
		}; 
	//初始化上传框
	$('#attachment').ace_file_input(setting); 
	
	//添加附件
	//the button to add a new file input
		$('#id-add-attachment').on('click', function(){  
		var file = jQuery('<input type="file" name="attachment" id="attachment" />').appendTo('#form-attachments');
		file.ace_file_input(setting);
		file.closest('.ace-file-input')
		.addClass('width-90 inline')
		.wrap('<div class="form-group file-input-container"><div class="col-sm-9"></div></div>')
		.parent().append('<div class="action-buttons pull-right col-xs-1">\
			<a href="#" data-action="delete" class="middle">\
				<i class="ace-icon fa fa-trash-o red bigger-130 middle"></i>\
			</a>\
		</div>')
		.find('a[data-action=delete]').on('click', function(e){
			//the button that removes the newly inserted file input
			e.preventDefault();
			$(this).closest('.file-input-container').hide(300, function(){ $(this).remove() });
		}); 
	});
	
	//给date-picker写样式才能使用
	$('.date-picker').datepicker({
		autoclose: true,				
		language: 'cn',
		todayHighlight: true
	}) 
	//日期图标的JS
	.next().on(ace.click_event, function(){
		$(this).prev().focus();
	});
	 
	
    
});