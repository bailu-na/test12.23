package com.gzu.trainingcommon.model;

import lombok.Data;

import java.util.Map;

/**
 * @ClassName:     ResultModel.java
 * @Description:   用于json返回结果Model
 *
 * @author         郭华
 * @version        V1.0
 * @Date           2020.2.24
 */
@Data
public class ResultModel {
    private int result;  // 返回的处理编号
    private String message;  // 返回要输出的信息
    private String url;    // 需要跳转的url地址
    private Map data;

    /**
     * 构造函数
     */
    public ResultModel(){

    }

    /**
     * 构造函数
     * @param result
     * @param message
     * @param url
     */
    public ResultModel(int result, String message, String url){
        this.result = result;
        this.message = message;
        this.url = url;
    }

    /**
     * 构造函数
     * @param result
     * @param message
     * @param url
     */
    public ResultModel(int result, String message, String url, Map data){
        this.result = result;
        this.message = message;
        this.url = url;
        this.data = data;
    }


    /**
     * 构造函数
     * @param result
     * @param message
     */
    public ResultModel(int result, String message, Map data){
        this.result = result;
        this.message = message;
        this.data = data;
    }


    /**
     * 构造函数
     * @param result
     * @param message
     */
    public ResultModel(int result, String message){
        this.result = result;
        this.message = message;
    }

    public int getResult() {
        return result;
    }

    public void setResult(int result) {
        this.result = result;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public Map getData() {
        return data;
    }

    public void setData(Map data) {
        this.data = data;
    }
}

