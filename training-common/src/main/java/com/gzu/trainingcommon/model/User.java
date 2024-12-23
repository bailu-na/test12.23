package com.gzu.trainingcommon.model;


import lombok.Data;

/**
 * @ClassName:     User.java
 * @Description:   用户信息Model
 *
 * @author         郭华
 * @version        V1.0
 * @Date           2020.2.19
 */
@Data
public class User extends PageBean{
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

    private String oldpassword;
    private String rolename;
    private String authorityid;
    private String authorityname;
    private String usedate;
    private String overdate;
    private String ins_no;
    private String ins_department;

    private String menuid;
    private String roleval;
}