$(function(){
    $("#insertBtn").on("click", function(){
        let nickName = $("#nickName").val();
        let sex = $("input[name='sex']:checked").val();
        let phone = $("#phone").val();
        let password = $("#password").val();
        let birthday = $("#birthday").val();

        $.ajax({
            url:"/servlet/index?method=insertPlayer",
            method:"post",
            dataType:"json",
            data:{"sex":sex,"nickName":nickName,"phone":phone,"password":password,"birthday":birthday},
            success:function(response){
                if (response.code === 200){
                    window.location.href = "index.html";
                }
            }
        })
    })
})