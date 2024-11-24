package com.xhonell.utils.servlet;

import com.alibaba.fastjson.JSON;
import com.xhonell.utils.common.R;
import com.xhonell.utils.common.Write;
import com.xhonell.utils.entity.Player;
import com.xhonell.utils.service.PlayerService;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;

import static com.xhonell.utils.utils.MD5Utils.md5;

/**
 * <p>Project:test_01 - LoginServlet
 * <p>POWER by xhonell on 2024-11-22 17:00
 * <p>description：
 * <p>idea：
 *
 * @author xhonell
 * @version 1.0
 * @since 1.8
 */
@WebServlet("/servlet/login/*")
public class LoginServlet extends HttpServlet {
    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String method = req.getParameter("method");
        if (method==null || (method.trim()).isEmpty()){
            String jsonString = JSON.toJSONString(R.failed("请求错误"));
            resp.getWriter().write(jsonString);
        }
        switch (method) {
            case "login":
                login(req, resp);
                break;
            case "loginOut":
                loginOut(req,resp);

        }
    }

    private void loginOut(HttpServletRequest req, HttpServletResponse resp) {
        req.getSession().invalidate();
        Write.writeSuccess(resp);
    }

    public void login(HttpServletRequest req, HttpServletResponse resp) throws IOException {
        String playerName = req.getParameter("name");
        String playPassword = req.getParameter("password");
        playPassword = md5(playPassword);
        Object[] obj = {playerName, playPassword};
        PlayerService playerService = new PlayerService();
        Player player = playerService.login(obj);
        System.out.println(player);
        if (player != null){
            req.getSession().setAttribute("player", player);
            Write.writeSuccess(resp);
        }else{
            Write.writeFail(resp);
        }
    }

}
