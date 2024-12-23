package com.gzu.trainingcommon.controller;

import com.gzu.trainingcommon.model.Menu;
import com.gzu.trainingcommon.model.User;
import com.gzu.trainingcommon.service.SysService;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpSession;
import org.slf4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import java.text.DecimalFormat;
import java.util.HashMap;
import java.util.Map;

/**
 * @ClassName:     BaseController.java
 * @Description:   系统控制器基类
 *
 * @author         郭华
 * @version        V1.0
 * @Date           2020.2.11
 */

@Controller
public class BaseController {
    protected static final Logger log = org.slf4j.LoggerFactory.getLogger(BaseController.class);

    /**
     * 系统业务service
     */
    @Autowired
    public SysService sysService;

    /**
     * 默认访问操作
     * @param request
     * @return
     */
    @RequestMapping("/")
    public String view(HttpServletRequest request){
        return "forward:/user/index";
    }

    /**
     * 打开指定页面，同时查询菜单情况
     * @param menuid
     * @return
     */
    public String index(String menuid, Model model){
        // 查询当前menuid对应的内容
        Menu menu = sysService.findSingleMenu(menuid);

        // 构建面包屑菜单
        model.addAttribute("submenuname", menu.getSubmenuname());
        model.addAttribute("menuname", menu.getMenuname());

        return  menu.getPageurl();
    }

    /**
     * 打开指定页面，同时查询菜单情况
     * @param menuid
     * @return
     */
    public ModelAndView index(String menuid, HttpServletRequest request){
        HttpSession session = request.getSession();
        User user = (User)session.getAttribute("user");

        // 查询当前menuid对应的内容
        Menu menu = sysService.findSingleMenu(menuid);

        // 构建面包屑菜单
        Map map = new HashMap();
        // 对比menuid是否存在
        if(user.getMenuid().indexOf(menuid) < 0){
            // 不存在，不能访问
            return new ModelAndView("forward:/welcome/index", "data", map);
        }

        // log部分
        log.info("打开"+menu.getMenuname()+"页面");

        // 构建面包屑菜单
        map.put("submenuname", menu.getSubmenuname());
        map.put("menuname", menu.getMenuname());

        return new ModelAndView(menu.getPageurl(), "data", map);
    }

    /**
     * 打开指定页面，同时查询菜单情况
     * @param menuid
     * @param map 页面要查询的业务内容
     * @return
     */
    public ModelAndView index(String menuid, Map map){
        // 查询当前menuid对应的内容
        Menu menu = sysService.findSingleMenu(menuid);

        // log部分
        log.info("打开"+menu.getMenuname()+"页面");

        // 构建面包屑菜单
        map.put("submenuname", menu.getSubmenuname());
        map.put("menuname", menu.getMenuname());

        return new ModelAndView(menu.getPageurl(), "data", map);
    }

    /**
     * 打开指定页面，同时查询菜单情况
     * @param menuid
     * @param map 页面要查询的业务内容
     * @return
     */
    public ModelAndView index(String menuid, Map map, HttpServletRequest request){
        HttpSession session = request.getSession();
        User user = (User)session.getAttribute("user");

        // 查询当前menuid对应的内容
        Menu menu = sysService.findSingleMenu(menuid);

        // 对比menuid是否存在
        if(user.getMenuid().indexOf(menuid) < 0){
            // 不存在，不能访问
            return new ModelAndView("forward:/welcome/index", "data", map);
        }

        // log部分
        log.info("打开"+menu.getMenuname()+"页面");

        // 构建面包屑菜单
        map.put("submenuname", menu.getSubmenuname());
        map.put("menuname", menu.getMenuname());

        return new ModelAndView(menu.getPageurl(), "data", map);
    }

    //计算NRV的值
    public String getNRV(String nutrition,String demand){
        if(demand.contains("-")){
            String[] strings = demand.split("-");
            double ave = ( Double.parseDouble(strings[0]) + Double.parseDouble(strings[1]) ) / 2;
            demand = ave +"";
        }
        DecimalFormat df = new DecimalFormat("0");
        return df.format((Double.parseDouble(nutrition) / Double.parseDouble(demand)) * 100) + "%";
    }


}
