$(function(){
    $("#getVerificationCode").on("click", function(){
        let email = $("#forgetEmail").val();
        $.ajax({
            url:"/servlet/login?method=forgetPassword",
            method:"POST",
            dataType:"json",
            data:{"email": email},
            success:function(response){

            }
        })
    })
})