var common = {
    //add_like or remove_like
//target  修改数字的地方
    add_remove_like: function (selector, target, absolute, withimg) {
        var target = target || false;
        var absolute = absolute || false;
        var withimg = withimg || false;
        var data = 'album_id';
        var url = 'http://cq.jfr121314.com/ajax/addFavorite.html';
        this._base(selector, target, absolute, url, data, withimg);
    },
    add_remove_follow: function (selector, target, absolute) {
        var target = target || false;
        var absolute = absolute || false;
        var data = 'photographer_id';
        var url = 'http://cq.jfr121314.com/ajax/addFollow.html';
        this._base(selector, target, absolute, url, data);
    },
    _base: function (selector, target, absolute, url, data, withimg) {
        var target = target || false;
        var absolute = absolute || false;
        var withimg = withimg || false;
        var url = url || '';
        var data = data || '';
        $(selector).click(function () {
            var id = $(this).data('bind');
            var that = this;
            var params = {};
            params[data] = id;
            if (withimg) {
                params['widthimg'] = 1;
            }
            $.ajax({
                url: url,
                data: params,
                type: 'GET',
                dataType: 'json',
                success: function (mdata) {
                    console.log(mdata)
                    if (mdata['err'] == 0) {
                        if (absolute) {
                            if (mdata['widthimg'] && mdata['widthimg'] != 0) {
                                $(target).html(mdata['widthimg']);
                            } else {
                                $(target).html(mdata['msg']);
                            }
                        } else {
                            if (target) {
                                if (mdata['widthimg'] && mdata['widthimg'] != 0) {
                                    $(target, that).html(mdata['widthimg']);
                                } else {
                                    $(target, that).html(mdata['msg']);
                                }
                            } else {
                                if (mdata['widthimg'] && mdata['widthimg'] != 0) {
                                    $(that).html(mdata['widthimg']);
                                } else {
                                    $(that).html(mdata['msg']);
                                }
                            }
                        }
                    } else if (mdata['err'] == 2) {
                       /* var xiao = $.layer({
                            type: 2,
                            shadeClose: true,
                            fix: false,
                            title: false,
                            closeBtn: [0, true],
                            shade: [0.8, '#000'],
                            border: [0],
                            offset: ['', ''],
                            area: ['900px', '700px'],
                            iframe: {src: 'http://cq.jfr121314.com/site/login.html?is_iframe=true'}
                        });*/
                        showLogin(SMALL);
                    } else {
                        alert(mdata['msg']);
                    }
                },
                error: function (e, xhr, s, err) {
                    console.log(e.responseText);
                }
            });
        });
    }
};
var dAnimator = {
    init: function () {
        var d = new DomAnimator();
        d.addFrame(['Author : Tsotsi']);
        d.addFrame(['（╯‵□′）╯︵┴─┴']);
        d.addFrame(['┴─┴︵╰（‵□′╰）']);
        d.addFrame(['┴┴︵╰（‵□′）╯︵┴┴ ']);
        d.animate(1000);
    }
}
//城市跳转
var mainTools = {
    template: '<a href="javascript:void(0);" class="hover" style="display:block;float:left;border:1px solid #cccccc;width:120px;padding:1px 2px;height:28px;line-height:28px;margin:8px 8px;">{data}</a>',
    changeCity: function (data, url, selector, key) {
        var that = this;
        var myid = this._showSelectOptions(data, url, key);
        $(selector).data('id', myid);
        $(selector).click(function () {
            $.layer({
                type: 1,
                shadeClose: true,
                fix: false,
                title: false,
                closeBtn: [0, true],
                shade: [0.8, '#000'],
                border: [0],
                offset: ['', ''],
                area: ['900px', '250px'],
                page: {dom: '#' + $(this).data('id')}
            });
        });
    },
    _showSelectOptions: function (data, url, key) {
        var key = key || 'area_id';
        var _id = Math.ceil(Math.random() * 10000) + Date.now();
        var url = url || '';
        var div = $('<div id="' + _id + '" style="display:none;"></div>');
        $('body').append(div);
        for (var i in data) {
            var a = $(this.template.replace('{data}', data[i]));
            a.data(key, i);
            div.append(a);
        }
        $('a', div).click(function () {
            var input = $('<input type="hidden" name="' + key + '" />');
            input.val($(this).data(key));
            var urli = $('<input type="hidden" name="url" />');
            urli.val(encodeURIComponent(location.href));
            var form = $('<form action="' + url + '" method="get" style="display:none;"></form>')
                .append(input).append(urli);
            $('body').append(form);
            form.submit();
        });

        return _id;
    },
    changeCity2: function (jq, selector, bindDoms, url, key) {
        var selector = selector || '';
        var url = url || '';
        var key = key || 'area_id';
        var h = 88 + $(selector + ' ul>li').size() * 20 + 'px';
        jq.click(function () {
            location.href = 'http://www.marryandjoy.com/?js=1';
            $.layer({
                type: 1,
                shadeClose: true,
                fix: false,
                title: false,
                closeBtn: [0, true],
                shade: [0.8, '#000'],
                border: [0],
                offset: ['', ''],
                area: ['660px', h],
                page: {dom: selector}
            });
        });

        $(bindDoms).click(function () {
            var input = $('<input type="hidden" name="' + key + '" />');
            input.val($(this).data('aid'));
            var urli = $('<input type="hidden" name="url" />');
            urli.val(encodeURIComponent(location.href));
            var form = $('<form action="' + url + '" method="get" style="display:none;"></form>')
                .append(input).append(urli);
            $('body').append(form);
            form.submit();
        });
    }
};
function linkage_place(selector, places, place_id) {
    var place_id = place_id || 0;
    var select_p = $('<select data-lv="1" ></select>');
    var select_c = $('<select data-lv="2"></select>');
    select_c.change(function () {
        select_area($(this).val(), 3);
    });
    select_p.change(function () {
        select_area($(this).val(), 2);
        select_c.trigger('change');
    });
    var lv = 3;

    function select_area(area_id, lv) {
        var lv = lv || 1;
        var data = {};
        if (lv == 1) {
            data = places;
        } else if (lv == 2) {
            select_c.empty();
            data = places[area_id]['children'];
        } else if (lv == 3) {
            $(selector).empty();
            data = places[select_p.val()]['children'][area_id]['children'];
        }
        for (var i in data) {
            if (lv == 1) {
                select_p.append('<option value="' + data[i]['id'] + '">' + data[i]['name'] + '</option>');
                continue;
            }
            if (lv == 2) {
                select_c.append('<option value="' + data[i]['id'] + '">' + data[i]['name'] + '</option>');
            }
            if (lv == 3) {
                $(selector).append('<option value="' + data[i]['id'] + '">' + data[i]['name'] + '</option>');
            }
        }
        if (data.length === 0 && lv == 3) {
            $(selector).append($('option:selected', select_c).clone());
        }
    }

    //            switch (lv){
//                    case 1:
//                    break;
//                    case 2:
//                    $(selector).before(select_c);
//
//                    break;
//                    case 3:
    $(selector).before(select_p);
    select_area(1, 1);
    $(selector).before(select_c);
//                    default:
//
//            }


    if (place_id > 0) {
        var mlv = 0;
        for (var i in places) {
            if (place_id == i) {
                mlv = places[i]['level'];
                select_p.val(place_id);
                break;
            }
            var t = places[i]['children'];
            for (var j in t) {
                if (place_id == j) {
                    mlv = t[j]['level'];
                    select_p.val(i);
                    select_p.trigger('change');
                    select_c.val(j);
                    select_c.trigger('change');
                    break;
                }

                var m = t[j]['children'];
                for (var k in m) {
                    if (place_id == k) {
                        mlv = m[k]['level'];
                        select_p.val(i);
                        select_p.trigger('change');
                        select_c.val(j);
                        select_c.trigger('change');
                        $(selector).val(k);
                        break;
                    }
                }

            }
        }
    } else {
        select_p.trigger('change');
    }
}
// 展现意向单
function show_attempt(photoer_id, start, end) {
    if (USER_ID < 1) {
        show_login();
        return;
    }
    var photoer_id = photoer_id || 0;
    var start = start || '';
    var end = end || '';
    $.layer({
        type: 2,
        shadeClose: true,
        fix: false,
        title: false,
        closeBtn: [0, true],
        shade: [0.8, '#000'],
        border: [0],
        area: ['800px', '600px'],
        iframe: {src: '/photoer/attempt.html?photoer_id=' + photoer_id + '&start=' + start + '&end=' + end}
    });
}

