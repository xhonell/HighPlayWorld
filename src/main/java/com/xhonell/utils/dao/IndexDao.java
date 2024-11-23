package com.xhonell.utils.dao;

import com.xhonell.utils.entity.Player;
import com.xhonell.utils.utils.DBHelper;
import com.xhonell.utils.utils.JDBCUtils;

import java.util.ArrayList;
import java.util.List;

/**
 * <p>Project:test_01 - IndexDao
 * <p>POWER by xhonell on 2024-11-23 10:21
 * <p>description：
 * <p>idea：
 *
 * @author xhonell
 * @version 1.0
 * @since 1.8
 */
public class IndexDao {
    DBHelper helper = new DBHelper();
    JDBCUtils utils = new JDBCUtils();
    public List<Player> selectAll(Object[] obj) {
        String sql = "SELECT player_id, player_nickName, player_birthday, player_sex, player_phone FROM player LIMIT ?, 10";
        return helper.getBeanList(Player.class, sql,obj);
    }

    public List<Player> selectById(Object[] obj) {
        ArrayList<Object> arr = new ArrayList<>();
        StringBuffer sql = new StringBuffer("select player_id,player_nickName,player_birthday,player_sex,player_phone from player where 1=1");
        if(obj[0] != null && obj[0] != "") {
            sql.append(" and player_id=?");
            arr.add(obj[0]);
        }
        if(obj[1] != null && obj[1] != "") {
            sql.append(" and player_nickName=?");
            arr.add(obj[1]);
        }
        if(obj[2] != null && obj[2] != "") {
            sql.append(" and player_phone=?");
            arr.add(obj[2]);
        }
        return helper.getBeanList(Player.class, sql.toString(),arr.toArray());
    }

    public int insertPlayer(Object[] obj) {
        String sql = "insert into player(player_nickName,player_password,player_sex,player_phone,player_birthday) values(?,?,?,?,?)";
        return utils.update(sql,obj);
    }

    public int editPlayer(Object[] obj) {
        String sql="update player set player_nickName=?,player_sex=?,player_phone=?,player_birthday=? where player_id=?";
        return utils.update(sql,obj);
    }

    public int deleteById(Integer id) {
        String sql = "delete from player where player_id=?";
        return utils.update(sql,new Object[]{id});
    }
}
