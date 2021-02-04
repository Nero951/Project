package org.example.config;

import lombok.extern.slf4j.Slf4j;
import org.example.exception.AppException;
import org.example.model.ResponseJSON;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;

/**
 * @ControllerAdvice:方法上可以使用@ExceptionHandler处理异常
 * @RestControllerAdvice：由@controllerAdvice和@ResponseBody组成
 */
@ControllerAdvice
@Slf4j//使用LomBok日志注解，之后使用log属性来完成日志打印
public class ExceptionAdvice {
    //如果客户端请求，执行控制器方法抛Exception异常，会执行本方法
    //方法返回值作为响应体
    @ExceptionHandler(AppException.class)
    @ResponseBody
    public Object handle(AppException e){//方法参数即为捕获到的异常
        //构造响应数据
        //自定义异常保存错误码和错误消息
        ResponseJSON rj = new ResponseJSON();
        rj.setCode(e.getCode());
        rj.setMessage(e.getMessage());
        log.debug("未知错误", e);
        return rj;
    }
    @ExceptionHandler(Exception.class)
    //方法返回值作为响应体
    @ResponseBody
    public Object handle1(Exception e){//方法参数即为捕获到的异常
        //非自定义异常：(英文报错，堆栈信息，不能展示给用户 )
        //指定一个错误码，错误消息(未知错误，请联系管理员)
        ResponseJSON rj = new ResponseJSON();
        rj.setCode("ERR002");
        rj.setMessage("未知错误，请联系管理员");

        return rj;
    }
    //输出流打印异常信息方法：
//    public String transfer(Exception e){
//        StringWriter sw = new StringWriter();
//        PrintWriter pw = new PrintWriter(sw, true);
//        e.printStackTrace(pw);//打印异常堆栈信息到PrintWriter里
//        return sw.toString();
//    }

}