//重写点赞
function ClickBase() {
    this._base = function (e, callback, url, data) {
        var callback = callback || null;
        var e = e || null;//绑定的dom
        $.ajax({
            url: url,
            data: data,
            type: 'GET',
            dataType: 'json',
            success: function (mdata) {
                if (mdata['err'] == 0) {
                    if (typeof callback == 'function') {
                        callback(e, mdata);
                    } else {
                        throw new Error('callback 类似必须为 function ');
                    }
                } else if (mdata['err'] == 2) {
                    var xiao = $.layer({
                        type: 2,
                        shadeClose: true,
                        fix: false,
                        title: false,
                        closeBtn: [0, true],
                        shade: [0.8, '#000'],
                        border: [0],
                        offset: ['', ''],
                        area: ['900px', '700px'],
                        iframe: {src: 'http://cq.jfr121314.com/site/login.html?is_iframe=true'}
                    });
                } else {
                    alert(mdata['msg']);
                }
            },
            error: function (e, xhr, s, err) {

                console.log(e.responseText);
            }
        });
    };

}
function ClickLike(callback) {
    this.callback = callback;
    this.url = 'http://cq.jfr121314.com/ajax/addFavorite.html';
    this.data = {album_id: 0};
    this.defaultCallback = function (e, mdata) {
        $('span', e).html(mdata['msg']);
        $('span', e).attr('title', mdata['msg']);
    };
    this.addLike = function (e, album_id) {
        this.data['album_id'] = album_id;
        var t = this.callback || this.defaultCallback;
        this._base(e, t, this.url, this.data);
    };
    this.clickEvent = function (e) {
        var album_id = $(e).attr('data-id');
        this.addLike(e, album_id);
    };

}
ClickLike.prototype = new ClickBase();
var Favorite_func = {
    index: function (e, mdata) {
        var v = $('<span class=\'my_add_mis\' style=\'color:red;\'>' + mdata['data'] + '</span>');
        $(e).after(v);
        v.offset({
            left: $(e).offset().left + 8,
            top: $(e).offset().top - 12
        }).animate({
            opacity: 0.25,
            top: '-=10'
        }, 700, function () {
            v.remove();
        });

    }
};
//初始化 (不知道初始化什么东西)
$(function () {
    if ($('.scroll-banner-photoer').size() > 0) {
        $(window).scroll(function () {
            var wtop = $(this).scrollTop();
            $('#bdsharebuttonbox_me').hide();
            var p = $('.scroll-banner-photoer:first').offset();
            if (wtop > p.top && $('#scroll-banner-photoer-copy').size() < 1) {

                var c = $('.scroll-banner-photoer:first').clone(true);
                c.attr('id', 'scroll-banner-photoer-copy');
                c.appendTo('body');
                c.css({
                    'position': 'fixed',
                    top: '0px',
                    left: '0px',
                    'padding-left': p.left + 'px',
                    'margin-top': '0px'
//                    width: '1200px'

                });
                $('a.cs-share', c).css({
                    position: 'absolute',
                    'margin-left': '245px'
                });
                c.css({
                    /* older safari/Chrome browsers */
                    '-webkit-opacity': 0.6,
                    /* Netscape and Older than Firefox 0.9 */
                    '-moz-opacity': 0.6,
                    /* Safari 1.x (pre WebKit!) 老式khtml内核的Safari浏览器*/
                    '-khtml-opacity': 0.6,
                    /* IE9 + etc...modern browsers */
                    opacity: 0.6,
                    /* IE 4-9 */
                    filter: 'alpha(opacity=60)',
                    /*This works in IE 8 & 9 too*/
                    '-ms-filter': "progid:DXImageTransform.Microsoft.Alpha(Opacity=60)",
                    /*IE4-IE9*/
                    filter: 'progid:DXImageTransform.Microsoft.Alpha(Opacity=60)'
                });
                $('.scroll-banner-photoer:first').css({
                    /* older safari/Chrome browsers */
                    '-webkit-opacity': 0,
                    /* Netscape and Older than Firefox 0.9 */
                    '-moz-opacity': 0,
                    /* Safari 1.x (pre WebKit!) 老式khtml内核的Safari浏览器*/
                    '-khtml-opacity': 0,
                    /* IE9 + etc...modern browsers */
                    opacity: 0,
                    /* IE 4-9 */
                    filter: 'alpha(opacity=0)',
                    /*This works in IE 8 & 9 too*/
                    '-ms-filter': "progid:DXImageTransform.Microsoft.Alpha(Opacity=0)",
                    /*IE4-IE9*/
                    filter: 'progid:DXImageTransform.Microsoft.Alpha(Opacity=0)'
                });
            } else {
                if ($('#scroll-banner-photoer-copy').size() > 0 && wtop <= p.top) {
                    $('#scroll-banner-photoer-copy').remove();
                    $('.scroll-banner-photoer:first').css({
                        /* older safari/Chrome browsers */
                        '-webkit-opacity': 1,
                        /* Netscape and Older than Firefox 0.9 */
                        '-moz-opacity': 1,
                        /* Safari 1.x (pre WebKit!) 老式khtml内核的Safari浏览器*/
                        '-khtml-opacity': 1,
                        /* IE9 + etc...modern browsers */
                        opacity: 1,
                        /* IE 4-9 */
                        filter: 'alpha(opacity=100)',
                        /*This works in IE 8 & 9 too*/
                        '-ms-filter': "progid:DXImageTransform.Microsoft.Alpha(Opacity=100)",
                        /*IE4-IE9*/
                        filter: 'progid:DXImageTransform.Microsoft.Alpha(Opacity=100)'
                    });
                }

            }
        });

    }
    //workdetail

});

