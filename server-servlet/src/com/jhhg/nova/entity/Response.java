package com.jhhg.nova.entity;

import com.jhhg.nova.constant.Constant;

import java.io.BufferedWriter;
import java.io.IOException;
import java.io.OutputStreamWriter;
import java.net.Socket;
import java.util.Date;

/***
 * @ClassName: Response
 * @Description: 用于处理服务器返回给客户端的封装
 * @Author: Lyn
 * @Date: 2020/2/9 上午9:54
 * @version : V1.0
 */
public class Response {

    /**通信管道*/
    private Socket client;
    /**数据回写*/
    private BufferedWriter bufferedWriter;
    /**写回给浏览器的内容*/
    private StringBuilder contentToClient;
    /**写回内容的长度*/
    private int len;

    public Response(Socket client) {
        this.client = client;
        contentToClient = new StringBuilder();
        try {
            bufferedWriter = new BufferedWriter(new OutputStreamWriter(client.getOutputStream()));
        } catch (IOException e) {
            e.printStackTrace();
            System.out.println("响应创建失败，socket忙碌或已关闭");
        }
    }

    /**
     * 发送数据给浏览器端
     * @param statusCode
     */
    public void send2Client(int statusCode) {

        /**创建响应头信息*/
        String headerInfo = createHeadInfo(statusCode);
        /**将数据发送给浏览器端*/
        try {
            bufferedWriter.append(headerInfo);
            bufferedWriter.append(contentToClient.toString());
            bufferedWriter.flush();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /**
     * 根据响应值创建返回内容
     * @param code
     * @return
     */
    private String createHeadInfo(int code) {

        /**回送一个信息给浏览器，因为要交给浏览器显示，所以要按照http协议的报文格式组织数据*/
        StringBuilder headerInfo = new StringBuilder();
        headerInfo.append("HTTP/1.1").append(Constant.BLANK).append(code).append(Constant.CRLF);

        /**根据不同status添加不同响应内容*/
        switch (code) {
            case 200:
                headerInfo.append("OK").append(Constant.CRLF);
                break;
            case 404:
                headerInfo.append("NOT FOUND").append(Constant.CRLF);
                break;
            case 500:
                headerInfo.append("SERVER INTERNAL ERROR").append(Constant.CRLF);
                break;
        }

        /**添加header剩余内容*/
        headerInfo.append("Content-Type: text/html;charset=utf-8").append(Constant.CRLF)
                .append("Date:"+new Date()).append(Constant.CRLF)
                .append("Content-Length: "+len).append(Constant.CRLF)
                .append("Server: LynServer/1.1").append(Constant.CRLF).append(Constant.CRLF);

        return headerInfo.toString();
    }

    /**
     * 包装返回体内容
     * @param content
     * @return
     */
    public Response print(String content) {
        contentToClient.append(content);
        len += content.getBytes().length;
        return this;
    }

    /**
     * 包装返回体内容
     * @param content
     * @return
     */
    public Response println(String content) {
        contentToClient.append(content);
        len += (content+Constant.CRLF).getBytes().length;
        return this;
    }

}
