package com.jhhg.nova.util;

import com.jhhg.nova.constant.Constant;

/***
 * @ClassName: HttpParser
 * @Description: TODO
 * @Author: Lyn
 * @Date: 2020/2/8 上午9:44
 * @version : V1.0
 */
public class HttpParser {

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
        String methodName = firstRow.split(Constant.BLANK)[0].trim().toLowerCase();
        System.out.println("请求方法："+methodName);
        /**解析请求url*/
        String requestUrl = firstRow.split(Constant.BLANK)[1].trim();
        System.out.println("请求url："+requestUrl);
        /**解析请求协议版本*/
        String protocolVersion = firstRow.split(Constant.BLANK)[2].trim();
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
                System.out.println("参数："+paramKey+" , 值："+paramValue);
            }else {  //多个参数
                String[] paramArray = paramStr.split("&");
                for (String param:paramArray) {
                    System.out.println("参数："+param.split("=")[0]+" , 值："+param.split("=")[1]);
                }
            }
        }

        /**如果请求体有参数，继续解析*/
        if (httpBodyArray.length > 1) {
            String bodyParamStr = httpBodyArray[1];
            if (bodyParamStr.contains("&")) {
                String[] bodyParamArray = bodyParamStr.split("&");
                for (String bodyParam:bodyParamArray) {
                    System.out.println("Body参数: "+bodyParam.split("=")[0]+", Body值："+bodyParam.split("=")[1]);
                }
            }else {
                System.out.println("Body参数: "+bodyParamStr.split("=")[0]+", Body值："+bodyParamStr.split("=")[1]);
            }

        }

    }
}
