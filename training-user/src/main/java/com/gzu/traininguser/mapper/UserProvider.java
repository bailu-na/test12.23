package com.gzu.traininguser.mapper;

import com.gzu.traininguser.model.User;
import org.apache.ibatis.jdbc.SQL;

/**
 * @ClassName:     UserProvider.java
 * @Description:   系统相关数据库操作Mapper
 *
 * @author         郭华
 * @version        V1.0
 * @Date           2020.2.19
 */

public class UserProvider {
    public String findByUsername(String username) {

        return new SQL(){
            {
                SELECT("*");
                FROM("s_user");
                WHERE("username = #{username}");
            }
        }.toString();
    }


    /**
     * 执行查询用户菜单的SQL语句
     * @param user
     * @return
     */
    public String getMenuList(User user){
        return new SQL(){
            {
                SELECT("t1.submenuid, t1.submenuname, t1.menuid, t1.menuname, t1.url, t1.icon");
                FROM("(\n" +
                        "\tselect s1.menuid AS submenuid, s2.menuid, s1.menuname AS submenuname, s2.menuname, s2.url, s1.icon, s1.addtime, s1.orderno \n" +
                        "\tfrom s_menu s1, s_menu s2\n" +
                        "\twhere s1.menuid = s2.submenuid\n" +
                        ") t1, \n" +
                        "s_user t2, s_role t3, s_authority t4");
                WHERE("t1.menuid = t4.menuid");
                WHERE("t2.role = t3.role");
                WHERE("t3.value = t4.roleval");
                WHERE("t2.username = #{username}");
                ORDER_BY("t1.orderno,t1.submenuid,t1.addtime,t1.menuname");

            }
        }.toString();
    }

    /**
     * 执行查询登录用户的SQL语句
     * @param user
     * @return
     */
    public String findSingleUser(User user){
        return new SQL(){
            {
                SELECT("t1.username," +
                        "t1.password, " +
                        "t1.roleid," +
                        "t1.enable," +
                        "t1.usedatestart," +
                        "t1.usedateend," +
                        "t1.realname," +
                        "case when CURDATE() between t1.usedatestart and t1.usedateend then 0 else 1 end AS overdate," +
                        "concat(t1.usedatestart,' ~ ',t1.usedateend) usedate, " +
                        "t2.rolename, " +
                        "t1.telno, " +
                        "t1.email, " +
                        "t1.ins_department, " +
                        "t1.retention," +
                        "t2.value roleval, " +
                        "GROUP_CONCAT(t3.menuid) menuid ");
                FROM("s_role t2, s_authority t3, s_user t1");
                WHERE("t1.roleid = t2.roleid");
                WHERE("t3.roleval = t2.value");
                // 电话号码登录
                if(user.getTelno() != null){
                    WHERE("telno = #{telno}");
                }
                // 邮箱登录
                else if(user.getEmail() != null){
                    WHERE("email = #{email}");
                }
                // 用户名登录
                else {
                    WHERE("username = #{username}");
                }

            }
        }.toString();
    }

    /**
     * 执行查询登录用户的SQL语句
     * @param user
     * @return
     */
    public String findSingleUserByUsername(User user){
        return new SQL(){
            {
                SELECT("t1.username, t1.password,t1.realname, concat(t1.usedatestart,' ~ ',t1.usedateend) usedate, t1.roleid, t1.telno, t1.email, t1.retention, t2.rolename");
                FROM("s_user t1, s_role t2");
                WHERE("t1.roleid = t2.roleid");
                WHERE("username = #{username}");
            }
        }.toString();
    }

    /**
     * 获取用户列表
     * @param user
     * @return
     */
    public String getUserList(User user){
        return new SQL(){
            {
                SELECT("t1.username, t1.roleid, t1.ins_no, t1.ins_department, t2.rolename, t1.regtime, t1.telno, t1.email, t1.enable, t1.realname, concat(t1.usedatestart,'~',t1.usedateend) usedate" +
                        ", case when t1.usedateend < now() then 1 else 0 end as overdate, t1.email, t1.telno");
                FROM("s_user t1");
                LEFT_OUTER_JOIN("s_role t2 on t1.roleid = t2.roleid");
                if(user.getUsername() != null && user.getUsername().equals("") == false){
                    WHERE("t1.username like CONCAT('%',#{username},'%') OR t1.realname like CONCAT('%',#{username},'%')");
                }
                if(user.getRole() != null && user.getRole().equals("") == false){
                    WHERE("t1.roleid = #{roleid}");
                }
                ORDER_BY("t1.regtime");
            }
        }.toString();
    }

