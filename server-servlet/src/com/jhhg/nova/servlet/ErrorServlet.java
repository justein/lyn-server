package com.jhhg.nova.servlet;

import com.jhhg.nova.entity.Request;
import com.jhhg.nova.entity.Response;

/**
 * @author : Lyn
 * @version V1.0
 * @Project: server-servlet
 * @Package com.jhhg.nova.servlet
 * @Description: TODO
 * @date Date : 2020-02-10 上午 10:31
 * @copyright http://www.jhhg.net.cn/
 */
public class ErrorServlet extends Servlet {

    @Override
    public void service(Request request, Response response) {
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
