$('.OnlineService_Chat').on('click', function(){
	var index = layer.open({
		id:1,
		type: 2,
		title: ['', 'background: url(http://chat.121314.com/Public/Home/img/logo.png) no-repeat;background-position: center;height: 62px;'],
		shadeClose: true,
		shade: 0,
		area: ['305px', '471px'],
		offset: 'rb', //右下角弹出
		shift: 2,//动画弹出类型
		content: ['http://chat.121314.com/', 'no'],
	}); 
});
$(document).ready(function(e) {
    $('.OnlineService_Chat a').attr("href","javascript:;");
});