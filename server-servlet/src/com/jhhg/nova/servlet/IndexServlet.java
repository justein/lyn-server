package com.jhhg.nova.servlet;

import com.jhhg.nova.entity.Request;
import com.jhhg.nova.entity.Response;

/**
 * @author : Lyn
 * @version V1.0
 * @Project: server-servlet
 * @Package com.jhhg.nova.servlet
 * @Description: TODO
 * @date Date : 2020-02-10 上午 10:02
 * @copyright http://www.jhhg.net.cn/
 */
public class IndexServlet extends Servlet {



    @Override
    public void service(Request request, Response response) {
        /**处理请求对象中的参数*/
        String currentUserName = request.getParameterByName("username");
        response.println("<html>");
        response.println("<body>");
        response.println("<h1>您好，"+currentUserName+"，欢迎使用 Lyn Server ");
        response.println("</h1>");
        response.println("</body>");
        response.println("</html>");

        response.send2Client(200);
    }
}
