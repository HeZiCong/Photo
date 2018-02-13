$(function() {
	

	var baseUrl = location.protocol + "//" + location.host + "/";
	$.post("/getAllBanner", {
		page: 1,
		rows: 99
	}, function(data) {
		if(data.status == 0) {
			$.each(data.data.rows, function(i, item) {
				switch(i) {
					case 0:
						$("#banner1").attr({
							src: baseUrl + item.path,
							title: item.title
						});
						break;
					case 1:
						$("#banner2").attr({
							src: baseUrl + item.path,
							title: item.title
						});
						break;
					case 2:
						$("#banner3").attr({
							src: baseUrl + item.path,
							title: item.title
						});
						break;
				}
			});

		}
	});
	$.post("/getSixInfo", {}, function(data) {
		if(data.status == 0) {
			var scroll = "";
			var btn = "";
			$.each(data.data, function(i, item) {

				scroll += "<li><a href='info.html?id=" + item.id + "' target='_blank'> <img width='588' height='365' src='" + baseUrl + item.path + "'></a></li>";

				btn += "<li";
				if(i == 0) {
					btn += " class='on'"
				}
				btn += "><a href='info.html?id=" + item.id + "' target='_blank'> <dfn>0" + (i + 1) + "</dfn><div class='play-txt'><div class='txt-con'><h3>" + item.title + "</h3><span>POST TIME: " + item.createTime + "</span> </div><div class='seemore'><span>see more</span></div></div></a></li>";

			});
			$("#play-scroll").html(scroll);
			$("#play-btn").html(btn);
			$("#play-btn li").each(function(i, j) {
				$(this).hover(function() {
					go_to(i);
				});
			});
		}
	});
	
	$.post("/getFiveSpots", {}, function(data) {
		if(data.status == 0) {
			var scroll = "";
			var btn = "";
			$.each(data.data, function(i, item) {

				scroll += "<li><a href='spots.html' target='_blank'> <img width='855' height='520' src='"+baseUrl+item.path+"'></a></li>";

				btn += "<li";
				if(i == 0) {
					btn += " class='on'"
				}
				btn += "><a href='spots.html' target='_blank'><div class='show-txt'><h3>"+item.name+"<span></span></h3><span class='con'>"+item.content+"</span> <i></i> </div></a></li>";
			});
			$("#play-scroll2").html(scroll);
			$("#play-btn2").html(btn);
			$("#play-btn2 li").each(function(i, j) {
		$(this).hover(function() {
			go_to2(i);
		});
	});
		}
	});
		$.post("/getWorksListByPageStatus", {
					sord: "DESC"
				}, function(data) {
					if(data.status == 0) {
						var result = "";
						$.each(data.data, function(i, item) {
							if(i<4){
							result += "<li><a href='works.html?id="+item.id+"' target='_blank'> <img src='" + baseUrl + item.path + "' /><div class='info-box'><div class='photos-txt clearfix'><h4>" + item.title + "</h4> </div><div class='photos-pt clearfix'> <i></i> <span>POST TIME: " + new Date(item.createTime).format("yyyy-MM-dd") + "</span> </div></div><div class='photos-shade'> <span></span><h4>" + item.title + "</h4></div></a></li>";
							}
						});

						$("#works").html(result);
					}
					$(".C-photos-list a").hover(function() {
						$(this).find(".photos-shade").show();
					}, function() {
						$(this).find(".photos-shade").hide();
					});
				});

});
//右侧拉条特效
$(function() {
	$(window).resize(function() {
		goTop();
	});
	$(window).scroll(function() {
		goTop();
	});
	$("a").focus(function() {
		$(this).blur();
	});
});

function goTop() {
	$(".shiftright").stop().animate({
		"top": ($(window).height() + $(window).scrollTop() - ($(window).height()) / 2) < 822 ? 822 : $(window).height() + $(window).scrollTop() - ($(window).height()) / 2
	}, 600);
}
$(function() {
	$(".shiftright").click(function() {
		$('body,html').animate({
			scrollTop: 0
		}, 1000);
		return false;
	});
});

//广告大图自动翻
$(function() {
	(function() {
		var curr = 0;
		var picLen = $(".bigpic img").length;
		$(".bigpic .pica").eq(0).show();
		$(".jsNav .trigger").each(function(i) {
			$(this).click(function() {
				curr = i;
				$(".bigpic .pica").eq(i).fadeIn("slow").siblings(".pica").hide();
				$(this).siblings(".trigger").removeClass("imgSelected").end().addClass("imgSelected");
				return false;
			});
		});

		var pg = function(flag) {
			//flag:true表示前翻， false表示后翻
			if(flag) {
				if(curr == 0) {
					todo = 2;
				} else {
					todo = (curr - 1) % picLen;
				}
			} else {
				todo = (curr + 1) % picLen;
			}
			$(".jsNav .trigger").eq(todo).click();
		};

		//前翻
		$("#prev").click(function() {
			pg(true);
			return false;
		});

		//后翻
		$("#next").click(function() {
			pg(false);
			return false;
		});

		//自动翻
		var timer = setInterval(function() {
			todo = (curr + 1) % picLen;
			$(".jsNav .trigger").eq(todo).click();
		}, 5000);

		//鼠标悬停在触发器上时停止自动翻
		$(".jsNav a").hover(function() {
				clearInterval(timer);
			},
			function() {
				timer = setInterval(function() {
					todo = (curr + 1) % picLen;
					$(".jsNav .trigger").eq(todo).click();
				}, 5000);
			}
		);
	})();
});

