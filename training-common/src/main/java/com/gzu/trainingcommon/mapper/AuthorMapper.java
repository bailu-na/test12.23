package com.gzu.trainingcommon.mapper;

import com.gzu.trainingcommon.model.Authority;
import com.gzu.trainingcommon.model.Role;
import org.apache.ibatis.annotations.*;

import java.util.List;

/**
 * @ClassName:     AuthorMapper.java
 * @Description:   权限相关数据库操作Mapper
 *
 * @author         郭华
 * @version        V1.0
 * @Date           2020.2.19
 */

@Mapper
public interface AuthorMapper {

    /**
     * 获取每个value下对应的角色名
     * @return
     */
    @SelectProvider(type = AuthorProvider.class, method = "getRoleVale")
    public List<Role> getRoleVale();

    /**
     * 根据角色查出对应的菜单列表
     * @param roleval
     * @return
     */
    @SelectProvider(type = AuthorProvider.class, method = "getAuthorList")
    public List<Authority> getAuthorList(String roleval);

    /**
     * 根据角色对应值删除该角色下的全部菜单
     * @param roleval
     * @return
     */
    @DeleteProvider(type = AuthorProvider.class, method = "delAuthorByVal")
    public int delAuthorByVal(String roleval);

    /**
     * 根据菜单ID删除该角色下的全部菜单
     * @param menuid
     * @return
     */
    @DeleteProvider(type = AuthorProvider.class, method = "delAuthorByMenuid")
    public int delAuthorByMenuid(String menuid);

    /**
     * 根据主菜单ID删除该角色下的全部菜单
     * @param menuid
     * @return
     */
    @DeleteProvider(type = AuthorProvider.class, method = "delAuthorBySubMenuid")
    public int delAuthorBySubMenuid(String menuid);

    /**
     * 插入新的权限菜单
     * @param authority
     * @return
     */
    @InsertProvider(type = AuthorProvider.class, method = "insertAuthority")
    public int insertAuthority(Authority authority);
}