//星星事件
function starActive() {
    $('.tm-dsr-item .stars>.J_ratingStar').mouseenter(function () {
        var _class = 'tm-rating-level-' + $(this).data('star-value');
        if ($(this).parent('.stars').children('.J_ratingLevel').val() < $(this).data('star-value')) {
            var old_class = $(this).closest('.tm-dsr-item-content').attr('class');
            $(this).closest('.tm-dsr-item-content').attr('class', old_class.replace(/tm-rating-level-\d+/ig, ''));
            $(this).closest('.tm-dsr-item-content').addClass(_class);
        } else {
            $(this).closest('.tm-dsr-item-content').removeClass(_class).addClass(_class);
        }
    }).mouseleave(function () {
        var _class = 'tm-rating-level-' + $(this).data('star-value');
        var init_class = 'tm-rating-level-' + Math.floor($(this).parent('.stars').children('.J_ratingLevel').val() / 2);

        $(this).closest('.tm-dsr-item-content').removeClass(_class).addClass(init_class);
    }).click(function (e) {
        var level = $(this).data('star-value');
        var _class = 'tm-rating-level-' + level;
        $(this).parent('.stars').children('.J_ratingLevel').val(level * 2);
        var old_class = $(this).closest('.tm-dsr-item-content').attr('class');
        $(this).closest('.tm-dsr-item-content').attr('class', old_class.replace(/tm-rating-level-\d+/ig, ''));
//        console.log(e.target);alert(e.target);alert($(this).closest('.tm-dsr-item-content').attr('class'));
        $(this).closest('.tm-dsr-item-content').addClass(_class);
    });
    $('.tm-dsr-item .stars').each(function () {
        var init_class = 'tm-rating-level-' + Math.floor($(this).children('.J_ratingLevel').val() / 2);
//       $('.J_ratingStar:eq('+(v-1)+')',this).trigger('click');
        $(this).closest('.tm-dsr-item-content').addClass(init_class);
    });
}
//mobie
function starActiveM() {
    $('.tm-dsr-item .stars>.J_ratingStar').tap(function (e) {
        var level = $(this).data('star-value');
        var _class = 'tm-rating-level-' + level;
        $(this).parent('.stars').children('.J_ratingLevel').val(level * 2);
        var old_class = $(this).closest('.tm-dsr-item-content').attr('class');
        $(this).closest('.tm-dsr-item-content').attr('class', old_class.replace(/tm-rating-level-\d+/ig, ''));
//        console.log('1'+e.target);alert(e.target);alert($(this).closest('.tm-dsr-item-content').attr('class'));
        $(this).closest('.tm-dsr-item-content').addClass(_class);
    });
    $('.tm-dsr-item .stars').each(function () {
        var init_class = 'tm-rating-level-' + Math.floor($(this).children('.J_ratingLevel').val() / 2);
//       $('.J_ratingStar:eq('+(v-1)+')',this).trigger('click');
        $(this).closest('.tm-dsr-item-content').addClass(init_class);
    });
}

