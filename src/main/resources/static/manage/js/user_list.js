require.config({
	baseUrl : "../assets/js/",
	//urlArgs: "bust=" + (new Date()).getTime(), //开发环境使用。实际环境删除
	paths : {
		"jquery" : "jquery1x",
		"bootstrap" : "bootstrap",
		"jqGrid" : "jqGrid/jquery.jqGrid.src",
		"gridLocale" : "jqGrid/i18n/grid.locale-cn",
		"zDialog" : "zDialog",
		"zDrag" : "zDrag",
		"jqtips" : "jquery.tips",
		"jqueryForm": "jquery.form",
		"jqValidate":"jquery.validate.min",
		"chosen" : "chosen.jquery",
		"moment":"date-time/moment",
		"momLocale":"date-time/zh-cn",
		"daterangepicker":"date-time/daterangepicker"
	},
	shim : {
		'jqGrid' : {
			deps : ['jquery', 'gridLocale'],
			exports : 'jqGrid'
		},
		'bootstrap' : {
			deps : [ 'jquery' ],
			exports : 'bootstrap'
		},
		'chosen' : {
			deps : [ 'jquery' ],
			exports : 'chosen'
		},
		'gridLocale' : {
			deps : [ 'jquery' ],
			exports : 'gridLocale'
		},
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
		'daterangepicker':{
			deps : [ 'bootstrap','momLocale','moment'],
			exports : 'daterangepicker'
		}
	}
});

