package com.jhhg.nova.entity;

import java.util.List;
import java.util.Map;

/***
 * @ClassName: Request
 * @Description: Http协议的封装，简装版
 * @Author: Lyn
 * @Date: 2020/2/8 上午9:47
 * @version : V1.0
 */
public class Request {

    /**请求方法*/
    private String methodName;
    /**请求路径*/
    private String url;
    /**请求协议版本*/
    private String version;
    /**请求参数的封装*/
    private Map<String,List<String>> parameterMap;

    public String getMethodName() {
        return methodName;
    }

    public void setMethodName(String methodName) {
        this.methodName = methodName;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public String getVersion() {
        return version;
    }

    public void setVersion(String version) {
        this.version = version;
    }

    public Map<String, List<String>> getParameterMap() {
        return parameterMap;
    }

    public void setParameterMap(Map<String, List<String>> parameterMap) {
        this.parameterMap = parameterMap;
    }
}