    /**
     * 获取用户列表
     * @param user
     * @return
     */
    public String getDoctorUserList(User user){
        return new SQL(){
            {
                SELECT("t1.username, t1.roleid, t1.regtime, t1.telno, t1.email, t1.enable, t1.realname, concat(t1.usedatestart,'~',t1.usedateend) usedate" +
                        ", case when t1.usedateend < now() then 1 else 0 end as overdate, t1.email, t1.telno");
                FROM("s_user t1");
                WHERE("t1.roleid != 'admin'");
                ORDER_BY("t1.regtime, t1.username");
            }
        }.toString();
    }

    /**
     * 获取角色列表
     * @return
     */
    public String getRoleList(){
        return new SQL(){
            {
                SELECT("roleid, rolename, value");
                FROM("s_role");
            }
        }.toString();
    }

    /**
     * 插入用户信息
     * @param user
     * @return
     */
    public String insertUser(User user){
        return new SQL(){
            {
                INSERT_INTO("s_user");
                VALUES("username, password, roleid, regtime, enable, usedatestart, usedateend, realname, telno, email, ins_no, ins_department",
                        "#{username}, #{password}, #{roleid}, now(), #{enable}, #{usedatestart}, #{usedateend}, #{realname}, #{telno}, #{email}, #{ins_no}, #{ins_department}");
            }
        }.toString();
    }

    /**
     * 查询用户是否已经存在
     * @param username
     * @return
     */
    public String findUserCountByUsername(String username){
        return new SQL(){
            {
                SELECT("count(0)");
                FROM("s_user");
                WHERE("username = #{username}");
            }
        }.toString();
    }

    /**
     * 删除用户信息
     * @param username
     * @return
     */
    public String deleteUser(String username){
        return new SQL(){
            {
                DELETE_FROM("s_user");
                WHERE("username = #{username}");
            }
        }.toString();
    }

    /**
     * 用户信息更新
     * @return
     */
    public String updateUser(User user){
        return new SQL(){
            {
                UPDATE("s_user");
                if(user.getRole() != null && user.getRole().equals("") == false){
                    SET("roleid = #{roleid}");
                }
                if(user.getStatus() != null && user.getStatus().equals("") == false){
                    SET("enable = #{enable}");
                }
                if(user.getUsedatestart() != null && user.getUsedatestart().equals("") == false){
                    SET("usedatestart = #{usedatestart}");
                }
                if(user.getUsedateend() != null && user.getUsedateend().equals("") == false){
                    SET("usedateend = #{usedateend}");
                }
                if(user.getRealname() != null && user.getRealname().equals("") == false){
                    SET("realname = #{realname}");
                }
                if(user.getEmail() != null && user.getEmail().equals("") == false){
                    SET("email = #{email}");
                }
                if(user.getTelno() != null && user.getTelno().equals("") == false){
                    SET("telno = #{telno}");
                }
                if(user.getIns_id() != null && user.getIns_id().equals("") == false){
                    SET("ins_no = #{ins_no}");
                }
                WHERE("username = #{username}");
            }
        }.toString();
    }

    /**
     * 修改用户密码
     * @param user
     * @return
     */
    public String updatePassword(User user){
        return new SQL(){
            {
                UPDATE("s_user");
                SET("password=#{password}");
                WHERE("username = #{username}");
            }
        }.toString();
    }

    /**
     * 查询用户是否过期
     * @param user
     * @return
     */
    public String checkDateUse(User user){
        return new SQL(){
           {
               SELECT("t1.usedatestart,t1.usedateend,case when CURDATE() between t1.usedatestart and t1.usedateend then 0 else 1 end AS overdate");
               FROM("s_user t1");
               WHERE("username = #{username}");
           }
        }.toString();
    }

    /**
     * 查询系统管理员个数
     * @return
     */
    public String getAdminUserCnt(){
        return new SQL(){
            {
                SELECT("count(0) cnt");
                FROM("s_user t1");
                WHERE("roleid = 'admin'");
            }
        }.toString();
    }

    public String getAllInsNoList(){
        return new SQL(){
            {
                SELECT("DISTINCT ins_no, ins_department");
                FROM("s_user");
                WHERE("ins_no is not null");
                ORDER_BY("ins_no");
            }
        }.toString();
    }
}
