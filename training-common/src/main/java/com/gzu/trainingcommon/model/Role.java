package com.gzu.trainingcommon.model;

/**
 * @ClassName:     Role.java
 * @Description:   角色信息Model
 *
 * @author         郭华
 * @version        V1.0
 * @Date           2020.2.25
 */

public class Role extends PageBean {
    private String oroleid;
    private String roleid;
    private String rolename;
    private String value;
    private String rolenames;

    public String getOroleid() {
        return oroleid;
    }

    public void setOroleid(String oroleid) {
        this.oroleid = oroleid;
    }

    public String getRoleid() {
        return roleid;
    }

    public void setRoleid(String roleid) {
        this.roleid = roleid;
    }

    public String getRolename() {
        return rolename;
    }

    public void setRolename(String rolename) {
        this.rolename = rolename;
    }

    public String getValue() {
        return value;
    }

    public void setValue(String value) {
        this.value = value;
    }

    public String getRolenames() {
        return rolenames;
    }

    public void setRolenames(String rolenames) {
        this.rolenames = rolenames;
    }
}
