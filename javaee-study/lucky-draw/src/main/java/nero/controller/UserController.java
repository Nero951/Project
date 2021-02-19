package nero.controller;

import nero.model.Setting;
import nero.model.User;
import nero.service.SettingService;
import nero.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

@RestController
@RequestMapping("/user")
public class UserController {

    @Autowired
    private UserService userService;//一定要在类型上加上@Service，注册到容器中

    @Autowired
    private SettingService settingService;

    @PostMapping("/login")
    public Object login(@RequestBody User user, HttpServletRequest req){
        //如果用户名密码校验失败，再Service中抛出异常，这里exist一定不为null
        User exist = userService.login(user);//exist为数据库查询的用户，如果查询不到，为null
        Setting setting = settingService.query(exist.getId());//可以提供一个方法，根据用户id，获取setting_id
        exist.setSettingId(setting.getId());
        HttpSession session = req.getSession();
        session.setAttribute("user", exist);
        return null;
    }

    @PostMapping("/register")
    public Object register(User user,
                           //上传的头像保存在本地文件夹（web服务器需要加载到），url存放在注册用户的head字段
                           @RequestParam(value = "headFile", required = false) MultipartFile headFile){
        //没有做服务器请求数据的校验，可以通过两种方式实现：1.手动校验  2.使用valida框架校验(注解)
        userService.register(user, headFile);
        return null;
    }
}