// 好像是提交评论
function commentIt(e, album_id, err, not_null,url) {
    layer.load(0, 3);
    var err = err || '';
    var flag = false;
    var not_null = not_null || '';
    url=url||'';
    var data = {
        StatisticsComment: {
            album_id: album_id,
            comment_content: $('#comment_statistics').val()
        }
    };
    $('.markwrap .tm-dsr-item .J_ratingLevel').each(function () {
        data['StatisticsComment'][$(this).attr('name')] = $(this).val();
        if ($(this).val() == 0) {
            flag = true;
        }
    });
    if (flag || !data['StatisticsComment']['comment_content']) {
        layer.closeAll();
        layer.alert(not_null, 8);
        return;
    }
    var that = e;

    $.ajax({
        url: 'http://cq.jfr121314.com/share/statisticsIt.html',
        method: 'POST',
        dataType: 'text',
        //dataType: 'text',
        data: data,
        success: function (mdata) {alert(mdata+'|1');
            layer.closeAll();
            layer.alert(mdata['msg'], mdata['err'] == 0 ? 9 : 8,false);
            if (mdata['err'] == 0) {
                $(that).closest('.comments').remove();
                $('#comment_statistics').attr('readonly', 'readonly');
                $('#comment_statistics').css('background-color', '#cccccc');

            }
        },
        error: function (e) {alert(e.responseText);alert(e.getResponseHeader());
            layer.closeAll();
            layer.alert(err, 8,false);
        }
    });

}
function addScrollTop() {
    $(function () {
        $('body').append('<div id="scrollToTop" style="width:60px;height:60px;display:none;position:fixed;right:5px;bottom:120px;border:1px solid #ddd"><a style="display:block;width:60px;height:60px;background:url(data:image/png;base64,iVBORw0KGgoAAAANSUhEUgAAADAAAAAwCAYAAABXAvmHAAAABHNCSVQICAgIfAhkiAAAAAlwSFlzAAALEwAACxMBAJqcGAAAAhlJREFUaIHtmL+L1EAUx988s3KFggibRg881G4X1P/gSptNsrCNf8NhcZbnNWJnYSXi/guLmwlsf7XC1rKIqHdncxwIx3Um71m4rzmS7GQyKYT5dJnHvPf9wIT8APB4PB6P5z9Gddl8MpncyPP8HhFdZln2EwDY9YyuBFQcxwfMfICIW+u1z8z8TGv9zekgl82kZxzHb5RSL64WmPkXIu7O5/Ovzoa5aiT9qsILriVcCmwML7iUcCVgHF5wJeFCoHF4wYVEWwHr8EJbiTYCrcMLbSRsBZyFF2wlbASchxdsJLDhDJvwrwHA6OmrlLpDREfj8fihafMmAjbh99M0PSSiXehIwvQI2YZ/KxdRFG0j4hEA3DfZbHqcTARahxe6kNgk4Cy84FqiViBJkkMAeGUyaE1teKGpBBGdFkXxeLFYnF+tVd7EURTdIqKXJgPWGIUHAMiy7KTJjY2Id3u93l5prWpTEAQPEPG6yQBoEF5oKgEAg7LFSgEiOgazT8DG4YUmEsz8vWy9UiBN0zMA+LChr3V4wVDigpnflRVqH2T9fv85M6cV5dbhhToJIrokoqdZlv0o23utrvFyuaThcJjmeX5bKfUE/gn/ZuY9rfV7F+GF1Wp1MRgMPhZF8UgptbNe/qKUSrTWn6r2Gb/MjUajm4gYhmF4PJ1O/7ROXEOSJGEQBFuz2ewEOvgV4/F4PB6Px+PxeAAA4C/ILDaOO6dLPQAAAABJRU5ErkJggg==) no-repeat center" href="#"></a></div>');
        $('#scrollToTop').mouseenter(function () {
            if ($('#scrollToTop:visible').size() > 0) {
                $(this).fadeTo(200, 0.6);
            }
        }).mouseleave(function () {
            if ($('#scrollToTop:visible').size() > 0) {
                $(this).fadeTo(200, 1);
            }
        }).click(function () {
            $(document).scrollTop(0);
        });
        $(document).on('scroll', function () {
            var s = $('#scrollToTop');
            if (s.size() > 0 && $(this).scrollTop() > 1200) {
                s.fadeTo(10, 1).fadeIn(300);
            }
            if (s.size() > 0 && $(this).scrollTop() <= 1200) {
                s.fadeOut(300);
            }
        });
        $(document).trigger('scroll');
    });

}
function moreArea(lang, url) {
    $(function () {
        $('body').append('<div id="moreArea" style="width:60px;height:60px;position:fixed;right:5px;bottom:50px;border:1px solid #ddd"><a style="font-size:16px;text-align: center;display:block;width:60px;height:60px;line-height: 30px;color:#ffffff;background-color: #ff0000;" href="javascript:void(0);">' + lang + '</a></div>');
        $('#moreArea').mouseenter(function () {
            if ($('#moreArea:visible').size() > 0) {
                $(this).fadeTo(200, 0.6);
            }
        }).mouseleave(function () {
            if ($('#moreArea:visible').size() > 0) {
                $(this).fadeTo(200, 1);
            }
        }).click(function () {
            location.href = url;
        });
    });

}

