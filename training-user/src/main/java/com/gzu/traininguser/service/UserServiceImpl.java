package com.gzu.traininguser.service;

import com.gzu.trainingcommon.model.Menu;
import com.gzu.trainingcommon.model.Role;
import com.gzu.traininguser.mapper.UserMapper;
import com.gzu.traininguser.model.User;
import jakarta.annotation.Resource;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.util.DigestUtils;

import java.util.List;

@Service
public class UserServiceImpl implements UserService {
    @Resource
    UserMapper userMapper;
    @Autowired
    PasswordEncoder passwordEncoder;

    public User findByUsername(String username){
        return userMapper.findByUsername(username);
    }

    /**
     * 获取用户的菜单列表业务
     * @param user
     * @return
     */
    @Override
    public List<Menu> getMenuList(User user) {
        return userMapper.getMenuList(user);
    }


    public int saveUser(User user) {
        // 加密用户密码
        user.setPassword(passwordEncoder.encode(user.getPassword()));
        return userMapper.saveUser(user);
    }


    /**
     * 更新密码
     * @param user
     * @return
     */
    public int updatePassword(User user){
        String md5Password = DigestUtils.md5DigestAsHex(user.getPassword().getBytes());
        user.setPassword(md5Password);
        return userMapper.updatePassword(user);
    }

    /**
     * 判断密码是否正确
     * @param user
     * @return
     */
    public boolean checkPassword(User user){
        User u = userMapper.findSingleUserByUsername(user);
        // 取用户输入的密码进行加密对比
        String encPassword = passwordEncoder.encode(user.getOldpassword());
        // 判断加密后是否一致
        if(u.getPassword().equals(encPassword)){
            // 登录成功
            return true;
        }
        else{
            // 登录失败
            return false;
        }
    }
    /**
     * 获取单个用户
     * @param username
     * @return
     */
    public User getSingleUser(String username){
        User user = new User();
        user.setUsername(username);
        return userMapper.findSingleUserByUsername(user);
    }

    /**
     * 获取用户列表
     * @param user
     * @return
     */
    public List<User> getUserList(User user){
        return userMapper.getUserList(user);
    }


    /**
     * 获取角色列表
     * @return
     */
    public List<Role> getRoleList(){
        return userMapper.getRoleList();
    }

    /**
     * 插入用户数据
     * @param user
     * @return
     */
    public int insertUser(User user){
        // 对用户输入的密码进行MD5加密
        String md5Password = DigestUtils.md5DigestAsHex(user.getPassword().getBytes());
        user.setPassword(md5Password);

        // 拆分有效日期
        String useDate = user.getUsedate();
        String[] datas = useDate.split("~");

        user.setUsedatestart(datas[0].trim());
        user.setUsedateend(datas[1].trim());

        return userMapper.insertUser(user);
    }

    /**
     * 查询用户是否已经存在
     * @param username
     * @return
     */
    public int findUserCountByUsername(String username){
        return userMapper.findUserCountByUsername(username);
    }

    /**
     * 删除用户信息
     * @param username
     * @return
     */
    public int deleteUser(String username){
        return userMapper.deleteUser(username);
    }

    /**
     * 用户信息更新
     * @return
     */
    public int updateUser(User user){
        // 若日期存在，拆分有效日期
        String useDate = user.getUsedate();
        if(useDate != null && useDate.equals("") == false ){
            String[] datas = useDate.split("~");

            user.setUsedatestart(datas[0].trim());
            user.setUsedateend(datas[1].trim());
        }

        return userMapper.updateUser(user);
    }

    /**
     * 查询用户是否过期
     * @return
     */
    public User checkDateUse(User user){
        return userMapper.checkDateUse(user);
    }

    /**
     * 查询系统管理员个数
     * @return
     */
    public int getAdminUserCnt(){
        return userMapper.getAdminUserCnt();
    }

    /**
     * 查询所有的所属科室数据
     * @return
     */
    public List<User> getAllInsNoList(){
        return userMapper.getAllInsNoList();
    }
}
