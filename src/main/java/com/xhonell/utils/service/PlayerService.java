package com.xhonell.utils.service;


import com.xhonell.utils.dao.PlayerLoginDao;
import com.xhonell.utils.entity.Player;

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
}
