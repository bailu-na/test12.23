package com.gzu.traininguser.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.gzu.trainingcommon.model.Menu;
import com.gzu.trainingcommon.model.Role;
import org.apache.ibatis.annotations.*;
import com.gzu.traininguser.model.User;

import java.util.List;

@Mapper
public interface UserMapper  {
    /**
     * 查询登录用个人信息
     * @param username
     * @return
     */
    @SelectProvider(type = UserProvider.class, method = "findByUsername")
    User findByUsername(String username);
    int saveUser(User user);

    /**
     * 获取用户的菜单列表业务
     * @param user
     * @return
     */
    @SelectProvider(type = UserProvider.class, method = "getMenuList")
    public List<Menu> getMenuList(User user);

    /**
     * 根据用户名、电话号码、邮箱查询单个登录用户
     * @param user
     * @return
     */
    @SelectProvider(type = UserProvider.class, method = "findSingleUser")
    public User findSingleUser(User user);

    /**
     * 根据用户名单个登录用户
     * @param user
     * @return
     */
    @SelectProvider(type = UserProvider.class, method = "findSingleUserByUsername")
    public User findSingleUserByUsername(User user);

    /**
     * 获取用户列表
     * @param user
     * @return
     */
    @SelectProvider(type = UserProvider.class, method = "getUserList")
    public List<User> getUserList(User user);

    /**
     * 获取角色列表
     * @return
     */
    @SelectProvider(type = UserProvider.class, method = "getRoleList")
    public List<Role> getRoleList();

    /**
     * 插入用户数据
     * @param user
     * @return
     */
    @InsertProvider(type = UserProvider.class, method = "insertUser")
    public int insertUser(User user);

    /**
     * 查询用户是否已经存在
     * @param username
     * @return
     */
    @SelectProvider(type = UserProvider.class, method = "findUserCountByUsername")
    public int findUserCountByUsername(String username);

    /**
     * 删除用户信息
     * @param username
     * @return
     */
    @DeleteProvider(type = UserProvider.class, method = "deleteUser")
    public int deleteUser(String username);

    /**
     * 用户信息更新
     * @return
     */
    @UpdateProvider(type = UserProvider.class, method = "updateUser")
    public int updateUser(User user);

    /**
     * 修改用户密码
     * @param user
     * @return
     */
    @UpdateProvider(type = UserProvider.class, method = "updatePassword")
    public int updatePassword(User user);

    /**
     * 查询用户是否过期
     * @return
     */
    @SelectProvider(type = UserProvider.class, method = "checkDateUse")
    public User checkDateUse(User user);

    /**
     * 查询系统管理员个数
     * @return
     */
    @SelectProvider(type = UserProvider.class, method = "getAdminUserCnt")
    public int getAdminUserCnt();

    /**
     * 查询所有的所属科室数据
     * @return
     */
    @SelectProvider(type = UserProvider.class, method = "getAllInsNoList")
    public List<User> getAllInsNoList();

}
