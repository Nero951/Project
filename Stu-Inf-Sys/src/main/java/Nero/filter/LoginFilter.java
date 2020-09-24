package Nero.filter;

import Nero.model.Response;
import Nero.util.JSONUtil;

import javax.servlet.*;
import javax.servlet.annotation.WebFilter;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.io.PrintWriter;

/**
 * 配置过滤器，只要请求路径匹配到过滤器路径，都会先执行过滤器的doFilter方法
 * 是否往后边的顺序执行，依赖于是否再次调用filterChain.doFilter方法
 */
@WebFilter("/*")
public class LoginFilter implements Filter {
    @Override
    public void init(FilterConfig filterConfig) throws ServletException {

    }

    @Override
    public void doFilter(ServletRequest servletRequest, ServletResponse servletResponse, FilterChain filterChain) throws IOException, ServletException {
        HttpServletRequest req = (HttpServletRequest) servletRequest;
        HttpServletResponse res = (HttpServletResponse) servletResponse;

        String uri = req.getServletPath();
        HttpSession session = req.getSession(false);//没有session的时候返回null
        if(session == null){
            //首页重定向到登录页面，如果后端接口，返回错误的json数据
            req.setCharacterEncoding("utf-8");
            res.setCharacterEncoding("utf-8");
            //首页重定向岛登陆页面
            if("/public/page/main.html".equals(uri)){
                res.setContentType("text/html; charset=utf-8");
                String schema = req.getScheme();//http
                String host = req.getServerName();//服务器ip
                int port = req.getServerPort();//端口号
                String ctx = req.getContextPath();//项目部署路径
                String basePath = schema+"://"+host+":"+port+ctx;
                res.sendRedirect(basePath+"/public/index.html");
                return;
                //请求后端非登录接口:未登录请求返回401状态码
            }else if((!uri.startsWith("/public/")
                    && !uri.startsWith("/static/") && !"/user/login".equals(uri))){
                res.setContentType("application/json");
                PrintWriter pw = res.getWriter();
                Response r = new Response();
                r.setCode("ERR401");
                r.setMessage("禁止访问！");
                res.setStatus(401);
                pw.println(JSONUtil.write(r));
                pw.flush();
                return;
            }
            return;
        }
        filterChain.doFilter(servletRequest,servletResponse);
    }

    @Override
    public void destroy() {

    }
}
