package com.xhonell.dao;

import com.xhonell.entity.Player;
import com.xhonell.entity.Sex;
import com.xhonell.utils.DBHelper;
import com.xhonell.utils.JDBCUtils;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Arrays;
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
        String sql = "SELECT player_id, player_nickName, player_birthday, player_sex, player_phone, player_img FROM player LIMIT ?, 10";
        return helper.getBeanList(Player.class, sql,obj);
    }

    public List<Player> selectById(Object[] obj) {
        ArrayList<Object> arr = new ArrayList<>();
        StringBuffer sql = new StringBuffer("select player_id,player_nickName,player_birthday,player_sex,player_phone,player_img from player where 1=1");
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
        String sql = "insert into player(player_nickName,player_password,player_phone) values(?,?,?)";
        return utils.update(sql,obj);
    }

    public int editPlayer(Object[] obj) {
        if (obj.length == 5){
            String sql="update player set player_nickName=?,player_sex=?,player_phone=?,player_birthday=? where player_id=?";
            return utils.update(sql,obj);
        }else if(obj.length == 6){
            String sql="update player set player_nickName=?,player_sex=?,player_phone=?,player_birthday=?,player_img=? where player_id=?";
            return utils.update(sql,obj);
        }else{
            return 0;
        }

    }

    public int deleteById(Integer id) {
        String sql = "delete from player where player_id=?";
        return utils.update(sql,new Object[]{id});
    }

    public int batchAdd(List<Player> list) {
        Connection connection = utils.openConnection();
        int rows = 0;
        try {
            connection.setAutoCommit(false);
            String sql = "insert into player(player_nickName,player_phone,player_birthday,player_sex,player_password) values(?,?,?,?,?)";
            PreparedStatement preparedStatement = connection.prepareStatement(sql);
            for (Player player : list) {
                preparedStatement.setString(1, player.getPlayer_nickName());
                preparedStatement.setLong(2, player.getPlayer_phone());
                preparedStatement.setDate(3, new java.sql.Date(player.getPlayer_birthday().getTime()));
                preparedStatement.setString(4, player.getPlayer_sex());
                preparedStatement.setString(5, player.getPlayer_password());
                rows += preparedStatement.executeUpdate();
                connection.commit();
                connection.close();
                return rows;
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return rows;
    }

    public List<Sex> echarts() {
        String sql = "select  player_sex as name ,count(*) as count from player GROUP BY player_sex";
        List<Sex> beanList = helper.getBeanList(Sex.class, sql);
        return beanList;
    }
}
