package org.example.controller;

import org.example.exception.AppException;
import org.example.model.User;
import org.example.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

@RestController
@RequestMapping("/user")
public class UserController {
    @Autowired
    private UserService userService;

    @PostMapping("/register")
    public Object register(User user, MultipartFile headFile){
        //校验请求数据

        //保存上传的用户头像到服务端本地
        if (headFile!=null) {
            String head = userService.saveHead(headFile);
            //将上传的路径映射为http服务路径

            //用户头像的http路径设置到user，head，把user插入到数据库
            user.setHead(head);
        }
        userService.register(user);
        return null;
    }

    @PostMapping("/login")
    public Object login(@RequestBody User user, HttpServletRequest req){
        User exist = userService.queryByUsername(user.getUsername());

        if(exist==null) throw new AppException("LOG001", "用户不存在");

        if(!user.getPassword().equals(exist.getPassword()))
            throw new AppException("LOG002", "账号或密码错误");
        //校验通过，保存数据库的用户(包含所有字段)到session
        HttpSession session = req.getSession();
        session.setAttribute("user", exist);
        return null;
    }
    
    @GetMapping("/logout")
    public Object logout(HttpSession session){
        session.removeAttribute("user");
        return null;
    }
}
