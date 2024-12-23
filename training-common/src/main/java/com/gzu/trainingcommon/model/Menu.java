package com.gzu.trainingcommon.model;

import lombok.Data;

/**
 * @ClassName:     Menu.java
 * @Description:   用户信息Model
 *
 * @author         郭华
 * @version        V1.0
 * @Date           2020.2.19
 */
@Data
public class Menu extends PageBean{
    private String menuid;
    private String menuname;
    private String submenuid = null;
    private String submenuname;
    private String addtime;
    private String username;
    private String url = null;
    private String pageurl = null;
    private String icon = null;
}
