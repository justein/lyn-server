package com.jhhg.nova.bootstrap;

import com.jhhg.nova.constant.Constant;
import com.jhhg.nova.entity.Request;
import com.jhhg.nova.entity.Response;
import com.jhhg.nova.servlet.ErrorServlet;
import com.jhhg.nova.servlet.IndexServlet;
import com.jhhg.nova.servlet.LoginServlet;
import com.jhhg.nova.servlet.Servlet;
import com.jhhg.nova.util.HttpParser;
import com.jhhg.nova.util.PrepareWebContext;
import com.jhhg.nova.util.WebContext;
import com.jhhg.nova.util.WebXmlHandler;

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
    /**web上下文环境*/
    private WebContext webContext;

    public Bootstrap(int port) {
        this.port = port;
    }

    /**
     * 服务器启动
     */
    public void start() throws Exception {
        try {

            /**创建 ServerSocket*/
            serverSocket = new ServerSocket(port);
            /**拿到socket实例*/
            client = serverSocket.accept();
            /**初始化webcontext*/
            webContext = PrepareWebContext.parseWebContext();
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
    public void doService(Socket client) throws Exception {

       /**创建请求对象*/
        Request request = new Request(client);
        Response response = new Response(client);
        /**通过反射拿到Servlet的实例*/
        String servletClassName = webContext.getEntityMap(request.getRequestUrl());
        Class clz = Class.forName(servletClassName);
        Servlet servlet = (Servlet) clz.getConstructor().newInstance();
        /**调用servlet的service方法*/
        servlet.service(request, response);
    }

    public static void main(String[] args) throws Exception {

        /**创建server实例*/
        Bootstrap bootstrap = new Bootstrap(Constant.SERVER_PORT);
        /**服务器启动*/
        bootstrap.start();
    }
}
