var baseUrl = location.protocol  +"//"+location.host+"/";
var labelId,levelId,spotsId;
function chooseLabelId(id){
	labelId=id;
	 getData(1);
}
function chooseLevelId(id){
	levelId=id;
	 getData(1);
}
function chooseSpotsId(id){
	spotsId=id;

    getData(1);
}

	 $.post("/getAllLabel",function(data) {
		 if(data.status==0){
			 var result="<a class='work_tag cur' href='javascript:chooseLabelId();'>不限</a>";
			$.each(data.data, function(i, item) {
				result+="<a class='work_tag' href='javascript:chooseLabelId("+item.id+");'>"+item.name+"</a>"
			});
				$("#label").html(result);
				 $('.work_tag').click(function () {
				        $('.work_tag').removeClass('cur');
				        $(this).addClass('cur');
				       
				    });
		 }
		});
	 $.post("/getAllLevel",function(data) {
		 if(data.status==0){
			 var result="<a class='levels cur' href='javascript:chooseLevelId();'>不限</a>";
			$.each(data.data, function(i, item) {
				result+="<a class='levels' href='javascript:chooseLevelId("+item.id+");'>"+item.name+"</a>"
			});
				$("#level").html(result);
				 $('.levels').click(function () {
				        $('.levels').removeClass('cur');
				        $(this).addClass('cur');
				    });
		 }
	 });
	 $.post("/getAllSpots",function(data) {
		 if(data.status==0){
			 var result="<a class='store cur' href='javascript:chooseSpotsId();'>不限</a>";
				$.each(data.data, function(i, item) {
					result+="<a class='store' href='javascript:chooseSpotsId("+item.id+");'>"+item.name+"</a>"
				});
					$("#spots").html(result);
					 $('.store').click(function () {
					        $('.store').removeClass('cur');
					        $(this).addClass('cur');
					    });
		 }
	 });
    //搜索条件
    var G_page = '';
    var G_tag = '';
    var G_level = '';
    var G_order_by = '';
    var G_storeid = '';
    var G_startdate = '';
    var G_enddate = '';

   

    
   
    


    var setData = function (data) {
        var html = '';

        if (data) {
            for (i = 0; i < data.length; i++) {
                html += '<div class="col-xs-3 col-md-3 sycon">' +
                    '<div class="thumbnail sylist">' +
                    '<a href="photographer.html?id=' + data[i].id + '" target="_blank"><img src="'+baseUrl + data[i].head + '" alt="摄影师"></a>' +
                    '<div class="sycaption">' +
                    '<div class="clearfix syse">' +
                    '<div class="pull-left sysetit">' + data[i].name + '</div>' +
                    '</div>' +
                    '<div class="clearfix sylab">' +
                    '<div class="pull-left"><span>标签：</span>' + data[i].label + '</div>' +
                    '<div class="pull-right"><span>级别：</span>' + data[i].level + '</div>' +
                    '</div></div></div></div>';
            }
        } else {
            html = '暂无数据';
        }
        $('#photoerList').html(html);
    };

    var getData = function (page) {
        G_page = page || "1";
        G.ajax({
            url: "/getPhotographerListByPageStatus",
            data: {
                page: G_page,
                rows:8,
                labelId:labelId,
                levelId:levelId,
                spotsId:spotsId,
                start:G_startdate,
                end:G_enddate
                
            },
            callback: function (json) {
                setData(json.rows);
                tree.callPage({
                    total: json.records,
                    pageSize: 8,
                    onPage: json.page
                })
            }
        })
    };

    var tree = new G.pageTree();
    tree.init("#photoerPage");
    tree.setCallback(getData);

    $("#search_button").click(function () {
        G_startdate = $("#start").html();
        G_enddate = $("#end").html();
        getData(1);
    })
    

