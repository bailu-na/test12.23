package com.gzu.trainingcommon.model;

/**
 * @ClassName:     Authority.java
 * @Description:   系统权限Model
 *
 * @author         郭华
 * @version        V1.0
 * @Date           2020.2.24
 */

public class Authority {
    public String authorityid;
    public String roleval;
    public String menuid;
    public String menuname;
    public String submenuname;
    public String submenuid;
    public String addtime;
    public String ischecked;

    public String getAuthorityid() {
        return authorityid;
    }

    public void setAuthorityid(String authorityid) {
        this.authorityid = authorityid;
    }

    public String getRoleval() {
        return roleval;
    }

    public void setRoleval(String roleval) {
        this.roleval = roleval;
    }

    public String getMenuid() {
        return menuid;
    }

    public void setMenuid(String menuid) {
        this.menuid = menuid;
    }

    public String getMenuname() {
        return menuname;
    }

    public void setMenuname(String menuname) {
        this.menuname = menuname;
    }

    public String getSubmenuname() {
        return submenuname;
    }

    public void setSubmenuname(String submenuname) {
        this.submenuname = submenuname;
    }

    public String getSubmenuid() {
        return submenuid;
    }

    public void setSubmenuid(String submenuid) {
        this.submenuid = submenuid;
    }

    public String getAddtime() {
        return addtime;
    }

    public void setAddtime(String addtime) {
        this.addtime = addtime;
    }

    public String getIschecked() {
        return ischecked;
    }

    public void setIschecked(String ischecked) {
        this.ischecked = ischecked;
    }
}
