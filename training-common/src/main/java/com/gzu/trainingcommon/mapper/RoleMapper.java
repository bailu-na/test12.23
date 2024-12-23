package com.gzu.trainingcommon.mapper;

import com.gzu.trainingcommon.model.Role;
import org.apache.ibatis.annotations.*;

import java.util.List;

/**
 * @ClassName:     RoleMapper.java
 * @Description:   角色相关数据库操作Mapper
 *
 * @author         郭华
 * @version        V1.0
 * @Date           2020.2.19
 */

@Mapper
public interface RoleMapper {
    /**
     * 获取角色列表
     * @param role
     * @return
     */
    @SelectProvider(type = RoleProvider.class, method = "getRoleList")
    public List<Role> getRoleList(Role role);

    /**
     * 插入角色数据
     * @param role
     * @return
     */
    @InsertProvider(type = RoleProvider.class, method = "insertRole")
    public int insertRole(Role role);

    /**
     * 查询角色是否已经存在
     * @param roleid
     * @return
     */
    @SelectProvider(type = RoleProvider.class, method = "findRoleCountById")
    public int findRoleCountById(String roleid);

    /**
     * 删除角色信息
     * @param roleid
     * @return
     */
    @DeleteProvider(type = RoleProvider.class, method = "deleteRole")
    public int deleteRole(String roleid);

    /**
     * 角色信息更新
     * @return
     */
    @UpdateProvider(type = RoleProvider.class, method = "updateRole")
    public int updateRole(Role role);

    /**
     * 获取角色权限值
     * @param roleId
     * @return
     */
    @SelectProvider(type = RoleProvider.class,method = "getRoleByRoleID")
    public Role getRoleByRoleID(String roleId);
}
