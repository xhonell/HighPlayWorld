$(function(){
    const queryString = window.location.search;
    let data;

    const urlParams = new URLSearchParams(queryString);
    const playerId = urlParams.get('id');
    window.onload = function(){
        $.ajax({
            url:"/servlet/index?method=selectById",
            method:"get",
            data:{"id":playerId},
            contentType: 'application/x-www-form-urlencoded; charset=UTF-8',
            success:function(response){
                if (response.code === 200) {
                    console.log(response.data);
                    data = response.data[0];
                    $("#nickName").val(data.player_nickName);
                    $("[name='sex'][value='" + data.player_sex + "']").prop("checked", true);
                    $("#phone").val(data.player_phone);
                    $("#birthday").val(data.player_birthday);
                    $("#hiddenId").val(data.player_id);
                }
            }
        })
    }
    $("#editBtn").on("click", function(){
        var formData = new FormData($("form")[0]);
        $.ajax({
            url:"/servlet/index?method=editPlayer",
            method:"post",
            dataType:"json",
            contentType: false,
            processData: false,
            data:formData,
            success:function(response){
                if (response.code === 200){
                    data = response.data;
                    location.href = "index.html";
                }
            }

        })
    })
})