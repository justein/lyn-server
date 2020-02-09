package com.jhhg.nova.bootstrap;

import com.jhhg.nova.constant.Constant;
import com.jhhg.nova.entity.Request;
import com.jhhg.nova.entity.Response;
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

       /**创建请求对象*/
        Request request = new Request(client);
        /**处理请求对象中的参数*/
        String currentUserName = request.getParameterByName("username");

        if (request.getRequestUrl().contains("/index")) {
            /**创建响应对象*/
            Response response = new Response(client);
            response.println("<html>");
            response.println("<body>");
            response.println("<h1>您好，"+currentUserName+"，欢迎使用 Lyn Server ");
            response.println("</h1>");
            response.println("</body>");
            response.println("</html>");

            response.send2Client(200);
        }else if (request.getRequestUrl().contains("/login")) {
            /**创建响应对象*/
            Response response = new Response(client);
            response.println("<html>");
            response.println("<head>");
            response.println("<meta charset=\"utf-8\">");
            response.println("<title>Lyn Server 登录页面</title>");
            response.println("<body>");
            response.println("<input type=\"text\" placeholder=\"输入用户名\" />");
            response.println("<input type=\"password\" placeholder=\"输入密码\" />");
            response.println("<a href=\"index.html\" target=\"_self\"><button>登 录</button></a>");
            response.println("</body>");
            response.println("</html>");

            response.send2Client(200);
        } else {
            /**创建响应对象*/
            Response response = new Response(client);
            response.println("<html>");
            response.println("<head>");
            response.println("<meta charset=\"utf-8\">");
            response.println("<title>Lyn Server 404 Not Found</title>");
            response.println("<body>");
            response.println("<h2>页面跑到火星去了。。。</h2>");
            response.println("</body>");
            response.println("</html>");

            response.send2Client(404);
        }



    }

    public static void main(String[] args) {

        /**创建server实例*/
        Bootstrap bootstrap = new Bootstrap(Constant.SERVER_PORT);
        /**服务器启动*/
        bootstrap.start();
    }
}
