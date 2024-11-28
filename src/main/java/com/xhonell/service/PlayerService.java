package com.xhonell.service;


import com.xhonell.dao.PlayerLoginDao;
import com.xhonell.entity.Player;

/**
 * <p>Project:test_01 - PlayerService
 * <p>POWER by xhonell on 2024-11-22 19:14
 * <p>description：
 * <p>idea：
 *
 * @author xhonell
 * @version 1.0
 * @since 1.8
 */
public class PlayerService {
    PlayerLoginDao login = new PlayerLoginDao();
    public Player login(Object[] obj) {
        return login.check(obj);
    }

    public Player forgetPassword(String email) {
        Object[] obj = {email};
        return login.selectByEmail(obj);
    }

    public boolean resetPassword(Object[] obj) {
        return login.resetPassword(obj) >0;
    }
}
