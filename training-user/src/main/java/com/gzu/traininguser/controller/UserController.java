package com.gzu.traininguser.controller;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.gzu.trainingcommon.controller.BaseController;
import com.gzu.trainingcommon.model.Menu;
import com.gzu.trainingcommon.model.ResultModel;
import com.gzu.trainingcommon.model.Role;
import com.gzu.traininguser.model.User;
import com.gzu.traininguser.service.UserService;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.security.access.annotation.Secured;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Controller
@RequestMapping("/api/user")
public class UserController extends BaseController {

    @Autowired
    UserService userService;

    @GetMapping("/index")
    public String index(HttpServletRequest request, Model model) {
        // 获取当前的 Authentication 对象
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        User user = userService.findByUsername(authentication.getName());
        user.setPassword(null);

        request.getSession().setAttribute("user", user);
        log.info(user.getUsername() + "：用户保存如session。");
        // 查询菜单
        List<Menu> menulist = userService.getMenuList(user);

        // 生成菜单html内容
        String html = makeMenuHtml(menulist);

        // 存放页面内容
        model.addAttribute("user", user); // 用户消息
        model.addAttribute("menuhtml", html); // 用户消息

        return "index"; //
    }

    private String makeMenuHtml(List<Menu> menulist){
        StringBuilder html = new StringBuilder();
        String lastMenu = ""; // 初始化 lastMenu 变量

        for (int i = 0; i < menulist.size(); i++) {
            Menu menu = menulist.get(i);

            // 开始一级菜单
            if (!lastMenu.equals(menu.getSubmenuid())) {
                html.append("<li>");
                html.append("<a href='javascript:;'><i class='iconfont'>").append(menu.getIcon() != null ? menu.getIcon() : "").append("</i><cite>").append(menu.getSubmenuname()).append("</cite><i class='iconfont nav_right'>&#xe602;</i></a>");

                lastMenu = menu.getSubmenuid(); // 更新 lastMenu

                html.append("<ul class='sub-menu'>");

                for (int j = 0; j < menulist.size(); j++) {
                    Menu submenu = menulist.get(j);
                    if (lastMenu.equals(submenu.getSubmenuid())) {
                        System.out.println(lastMenu + "  " + submenu.getSubmenuid() + "  " + submenu.getMenuid());
                        html.append("<li menuid='").append(submenu.getMenuid()).append("'><a _href='").append(submenu.getUrl()).append("?menuid=").append(submenu.getMenuid()).append("'><i class='iconfont'>&#xe602;</i><cite>").append(submenu.getMenuname()).append("</cite></a></li>");
                    }
                }

                html.append("</ul>");
                // 结束一级菜单
                html.append("</li>");
            }
        }
        return html.toString();
    }

    @GetMapping("/menulist")
    @ResponseBody
    public ResultModel getMenuList(HttpServletRequest request){
        User user = (User)request.getSession().getAttribute("user");
        // 查询菜单
        List<Menu> menulist = userService.getMenuList(user);
        log.info("查询出菜单：" + menulist.size() + "条");



        Map data = new HashMap<>();
        data.put("menulist", menulist);

        return new ResultModel(1, "查询菜单成功！",data);
    }

    @GetMapping("/info")
    public String openUserInfoPage(String menuid, Model model) {
        // 获取当前的 Authentication 对象
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        User user = userService.findByUsername(authentication.getName());
        model.addAttribute("user", user);

        return super.index(menuid, model);
    }

    /**
     * 打开用户管理页面
     * @param menuid
     * @return
     */
    @RequestMapping("/users")
    public ModelAndView userPage(String menuid){

        //获取角色下拉框
        List<Role> list = userService.getRoleList();

        Map map = new HashMap();
        map.put("list", list);

        return super.index(menuid, map);
    }


    /**
     * 获取所有用户列表
     * @return
     */
    @RequestMapping("/list")
    @Secured({"ROLE_admin"})
    @ResponseBody
    public Map userList(User user){
        // 设置分页
        PageHelper.startPage(user.getPage(),user.getLimit());
        // 查询所有一级菜单列表
        List<User> list = userService.getUserList(user);

        log.info("查询出"+ list.size() +"条一级菜单。");

        PageInfo<User> info = new PageInfo<User>(list);

        // 构建layui固定返回格式
        Map map = new HashMap();
        map.put("code", 0);
        map.put("msg","正常返回数据。");
        map.put("count",info.getTotal());
        map.put("data", info.getList());

        return map;
    }

    /**
     * 打开添加用户页面
     * @param username
     * @return
     */
    @RequestMapping("/add")
    public ModelAndView addUser(String username){
        User user = new User();
        // 判断用户名是否为空
        if(username != null && username.equals("") ==false ){
            user = userService.getSingleUser(username);
            user.setPassword("******");
        }

        //获取角色下拉框
        List<Role> rlist = userService.getRoleList();
        List<User> ulist = userService.getAllInsNoList();

        Map map = new HashMap();
        map.put("user", user);
        map.put("rlist", rlist);
        map.put("ulist", ulist);

        return new ModelAndView("html/sys/adduser", "data", map);
    }