//新闻、目的地动态图片切换
var cfg = {
	"scroll": 500, //滚动时间
	"stop": 3000, //停留时间
	"num": 6 //图片数
};

function run() {
	if(parseInt($("#play-scroll").css("left")) > -(588 * (cfg.num - 1))) {
		run.index++;
		$("#play-scroll").animate({
			left: '-=588px'
		}, cfg.scroll);
		$("#play-btn li.on").removeClass("on");
		$("#play-btn li").eq(run.index).addClass("on");
		start_auto();
	} else {
		run.index = 0;
		$("#play-scroll").animate({
			left: '0px'
		}, cfg.scroll);
		$("#play-btn li.on").removeClass("on");
		$("#play-btn li").eq(run.index).addClass("on");
		start_auto();
	}
}
run.index = 0;
run.time = 0;

function go_to(index) {
	run.index = index;
	var left = 588 * index;
	$("#play-scroll").stop().animate({
		left: '-' + left + 'px'
	}, cfg.scroll);
	$("#play-btn li.on").removeClass("on");
	$("#play-btn li").eq(run.index).addClass("on");
}

function start_auto() {
	stop_auto();
	run.time = setTimeout(run, cfg.stop);
}

function stop_auto() {
	clearTimeout(run.time);
}
$(function() {
	start_auto();
	$("#play-btn,#play-scroll").hover(function() {
		stop_auto();
	}, function() {
		start_auto();
	});

});

//拍摄场景图片切换
var cfg2 = {
	"scroll": 500, //滚动时间
	"stop": 3000, //停留时间
	"num": 5 //图片数
};

function run2() {
	if(parseInt($("#play-scroll2").css("top")) > -(520 * (cfg2.num - 1))) {
		run2.index++;
		$("#play-scroll2").animate({
			top: '-=520px'
		}, cfg2.scroll);
		$("#play-btn2 li.on").removeClass("on");
		$("#play-btn2 li").eq(run2.index).addClass("on");
		start_auto2();
	} else {
		run2.index = 0;
		$("#play-scroll2").animate({
			top: '0px'
		}, cfg2.scroll);
		$("#play-btn2 li.on").removeClass("on");
		$("#play-btn2 li").eq(run2.index).addClass("on");
		start_auto2();
	}
}
run2.index = 0;
run2.time = 0;

function go_to2(index) {
	run2.index = index;
	var top = 520 * index;
	$("#play-scroll2").stop().animate({
		top: '-' + top + 'px'
	}, cfg2.scroll);
	$("#play-btn2 li.on").removeClass("on");
	$("#play-btn2 li").eq(run2.index).addClass("on");
}

function start_auto2() {
	stop_auto2();
	run2.time = setTimeout(run2, cfg2.stop);
}

function stop_auto2() {
	clearTimeout(run2.time);
}
$(function() {
	start_auto2();
	$("#play-btn2,#play-scroll2").hover(function() {
		stop_auto2();
	}, function() {
		start_auto2();
	});
	
});

//照片遮罩触发
$(function() {
	$(".C-photos-list a, .M-photos-list a").hover(function() {
		$(this).find(".photos-shade").show();
		$(this).find(".photos-shade2").show();
	}, function() {
		$(this).find(".photos-shade").hide();
		$(this).find(".photos-shade2").hide();
	});

	$(".C-quotation-list a, .M-quotation-list a").hover(function() {
		$(this).find(".quotation-shade").show();
	}, function() {
		$(this).find(".quotation-shade").hide();
	});

	$(".M-scenario-list a").hover(function() {
		$(this).find(".scenario-shade").show();
	}, function() {
		$(this).find(".scenario-shade").hide();
	});
});

//最新资讯自动翻
$(function() {
	(function() {
		var curr2 = 0;
		var picLen2 = $(".bigpic2 img").length;
		$(".bigpic2 .pica2").eq(0).show();
		$(".jsNav2 .trigger2").each(function(i) {
			$(this).click(function() {
				curr2 = i;
				$(".bigpic2 .pica2").eq(i).fadeIn("slow").siblings(".pica2").hide();
				$(this).siblings(".trigger2").removeClass("imgSelected").end().addClass("imgSelected");
				return false;
			});
		});

		var pg2 = function(flag) {
			//flag:true表示前翻， false表示后翻
			if(flag) {
				if(curr2 == 0) {
					todo2 = 2;
				} else {
					todo2 = (curr2 - 1) % picLen2;
				}
			} else {
				todo2 = (curr2 + 1) % picLen2;
			}
			$(".jsNav2 .trigger2").eq(todo2).click();
		};

		//前翻
		$(".jsNav2 #prev").click(function() {
			pg2(true);
			return false;
		});

		//后翻
		$("jsNav2 #next").click(function() {
			pg2(false);
			return false;
		});

		//自动翻
		var timer2 = setInterval(function() {
			todo2 = (curr2 + 1) % picLen2;
			$(".jsNav2 .trigger2").eq(todo2).click();
		}, 5000);

		//鼠标悬停在触发器上时停止自动翻
		$(".jsNav2 a").hover(function() {
				clearInterval(timer2);
			},
			function() {
				timer2 = setInterval(function() {
					todo2 = (curr2 + 1) % picLen2;
					$(".jsNav2 .trigger2").eq(todo2).click();
				}, 5000);
			}
		);
	})();
});