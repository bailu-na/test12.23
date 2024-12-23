package com.gzu.trainingcommon.mapper;

import com.gzu.trainingcommon.model.Role;
import org.apache.ibatis.annotations.DeleteProvider;
import org.apache.ibatis.annotations.InsertProvider;
import org.apache.ibatis.annotations.SelectProvider;
import org.apache.ibatis.annotations.UpdateProvider;
import org.apache.ibatis.jdbc.SQL;

import java.util.List;

/**
 * @ClassName:     RoleProvider.java
 * @Description:   角色相关数据库操作Mapper
 *
 * @author         郭华
 * @version        V1.0
 * @Date           2020.2.19
 */

public class RoleProvider {
    /**
     * 获取角色列表
     * @param role
     * @return
     */
    public String getRoleList(Role role){
        return new SQL(){
            {
                SELECT("t1.roleid, t1.rolename, t1.value");
                FROM("s_role t1");
                if(role.getRolename()!=null && role.getRolename().equals("") == false){
                    WHERE("t1.rolename like CONCAT('%',#{rolename},'%')");
                }
            }
        }.toString();
    }

    /**
     * 插入角色数据
     * @param role
     * @return
     */
    public String insertRole(Role role){
        return new SQL(){
            {
                INSERT_INTO("s_role");
                VALUES("roleid, rolename, value","#{roleid}, #{rolename}, #{value}");
            }
        }.toString();
    }

    /**
     * 查询角色是否已经存在
     * @param roleid
     * @return
     */
    public String findRoleCountById(String roleid){
        return new SQL(){
            {
                SELECT("COUNT(0) AS cnt");
                FROM("s_role");
                WHERE("roleid=#{roleid}");
            }
        }.toString();
    }

    /**
     * 删除角色信息
     * @param roleid
     * @return
     */
    public String deleteRole(String roleid){
        return new SQL(){
            {
                DELETE_FROM("s_role");
                WHERE("roleid=#{roleid}");
            }
        }.toString();
    }

    /**
     * 角色信息更新
     * @return
     */
    public String updateRole(Role role){
        return new SQL(){
            {
                UPDATE("s_role");
                SET("rolename=#{rolename}, value=#{value}");
                WHERE("roleid=#{roleid}");
            }
        }.toString();
    }

    /**
     * 获取角色权限值
     * @param roleId
     * @return
     */
    public String getRoleByRoleID(String roleId){
        return new SQL(){
            {
                SELECT("roleid" +
                        ",rolename" +
                        ",value");
                FROM("s_role");
                WHERE("roleid = #{roleid}");
            }
        }.toString();
    }
}
