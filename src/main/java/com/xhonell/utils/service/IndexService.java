package com.xhonell.utils.service;

import com.xhonell.utils.dao.IndexDao;
import com.xhonell.utils.entity.Player;

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
}

