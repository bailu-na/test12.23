package com.gzu.trainingcommon.model;

import java.text.DecimalFormat;

public class PageBean {
    /**
     * 数据总条数
     */
    private int pageSumCount;

    /**
     * 当前的页数
     */
    private int page=1;
    /**
     * 每一页多少条数据
     */
    private int limit=10;
    /**
     * 总条数
     */
    private int cnt;

    /**
     * 用户名
     */
    private String username;

    private String data;

    // 排序用
    private String field;
    private String type;

    public String getField() {
        return field;
    }

    public void setField(String field) {
        this.field = field;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public int getCnt() {
        return cnt;
    }

    public void setCnt(int cnt) {
        this.cnt = cnt;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public PageBean() {
    }

    public int getPageSumCount() {
        return pageSumCount;
    }

    public void setPageSumCount(int pageSumCount) {
        this.pageSumCount = pageSumCount;
    }

    public int getPage() {
        return page;
    }

    public void setPage(int page) {
        this.page = page;
    }

    public int getLimit() {
        return limit;
    }

    public void setLimit(int limit) {
        this.limit = limit;
    }

    public String getData() {
        return data;
    }

    public void setData(String data) {
        this.data = data;
    }

    /**
     * 获取某个范围内的平均值
     * @return
     */
    public String getAvg(String value){
        if(value.indexOf("-")<=0){
            return "0";
        }
        String[] varr = value.split("-");
        if(varr.length!=2){
            return "0";
        }
        
        DecimalFormat df = new DecimalFormat(".00");
        float result = (Float.parseFloat(varr[0])+Float.parseFloat(varr[1]))/2;

        return String.valueOf(df.format(result));
    }
}
