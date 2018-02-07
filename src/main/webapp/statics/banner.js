/**
 * Created by mjay on 2016/6/15 0015.
 */
function index() {
    $.ajax({
        url:'/site/banner',
        dataType:'json',
        data:{posid:1},
        type:'post',
        success:function (data) {
            var html='<ul id="top">';
            var banner=data.data.list;
            if(data.status==1){
                for (var i=0;i<banner.length;i++){
                    html+='<li><a href="'+ banner[i].url +'" title=""><img src="'+ banner[i].pic +'" style="width: '+data.data.width+'px;height: '+ data.data.height +'px;"></a></li>';
                }
            }
            html+='</ul>';
            $("#banner").html(html);
        }
    })
}

var css = '<style>/*------------顶部------------*/' +
    '#top-banner {width:100%; min-width:1200px; min-height:525px; overflow:hidden;}' +
    '#top-banner li {width:100%; display:none;}' +
    '#top-banner li a { }' +
    '#top-banner li img {display:block; margin:0 auto;}' +
    '#top-banner .bx-wrapper { position: relative;*zoom: 1;}' +
    '/*加载界面*/' +
    '#top-banner .bx-wrapper .bx-loading { min-height:50px; background:#fff url(images/loading.gif) center center no-repeat; height:100%; width:100%; position:absolute; top:0; left:0; z-index:2000; }' +
    '/*导航 相对bx-wrapper定位*/' +
    '#top-banner .bx-wrapper .bx-pager { position: absolute; height:30px; background:url(images/line01.png) repeat-x center bottom; bottom:40px; z-index:80; width:100%; text-align: center;}' +
    '#top-banner .bx-wrapper .bx-pager.bx-default-pager a {background:url(images/flash_page.png) no-repeat; display:block; width:30px; height:30px; line-height:0; font-size:0; font-family:Arial;  margin:0 150px; outline:0;  }' +
    '#top-banner .bx-wrapper .bx-pager.bx-default-pager a:hover,' +
    '#top-banner .bx-wrapper .bx-pager.bx-default-pager a.active { background:url(images/flash_page_cur.png) no-repeat; text-decoration:none;}' +
    '#top-banner .bx-wrapper .bx-pager .bx-pager-item{ display:inline-block; *zoom:1; *display:inline;}' +
    '#top-close{cursor:pointer;background:#999;position:absolute;right:10px;top:10px;color:#fff;z-index:99999;padding:2px 6px;}</style>';


function banner() {
    $.post('http://cq.jfr121314.com/site-banner.html', {posid: 1}, function (data) {
        if (data.err || !data.list.length) {
            return false;
        }

        var tpl = [css, '<div id="top-close">关闭</div>', '<div id="top-banner">', '<ul id="top-banner-box">'];
        $.each(data.list, function (idx, el) {
            tpl.push('<li><a href="' , el.url , '"><img src="' , el.pic , '"></a></li>');
        });
        tpl.push('</ul></div>');

        var iCount = 1;
        $.each(data.list, function (idx, el) { // 预加载图片
            $('<img>', {src: el.pic}).load(function () {
                // console.log(iCount, data.list.length);
                if (iCount === data.list.length) { // 加载完成后显示轮播
                    $(tpl.join('')).prependTo('body');
                    runBxSlider();
                }
                iCount++
            });
        });

        function runBxSlider() {
            var $banner = $('#top-banner-box');
            $banner.bxSlider({
                mode: 'fade',
                autowidth: true,
                controls: false,
                auto: true,
                speed: 500,
                pause: 4000
            });

            var isDestroySlider = false;

            $('#top-close').on('click', function () {
                isDestroySlider = true; // 注销标志
                $banner.data('bxSlider').destroySlider(); // 注销轮播
                $('#top-close, #top-banner').remove(); // 移除节点
                // cookie.set('top-banner', 'hide', {expires: 1}); // 1天不加载banner
                sessionStorage['top-banner'] = 'hide'; // 当前会话只加载一次
            });

            var $top = $('#top-banner, #top-banner img, #top-banner .bx-viewport');
            var $nav = $(".Navwrap");
            var $win = $(window);

            $win.resize(function() {
                if (isDestroySlider) {
                    return true;
                }
                if ($win.width() < 1500) {
                    $nav.addClass("small");
                } else {
                    $nav.removeClass("small");
                }

                var height  = $win.height() - 60;
                height = height > 525 ? height : 525;
                // console.log(height);
                $top.animate({height: height});
            });

            $('#top-banner img').load(function () {
                $win.resize();
            });
        }

    }, 'json');
}


// 匹配 wildog.cn 和 www.wildog.cn
if (location.pathname === '/' && /^(?:www\.)?\w+\.\w+$/.test(location.hostname) && sessionStorage['top-banner'] !== 'hide') {
    banner(); // 首页大图 banner
}