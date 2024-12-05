package com.xhonell.servlet;

import com.alibaba.fastjson.JSON;
import com.xhonell.common.R;
import com.xhonell.common.Write;
import com.xhonell.entity.Player;
import com.xhonell.service.PlayerService;
import com.xhonell.utils.EmailUtils;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.Random;

import static com.xhonell.utils.MD5Utils.md5;

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
    PlayerService playerService = new PlayerService();

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        doPost(req, resp);
    }

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
                break;
            case "forgetPassword":
                forgetPassword(req,resp);
                break;
            case "resetPassword":
                resetPassword(req,resp);
                break;
            default:
                break;
        }
    }

    private void resetPassword(HttpServletRequest req, HttpServletResponse resp) {
        String email = req.getParameter("email");
        String password = req.getParameter("password");
        String code = req.getParameter("validateCode");
        Object codeSession = req.getSession().getAttribute("code");
        if (!codeSession.equals(code)){
            Write.writeFail(resp,"验证码错误");
        }
        password = md5(password);
        Object [] obj={password,email};
        boolean b = playerService.resetPassword(obj);
        if(b){
            Write.writeSuccess(resp);
        }else{
            Write.writeFail(resp);
        }
    }

    private void forgetPassword(HttpServletRequest req, HttpServletResponse resp) {
        String email = req.getParameter("email");
        Player playerByEmail = playerService.forgetPassword(email);
        if (playerByEmail != null) {
            Random random = new Random();
            Integer randomNumber = 100000 + random.nextInt(900000);
            req.getSession().setAttribute("randomNumber", randomNumber);
            EmailUtils.sendAuthCodeEmail(email,"犇腾文化有限公司",randomNumber.toString());
            req.getSession().setAttribute("code", randomNumber.toString());
            Write.writeSuccess(resp);
        }else{
            Write.writeFail(resp);
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
        Player player = playerService.login(obj);
        if (player != null){
            req.getSession().setAttribute("player", player);
            Write.writeSuccess(resp,player);
        }else{
            Write.writeFail(resp);
        }
    }

}
