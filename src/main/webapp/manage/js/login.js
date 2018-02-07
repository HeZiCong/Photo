var code;

var codeChars = new Array(0, 1, 2, 3, 4, 5, 6, 7, 8, 9,
	'a', 'b', 'c', 'd', 'e', 'f', 'g', 'h', 'i', 'j', 'k', 'l', 'm', 'n', 'o', 'p', 'q', 'r', 's', 't', 'u', 'v', 'w', 'x', 'y', 'z',
	'A', 'B', 'C', 'D', 'E', 'F', 'G', 'H', 'I', 'J', 'K', 'L', 'M', 'N', 'O', 'P', 'Q', 'R', 'S', 'T', 'U', 'V', 'W', 'X', 'Y', 'Z'); //所有候选组成验证码的字符，当然也可以用中文的

var canvas = document.getElementById("myCanvas");

function createTextCode() {
	code = "";
	var codeLength = 6; //验证码的长度
	var checkCode = document.getElementById("codeshow");
	for(var i = 0; i < codeLength; i++) {
		var charNum = Math.floor(Math.random() * 52);
		code += codeChars[charNum];
	}
	if(checkCode) {
		checkCode.className = "textCode";
		checkCode.innerHTML = code;
	}
}

function createCanvasCode() {
	var context = canvas.getContext("2d");
	canvas.width = canvas.width;

	var rand1 = codeChars[Math.floor(Math.random() * codeChars.length)];
	var rand2 = codeChars[Math.floor(Math.random() * codeChars.length)];
	var rand3 = codeChars[Math.floor(Math.random() * codeChars.length)];
	var rand4 = codeChars[Math.floor(Math.random() * codeChars.length)];
	var rand5 = codeChars[Math.floor(Math.random() * codeChars.length)];
	var rand6 = codeChars[Math.floor(Math.random() * codeChars.length)];
	code = rand1 + rand2 + rand3 + rand4 + rand5 + rand6;
	// Fill the background  
	context.fillStyle = "#ffffff";
	context.fillRect(0, 0, canvas.width, canvas.height);

	// Draw some random lines  
	for(i = 0; i < 3; i++) {
		drawline(canvas, context);
	}

	// Sprinkle in some random dots  
	for(i = 0; i < 30; i++) {
		drawDot(canvas, context);
	}

	// Draw the pass-phrase string  
	context.fillStyle = getRandomColor();
	context.font = "28px Arial";
	context.fillText(rand1, 2, 20 + Math.floor(Math.random() * 10));
	context.fillStyle = getRandomColor();
	context.fillText(rand2, 22, 20 + Math.floor(Math.random() * 10));
	context.fillStyle = getRandomColor();
	context.fillText(rand3, 42, 20 + Math.floor(Math.random() * 10));
	context.fillStyle = getRandomColor();
	context.fillText(rand4, 62, 20 + Math.floor(Math.random() * 10));
	context.fillStyle = getRandomColor();
	context.fillText(rand5, 82, 20 + Math.floor(Math.random() * 10));
	context.fillStyle = getRandomColor();
	context.fillText(rand6, 102, 20 + Math.floor(Math.random() * 10));

}

function getRandomColor() {

	return '#' +
		(function(color) {
			return(color += '0123456789abcdef' [Math.floor(Math.random() * 16)]) &&
				(color.length == 6) ? color : arguments.callee(color);
		})('');
}

function drawline(canvas, context) {
	context.moveTo(0, Math.floor(Math.random() * canvas.height));
	context.lineTo(canvas.width, Math.floor(Math.random() * canvas.height));
	context.lineWidth = 0.5;
	/*context.strokeStyle = 'rgb(50,50,50)'; */
	context.strokeStyle = getRandomColor();
	context.stroke();
}

function drawDot(canvas, context) {
	var px = Math.floor(Math.random() * canvas.width);
	var py = Math.floor(Math.random() * canvas.height);
	context.moveTo(px, py);
	context.lineTo(px + 1, py + 1);
	context.lineWidth = 0.2;
	context.stroke();
}

function createCode() {
	if(!canvas.getContext) {
		createTextCode()
			//alert("你的浏览器不支持Canvas.");
	} else {
		createCanvasCode()
			//alert("你的浏览器支持Canvas.");
	}
	//console.log(code);
}
createCode();

$(function($) {
	$('#loginbtn').click(function() {

		if($('#username').val() == "") {
			$("#username").tips({
				side: 3,
				msg: '用户名不能为空',
				bg: '#AE81FF',
				time: 3
			});
			return false;
		} else if($('#password').val() == "") {
			$("#password").tips({
				side: 3,
				msg: '密码不能为空',
				bg: '#AE81FF',
				time: 3
			});
			return false;
		} else {
			var inputCode = document.getElementById("code").value;
			if(inputCode.length <= 0) {
				$("#code").tips({
					side: 3,
					msg: '请输入验证码！',
					bg: '#AE81FF',
					time: 3
				});
				return false;
			} else if(inputCode.toUpperCase() != code.toUpperCase()) {
				$("#code").tips({
					side: 3,
					msg: '验证码输入有误！',
					bg: '#AE81FF',
					time: 3
				});
				$("#code").val("");
				createCode();
				return false;
			}
		}
		$('#loginbtn').html("登录中..");
		$("#loginbtn").attr("disabled", true);
		$.ajax({
			type: "POST",
			url: "../admin/login",
			data: {
				name: $('#username').val(),
				password: $('#password').val()
			},
			dataType: "json",
			cache: false,
			success: function(data) {
				if("0" == data.status) {
					var baseUrl = location.protocol + "//" + location.host;
					window.location.href = baseUrl + "/Photo/manage/index.html";
				} else {

					$('#loginbtn').html("登录");
					$("#loginbtn").attr("disabled", false);
					alert("用户名或密码错误" + data.status);
				}
			},
			error: function() {

				$('#loginbtn').html("登录");
				$("#loginbtn").attr("disabled", false);
				alert("用户名或密码错误");
			}
		});
	});
});

$(document).keyup(function(event) {
	if(event.keyCode == 13) {
		$("#loginbtn").trigger("click");
	}
});

function isPlaceholder() {
	var input = document.createElement('input');
	return 'placeholder' in input;
}
if(!isPlaceholder()) { //不支持placeholder 用jquery来完成
	$(document).ready(function() {
		if(!isPlaceholder()) {
			$("input").not("input[type='password']").each( //把input绑定事件 排除password框
				function() {
					if($(this).val() == "" && $(this).attr("placeholder") != "") {
						$(this).val($(this).attr("placeholder"));
						$(this).focus(function() {
							if($(this).val() == $(this).attr("placeholder")) $(this).val("");
						});
						$(this).blur(function() {
							if($(this).val() == "") $(this).val($(this).attr("placeholder"));
						});
					}
				});
			//对password框的特殊处理1.创建一个text框 2获取焦点和失去焦点的时候切换
			$("input[type='password']").each(
				function() {
					var pwdField = $(this);
					var pwdVal = pwdField.attr('placeholder');
					pwdField.after('<input  class="login-input" type="text" value=' + pwdVal + ' autocomplete="off" />');
					var pwdPlaceholder = $(this).siblings('.login-input');
					pwdPlaceholder.show();
					pwdField.hide();

					pwdPlaceholder.focus(function() {
						pwdPlaceholder.hide();
						pwdField.show();
						pwdField.focus();
					});

					pwdField.blur(function() {
						if(pwdField.val() == '') {
							pwdPlaceholder.show();
							pwdField.hide();
						}
					});
				})
		}
	});
}