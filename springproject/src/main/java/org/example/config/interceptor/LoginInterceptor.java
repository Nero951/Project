package org.example.config.interceptor;


import com.fasterxml.jackson.databind.ObjectMapper;
import org.example.model.ResponseJSON;
import org.example.model.User;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.web.servlet.HandlerInterceptor;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.PrintWriter;

public class LoginInterceptor implements HandlerInterceptor {
    private ObjectMapper objectMapper;
    public LoginInterceptor(ObjectMapper objectMapper) {
        this.objectMapper = objectMapper;
    }

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        HttpSession session = request.getSession(false);
        if(session!=null){
            User user = (User)session.getAttribute("user");
            if(user!=null){
                return true;
            }
        }
        //登陆失败，不允许访问的业务：区分前后端
        String servletPath = request.getServletPath();
        if(servletPath.startsWith("/api")){
            response.setCharacterEncoding("UTF-8");
            response.setContentType(MediaType.APPLICATION_JSON_VALUE);
            ResponseJSON rj = new ResponseJSON();
            rj.setCode("ERR4042");
            rj.setMessage("用户未登录，禁止访问");
            String str = objectMapper.writeValueAsString(rj);
            response.setStatus(HttpStatus.UNAUTHORIZED.value());
            PrintWriter pw = response.getWriter();
            pw.println(str);
            pw.flush();
        }else{//前端逻辑：跳转到登录页面 /views/index.html
            //相对路径的写法，一定时请求路径作为相对位置的参照点
            //使用绝对路径类重定向，不建议使用相对路径和转发
            String schema = request.getScheme();//http
            String host = request.getServerName();//ip
            int port = request.getServerPort();//port
            String contextPath = request.getContextPath();//application Context path应用上下文路径
            String bassPath = schema+"://"+host+":"+port+contextPath;
            //重定向到登陆页面
            response.sendRedirect(bassPath+"/index.html");
        }
        return false;
    }
}
