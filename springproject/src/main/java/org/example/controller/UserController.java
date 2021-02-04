package org.example.controller;

import org.example.exception.AppException;
import org.example.model.User;
import org.example.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

@RestController
@RequestMapping("/user")
public class UserController {

    @Autowired
    private UserService userService;

    @PostMapping("/login")
    public Object login(User user, HttpServletRequest req){
        User exist = userService.query(user.getUsername());//user是浏览器界面输入的账号密码，先更具账号从数据库查用户
        //user是数据库根据账号查询到的一条数据转为User得对象
        //模拟用户名密码校验
        if(exist!=null){
            if(exist.getPassword().equals(user.getPassword())){
                //用户名密码都正确，登录成功！
                HttpSession session = req.getSession();//获取session，获取不到就创建一个
                session.setAttribute("user", exist);
                return null;
            }else {
                throw new AppException("USERLog002", "用户名或密码错误");
            }
        }else{
            throw new AppException("USERLog001", "用户不存在");
        }
    }

    @GetMapping("/logout")
    public Object logout(HttpSession session){
        session.removeAttribute("user");
        return null;
    }
}
