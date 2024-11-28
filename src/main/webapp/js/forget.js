$(function () {

    // 获取验证码按钮点击事件
    $("#getVerificationCode").on("click", function () {
        // 可以在这里添加获取验证码的逻辑
        let email = $("#forgetEmail").val();
        $.ajax({
            url: "/servlet/login?method=forgetPassword",
            method: "POST",
            dataType: "json",
            data: {"email": email},
            success: function (response) {
                if (response.code === 200) {
                    alert(response.msg);
                } else {
                    alert(response.msg);
                }
            },
            error: function () {
                alert("请求失败，请重试");
            }
        });
    });

    /*$("#resetBtn").on("click", function () {
        $("form").submit();
    })*/

    $("form").validate({
        rules: {
            forgetEmail: {
                required: true,
                email: true
            },
            'verification-code': {
                required: true,
                minlength: 6,
                maxlength: 6  // 验证验证码必须是6位
            },
            forgetPassword: {
                required: true,
                minlength: 6
            },
            rePassword: {
                required: true,
                equalTo: '#forgetPassword'
            }
        },
        messages: {
            forgetEmail: {
                required: "请输入邮箱地址",
                email: "请输入有效的邮箱地址"
            },
            'verification-code': {
                required: "请输入验证码",
                minlength: "验证码需要6位",
                maxlength: "验证码不能超过6位"
            },
            forgetPassword: {
                required: "请输入密码",
                minlength: "密码长度至少6个字符"
            },
            rePassword: {
                required: "请确认密码",
                equalTo: "两次输入的密码不一致"
            }
        },
        submitHandler: function () {
            // 验证通过后，使用 AJAX 提交表单
            let formData = {
                "email": $("#forgetEmail").val(),
                "password": $("#forgetPassword").val(),
                "validateCode":$("#verification-code").val()
            }
            $.ajax({
                url: "/servlet/login?method=resetPassword",
                method: "POST",
                data: formData,
                dataType: "json",
                success: function (response) {
                    if(response.code === 200) {
                        alert("修改成功");
                        window.location.href = "../login.html";
                    }else{
                        alert(response.msg);
                    }
                }
            })
        }
    });
});
