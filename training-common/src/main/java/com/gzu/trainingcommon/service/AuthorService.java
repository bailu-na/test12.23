package com.gzu.trainingcommon.service;

import com.gzu.trainingcommon.model.Authority;
import com.gzu.trainingcommon.model.Role;

import java.util.List;

/**
 * @ClassName:     AuthorService.java
 * @Description:   权限控制相关业务处理Service
 *
 * @author         郭华
 * @version        V1.0
 * @Date           2020.2.29
 */

public interface AuthorService {
    /**
     * 获取每个value下对应的角色名
     * @return
     */
    public List<Role> getRoleVale();
    /**
     * 根据角色查出对应的菜单列表
     * @param roleval
     * @return
     */
    public List<Authority> getAuthorList(String roleval);

    /**
     * 保存当前权限
     * @param menus
     * @return
     */
    public int updateAuthor(List<Authority> menus);
}
