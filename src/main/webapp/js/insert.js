$(document).ready(function() {
    jQuery.validator.addMethod("phoneCN", function(value, element) {
        const regex = /^1[3-9]\d{9}$/;
        return this.optional(element) || regex.test(value);
    }, "请输入有效的手机号");
    // 表单验证
    $("form").validate({
        rules: {
            nickName: {
                required: true,
                minlength: 2,
                maxlength: 20
            },
            sex: {
                required: true
            },
            phone: {
                required: true,
                phoneCN: true // 自定义手机号验证
            },
            password: {
                required: true,
                minlength: 6
            },
            rePassword: {
                required: true,
                equalTo: "#password" // 确保确认密码与密码一致
            },
            birthday: {
                required: true,
                date: true
            }
        },
        messages: {
            nickName: {
                required: "请输入昵称",
                minlength: "昵称至少需要2个字符",
                maxlength: "昵称最多20个字符"
            },
            sex: {
                required: "请选择性别"
            },
            phone: {
                required: "请输入手机号",
                phoneCN: "请输入有效的手机号"
            },
            password: {
                required: "请输入密码",
                minlength: "密码至少需要6个字符"
            },
            rePassword: {
                required: "请输入确认密码",
                equalTo: "密码和确认密码不一致"
            },
            birthday: {
                required: "请选择生日",
                date: "请输入有效的日期"
            }
        },
        submitHandler: function() {
            // 当表单验证通过后，使用 AJAX 提交数据
            var formData = {
                nickName: $("#nickName").val(),
                sex: $("input[name='sex']:checked").val(),
                phone: $("#phone").val(),
                password: $("#password").val(),
                birthday: $("#birthday").val()
            };

            $.ajax({
                url: "/servlet/index?method=insertPlayer",
                method: "POST",
                dataType: "json",
                contentType: 'application/x-www-form-urlencoded; charset=UTF-8',
                data: formData,
                success: function(response) {
                    if (response.code === 200) {
                        location.href="index.html";
                    } else {
                        alert("注册失败，请重试。");
                    }
                },
                error: function(xhr, status, error) {
                    alert("请求失败，请稍后重试。");
                }
            });
        }
    });

    // 通过点击按钮触发表单验证
    $("#insertBtn").click(function() {
        $("form").submit();  // 手动触发表单验证
    });
});
