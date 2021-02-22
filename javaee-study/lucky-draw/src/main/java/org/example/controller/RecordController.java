package org.example.controller;

import org.example.model.User;
import org.example.service.RecordService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpSession;
import java.util.List;

@RestController
@RequestMapping("/record")
public class RecordController {
    @Autowired
    private RecordService recordService;

    //开始抽奖：某个奖项下，一次性抽多个人(在数据库里一次性添加多个人)
    @PostMapping("/add/{awardId}")
    public Object add(@RequestBody List<Integer> membersId, @PathVariable Integer awardId){
        int n = recordService.add(membersId, awardId);
        return null;
    }

    @GetMapping("/delete/member")
    public Object deleteByMemberId(Integer id){
        int n = recordService.deleteByMemberId(id);
        return null;
    }

    @GetMapping("/delete/award")
    public Object deleteByAwardId(Integer id){
        int n = recordService.deleteByAwardId(id);
        return null;
    }

    @GetMapping("/delete/setting")
    public Object deleteBySetting(HttpSession session){
        User user = (User)session.getAttribute("user");
        //获取userId-->关联settingId-->关联memberId，awardId
        //删除关联record
        int n = recordService.deleteByUserId(user.getId());
        return null;
    }
}
