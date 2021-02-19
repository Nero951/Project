package nero.controller;

import nero.model.Member;
import nero.model.User;
import nero.service.MemberService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpSession;

@RestController
@RequestMapping("/member")
public class MemberController {
    @Autowired
    private MemberService memberService;

    @PostMapping("/add")
    public Object add(@RequestBody Member member, HttpSession session){//插入时，请求数据不带id
        User user = (User) session.getAttribute("user");
        memberService.add(member);
        return member.getId();//数据库插入时，自动设置id为自增主键；返回前端，否则会有bug
    }
}
