package com.gzu.traininguser.model;

import com.baomidou.mybatisplus.annotation.TableId;
import com.gzu.trainingcommon.model.PageBean;
import lombok.Data;

@Data
public class User extends PageBean {
    @TableId  // 如果是自增主键
    private Long id;

    private String username;
    private String password;
    private String role;
    private String regtime;
    private String telno;
    private String email;
    private String usedatestart;
    private String usedateend;
    private String realname;
    private String ins_id;
    private Integer status;

    private String usedate;
    private String oldpassword;
}