    /**
     * 添加一条用户数据
     * @param request
     * @param user
     * @return
     */
    @RequestMapping("/insert")
    @Secured({"ROLE_admin"})
    @ResponseBody
    public ResultModel insertUser(HttpServletRequest request, User user){
        // 用户名不能为空
        if(user.getUsername() == null || user.getUsername().equals("")){
            return new ResultModel(0,"用户名不能为空。","");
        }
        // 登录密码不能为空
        if(user.getPassword() == null || user.getPassword().equals("")){
            return new ResultModel(0,"登录密码不能为空。","");
        }
        // 判断日期格式
        if(user.getUsedate() == null || user.getUsedate().indexOf("~")<=0){
            return new ResultModel(0,"日期格式不正确。","");
        }
        // 查询用户是否存在
        int count = userService.findUserCountByUsername(user.getUsername());
        if(count > 0){
            log.info(user.getUsername() + ":用户已经存在。");
            return new ResultModel(0, "该用户名已经存在。","");
        }
        // 系统管理员唯一性
        if (user.getRole().equals("admin")){
            int adminCnt = userService.getAdminUserCnt();
            if(adminCnt > 0){
                log.info("已存在系统管理员，系统管理员只能有1个。");
                return new ResultModel(-1,"已存在系统管理员，系统管理员只能有1个。。","");
            }
        }

        // 用户插入操作
        int result = userService.insertUser(user);
        if(result >= 1){
            log.info("成功插入" + result + "条用户数据。");
            // 创建返回内容
            return new ResultModel(1,"用户插入成功！","");
        }
        else{
            log.info("数据插入出错。");
            // 创建返回内容
            return new ResultModel(-1,"数据插入出错。","");
        }
    }

    /**
     * 更新一条用户数据
     * @param request
     * @param user
     * @return
     */
    @RequestMapping("/update")
    @Secured({"ROLE_admin"})
    @ResponseBody
    public ResultModel updateUser(HttpServletRequest request, User user){
        // 用户名不能为空
        if(user.getUsername() == null || user.getUsername().equals("")){
            return new ResultModel(0,"用户名不能为空。","");
        }
        // 角色id不能为空
        if(user.getRole() == null || user.getRole().equals("")){
            return new ResultModel(0,"角色id不能为空。","");
        }
        // 判断日期格式
        if(user.getUsedate() == null || user.getUsedate().indexOf("~")<=0){
            return new ResultModel(0,"日期格式不正确。","");
        }

        // 清空不更新的内容
        user.setPassword(null);

        // 系统管理员唯一性
        if (user.getRole().equals("admin")){
            int adminCnt = userService.getAdminUserCnt();
            if(adminCnt > 0){
                log.info("已存在系统管理员，系统管理员只能有1个。");
                return new ResultModel(-1,"已存在系统管理员，系统管理员只能有1个。","");
            }
        }
        // 判断是否从系统管理员变更到其他角色
        List<User> list = userService.getUserList(user);
        if (list.size()>0){
            if(list.get(0).getRole().equals("admin")) {
                log.info("不能变更，系统管理员必须有1个。");
                return new ResultModel(-1, "不能变更，系统管理员必须有1个。", "");
            }
        }

        int result = userService.updateUser(user);
        if(result >= 1){
            log.info("成功更新" + result + "条用户数据。");
            // 创建返回内容
            ResultModel resultModel = new ResultModel(1,"用户更新成功。","");
            return resultModel;
        }
        else{
            log.info("数据插入出错。");
            // 创建返回内容
            ResultModel resultModel = new ResultModel(-1,"数据更新出错。","");
            return resultModel;
        }
    }

    /**
     * 删除指定的用户
     * @param username
     * @return
     */
    @RequestMapping("delete")
    @Secured({"ROLE_admin"})
    @ResponseBody
    public ResultModel deleteUser(String username){
        // username不能为空
        if(username == null || username.equals("")){
            return new ResultModel(0,"用户名不能为空。","");
        }
        // 系统管理员唯一性
        User user = new User();
        user.setUsername(username);
        List<User> list = userService.getUserList(user);
        if (list.size()>0){
            if(list.get(0).getRole().equals("admin")) {
                log.info("不能删除，系统管理员必须有1个。");
                return new ResultModel(-1, "不能删除，系统管理员必须有1个。", "");
            }
        }

        // 菜单插入，SQL自动判断一级二级菜单
        int result = userService.deleteUser(username);

        if(result >= 1){
            log.info("成功删除" + result + "条用户数据。");
            // 创建返回内容
            ResultModel resultModel = new ResultModel(1,"用户删除成功！","");
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
     * @param user
     * @return
     */
    @RequestMapping("changeenable")
    @Secured({"ROLE_admin"})
    @ResponseBody
    public ResultModel changeUserEnable(User user){
        if(user.getUsername() == null || user.getUsername().equals("")){
            return new ResultModel(0,"用户名不能为空。","");
        }
        if(user.getStatus() == null || user.getStatus().equals("")){
            return new ResultModel(0,"更新数据不能为空。。","");
        }
        int result = userService.updateUser(user);
        if(result > 0){
            log.info("用户状态修改成功：" + user.getUsername());
            ResultModel resultModel = new ResultModel(1,"用户状态修改成功！","");
            return resultModel;
        }
        else{
            log.info("用户状态更新出错。");
            // 创建返回内容
            ResultModel resultModel = new ResultModel(-1,"用户状态更新出错。","");
            return resultModel;
        }
    }
}
