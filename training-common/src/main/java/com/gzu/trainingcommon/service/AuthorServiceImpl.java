package com.gzu.trainingcommon.service;

import com.gzu.trainingcommon.mapper.AuthorMapper;
import com.gzu.trainingcommon.model.Authority;
import com.gzu.trainingcommon.model.Role;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * @ClassName:     AuthorServiceImpl.java
 * @Description:   权限控制相关业务处理Service
 *
 * @author         郭华
 * @version        V1.0
 * @Date           2020.2.29
 */

@Service
public class AuthorServiceImpl implements AuthorService {

    @Autowired
    AuthorMapper authorMapper;

    /**
     * 获取每个value下对应的角色名
     * @return
     */
    public List<Role> getRoleVale(){
        return authorMapper.getRoleVale();
    }

    /**
     * 根据角色查出对应的菜单列表
     * @param roleval
     * @return
     */
    public List<Authority> getAuthorList(String roleval){
        return authorMapper.getAuthorList(roleval);
    }

    /**
     * 保存当前权限
     * @param list
     * @return
     */
    public int updateAuthor(List<Authority> list){
        // 获取当前权限值
        String roleval = list.get(0).roleval;

        // 删除原有的数据
        authorMapper.delAuthorByVal(roleval);

        int result = 0;

        // 循环list
        for(Authority authority : list){
            result += authorMapper.insertAuthority(authority);
        }

        // 判断插入数据是否正确
        if(result == list.size()){
            return 1;
        }

        return 0;
    }
}
