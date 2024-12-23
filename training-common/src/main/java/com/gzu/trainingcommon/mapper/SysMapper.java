package com.gzu.trainingcommon.mapper;

import com.gzu.trainingcommon.model.Authority;
import com.gzu.trainingcommon.model.Menu;
import com.gzu.trainingcommon.model.User;
import org.apache.ibatis.annotations.*;

import java.util.List;

/**
 * @ClassName:     UserMapper.java
 * @Description:   用户相关数据库操作Mapper
 *
 * @author         郭华
 * @version        V1.0
 * @Date           2020.2.19
 */

@Mapper
public interface SysMapper {

    /**
     * 查询所有的大分类菜单列表
     * @return
     */
    @SelectProvider(type = SysProvider.class, method = "getBigMenuMst")
    public List<Menu> getBigMenuMst(Menu menu);


    /**
     * 查询所有的小分类菜单列表
     * @return
     */
    @SelectProvider(type = SysProvider.class, method = "getSmallMenuMst")
    public List<Menu> getSmallMenuMst(Menu menu);

    /**
     * 查询单个菜单信息
     * @param menuid
     * @return
     */
    @SelectProvider(type = SysProvider.class, method = "findSingleMenu")
    public Menu findSingleMenu(String menuid);

    /**
     * 插入菜单内容（一级，二级）
     * @param menu
     * @return
     */
    @Options(useGeneratedKeys = true, keyProperty = "menuid", keyColumn = "menuid")
    @InsertProvider(type = SysProvider.class, method = "insertMenu")
    public int insertMenu(Menu menu);

    /**
     * 更新菜单数据（一级，二级）
     * @param menu
     * @return
     */
    @UpdateProvider(type = SysProvider.class, method = "updateMenu")
    public int updateMenu(Menu menu);

    /**
     * 删除菜单数据
     * @param menuid
     * @return
     */
    @DeleteProvider(type = SysProvider.class, method = "deleteMenu")
    public int deleteMenu(String menuid);


    /**
     * 删除一级菜单下的二级菜单数据
     * @param menuid
     * @return
     */
    @DeleteProvider(type = SysProvider.class, method = "deleteSubMenu")
    public int deleteSubMenu(String menuid);

    /**
     * 插入菜单权限设置数据
     * @param
     * @return
     */
    @InsertProvider(type = SysProvider.class, method = "insertAuthority")
    public int insertAuthority(Authority authority);
}
