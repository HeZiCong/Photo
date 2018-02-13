
$(function($) {
	$("#username").blur(function(){
		$.get("/user/checkUserName",{name:$('#username').val()},function(data){
			if(data.status!=0){
				$("#username").tips({
					side: 3,
					msg: '用户名已存在',
					bg: '#AE81FF',
					time: 3
				});
			}
		});
	});
	$('#loginbtn').click(function() {

		if($('#username').val() == "") {
			$("#username").tips({
				side: 3,
				msg: '用户名不能为空',
				bg: '#AE81FF',
				time: 3
			});
			return false;
		}else if($('#tel').val().length!=11){
			$("#tel").tips({
				side: 3,
				msg: '请输入正确的手机号',
				bg: '#AE81FF',
				time: 3
			});
			return false;
		} else if($('#password1').val() == "") {
			$("#password1").tips({
				side: 3,
				msg: '密码不能为空',
				bg: '#AE81FF',
				time: 3
			});
			return false;
		}else if($('#password2').val() == "") {
			$("#password2").tips({
				side: 3,
				msg: '密码不能为空',
				bg: '#AE81FF',
				time: 3
			});
			return false;
		}else if($('#password2').val()!=$('#password1').val()){
			$("#password2").tips({
				side: 3,
				msg: '两次输入的密码不一致',
				bg: '#AE81FF',
				time: 3
			});
			return false;
		}
		$('#loginbtn').html("注册中..");
		$("#loginbtn").attr("disabled", true);
		$.ajax({
			type: "POST",
			url: "/user/register",
			data: {
				name: $('#username').val(),
				password: $('#password2').val(),
				tel:$('#tel').val()
			},
			dataType: "json",
			cache: false,
			success: function(data) {
				if("0" == data.status) {
					var baseUrl = location.protocol + "//" + location.host;
					window.location.href = baseUrl + "/login.html";
				} else {

					$('#loginbtn').html("注册");
					$("#loginbtn").attr("disabled", false);
					alert("注册失败" + data.status);
				}
			},
			error: function() {

				$('#loginbtn').html("注册");
				$("#loginbtn").attr("disabled", false);
				alert("注册失败");
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