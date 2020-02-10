package com.jhhg.nova.servlet;

import com.jhhg.nova.entity.Request;
import com.jhhg.nova.entity.Response;

/**
 * @author : Lyn
 * @version V1.0
 * @Project: server-servlet
 * @Package com.jhhg.nova.servlet
 * @Description: TODO
 * @date Date : 2020-02-10 上午 09:54
 * @copyright http://www.jhhg.net.cn/
 */
public class LoginServlet extends Servlet {

    @Override
    public void service(Request request, Response response) {
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
    }
}
