package com.jhhg.nova.servlet;

import com.jhhg.nova.entity.Request;
import com.jhhg.nova.entity.Response;

/**
 * @author : Lyn
 * @version V1.0
 * @Project: server-servlet
 * @Package com.jhhg.nova.servlet
 * @Description: 定义Servlet抽象类
 * @date Date : 2020-02-10 上午 09:40
 * @copyright http://www.jhhg.net.cn/
 */
public abstract class Servlet {

    /**Service方法*/
    public void service(Request request, Response response) {}
}
