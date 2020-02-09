package com.jhhg.nova.entity;

import com.jhhg.nova.constant.Constant;
import com.jhhg.nova.util.HttpParser;

import java.io.IOException;
import java.net.Socket;
import java.util.ArrayList;
import java.util.HashMap;
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

    private Socket client;
    /**请求方法*/
    private String methodName;
    /**请求路径*/
    private String requestUrl;
    /**请求协议版本*/
    private String protocolVersion;
    /**请求参数的封装*/
    private Map<String,List<String>> parameterMap;

    /**构造函数*/
    public Request(Socket client) {

        this.client = client;
        /**初始化用于保存参数的容器*/
        this.parameterMap = new HashMap<>();
        /**用于接收流数据*/
        byte[] bytes = new byte[1024 * 1024 * 50];
        try {
            int dataLength = client.getInputStream().read(bytes);
            /**将流数据转化为字符串*/
            String content = new String(bytes,0,dataLength);
            parseHttpContent(content);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }


    /**
     *
     * @param httpContent
     * @return
     */
    public String parseHttpContent(String httpContent) {

        /**简单处理了，不做格式校验了*/
        if ("".equals(httpContent)) {
            return null;
        }
        /**获取报文第一行*/
        String firstRow = httpContent.split(Constant.CRLF)[0];
        /**解析请求方法*/
        methodName = firstRow.split(Constant.BLANK)[0].trim().toLowerCase();
        System.out.println("请求方法："+methodName);
        /**解析请求url*/
        requestUrl = firstRow.split(Constant.BLANK)[1].trim();
        System.out.println("请求url："+requestUrl);
        /**解析请求协议版本*/
        protocolVersion = firstRow.split(Constant.BLANK)[2].trim();
        System.out.println("请求协议版本："+protocolVersion);
        parseRequestParam(httpContent);
        return null;
    }

    /**
     * 解析请求参数，暂只考虑get和post方式，参数封装为map，k-v格式，考虑一对多
     * @param httpContent
     */
    public void parseRequestParam(String httpContent) {

        /**url后带问号的形式，/addUser.do?username=xx&pwd=xxx */
        String firstRow = httpContent.split(Constant.CRLF)[0];
        String methodName = firstRow.split(Constant.BLANK)[0].trim().toLowerCase();
        String urlStr = firstRow.split(Constant.BLANK)[1];

        /**根据请求体中是否包含参数来分类*/
        String[] httpBodyArray = httpContent.split("\n\r\n");

        if ("".equals(firstRow) || (!"post".equals(methodName) && !urlStr.contains("?")) ) return;

        if (urlStr.contains("?")) {
            /**格式为 key1=value1&key2=value2... */
            String paramStr = urlStr.split("\\?")[1];
            //只有一个参数
            if (!urlStr.contains("&")) {
                String paramKey = paramStr.split("=")[0];
                String paramValue = paramStr.split("=")[1];
                putKV2Map(paramKey, paramValue);
            }else {  //多个参数
                String[] paramArray = paramStr.split("&");
                for (String param:paramArray) {
                    putKV2Map(param.split("=")[0], param.split("=")[1]);
                }
            }
        }

        /**如果请求体有参数，继续解析*/
        if (httpBodyArray.length > 1) {
            String bodyParamStr = httpBodyArray[1];
            if (bodyParamStr.contains("&")) {
                String[] bodyParamArray = bodyParamStr.split("&");
                for (String bodyParam:bodyParamArray) {
                    putKV2Map(bodyParam.split("=")[0], bodyParam.split("=")[1]);
                }
            }else {
                putKV2Map(bodyParamStr.split("=")[0], bodyParamStr.split("=")[1]);
            }

        }

    }

    private void putKV2Map(String key, String value) {

        /**如果存在key，则进行参数追加*/
        if (parameterMap.containsKey(key)) {
            parameterMap.get(key).add(value);
            return;
        }
        /**不存在则创建*/
        List<String> valueList = new ArrayList<>();
        valueList.add(value);
        parameterMap.put(key,valueList);
    }

    public String getRequestUrl() {
        return requestUrl;
    }

    public void setRequestUrl(String requestUrl) {
        this.requestUrl = requestUrl;
    }

    /**根据名称拿到请求参数的值*/
    public String getParameterByName(String key) {
       List valueList = parameterMap.get(key);
       if (valueList!=null && !valueList.isEmpty()) {
           return (String) valueList.get(0);
       }
       return "";
    }
}
