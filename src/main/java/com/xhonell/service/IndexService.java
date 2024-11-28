package com.xhonell.service;

import com.xhonell.dao.IndexDao;
import com.xhonell.entity.Player;

import java.util.List;

/**
 * <p>Project:test_01 - IndexService
 * <p>POWER by xhonell on 2024-11-23 10:19
 * <p>description：
 * <p>idea：
 *
 * @author xhonell
 * @version 1.0
 * @since 1.8
 */
public class IndexService {
    IndexDao dao = new IndexDao();
    public List<Player> selectAll(Object[] obj) {
        return dao.selectAll(obj);
    }

    public List<Player> selectById(Object[] obj) {
        return dao.selectById(obj);
    }

    public boolean insertPlayer(Object[] obj) {
        return dao.insertPlayer(obj) > 0;
    }

    public boolean editPlayer(Object[] obj) {
        return dao.editPlayer(obj) > 0;
    }

    public boolean deleteById(Integer id) {
        return dao.deleteById(id)>0;
    }

    public  boolean batchAdd(List<Player> list) {
        return dao.batchAdd(list)>=list.size();
    }
}

