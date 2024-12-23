package com.gzu.traininguser.service;

import com.gzu.trainingcommon.model.Menu;
import com.gzu.trainingcommon.model.Role;
import com.gzu.traininguser.model.User;

import java.util.List;

public interface UserService {
    User findByUsername(String username);
    /**
     * 获取单个用户
     * @param username
     * @return
     */
    public User getSingleUser(String username);

    /**
     * 获取用户列表
     * @param user
     * @return
     */
    public List<User> getUserList(User user);

    /**
     * 获取角色列表
     * @return
     */
    public List<Role> getRoleList();

    /**
     * 插入用户数据
     * @param user
     * @return
     */
    public int insertUser(User user);

    /**
     * 查询用户是否已经存在
     * @param username
     * @return
     */
    public int findUserCountByUsername(String username);

    /**
     * 删除用户信息
     * @param username
     * @return
     */
    public int deleteUser(String username);

    /**
     * 用户信息更新
     * @return
     */
    public int updateUser(User user);

    /**
     * 查询用户是否过期
     * @return
     */
    public User checkDateUse(User user);

    /**
     * 查询系统管理员个数
     * @return
     */
    public int getAdminUserCnt();

    /**
     * 查询所有的所属科室数据
     * @return
     */
    public List<User> getAllInsNoList();

    /**
     * 获取用户的菜单列表业务
     * @param user
     * @return
     */
    public List<Menu> getMenuList(User user);

    /**
     * 修改用户密码
     * @param user
     * @return
     */
    public int updatePassword(User user);

    /**
     * 检查用户旧密码是否正确
     * @param user
     * @return
     */
    public boolean checkPassword(User user);
}
