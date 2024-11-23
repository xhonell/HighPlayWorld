$(function() {
    $("#container [type='button']").on("click", function() {
        let name = $("#username").val();
        let psd = $("#password").val();

        $.ajax({
            url: "/servlet/login?method=login",
            method: "POST",
            dataType: "json",  // 返回的数据类型是 JSON
            contentType: 'application/x-www-form-urlencoded; charset=UTF-8', // 确保请求发送 UTF-8 编码的数据
            data: {
                "name": name,
                "password": psd
            },
            success: function(response) {
                if (response.code === 200) {
                    location.href = "../page/index.html";  // 登录成功跳转
                } else {
                    alert(response.msg);  // 错误消息
                    location.reload();
                }
            },
            error: function(xhr, status, error) {
                alert("请求失败，请稍后再试！");
            }
        });
    });
});
