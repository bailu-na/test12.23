package com.gzu.trainingcommon.service;

import com.gzu.trainingcommon.model.Role;

import java.util.List;

/**
 * @ClassName:     RoleService.java
 * @Description:   角色相关业务处理Service
 *
 * @author         郭华
 * @version        V1.0
 * @Date           2020.2.19
 */


public interface RoleService {
    /**
     * 获取角色列表
     * @param role
     * @return
     */
    public List<Role> getRoleList(Role role);

    /**
     * 插入角色数据
     * @param role
     * @return
     */
    public int insertRole(Role role);

    /**
     * 查询角色是否已经存在
     * @param roleid
     * @return
     */
    public int findRoleCountById(String roleid);

    /**
     * 删除角色信息
     * @param roleid
     * @return
     */
    public int deleteRole(String roleid);

    /**
     * 角色信息更新
     * @return
     */
    public int updateRole(Role role);

    /**
     * 获取角色权限值
     * @param roleId
     * @return
     */
    public Role getRoleByRoleID(String roleId);
;
}
