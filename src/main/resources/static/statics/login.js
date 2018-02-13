$("#form_submit").on("click", function(){
	var phonenum = $("input[name=phonenum]").val();
	var password = $("input[name=password]").val();

	if(phonenum == ''){
		alert('帐号不能为空');
		return false;
	}
	if(password == ''){
		alert('密码不能为空');
		return false;
	}
	$.ajax({
		url : '/user/login',
		type :'POST',
		data : {'name': phonenum, 'password': password},
		dataType : 'JSON',
		beforeSend: function(){
			layer.msg("数据提交中，请稍等...");
		},
		success : function(data){
			if(data.status == 0){
				window.location.reload();
			}else{
				alert("用户名或密码错误");
			}
		},error : function(){
			alert("网络错误！");
		}
	})
});

//账户注册
function register(){
	var nickname = $('#User_nickname').val();
	var phonenum = $('#User_phonenum').val();
	var passwd = $('#User_passwd').val();
	var repasswd = $('#User_repasswd').val();
	var read = $('input[name = read]:checked').val();
	if(nickname == ''){
		alert('姓名不能为空');
		return false;
	}
	if(phonenum == ''){
		alert('手机号码不能为空');
		return false;
	}
	if(passwd == ''){
		alert('密码不能为空');
		return false;
	}
	if(passwd.length<6 || passwd.length>12){
		alert('密码格式不正确，密码必须为6-12位！');
		return false;
	}
	if(passwd != repasswd){
		alert('两次密码输入不一致');
		return false;
	}
	if(!read){
		alert('请阅读并勾选同意《协议》后继续完成注册。');
		return false;
	}
	$.ajax({
		url : '/user/register',
		type :'POST',
		data : {'name': nickname, 'tel': phonenum, 'password': repasswd},
		dataType : 'JSON',
		beforeSend: function(){
			layer.msg("数据提交中，请稍等...");
		},
		success : function(data){
			layer.closeAll();
			if(data.status == 0){
				show_login();
			}else{
				alert("注册失败，请更换用户名");
			}
		},error : function(){
			layer.closeAll();
			alert("网络错误！");
		}
	})

}

//找回密码
function find(){
	var phonenum = $('#forget_phonenum').val();
	var passwd = $('#forget_passwd').val();
	var repasswd = $('#forget_repasswd').val();
	var name = $('#forget_name').val();

	if(phonenum == ''){
		alert('手机号码不能为空');
		return false;
	}
	if(name == ''){
		alert('用户名不能为空');
		return false;
	}
	if(passwd == ''){
		alert('密码不能为空');
		return false;
	}
	if(passwd.length<6 || passwd.length>12){
		alert('密码格式不正确，密码必须为6-12位！');
		return false;
	}
	if(passwd != repasswd){
		alert('两次密码输入不一致');
		return false;
	}

	$.ajax({
		url : '/user/updatePassword',
		type :'POST',
		data : {'tel': phonenum, 'name': name, 'password': repasswd},
		dataType : 'JSON',
		beforeSend: function(){
			layer.msg("数据提交中，请稍等...");
		},
		success : function(data){
			layer.closeAll();
			if(data.status == 0){
				show_login();
			}else{
			alert("找回密码失败");
			}
		},error : function(){
			alert("网络错误！");
		}
	})

}

//获取手机验证码
function getVcode(obj, type) {
	if(type == 'user'){
		var phone_obj = $("#User_phonenum");
	}else{
		var phone_obj = $("#forget_phonenum");
	}

	var phone = phone_obj.val();
	if (phone == '') {
		phone_obj.focus();
		return false;
	}
	
	if (!isMobile(phone)) {
		alert("无效手机号码");
		return false;
	}

	$.ajax({
		url: "/message/sendCaptcha",
		data: {phone: phone,type:type},
		type: 'post',
		dataType: 'json',
		beforeSend: function(){
			layer.msg("数据提交中，请稍等...");
		},
		success: function (res) {
			alert(res.msg);
			if (res.status == 1) {
				time(obj);
			}
		}
	});
}

//手机号验证
function isMobile(mobile) {
	var flag = RegExp(/^(0|86|17951)?(13[0-9]|15[012356789]|18[0-9]|14[57]|17[0-9])[0-9]{8}$/).test(mobile);
	if (flag) {
		$("#User_phonenum").css('border', '1px solid #ccc');
		return true;
	} else {
		$("#User_phonenum").css('border', '1px solid #e42121');
		return false;
	}
}

var wait = 60;
function time(o) {
	if (wait == 0) {
		$(o).removeClass('red').addClass('white').attr('disabled', false).css({'cursor': 'pointer', 'color': 'black'});
		$(o).val("免费获取验证码");
		wait = 60;
	} else {
		$(o).removeClass('white').addClass('red').attr('disabled', true).css({'cursor': 'wait', 'color': 'white'});
		$(o).val("重新发送(" + wait + ")");
		wait--;
		setTimeout(function () {
				time(o)
			},
			1000)
	}
}

function checkMobile(){
	mobileFlag = false;
	var mobile = $("#User_phonenum").val();
	var vcode = $("#vcode").val();

	if(mobile!='' && vcode!=''){
		$.ajax({
			url : 'http://cq.jfr121314.com/ajax/checkVcode.html',
			async : false,
			data : {yzm:vcode,mobile:mobile},
			type : 'post',
			beforeSend: function(){
				layer.msg("数据提交中，请稍等...");
			},
			success : function(result){
				if(result=='success'){
					mobileFlag = true;
				}
			}
		});
	}
	return mobileFlag;
}

function checkCaptcha(){
	mobileFlag = false;
	var mobile = $("#User_phonenum").val();
	var vcode = $("#vcode").val();

	if(mobile!='' && vcode!=''){
		$.ajax({
			url : 'http://cq.jfr121314.com/site/checkCaptcha.html',
			async : false,
			data : {code:vcode},
			type : 'post',
			beforeSend: function(){
				layer.msg("数据提交中，请稍等...");
			},
			success : function(result){
				if(result=='success'){
					mobileFlag = true;
				}else{
					$('#captcha_id').attr('src', $('#captcha_id').attr('src')+1);
				}
			}
		});
	}
	return mobileFlag;
}

function checkUname(uname){
	var flag = false;
	$.ajax({
		async : false,
		url : "<?php echo $this->createUrl('ajax/isExistUser');?>",
		data : {uname:uname},
		success : function(res){
			if(res==0){
				flag = true;
			}
		}
	});
	return flag;
}

function isExistPhone(mobile){
	var flag = false;

	$.ajax({
		async : false,
		url : "<?php echo $this->createUrl('ajax/isExistMobile');?>",
		data : {mobile:mobile},
		success : function(res){
			if(res==0){
				flag = true;
			}
		}
	});
	return flag;
}
