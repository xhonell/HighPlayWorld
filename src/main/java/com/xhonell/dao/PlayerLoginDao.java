package com.xhonell.dao;

import com.xhonell.entity.Player;
import com.xhonell.utils.DBHelper;
import com.xhonell.utils.JDBCUtils;

/**
 * <p>Project:test_01 - PlayerLogin
 * <p>POWER by xhonell on 2024-11-22 19:16
 * <p>description：
 * <p>idea：
 *
 * @author xhonell
 * @version 1.0
 * @since 1.8
 */
public class PlayerLoginDao {
    DBHelper dbHelper = new DBHelper();
    JDBCUtils jdbcUtils = new JDBCUtils();
    public Player check(Object[] obj) {
        String sql = "select player_id, player_nickName, player_birthday, player_sex, player_phone,player_img from player where player_nickName = ? and player_password = ?";
        Player player = dbHelper.getBean(Player.class, sql, obj);
        return player;
    }

    public Player selectByEmail(Object[] obj) {
        String sql = "select player_id, player_nickName, player_birthday, player_sex, player_phone,player_img from player where player_email = ?";
        Player player = dbHelper.getBean(Player.class, sql, obj);
        return player;
    }

    public int resetPassword(Object[] obj) {
        String sql = "update player set player_password = ? where player_email = ?";
        int update = jdbcUtils.update(sql, obj);
        return update;
    }
}
