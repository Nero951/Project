package nero.config.interceptor;

import com.fasterxml.jackson.databind.ObjectMapper;
import nero.base.ResponseResult;
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

    /**
     *if(session != null && session.getAttribute("user") != null) return true; ==>继续执行
     * else 返回一个带错误码和所悟信息的json
     */
    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        HttpSession session = request.getSession(false);
        if(session != null && session.getAttribute("user") != null){
            return true;
        }
        response.setCharacterEncoding("UTF-8");//设置响应体编码，和Java文件的编码一致
        response.setContentType(MediaType.APPLICATION_JSON_UTF8_VALUE);//设置浏览器
        response.setStatus(HttpStatus.UNAUTHORIZED.value());
        //这部分可以不返回响应体内容，前端通过状态码已经处理过了
        ResponseResult r =ResponseResult.error("ERROR 001","用户未登录");
        PrintWriter pw = response.getWriter();
        pw.println(objectMapper.writeValueAsString(r));
        pw.flush();
        return false;
    }
}
