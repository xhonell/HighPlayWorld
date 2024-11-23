$(function() {
    let data ;
    let pageNumber = $("#pageNumber").val();
    console.log(pageNumber);
    $("#loginOut").on("click", function() {

        $.ajax({
            url:"/servlet/login?method=loginOut",
            method:"POST",
            contentType: 'application/x-www-form-urlencoded; charset=UTF-8',
            dataType:"json",
            success:function(data){
                if (data.code === 200){
                    location.href="../login.html";
                }
            }
        })
    })
    window.onload=function(){
        $.ajax({
            url:"/servlet/index?method=selectAll",
            method: "GET",
            dateType:"json",
            data:{"pageNumber":pageNumber},
            contentType: 'application/x-www-form-urlencoded; charset=UTF-8',
            success:function(response){
                console.log(response.data);
                if (response.code === 200){
                    data = response.data;
                    show(data);
                }
            }
        })
    }

    function show(data) {
        $("#playerTableBody").empty();
        data.forEach(function(data) {
            let tr = $("<tr></tr>");
            tr.append("<td><input type='checkbox' name='checkboxAll'></td>");
            tr.append("<td>" + data.player_id + "</td>");
            tr.append("<td>" + data.player_nickName + "</td>");
            tr.append("<td>" + data.player_phone + "</td>");
            tr.append("<td>" + data.player_sex + "</td>");
            tr.append("<td>" + data.player_birthday + "</td>");
            tr.append("<td>" + "<button id="+'edit_'+ data.player_id + ">修改</button>"
                +"<button id=" + 'delete_'+data.player_id + ">删除</button>"+ "</td>");
            $("#playerTableBody").append(tr);
        });
    }
    $("#insertPlayer").on("click", function() {
        window.location.href="insert.html";
    })
    $("#selectPlayer").on("click", function() {
        let id = $("#playerId").val();
        let nickName = $("#playerName").val();
        let phone = $("#playerPhone").val();
        $.ajax({
            url:"/servlet/index?method=selectById",
            dataType:"json",
            method:"get",
            data:{"id":id,"nickName":nickName,"phone":phone},
            contentType: 'application/x-www-form-urlencoded; charset=UTF-8',
            success:function(response){
                if (response.code === 200){
                    data = response.data;
                    show(data);
                }
            }
        })
    })
    $("#lastPage").on("click", function() {
        if(pageNumber >1){
            pageNumber = pageNumber -1;
            $("#pageNumber").val(pageNumber);
            window.onload();
        }else return false;

    })
    $("#afterPage").on("click", function() {
        pageNumber = Number.parseInt(pageNumber) + 1;
        $("#pageNumber").val(pageNumber);
        window.onload();
    })
    $("#playerTableBody").on("click", "button[id^=delete_]",function () {
        let playerId = $(this).attr("id").replace("delete_", "");
        $.ajax({
            url:"/servlet/index?method=deleteById",
            method:"GET",
            dataType:"json",
            contentType: 'application/x-www-form-urlencoded; charset=UTF-8',
            data:{id:playerId},
            success:function(response){
                if (response.code === 200){
                    window.onload();
                }
            }
        })

    });

    $("#playerTableBody").on("click", "button[id^=edit_]",function () {
        let playerId = $(this).attr("id").replace("edit_", "");
        location.href="edit.html?id=" + playerId;
    });



})