require.config({
	baseUrl : "../assets/js/",
    //urlArgs: "bust=" + (new Date()).getTime(), //开发环境使用。实际环境删除
    paths: {
        "jquery": "jquery1x",
        "bootstrap": "bootstrap",
        "datepicker": "date-time/bootstrap-datepicker",
        "jqGrid": "jqGrid/jquery.jqGrid.src",
        "gridLocale": "jqGrid/i18n/grid.locale-cn",
        "zDialog": "zDialog",
        "zDrag": "zDrag",
        "jqtips": "jquery.tips",
        "jqueryUICus": "jquery-ui.custom", //必须用这个
        "multiselect": "ui.multiselect",
        "jqueryForm": "jquery.form",
        "jqValidate": "jquery.validate.min",
        "chosen": "chosen.jquery",
        "aceEle" : "ace-elements",
        "jvbtm": "jquery-validate.bootstrap-tooltip.min"
    },
    shim: {
        'jqGrid': {
            deps: ['jquery', 'gridLocale', 'datepicker', 'multiselect'],
            exports: 'jqGrid'
        },
        'bootstrap': {
            deps: ['jquery'],
            exports: 'bootstrap'
        }, 'datepicker': {
            deps: ['bootstrap'],
            exports: 'datepicker'
        },
        'chosen': {
            deps: ['jquery'],
            exports: 'chosen'
        },
        'gridLocale': {
            deps: ['jquery'],
            exports: 'gridLocale'
        },
        'zDialog': {
            deps: ['jquery', 'zDrag'],
            exports: 'zDialog'
        },
        'jqtips': {
            deps: ['jquery'],
            exports: 'jqtips'
        },
        'jqueryUICus': {
            deps: ['jquery'],
            exports: 'jqueryUICus'
        },
        'multiselect': {
            deps: ['jquery', 'jqueryUICus'],
            exports: 'multiselect'
        },
        'jqueryForm': {
            deps: ['jquery', 'jqueryUICus'],
            exports: 'jqueryForm'
        },
        'jvbtm': {
            deps: ['jquery', 'bootstrap', 'jqValidate'],
            exports: 'jvbtm'
        },
		'ace' : {
			deps : [ 'jquery', 'bootstrap'],
			exports : 'ace'
		},
		'aceEle' : {
			deps : ['ace'],
			exports : 'aceEle'
		}
    }
});

require(['aceEle','jquery', 'jqGrid', 'zDialog', 'jqtips', 'jqueryForm'], function () {
    var baseUrl = location.protocol + "//" + location.host;
    var LocString = String(window.document.location.href);

    function getQueryStr(str) {
        var rs = new RegExp("(^|)" + str + "=([^&]*)(&|$)", "gi").exec(LocString), tmp;
        if (tmp = rs) {
            return tmp[2];
        }
        return "";
    }

    var contractId = getQueryStr("id");

    $.ajax({
        type: "POST",
        url: "/CF/admin/getIconList",
        success: function (response) {
            var $iconForm = $("#iconForm");
            $.each(response.data, function (index, item) {
              var image='<li class="icon-item"><label><div>'; 
              image+=  '<img  src="/CF'+item.path+'" width="96" height="96"></div> <input id=name'+item.id+' name="icon" type="checkbox"class="ace ace-checkbox-2 " ';
              image+=  ' value="'+item.id+'" onclick="chooseOne(this)"><span class="lbl" >id:'+item.id+'</span></label></li>';
              $("#imageList").append(image);
            });
            $.post("/CF/admin/getChooseIcon",{id:contractId},function(data) {
        		var imageid='#name'+data.data;
        		$(imageid).attr("checked", true);
        	});
            
            top.hideProBar();
           
        }
    });

   
    $("#save").click(function () {
        $("#saving").show();
        $("#save").hide();
        $("#iconForm").ajaxSubmit({
            url: "/CF/admin/saveIcon",
            data: {id: contractId},
            success: function (data) {
               if(data.status==0){
                    window.parent.Dialog.close();
               }else{
            	   alert('保存失败');
               }
            }
        });
    })

});