$(function() {
    let loginData = window.localStorage.getItem("user");
    loginData = JSON.parse(loginData);
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
        console.log(loginData);

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
                    data.forEach(item => {
                        if (item.player_id === loginData.player_id){
                            loginData = item;
                        }
                    })
                    $("#photoHead").prop("src",loginData.player_img)
                    show(data);
                }
            }
        })
    }

    function show(data) {
        $("#playerTableBody").empty();
        data.forEach(function(data) {
            let tr = $("<tr></tr>");
            tr.append("<td><input type='checkbox' name='checkboxAll' id="+'checkbox_' + data.player_id+"></td>");
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

    /*跳转到新增页面*/
    $("#insertPlayer").on("click", function() {
        window.location.href="insert.html";
    })

    /*点击搜索按钮*/
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

    /*点击上一页*/
    $("#lastPage").on("click", function() {
        if(pageNumber >1){
            pageNumber = pageNumber -1;
            $("#pageNumber").val(pageNumber);
            window.onload();
        }else return false;

    })

    /*点击下一页*/
    $("#afterPage").on("click", function() {
        pageNumber = Number.parseInt(pageNumber) + 1;
        $("#pageNumber").val(pageNumber);
        window.onload();
    })

    /*点击操作中的删除按钮*/
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

    /*点击操作中的修改按钮*/
    $("#playerTableBody").on("click", "button[id^=edit_]",function () {
        let playerId = $(this).attr("id").replace("edit_", "");
        location.href="edit.html?id=" + playerId;
    });

    /*点击复选框时候的时间*/
    $("#playerCheckbox").on("click", function() {
        /*attr控制属性值，常用于页面第一次加载，prop常用于修改属性值*/
        let checkboxVal =$("#playerCheckbox").prop("checked");
        $("tbody input[name='checkboxAll']").prop("checked", checkboxVal);
    })

    /*删除复选框选中数据*/
    $("#deleteBox").click(function() {
        let arr = $("tbody input[name='checkboxAll']:checked").map(function() {
            console.log(this)
            return this.id;  // 获取每个复选框的 id
        }).get();
        console.log(arr)
        arr.forEach(function(item){
            let id = item.replace("checkbox_", "");
            $.ajax({
                url:"/servlet/index?method=deleteById",
                method:"GET",
                dataType:"json",
                contentType: 'application/x-www-form-urlencoded; charset=UTF-8',
                data:{id:id},
                success:function(response){
                    if (response.code !== 200){
                        console.log(id + "数据已经删除");
                    }else{
                        window.onload();
                    }
                }
            })
        })
    })

})