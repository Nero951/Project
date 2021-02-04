$(function (){
    $("#login_form").submit(function (){
       //真实业务场景需要校验表单数据---在此省略
        //发送Ajax请求
        $.ajax({
            url: "../api/user/login",
            type: "post",
            data: $("#login_form").serialize(),//表单数据序列化为k1=v1&k2=v2
            //前后端接口的数据，成功{success：true}   失败{success：false, code: xxx, message:xxx}

            success: function (r){
                //成功，跳转到首页
                if (r.success){
                    //js相对路径是以引入的html文件作为相对路径的参照点
                    // login.html跳转到index.html的相对路径写法
                    window.location.href ="../index.html";
                }
                //失败，显示错误信息
                else {
                    $("#error_message").show();
                    $("#error_message").html(r.message);
                }
            },
            error: function (req, statusText, err){
                alert(req.status)
            }
        })
        return false;//禁止表单的默认提交
    });
})