package com.jhhg.nova.bootstrap;

import com.jhhg.nova.constant.Constant;
import com.jhhg.nova.util.HttpParser;

import java.io.BufferedWriter;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStreamWriter;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.Date;

public class Bootstrap {

    private ServerSocket serverSocket;

    private Socket client;
    /**服务器端口号*/
    private int port;

    public Bootstrap(int port) {
        this.port = port;
    }

    /**
     * 服务器启动
     */
    public void start() {
        try {

            /**创建 ServerSocket*/
            serverSocket = new ServerSocket(port);
            /**拿到socket实例*/
            client = serverSocket.accept();
            /**对外提供服务*/
            doService(client);

        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /**
     * 服务器关闭,释放掉资源
     */
    public void stop() {
        try {
            serverSocket.close();
            client.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /**
     * 对外提供服务
     */
    public void doService(Socket client) throws IOException {

        /**获取socket中传输的流*/
        InputStream inputStream = client.getInputStream();
        /**用于接收流数据*/
        byte[] bytes = new byte[1024 * 1024];
        int dataLength = inputStream.read(bytes);
        /**将流数据转化为字符串*/
        String content = new String(bytes,0,dataLength);
        System.out.println(content);
        /**在这里添加http协议的解析*/
        new HttpParser().parseHttpContent(content);

        /**回送一个信息给浏览器，因为要交给浏览器显示，所以要按照http协议的报文格式组织数据*/
        StringBuilder headerInfo = new StringBuilder();
        headerInfo.append("HTTP/1.1 200 OK").append(Constant.CRLF)
                .append("Content-Type: text/html;charset=utf-8").append(Constant.CRLF)
                .append("Date:"+new Date()).append(Constant.CRLF)
                .append("Server: BWS/1.1").append(Constant.CRLF).append(Constant.CRLF);

        BufferedWriter bufferedWriter = new BufferedWriter(new OutputStreamWriter(client.getOutputStream()));
        StringBuilder bodyInfo = new StringBuilder();
        bodyInfo.append("<html>").append(Constant.CRLF).append("<body>").append(Constant.CRLF)
                .append("<h1>欢迎使用 Lyn Server ").append("</h1>").append(Constant.CRLF).append("</body>")
                .append("</html>");

        bufferedWriter.append(headerInfo).append(bodyInfo);
        /**将数据从缓存区刷到浏览器*/
        bufferedWriter.flush();
    }

    public static void main(String[] args) {

        /**创建server实例*/
        Bootstrap bootstrap = new Bootstrap(Constant.SERVER_PORT);
        /**服务器启动*/
        bootstrap.start();
    }
}
