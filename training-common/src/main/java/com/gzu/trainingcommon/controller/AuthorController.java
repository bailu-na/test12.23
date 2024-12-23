package com.gzu.trainingcommon.controller;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.gzu.trainingcommon.model.Authority;
import com.gzu.trainingcommon.model.ResultModel;
import com.gzu.trainingcommon.model.Role;
import com.gzu.trainingcommon.service.AuthorService;
import net.sf.json.JSONArray;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.annotation.Secured;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @ClassName:     AuthorController.java
 * @Description:   权限内容控制器
 *
 * @author         郭华
 * @version        V1.0
 * @Date           2020.2.19
 */
@Controller
@RequestMapping("/api/author")
public class AuthorController extends BaseController {

    @Autowired
    AuthorService authorService;

    /**
     * 打开权限分配页面
     * @param menuid
     * @return
     */
    @RequestMapping("/index")
    public String index(String menuid, Model model) {
        return super.index(menuid, model);
    }

    /**
     * 获取角色列表
     * @param role
     * @return
     */
    @RequestMapping("roles")
    @ResponseBody
    public Map getRoleList(Role role){
        // 设置分页
        PageHelper.startPage(role.getPage(),role.getLimit());
        // 查询所有一级菜单列表
        List<Role> list = authorService.getRoleVale();

        log.info("查询出"+ list.size() +"条角色数据。");

        PageInfo<Role> info = new PageInfo<Role>(list);

        // 构建layui固定返回格式
        Map map = new HashMap();
        map.put("code", 0);
        map.put("msg","正常返回数据。");
        map.put("count",info.getTotal());
        map.put("data", info.getList());

        return map;
    }

    /**
     * 根据角色的值获取权限列表
     * @param roleval
     * @return
     */
    @RequestMapping("/list")
    @ResponseBody
    public Map getAuthorList(String roleval){
        // 初始化
        Map map = new HashMap();
        // 判断角色id是否为空
        if(roleval == null || roleval.equals("")){
            map.put("code", -1);
            map.put("msg","角色值不能为空。");
            return map;
        }
        List<Authority> list =  authorService.getAuthorList(roleval);

        log.info("查询出"+ list.size() +"条权限数据。");

        map.put("code", 0);
        map.put("msg","正常返回数据。");
        map.put("count",list.size());
        map.put("data", list);

        return map;
    }

    /**
     * 更新角色值的权限内容
     * @param menus
     * @return
     */
    @RequestMapping("/update")
    @ResponseBody
    public ResultModel updateAuthorList(String menus){
        JSONArray json = JSONArray.fromObject(menus);
        List<Authority> list = JSONArray.toList(json, Authority.class);
        // 判断更新内容
        if(list.size() <= 0){
            log.info("没有需要修改的内容！");
            ResultModel resultModel = new ResultModel(1,"角色内容修改成功！","");
            return resultModel;
        }

        int result = authorService.updateAuthor(list);
        // 判断更新内容
        if(result > 0){
            log.info("权限修改成功！");
            ResultModel resultModel = new ResultModel(1,"权限内容修改成功！","");
            return resultModel;
        }
        else{
            log.info("权限修改出错。");
            // 创建返回内容
            ResultModel resultModel = new ResultModel(-1,"权限内容更新出错。","");
            return resultModel;
        }
    }
}
