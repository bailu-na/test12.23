package com.gzu.trainingcommon.service;

import com.gzu.trainingcommon.mapper.RoleMapper;
import com.gzu.trainingcommon.model.Role;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * @ClassName:     RoleServiceImpl.java
 * @Description:   角色相关业务处理Service
 *
 * @author         郭华
 * @version        V1.0
 * @Date           2020.2.19
 */

@Service
public class RoleServiceImpl implements RoleService{

    // 系统相关数据库操作
    @Autowired
    RoleMapper roleMapper;

    /**
     * 获取角色列表
     * @param role
     * @return
     */
    public List<Role> getRoleList(Role role){
        return roleMapper.getRoleList(role);
    }

    /**
     * 插入角色数据
     * @param role
     * @return
     */
    public int insertRole(Role role){
        return roleMapper.insertRole(role);
    }

    /**
     * 查询角色是否已经存在
     * @param roleid
     * @return
     */
    public int findRoleCountById(String roleid){
        return roleMapper.findRoleCountById(roleid);
    }

    /**
     * 删除角色信息
     * @param roleid
     * @return
     */
    public int deleteRole(String roleid){
        return roleMapper.deleteRole(roleid);
    }

    /**
     * 角色信息更新
     * @return
     */
    public int updateRole(Role role){
        return roleMapper.updateRole(role);
    }

    /**
     * 获取角色权限值
     * @param roleId
     * @return
     */
    public Role getRoleByRoleID(String roleId){
        return roleMapper.getRoleByRoleID(roleId);
    }

}
