package com.gzu.trainingcommon.service;

import com.gzu.trainingcommon.mapper.AuthorMapper;
import com.gzu.trainingcommon.mapper.SysMapper;
import com.gzu.trainingcommon.model.Authority;
import com.gzu.trainingcommon.model.Menu;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 * @ClassName:     SysServiceImpl.java
 * @Description:   系统相关业务处理Service实现类
 *
 * @author         郭华
 * @version        V1.0
 * @Date           2020.2.19
 */
@Service
public class SysServiceImpl implements SysService {
    // 系统相关数据库操作
    @Autowired
    SysMapper sysMapper;

    @Autowired
    AuthorMapper authorMapper;

    /**
     * 获取所有一级菜单列表
     * @return
     */
    public List<Menu> getBigMenuMst(Menu menu){
        return sysMapper.getBigMenuMst(menu);
    }

    /**
     * 获取所有二级菜单列表
     * @return
     */
    public List<Menu> getSmallMenuMst(Menu menu){
        return sysMapper.getSmallMenuMst(menu);
    }

    /**
     * 查询单个菜单信息
     * @param menuid
     * @return
     */
    public Menu findSingleMenu(String menuid){
        return sysMapper.findSingleMenu(menuid);
    }

    /**
     * 插入一级菜单内容
     * @param menu
     * @return
     */
    public int insertMenu(Menu menu){
        if(!menu.getIcon().startsWith("<i class=\"iconfont\">") && !menu.getIcon().endsWith("</i>")){
            // 修改icon格式
            menu.setIcon("<i class=\"iconfont\">" + menu.getIcon() + "</i>");
        }
        // 执行插入数据操作
        int result = sysMapper.insertMenu(menu);

        if(result == 1){
            // 如果是最高权限管理员，直接把菜单权限插入
            Authority authority = new Authority();
            authority.setRoleval("0");
            authority.setMenuid(menu.getMenuid());
            // 数据插入
            result += sysMapper.insertAuthority(authority);
        }

        return result;
    }

    /**
     * 更新菜单数据
     * @param menu
     * @return
     */
    public int updateMenu(Menu menu){
        // 如果没有加上标签，则添加标签
        if(!menu.getIcon().startsWith("<i class=\"iconfont\">") && !menu.getIcon().endsWith("</i>")){
            // 修改icon格式
            menu.setIcon("<i class=\"iconfont\">" + menu.getIcon() + "</i>");
        }
        return sysMapper.updateMenu(menu);
    }

    /**
     * 删除菜单数据
     * @param menuid
     * @return
     */
    @Transactional(rollbackFor = {RuntimeException.class, Error.class})
    public int deleteMenu(String menuid){
        // 删除权限表内容
        int result = authorMapper.delAuthorByMenuid(menuid);
        result += authorMapper.delAuthorBySubMenuid(menuid);

        // 删除菜单表
         result += sysMapper.deleteMenu(menuid);
        result += sysMapper.deleteSubMenu(menuid);

        return result;
    }

}
