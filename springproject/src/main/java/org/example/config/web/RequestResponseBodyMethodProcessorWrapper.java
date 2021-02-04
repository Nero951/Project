package org.example.config.web;

import org.example.model.ResponseJSON;
import org.springframework.core.MethodParameter;
import org.springframework.web.context.request.NativeWebRequest;
import org.springframework.web.method.support.HandlerMethodReturnValueHandler;
import org.springframework.web.method.support.ModelAndViewContainer;

public class RequestResponseBodyMethodProcessorWrapper implements HandlerMethodReturnValueHandler {

    private final HandlerMethodReturnValueHandler delegate;

    public RequestResponseBodyMethodProcessorWrapper(HandlerMethodReturnValueHandler delegate) {
        this.delegate = delegate;
    }

    @Override
    public boolean supportsReturnType(MethodParameter returnType) {
        return delegate.supportsReturnType(returnType);
    }

    @Override
    public void handleReturnValue(Object returnValue, MethodParameter returnType, ModelAndViewContainer mavContainer, NativeWebRequest webRequest) throws Exception {
        //returnValue是Controller请求方法执行完，返回值
        if(!(returnValue instanceof ResponseJSON)){//返回值本身就是需要的类型，不进行处理
            ResponseJSON json = new ResponseJSON();
            json.setSuccess(true);
            json.setData(returnValue);
            returnValue = json;
        }
        delegate.handleReturnValue(returnValue, returnType, mavContainer, webRequest);
    }
}