function showStarDetail(slt) {
    slt = slt || 'show_star_detail';
    var pid = $('#' + slt).attr('data-pid');
    var h = pid ? '?photoer_id=' + pid : '';
    var show = function () {

        var xiao = $.layer({
            type: 2,
            shadeClose: true,
            fix: true,
            title: '&nbsp;',
            closeBtn: [0, true],
            shade: [0.8, '#000'],
            border: [0],
            offset: ['0px', '0px'],
            area: ['380px', '100%'],
            iframe: {
                src: '/photoer/statistics.html' + h,
                scroll: 'auto'
            }
        });
    };
    $(".rate-score").click(show);
    $('#' + slt).click(show);
}

function showLogin(f) {

    f = f || false;
    var a = '';
    var area = ['900px', '700px'];
    if (f) {
        a = '&is_mobile=true';
        area = ['290px', '290px'];
        /*if($('#for_login_style').size()<1){
         var style = $('<style id="for_login_style"></style>');
         var s_h = '.form_d input {background: #f4c600 none repeat scroll 0 0; border: medium none;border-radius: 5px; color: #fff;font-size: 22px; height: 55px;width:260px;}';
         s_h += '.form_d {margin-top: 10px;}';
         s_h += '.form_a, .form_b {margin-bottom: 20px;}';
         s_h += '.errorMessage { color: #c33;  margin-top: -18px;}';
         s_h += '.form_log h2 {color: #333333;font-size: 24px;font-weight: normal; height: 45px; line-height: 45px;}';
         s_h += '.form_log {font-size: 14px; margin: 0 auto; width: 260px; }';
         s_h += '.clearfix::after { clear: both; content: ".";display: block; height: 0;  line-height: 0; visibility: hidden;}';
         s_h += '.form_a input { border: 1px solid #ccc;  font-size: 16px;  height: 50px;outline: medium none; text-indent: 1em; width: 260px;}';
         s_h += '#mobile_login{margin-left:5px;}';
         style.html(s_h);
         $('head').append(style);

         }
         var xiao = $.layer({
         type: 1,
         shadeClose: true,
         fix: true,
         title: false,
         closeBtn: [0, true],
         shade: [0.8, '#000'],
         border: [0],
         offset: ['', ''],
         area: area,
         page:{
         dom:'#mobile_login'
         }
         });*/
    }
    //}else{
    top._LOGIN_VIEW = top.$.layer({
            type: 2,
            shadeClose: true,
            fix: false,
            title: false,
            closeBtn: [0, true],
            shade: false,//[0.8, '#000'],
            border: [10, 0.3, '#000'],
            offset: ['', ''],
            area: area,
            iframe: {src: '/site/login.html?is_iframe=true' + a}
        });
    top.layer.close(top._REGISTER_VIEW);
    //}

}
function showRegister(f,url){

    url=url||'http://cq.jfr121314.com/site/register.html?is_iframe=true' ;
    f = f || false;
    var a = '';
    var area = ['900px', '700px'];
    if (f) {
        a = '&is_mobile=1';
        area = ['290px', '330px'];
    }
    top._REGISTER_VIEW = top.$.layer({
        type: 2,
        shadeClose: true,
        fix: false,
        title: false,
        closeBtn: [0, true],
        shade: false,//[0.8, '#000'],
        border: [10, 0.3, '#000'],
        offset: ['', ''],
        area: area,
        iframe: {src: url + a}
    });
    top.layer.close(top._LOGIN_VIEW);
}

