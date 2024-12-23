package com.gzu.trainingcommon.controller;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.gzu.trainingcommon.model.ResultModel;
import com.gzu.trainingcommon.model.Role;
import com.gzu.trainingcommon.service.RoleService;
import jakarta.servlet.http.HttpServletRequest;
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
 * @ClassName:     RoleController.java
 * @Description:   系统的用户信息控制器
 *
 * @author         郭华
 * @version        V1.0
 * @Date           2020.2.19
 */

@Controller
@RequestMapping("/role")
public class RoleController extends BaseController {

    // 角色处理Service
    @Autowired
    public RoleService roleService;

    /**
     * 打开角色管理页面
     * @param menuid
     * @return
     */
    @RequestMapping("/roles")
    @Secured({"ROLE_admin"})
    public String index(String menuid, Model model) {
        return super.index(menuid, model);
    }


    /**
     * 获取所有角色列表
     * @return
     */
    @RequestMapping("/list")
    @Secured({"ROLE_admin"})
    @ResponseBody
    public Map roleList(Role role){
        // 设置分页
        PageHelper.startPage(role.getPage(),role.getLimit());
        // 查询所有一级菜单列表
        List<Role> list = roleService.getRoleList(role);

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
     * 打开添加角色页面
     * @param roleid
     * @return
     */
    @RequestMapping("/add")
    @Secured({"ROLE_admin"})
    public ModelAndView addRole(String roleid){
        return new ModelAndView("html/sys/addrole");
    }


    /**
     * 添加一条角色数据
     * @param request
     * @param role
     * @return
     */
    @RequestMapping("/insert")
    @Secured({"ROLE_admin"})
    @ResponseBody
    public ResultModel insertRole(HttpServletRequest request, Role role){
        // 角色id不能为空
        if(role.getRoleid() == null || role.getRoleid().equals("")){
            return new ResultModel(0,"角色id不能为空。","");
        }
        // 角色名不能为空
        if(role.getRolename() == null || role.getRolename().equals("")){
            return new ResultModel(0,"角色名不能为空。","");
        }
        // 角色值不能为空
        if(role.getValue() == null || role.getValue().equals("")){
            return new ResultModel(0,"角色值不能为空。","");
        }

        // 查询用户是否存在
        int count = roleService.findRoleCountById(role.getRoleid());
        if(count > 0){
            log.info(role.getRoleid() + ":角色已经存在。");
            return new ResultModel(0, "该角色已经存在。","");
        }

        // 用户插入操作
        int result = roleService.insertRole(role);
        if(result >= 1){
            log.info("成功插入" + result + "条角色数据。");
            // 创建返回内容
            ResultModel resultModel = new ResultModel(1,"角色插入成功！","");
            return resultModel;
        }
        else{
            log.info("数据插入出错。");
            // 创建返回内容
            ResultModel resultModel = new ResultModel(-1,"角色数据插入出错。","");
            return resultModel;
        }
    }

    /**
     * 删除指定的角色
     * @param roleid
     * @return
     */
    @RequestMapping("delete")
    @Secured({"ROLE_admin"})
    @ResponseBody
    public ResultModel deleteRole(String roleid){
        // username不能为空
        if(roleid == null || roleid.equals("")){
            return new ResultModel(0,"角色ID不能为空。","");
        }
        // 菜单插入，SQL自动判断一级二级菜单
        int result = roleService.deleteRole(roleid);

        if(result >= 1){
            log.info("成功删除" + result + "条角色数据。");
            // 创建返回内容
            ResultModel resultModel = new ResultModel(1,"角色删除成功！","");
            return resultModel;
        }
        else{
            log.info("数据删除出错。");
            // 创建返回内容
            ResultModel resultModel = new ResultModel(-1,"数据删除出错。","");
            return resultModel;
        }
    }

    /**
     * 更新指定的用户状态
     * @param role
     * @return
     */
    @RequestMapping("update")
    @Secured({"ROLE_admin"})
    @ResponseBody
    public ResultModel updateRole(Role role){
        if(role.getRoleid() == null || role.getRoleid().equals("")){
            return new ResultModel(0,"角色id不能为空。","");
        }

        int result = roleService.updateRole(role);
        if(result > 0){
            log.info("角色修改成功：roleid=" + role.getRoleid());
            ResultModel resultModel = new ResultModel(1,"角色内容修改成功！","");
            return resultModel;
        }
        else{
            log.info("角色修改出错。");
            // 创建返回内容
            ResultModel resultModel = new ResultModel(-1,"角色内容更新出错。","");
            return resultModel;
        }
    }

}
