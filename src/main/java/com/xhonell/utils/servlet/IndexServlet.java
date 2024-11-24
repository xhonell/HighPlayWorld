package com.xhonell.utils.servlet;

import com.xhonell.utils.common.Write;
import com.xhonell.utils.entity.Player;
import com.xhonell.utils.service.IndexService;
import com.xhonell.utils.utils.MyFormatUtils;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.Arrays;
import java.util.Date;
import java.util.List;

import static com.xhonell.utils.utils.MD5Utils.md5;

/**
 * <p>Project:test_01 - IndexServlet
 * <p>POWER by xhonell on 2024-11-23 10:08
 * <p>description：
 * <p>idea：
 *
 * @author xhonell
 * @version 1.0
 * @since 1.8
 */
@WebServlet("/servlet/index/*")
public class IndexServlet extends HttpServlet {
    IndexService indexService = new IndexService();
    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String method = req.getParameter("method");
        switch (method) {
            case "insertPlayer":
                insertPlayer(req,resp);
                break;
            case "editPlayer":
                editPlayer(req,resp);
                break;
            default:
                break;
        }
    }

    private void editPlayer(HttpServletRequest req, HttpServletResponse resp) {
        Integer id = Integer.parseInt(req.getParameter("id"));
        String name = req.getParameter("nickName");
        String sex = req.getParameter("sex");
        Long phone = Long.parseLong(req.getParameter("phone"));
        Date birthday = MyFormatUtils.toDate(req.getParameter("birthday"));
        Object[] obj={name,sex,phone,birthday,id};
        boolean b = indexService.editPlayer(obj);
        if (b){
            Write.writeSuccess(resp);
        }else {
            Write.writeFail(resp);
        }
    }

    private void insertPlayer(HttpServletRequest req, HttpServletResponse resp) {
        String name = req.getParameter("nickName");
        String password = req.getParameter("password");
        password = md5(password);
        String sex = req.getParameter("sex");
        Long phone = Long.parseLong(req.getParameter("phone"));
        Date birthday = MyFormatUtils.toDate(req.getParameter("birthday"));
        Object[] obj={name,password,sex,phone,birthday};
        boolean b = indexService.insertPlayer(obj);
        if (b){
            Write.writeSuccess(resp);
        }else{
            Write.writeFail(resp);
        }

    }

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String method = req.getParameter("method");
        switch (method) {
            case "selectAll":
                selectAll(req,resp);
                break;
            case "selectById":
                selectById(req,resp);
                break;
            case "deleteById":
                deleteById(req,resp);
                break;
            default:
                break;
        }
    }

    private void deleteById(HttpServletRequest req, HttpServletResponse resp) {
        Integer id = Integer.parseInt(req.getParameter("id"));
        boolean b = indexService.deleteById(id);
        if (b){
            Write.writeSuccess(resp);
        }else{
            Write.writeFail(resp);
        }
    }

    private void selectById(HttpServletRequest req, HttpServletResponse resp) {
        Integer id = null;
        if(req.getParameter("id")!=null && !req.getParameter("id").trim().isEmpty()) {
             id= Integer.parseInt(req.getParameter("id"));
        }
        String name = req.getParameter("nickName");
        String phone = req.getParameter("phone");
        Object[] obj={id,name,phone};
        System.out.println(Arrays.toString(obj));
        List<Player> players = indexService.selectById(obj);
        Write.writeSuccess(resp, players);
    }

    private void selectAll(HttpServletRequest req, HttpServletResponse resp) {
        Object[] obj = {(Integer.parseInt(req.getParameter("pageNumber"))-1)*10};
        List<Player> players = indexService.selectAll(obj);
        Write.writeSuccess(resp, players);
    }


}
