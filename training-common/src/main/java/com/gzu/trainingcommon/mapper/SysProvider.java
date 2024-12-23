package com.gzu.trainingcommon.mapper;

import com.gzu.trainingcommon.model.Authority;
import com.gzu.trainingcommon.model.Menu;
import com.gzu.trainingcommon.model.User;
import org.apache.ibatis.annotations.SelectProvider;
import org.apache.ibatis.jdbc.SQL;

import java.util.List;

/**
 * @ClassName:     SysProvider.java
 * @Description:   系统相关数据库操作Mapper
 *
 * @author         郭华
 * @version        V1.0
 * @Date           2020.2.19
 */

public class SysProvider {
    /**
     * 查询所有的大分类菜单列表
     * @return
     */
    public String getBigMenuMst(Menu menu){
        return new SQL(){
            {
                SELECT("t1.menuid, t1.menuname, t1.url, t1.icon, t1.addtime, t1.username, t1.submenuid");
                FROM("s_menu t1");
                WHERE("submenuid is null OR submenuid = ''");
                if(menu.getMenuname() != null && menu.getMenuname().equals("") == false){
                    WHERE("t1.menuname like CONCAT('%',#{menuname},'%') OR menuid in (select submenuid " +
                            "from s_menu t2 " +
                            "WHERE t2.submenuid = t1.menuid " +
                            "and  t2.menuname like CONCAT('%',#{menuname},'%') " +
                            ")");

                }
            }
        }.toString();
    }

    /**
     * 查询所有的小分类菜单列表
     * @return
     */
    public String getSmallMenuMst(Menu menu){
        return new SQL(){
            {
                SELECT("*");
                FROM("s_menu");
                WHERE("submenuid is not null");
                WHERE("submenuid = #{menuid}");

            }
        }.toString();
    }

    /**
     * 查询单个菜单信息
     * @return
     */
    public String findSingleMenu(String menuid){
        return new SQL(){
            {
                SELECT("t1.submenuid, t2.menuname AS submenuname, t1.menuid, t1.menuname, t1.pageurl");
                FROM("s_menu t1, s_menu t2");
                WHERE("t1.submenuid = t2.menuid");
                WHERE("t1.menuid = #{menuid}");
            }
        }.toString();
    }

    /**
     * 插入菜单内容（一级、二级）
     * @param menu
     * @return
     */
    public String insertMenu(Menu menu){
        return new SQL(){
            {
                INSERT_INTO("s_menu");
                // 一级菜单
                if(menu.getSubmenuid() == null || menu.getSubmenuid().equals("")){
                    VALUES("menuid,menuname,addtime,username,icon","#{menuid},#{menuname},now(),#{username},#{icon}");
                }
                // 二级菜单
                else{VALUES("menuid,menuname,submenuid,addtime,username,url,pageurl","#{menuid},#{menuname},#{submenuid},now(),#{username},#{url},#{pageurl}");
                }
            }
        }.toString();
    }

    /**
     * 更新菜单内容（一级、二级）
     * @param menu
     * @return
     */
    public String updateMenu(Menu menu){
        return new SQL(){
            {
                UPDATE("s_menu");
                SET("menuname=#{menuname}");
                if(menu.getUrl() != null && menu.getUrl().equals("") == false){
                    SET("url=#{url}");
                }
                if(menu.getPageurl() != null && menu.getPageurl().equals("") == false){
                    SET("pageurl=#{pageurl}");
                }
                if(menu.getIcon() != null && menu.getIcon().equals("") == false){
                    SET("icon=#{icon}");
                }
                WHERE("menuid=#{menuid}");
            }
        }.toString();
    }

    /**
     * 删除菜单数据
     * @param menuid
     * @return
     */
    public String deleteMenu(String menuid){
        return new SQL(){
            {
                DELETE_FROM("s_menu");
                WHERE("menuid=#{menuid}");
            }
        }.toString();
    }

    /**
     * 删除一级菜单下的二级菜单数据
     * @param menuid
     * @return
     */
    public String deleteSubMenu(String menuid){
        return new SQL(){
            {
                DELETE_FROM("s_menu");
                WHERE("submenuid=#{menuid}");
            }
        }.toString();
    }

    /**
     * 插入菜单权限设置数据
     * @param authority
     * @return
     */
    public String insertAuthority(Authority authority){
        return new SQL(){
            {
                INSERT_INTO("s_authority");
                VALUES("roleval,menuid","#{roleval},#{menuid}");
            }
        }.toString();
    }

    /**
     * 获取公共数据的指定数据
     * @param classifyid
     * @return
     */
    public String getCommonDateList(String classifyid){
        return new SQL(){
            {
                SELECT("classifyid, classifyname, typeid, typename");
                FROM("s_commondata");
                WHERE("classifyid=#{classifyid}");
                ORDER_BY("typeid");
            }
        }.toString();
    }
}