/**
 * 废弃了, 使用新的插件
 * @link https://github.com/florian/cookie.js
 */
var docCookies = {
    getItem: function (sKey) {
        if (!sKey) {
            return null;
        }
        return decodeURIComponent(document.cookie.replace(new RegExp("(?:(?:^|.*;)\\s*" + encodeURIComponent(sKey).replace(/[\-\.\+\*]/g, "\\$&") + "\\s*\\=\\s*([^;]*).*$)|^.*$"), "$1")) || null;
    },
    setItem: function (sKey, sValue, vEnd, sPath, sDomain, bSecure) {
        if (!sKey || /^(?:expires|max\-age|path|domain|secure)$/i.test(sKey)) {
            return false;
        }
        var sExpires = "";
        if (vEnd) {
            switch (vEnd.constructor) {
                case Number:
                    sExpires = vEnd === Infinity ? "; expires=Fri, 31 Dec 9999 23:59:59 GMT" : "; max-age=" + vEnd;
                    break;
                case String:
                    sExpires = "; expires=" + vEnd;
                    break;
                case Date:
                    sExpires = "; expires=" + vEnd.toUTCString();
                    break;
            }
        }
        document.cookie = encodeURIComponent(sKey) + "=" + encodeURIComponent(sValue) + sExpires + (sDomain ? "; domain=" + sDomain : "") + (sPath ? "; path=" + sPath : "") + (bSecure ? "; secure" : "");
        return true;
    },
    removeItem: function (sKey, sPath, sDomain) {
        if (!this.hasItem(sKey)) {
            return false;
        }
        document.cookie = encodeURIComponent(sKey) + "=; expires=Thu, 01 Jan 1970 00:00:00 GMT" + (sDomain ? "; domain=" + sDomain : "") + (sPath ? "; path=" + sPath : "");
        return true;
    },
    hasItem: function (sKey) {
        if (!sKey) {
            return false;
        }
        return (new RegExp("(?:^|;\\s*)" + encodeURIComponent(sKey).replace(/[\-\.\+\*]/g, "\\$&") + "\\s*\\=")).test(document.cookie);
    },
    keys: function () {
        var aKeys = document.cookie.replace(/((?:^|\s*;)[^\=]+)(?=;|$)|^\s*|\s*(?:\=[^;]*)?(?:\1|$)/g, "").split(/\s*(?:\=[^;]*)?;\s*/);
        for (var nLen = aKeys.length, nIdx = 0; nIdx < nLen; nIdx++) {
            aKeys[nIdx] = decodeURIComponent(aKeys[nIdx]);
        }
        return aKeys;
    }
};


