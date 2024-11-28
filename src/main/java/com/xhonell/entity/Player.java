package com.xhonell.entity;

import com.alibaba.fastjson.annotation.JSONField;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;

/**
 * <p>Project:test_01 - player
 * <p>POWER by xhonell on 2024-11-22 20:25
 * <p>description：
 * <p>idea：
 *
 * @author xhonell
 * @version 1.0
 * @since 1.8
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Player {
    private String player_img;
    private Integer player_id;
    private String player_nickName;
    private String player_password;
    @JSONField(format = "yyyy-MM-dd")
    private Date player_birthday;
    private Long player_phone;
    private String player_sex;
}
