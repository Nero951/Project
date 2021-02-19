package nero.config;

import com.fasterxml.jackson.databind.ObjectMapper;
import nero.config.interceptor.LoginInterceptor;
import nero.config.web.RequestResponseBodyMethodProcessorWrapper;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.method.support.HandlerMethodReturnValueHandler;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.PathMatchConfigurer;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;
import org.springframework.web.servlet.mvc.method.annotation.RequestMappingHandlerAdapter;
import org.springframework.web.servlet.mvc.method.annotation.RequestResponseBodyMethodProcessor;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.List;

@Configuration
public class SysConfig implements WebMvcConfigurer, InitializingBean{

    @Resource
    private RequestMappingHandlerAdapter adapter;

    @Autowired
    private ObjectMapper objectMapper;

    //之前以@ControllerAdvice+实现ResponseBodyAdvice接口，完成统一处理返回数据包装，无法解决返回值为null的状况
    //改用这种方式，可以解决返回null包装为自定义类型
    @Override
    public void afterPropertiesSet() {
        List<HandlerMethodReturnValueHandler> returnValueHandlers = adapter.getReturnValueHandlers();
        List<HandlerMethodReturnValueHandler> handlers = new ArrayList(returnValueHandlers);
        for(int i=0; i<handlers.size(); i++){
            HandlerMethodReturnValueHandler handler = handlers.get(i);
            if(handler instanceof RequestResponseBodyMethodProcessor){
                handlers.set(i, new RequestResponseBodyMethodProcessorWrapper(handler));
            }
        }
        adapter.setReturnValueHandlers(handlers);
    }

    @Override
    public void addInterceptors(InterceptorRegistry registry) {
        registry.addInterceptor(new LoginInterceptor(objectMapper))
                .addPathPatterns("/api/**")
                .excludePathPatterns("/api/user/login")
                .excludePathPatterns("/api/user/register")
                .excludePathPatterns("/api/user/logout ");

    }

    @Override
    public void configurePathMatch(PathMatchConfigurer configurer){
        //Controller路径，统一添加请求的路径前缀，第二个参数表示是否添加，返回true表示添加
        //所有Controller请求路径，都要带/api前缀
        configurer.addPathPrefix("api",c->true);
    }
}


