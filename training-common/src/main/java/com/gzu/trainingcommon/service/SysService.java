package com.gzu.trainingcommon.service;

import com.gzu.trainingcommon.model.Menu;

import java.util.List;

/**
 * @ClassName:     SysService.java
 * @Description:   系统相关业务处理Service
 *
 * @author         郭华
 * @version        V1.0
 * @Date           2020.2.19
 */

public interface SysService {

    /**
     * 获取所有一级菜单列表
     * @return
     */
    public List<Menu> getBigMenuMst(Menu menu);

    /**
     * 获取所有二级菜单列表
     * @return
     */
    public List<Menu> getSmallMenuMst(Menu menu);

    /**
     * 查询单个菜单
     * @param menuid
     * @return
     */
    public Menu findSingleMenu(String menuid);

    /**
     * 插入一级菜单内容
     * @param menu
     * @return
     */
    public int insertMenu(Menu menu);

    /**
     * 更新菜单数据
     * @param menu
     * @return
     */
    public int updateMenu(Menu menu);

    /**
     * 删除菜单数据
     * @param menuid
     * @return
     */
    public int deleteMenu(String menuid);


}
