package com.gzu.trainingcommon.mapper;

import com.gzu.trainingcommon.model.Authority;
import org.apache.ibatis.annotations.DeleteProvider;
import org.apache.ibatis.annotations.InsertProvider;
import org.apache.ibatis.annotations.SelectProvider;
import org.apache.ibatis.jdbc.SQL;

import java.util.List;

/**
 * @ClassName:     AuthorProvider.java
 * @Description:   权限相关数据库操作Mapper
 *
 * @author         郭华
 * @version        V1.0
 * @Date           2020.2.19
 */

public class AuthorProvider {

    /**
     * 获取每个value下对应的角色名
     * @return
     */
    public String getRoleVale(){
        return new SQL(){
            {
                SELECT("value,group_concat(rolename) AS rolenames");
                FROM("s_role");
                GROUP_BY("value");
            }
        }.toString();
    }

    /**
     * 根据角色查出对应的菜单列表
     * @param roleval
     * @return
     */
    public String getAuthorList(String roleval){
        return new SQL(){
            {
                SELECT("s1.menuid, " +
                        "s1.submenuid, " +
                        "s1.submenuname as submenuname, " +
                        "s1.menuname, " +
                        "s1.addtime, " +
                        "#{roleval} AS roleval, " +
                        "case when s2.roleval is null then 0 else 1 end AS ischecked");
                FROM("(select t1.menuid, " +
                        "t1.submenuid, " +
                        "t2.menuname as submenuname, " +
                        "t1.menuname, " +
                        "t1.addtime from s_menu t1, s_menu t2 " +
                        "where t1.submenuid = t2.menuid) s1");
                LEFT_OUTER_JOIN("(select menuid,roleval " +
                        "from s_authority " +
                        "where roleval = #{roleval}) s2 on s1.menuid = s2.menuid");
                ORDER_BY("s1.submenuid");
            }
        }.toString();
    }

    /**
     * 根据角色对应值删除该角色下的全部菜单
     * @param roleval
     * @return
     */
    public String delAuthorByVal(String roleval){
        return new SQL(){
            {
                DELETE_FROM("s_authority");
                WHERE("roleval=#{roleval}");
            }
        }.toString();
    }

    /**
     * 根据菜单ID删除该角色下的全部菜单
     * @param menuid
     * @return
     */
    public String delAuthorByMenuid(String menuid){
        return new SQL(){
            {
                DELETE_FROM("s_authority");
                WHERE("menuid=#{menuid}");
            }
        }.toString();
    }

    /**
     * 根据主菜单ID删除该角色下的全部菜单
     * @param menuid
     * @return
     */
    public String delAuthorBySubMenuid(String menuid){
        return new SQL(){
            {
                DELETE_FROM("s_authority");
                WHERE("menuid=(select menuid from s_menu where submenuid = #{menuid})");
            }
        }.toString();
    }

    /**
     * 插入新的权限菜单
     * @param authority
     * @return
     */
    public String insertAuthority(Authority authority){
        return new SQL(){
            {
                INSERT_INTO("s_authority");
                VALUES("roleval, menuid","#{roleval}, #{menuid}");
            }
        }.toString();
    }
}
