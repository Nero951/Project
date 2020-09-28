package Nero.model;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@ToString
public class Response {
    private Boolean success;//业务是否处理成功
    private String code;//错误码
    private Integer total;
    private String message;//错误消息
    private Object data;//业务字段
    private String stackTrace;//出现异常的时候，堆栈信息
}
