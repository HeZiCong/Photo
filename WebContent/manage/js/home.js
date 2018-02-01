require.config({
    baseUrl: "assets/js/",
    //urlArgs: "bust=" + (new Date()).getTime(), //开发环境使用。实际环境删除
    paths: {
        "jquery": "jquery1x",
        "jquery2": "jquery.min",
        "bootstrap": "bootstrap",
        "zDialog": "zDialog",
        "zDrag": "zDrag",
        "jqtips": "jquery.tips",
        "jqueryForm": "jquery.form",
        "echarts": "echarts-all",
        "iframeResizer": "iframeResizer.contentWindow.min"
    },
    shim: {
        'bootstrap': {
            deps: ['jquery'],
            exports: 'bootstrap'
        },
        'zDialog': {
            deps: ['jquery', 'zDrag'],
            exports: 'zDialog'
        },
        'jqtips': {
            deps: ['jquery'],
            exports: 'jqtips'
        },
        'jqueryForm': {
            deps: ['jquery'],
            exports: 'jqueryForm'
        },
        'daterangepicker': {
            deps: ['bootstrap', 'momLocale', 'moment'],
            exports: 'daterangepicker'
        }
    }
});

require(['jquery2', 'zDialog', 'jqtips', 'jqueryForm', 'echarts', "iframeResizer"], function () {
    var LocString = String(window.document.location.href);
    var baseUrl = location.protocol + "//" + location.host;

    function getQueryStr(str) {
        var rs = new RegExp("(^|)" + str + "=([^&]*)(&|$)", "gi").exec(LocString), tmp;
        if (tmp = rs) {
            return tmp[2];
        }
        return "";
    }

    var priviledgeId = getQueryStr("id");
    $(window).on('resize.home', function () {
        $(window.parent.document).find("#iframepage_" + priviledgeId).height(document.body.scrollHeight);
    })

    /*// 基于准备好的dom，初始化echarts图表
    var myChart = echarts.init(document.getElementById('main'));*/
//	var option = null;
//	$.ajax({
//		type : "POST",
//		url : "<%=basePath%>ChartsServlet?method=getUserCountChart",
//	  	data : {
//			stime:v_stime,
//			etime:v_etime
//		},  
//		success : function(bardata) {
//			myChart.setOption(bardata);
//		}
//	}); 
    var nameList = [], valueList = [];
    // 同步执行
   /* $.ajaxSettings.async = false;
    $.post("/student/teacherHome/getStudentStatistics", function (response) {
        if (response.data != null && response.data != "") {
            nameList = response.data.nameList;
            valueList = response.data.valueList;
        }

    });*/
    var option = {
        tooltip: {
            show: true
        },
        legend: {
            data: ['在读人数']
        },
        xAxis: [
            {
                type: 'category',
                data: nameList, //["在读学生","毕业","休学","退学","结业"],
                axisLabel: {
                    show: true,
                    interval: 0,
                    formatter: function (val) {
                        return val.split("").join("\n");
                    }
                }
            }
        ],
        yAxis: [
            {
                type: 'value'
            }
        ],
        grid: { // 控制图的大小，调整下面这些值就可以，
            x: 80,
            x2: 80,
            y2: 90// y2可以控制 X轴跟Zoom控件之间的间隔，避免以为倾斜后造成 label重叠到zoom上
        },
        series: [
            {
                "name": "在读人数",
                "type": "bar",
                "data": valueList //[3823, 9898, 40, 10, 10]
            }
        ]
    };

    // 为echarts对象加载数据
  /*  myChart.setOption(option);*/

    /**
     * 注销
     */
    $("#logout").click(function () {
        $.ajax({
            type: "POST",
            url: "/CF/admin/logout",
            async: false,
            success: function (response) {
                window.parent.location.href = baseUrl + "/portalUI/login.html"
            }
        });
    });

    /**
     * 修改密码
     */
    $("#changePwd").click(function () {
        var setting = {
            id: "-1",
            title: "用户设置",
            icon: "fa-cog",
            url: "user/user_config.html",
            close: true
        };
        top.addTabs(setting);
    });

    /**
     * 新闻更多
     */
    $("#moreNews").click(function () {
//		var setting ={
//				id:"-2",
//				title:"新闻列表",
//				icon :"fa-cog",
//				url:"../page/news/news_list_client.html",
//				close:true
//		};
//		top.addTabs(setting);
//		window.open("../page/news/news_list_client.html");
        window.open("/portalUI/page/news/news_list_client.html")
    });

    /**
     * 用户信息加载
     */
   /* $.ajax({
        type: "POST",
        url: "/student/getUserInfo",
        success: function (response) {
            if (response.status == 0) {
                var data = response.data;
                $("#userId").val(data.userId);
                $("#userName").append(data.userName);//用户名
                $("#cardId").append(data.userCode);//卡号
                $("#departmentName").append(data.userDeparment);//部门
                $("#idAdress").append(data.userIpAddress);//ip地址
            }
        }
    });
*/
    /**
     * 新闻加载
     */
   /* $.ajax({
        type: "POST",
        url: "/student/teacherHome/getNewsList",
        async: false,
        success: function (response) {
            var tab_id = -3;
            if (response.data != null && response.data != "") {
                $.each(response.data, function (i, item) {
                    var param;
                    if (item.newstypeID == -999) {//新闻链接
                        param = item.newstypeID + ",\"" + item.content + "\"";
                    } else {
                        param = item.id + "," + tab_id--;
                    }*/
                    /*var option = "<div class='itemdiv homeCursor'  onclick='newsDetail(" + param + ")'>" +
                     "     <div class='user'>" +
                     "          \t<img src='image/news.png' alt=''>" +
                     "     </div>" +
                     "     <div class='body'>" +
                     "     <div class='name'>" +
                     "          <a href='#'>" + item.title + "</a>" +
                     "     </div>" +
                     "     <div class='time'>" +
                     "          <i class='ace-icon fa fa-clock-o'></i>" +
                     "          <span class='green'>" + item.createTime + "</span>" +
                     "     </div>" +
                     "     <div class='text'>" +
                     "     <i class='ace-icon fa fa-quote-left'></i>" +
                     item.summary +
                     "     </div>" +
                     "     </div>" +
                     "</div>";*/

                    /*var option = "<li class='list-item' onclick='newsDetail(" + param + ")'> <div class='inline list-left'> " +
                        "<i class='ace-icon fa fa-volume-up red'></i> <a href='#'>" + item.title + "</a> </div> " +
                        "<div class='inline list-right position-relative'> <div class='time'>" + item.createTime + "</div> " +
                        "<div class='tools action-buttons'> <a href='#' class='blue'> " +
                        "<i class='ace-icon fa fa-search-plus bigger-125'></i> </a> </div> </div> </li>"

                    $("#newsBody ul").append(option);
                });
            }
        }
    });*/


    /**
     * 通知更多
     */
    /*$("#moreMessages").click(function () {
        var setting = {
            id: "-30",
            title: "通知列表",
            icon: "fa-bell",
            url: "../home/teacher/message/message_list_more.html",
            close: true
        };
        top.addTabs(setting);
    });
*/
    /**
     * 通知加载
     */
    /*$.ajax({
        type: "POST",
        url: "/student/teacherHome/getMessageList?scopeType=1",
        async: false,
        success: function (response) {
            var tab_id = -20;
            if (response.data.results != null && response.data.results != "") {
                // $("#messageCount").append(response.data.total);
                $.each(response.data.results, function (i, item) {
                    var param;
                    if (item.isLink == 1) {//通知链接
                        param = item.isLink + ",\"" + item.content + "\"";
                    } else {
                        param = item.id + "," + tab_id--;
                    }*/
                    /*var option = "<li class='homeCursor' onclick='messageDetail(" + param + ")'>" +
                     "<i class='ace-icon fa fa-bell-o bigger-110 purple'></i>" + item.title +
                     "<div class='time pull-right' style='color:#666;font-size: 14px;padding-right:25px;'><i class='ace-icon fa fa-clock-o'>" +
                     "</i><span class='green' style='font-size:11px;font-weight:bold;'>" + item.publishTime + "</span></div>" +
                     "</li>";*/

                   /* var option = "<li class='list-item' onclick='messageDetail(" + param + ")'><div class='inline list-left'>" +
                        "<i class='ace-icon fa fa-caret-right green'></i>" +
                        "<a href='#'>" + item.title + "</a> </div> <div class='inline list-right position-relative'> " +
                        "<div class='time'>" + item.publishTime + "</div> <div class='tools action-buttons'> <a href='#' class='blue'> " +
                        "<i class='ace-icon fa fa-search-plus bigger-125'></i> </a> </div> </div> </li>";
                    $("#messageBody").append(option);
                });
            }
        }
    });*/

    /**
     * 天气预报
     */
    $.ajax({
        type: "POST",
        url: "http://api.map.baidu.com/telematics/v3/weather?location=%E5%B9%BF%E5%B7%9E&output=json&ak=Xym5LeLp3ebpGeGziRtZGUvA",
        dataType: 'JSONP',
        success: function (response) {
            if (response != null && response != "") {
                if (response.status == 'success') {
                    var currentDateData = response.results[0].weather_data[0];
                    $("#weatherTime").append(currentDateData.date.substring(3, 10));
                    $("#weatherTemperature").append(currentDateData.temperature);
                    $("#weatherPicture").attr("src", currentDateData.dayPictureUrl);
                }
            }
        }
    });

    /**
     * 根据权限配置图标
     */
   /* $.post("/student/userPriviledges/getShowIconListById", {userId: $("#userId").val()}, function (response) {
//根据权限展示相应的功能
        var $commonFunction = $("#commonFunction ul");
        $.each(response.data,function (index,item) {
            $commonFunction.append("<li class='icon-item' > " +
                "<div class='responsive-circle circle-shadow ' onclick='openTab(\"" + item.id + "\",\"" + item.url + "\",\"" + item.name + "\")' style='border-radius: 50%;background: " + item.color + "'>" +
                "<div class='circle-inner' ><i class='iconplus " + item.homeIcon + "' ></i> " +
                "</div> </div> <p><span onclick='openTab(\"" + item.id + "\",\"" + item.url + "\",\"" + item.name + "\")' >" + item.name + "</span></p> </li>");
        });
    });*/

})
;