/**
 * layer 替换默认 alert
 * @param {string} msg 弹层信息
 * @param {number} time 几秒后关闭
 */
window.alert = function (msg, time) {
    layer.alert(msg, time);
};


/**
 * 修复老版本 layer 兼容
 * @param {object} opt 参数选项
 */
$.layer = function (opt) {
    if (opt.page) {
        if (opt.page.dom) {
            opt.content = $(opt.page.dom);
        } else if (opt.page.html) {
            opt.content = opt.page.html;
        }
    } else if (opt.iframe) {
        opt.content = opt.iframe.src;
        if (opt.iframe.scroll === 'auto') {
            $('iframe[name^=layui]').load(function() {
                layer.iframeAuto(layer_id);
            });
        }
    }

    var layer_id = layer.open(opt);

    return layer_id;
};

/**
 * 添加到收藏夹
 */
function addFavorite() {
    var url = window.location;
    var title = document.title;
    var ua = navigator.userAgent.toLowerCase();

    if (ua.indexOf("360se") > -1) {
        alert("由于360浏览器功能限制，请按 Ctrl+D 手动收藏！");
    } else if (ua.indexOf("msie 8") > -1) {
        window.external.AddToFavoritesBar(url, title); // IE8
    } else if (document.all) {
        try {
            window.external.addFavorite(url, title);
        } catch (e) {
            alert('您的浏览器不支持,请按 Ctrl+D 手动收藏!');
        }
    } else if (window.sidebar) {
        try {
            window.sidebar.addPanel(title, url, "");
        } catch (e) {
            alert('您的浏览器不支持,请按 Ctrl+D 手动收藏!');
        }
    } else {
        alert('您的浏览器不支持,请按 Ctrl+D 手动收藏!');
    }
}