require(['jquery','jqGrid','zDialog','jqtips','jqueryForm','chosen','jqValidate','daterangepicker'],function() {
		var baseUrl = location.protocol  +"//"+location.host+"/CF";
		var LocString = String(window.document.location.href);  
		function getQueryStr(str) { 
		        var rs = new RegExp("(^|)" + str + "=([^&]*)(&|$)", "gi").exec(LocString), tmp; 
		        if (tmp = rs) { 
		         return tmp[2]; 
		        } 
		        return ""; 
		}  
		var priviledgeId = getQueryStr("id");
	
		//页面上的div的ID
		var grid_selector = "#grid-table";
		var pager_selector = "#grid-pager";
		
		
		
		
		
		//查询按钮
		$("#searchButton").click(function () {
			var vsname= $("#name").val();
			 $(grid_selector).jqGrid("setGridParam", {
			     postData: { name:vsname},page:1
			 }); 
            $(grid_selector).trigger("reloadGrid"); 
		});
		
		//让表格根据页面大小改变大小
		$(window).on('resize.jqGrid',function() {
			//($(window.parent.document).find("iframe").height()-330)
			$(grid_selector).jqGrid('setGridWidth',$(".page-content").width());
			$(grid_selector).jqGrid('setGridHeight',433);
			$(window.parent.document).find("#iframepage_"+priviledgeId).height(document.body.scrollHeight);
		})
		jQuery(grid_selector).jqGrid({
			url : "/getAllUserInfo",
			datatype : "json",
			mtype : "post",
			jsonReader : {   
						root: "data.rows",
						page: "data.page",
						total: "data.total",
						records: "data.records" }  ,
			colNames : ['IDs','用户名', '电话','邮箱','创建时间'],
			colModel : [ {name : 'id',index : 'id',width : 90,hidden : true,key :true,sorttype : "int"},
						 {name : 'name',index : 'name',width : 150}, 
						 {name : 'tel',index : 'type',width : 150}, 
						 {name : 'email',index : 'type',width : 200}, 
						 {name : 'createTime',index : 'createTime',width : 90}],
			viewrecords : true, //是否在导航栏显示记录总数
			rowNum : 10,
			rowList : [ 10, 20, 30 ,40,50,60,70,80,99],
			pager : pager_selector,
			multiselect : true,
			postData : {priviledgeId:priviledgeId},
			loadComplete : function() {
						var table = this;
						setTimeout(function() {
							updateActionIcons(table);
							updatePagerIcons(table); //更换分页的图标
								enableTooltips(table);
						}, 0);
					}
		
			});
				
		$(window).triggerHandler('resize.jqGrid');// trigger window resize to make 	 the grid get the  correct size

		function updateActionIcons(table) {
			/**
			 * var replacement = { 'ui-ace-icon fa fa-pencil' :
			 * 'ace-icon fa fa-pencil blue', 'ui-ace-icon fa fa-trash-o' :
			 * 'ace-icon fa fa-trash-o red', 'ui-icon-disk' : 'ace-icon
			 * fa fa-check green', 'ui-icon-cancel' : 'ace-icon fa
			 * fa-times red' }; $(table).find('.ui-pg-div
			 * span.ui-icon').each(function(){ var icon = $(this); var
			 * $class = $.trim(icon.attr('class').replace('ui-icon',
			 * '')); if($class in replacement) icon.attr('class',
			 * 'ui-icon '+replacement[$class]); })
			 */
		}

		function updatePagerIcons(table) {
			var replacement = {
				'ui-icon-seek-first' : 'ace-icon fa fa-angle-double-left bigger-140',
				'ui-icon-seek-prev' : 'ace-icon fa fa-angle-left bigger-140',
				'ui-icon-seek-next' : 'ace-icon fa fa-angle-right bigger-140',
				'ui-icon-seek-end' : 'ace-icon fa fa-angle-double-right bigger-140'
		    };
			$('.ui-pg-table:not(.navtable) > tbody > tr > .ui-pg-button > .ui-icon')
			.each(
				function() {
					var icon = $(this);
					var $class = $.trim(icon.attr('class')
						.replace('ui-icon', ''));

					if ($class in replacement)
						icon.attr('class', 'ui-icon '+ replacement[$class]);
				}
			)
		}

		function enableTooltips(table) {
			$('.navtable .ui-pg-button').tooltip({
				container : 'body'
			});
			$(table).find('.ui-pg-div').tooltip({
				container : 'body'
			});
		}
				
		function userTypeFun(c,o,r) {
			if(c==0) {
				return  "<i class='blue fa fa-user bigger-180'></i>"
			} else {
				return  "<i class='green fa fa-user bigger-180'></i>"
			}
		}
		function isLockFun(c,o,r) {
			if(c) {
				return  "<span class='label label-danger arrowed-in'>是</span>"
			} else {
				return  "<span class='label label-success arrowed'>否</span>"
			}
		}
		function statusFun(c,o,r) {
			if(c==0) {
				return  "<span class='label label-success arrowed'>启用</span>"
			} else {
				return  "<span class='label label-danger arrowed-in'>禁用</span>"
			}
		}
function statuFun(c,o,r) {
			if(c==0) {
				return  "<label><input id='status' name='status'  class='ace ace-switch ace-switch-5 btn-rotate' type='checkbox' checked onclick='isChecked(this,"+r.id+")'><span class='lbl'></span></label>"
			} else {
				return  "<label><input id='status' name='status'  class='ace ace-switch ace-switch-5 btn-rotate' type='checkbox'  onclick='isChecked(this,"+r.id+")'><span class='lbl'></span></label>"
			}
		}
		$(document).one('ajaxloadstart.page', function(e) {
			$(grid_selector).jqGrid('GridUnload');
			$('.ui-jqdialog').remove();
		});
				
		//选择框JS
		if(!ace.vars['touch']) {
			$('.chosen-select').chosen({allow_single_deselect:true,search_contains:true}); 
			
			$(window).off('resize.chosen').on('resize.chosen', function() {
				$('.chosen-select').each(function() {
					var $this = $(this);
					$this.next().css({'width': $this.parent().width()});
				})
				}).trigger('resize.chosen');
				$(document).on('settings.ace.chosen', function(e, event_name, event_val) {
					if(event_name != 'sidebar_collapsed') return;
					$('.chosen-select').each(function() {
						 var $this = $(this);
							$this.next().css({'width': $this.parent().width()});
					})
				});
			
			$('#chosen-multiple-style .btn').on('click', function(e){
				var target = $(this).find('input[type=radio]');
				var which = parseInt(target.val());
				if(which == 2) $('#form-field-select-4').addClass('tag-input-style');
					else $('#form-field-select-4').removeClass('tag-input-style');
				});
				}
		$(window.parent.document).find("#iframepage_"+priviledgeId).height(document.body.scrollHeight);
	
});