/**
 * 输出倒计时时间
 * @param  {number} time php时间戳
 * @param  {[type]} fmt  生成格式
 * @return {string}      剩余时间
 */
function countdown(time, fmt) {
    if (String(time).length == 10) {
        time *= 1000;
    }

    fmt = fmt || '$d天 $h时 $m分 $s秒';

    var ms = new Date(time) - new Date().getTime();
    if (ms < 0) {
        return tofmt(0);
    }

    var sec = Math.floor(ms / 1000);
    var min = Math.floor(sec / 60);
    var hr = Math.floor(min / 60);
    var day = Math.floor(hr / 24);

    var dt = {
        s: sec % 60,
        m: min % 60,
        h: hr % 24,
        d: day
    };

    return tofmt(dt);

    function tofmt(dt) {
        return fmt.replace(/\$([dhms])/g, function(m, k) {
            var n = (dt[k] || 0);
            return n > 9 ? n : '0' + n;
        });
    }
}
$('#ishare').on("click",' .icon-heart',function(){
    var _that = $(this);
    var share_id = _that.data('id');
    var total_like = _that.children('span').html();
    $.ajax({
        url : '/share/zan',
        type : 'POST',
        data : {'share_id': share_id},
        dataType : 'json',
        success : function(data){
            if(data.status == -1){
                show_login();
                return false;
            }
            if(data.status == 1){
                _that.children().html(parseInt(total_like)+1);
            }
            alert(data.msg);
        },
        error : function(){
            alert('网络错误');
        }
    })
});
$('#sample').on("click",'.iheart',function(){
    var _that = $(this);
    var album_id = _that.data('id');
    var total_like = _that.children('span').html();
    $.ajax({
        url : '/works/zan',
        type : 'POST',
        data : {'album_id': album_id},
        dataType : 'json',
        success : function(data){
            if(data.status == -1){
                show_login();
                return false;
            }
            if(data.status == 1){
                _that.children().html(parseInt(total_like)+1);
            }
            alert(data.msg);
        },
        error : function(){
            alert('网络错误');
        }
    })
});
