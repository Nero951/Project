package org.example.controller;

import org.example.model.Award;
import org.example.model.Member;
import org.example.model.Setting;
import org.example.model.User;
import org.example.service.AwardService;
import org.example.service.MemberService;
import org.example.service.SettingService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpSession;
import java.util.List;

@RestController
@RequestMapping("/setting")
public class SettingController {
    @Autowired
    private SettingService settingService;

    @Autowired
    private AwardService awardService;

    @Autowired
    private MemberService memberService;

    /** 进入抽奖设置页面，初始化的接口，返回页面所有需要的数据：
    * setting对象中的属性
    * setting对象目前没有的属性：
    * user(用户星系)，
    * award(奖项列表：根据setting_id查)，
    * members(抽奖人员列表，根据Setting_id查)
    */
    @GetMapping("/query")
    public Object query(HttpSession session){//已经登陆了，就可以直接使用HttpSession
        /*
         * 获取session中的user
         * 根据userId查setting信息
         * 把user设置到setting新增属性user中
         * 根据setting_id查award列表，设置到setting新增属性awards中
         * 根据setting_id查member列表，设置到setting新增属性members中
         */
        User user = (User) session.getAttribute("user");
        Setting setting = settingService.queryByUserId(user.getId());
        setting.setUser(user);
        List<Award> awards = awardService.queryBySettingId(setting.getId());
        setting.setAwards(awards);
        List<Member> members = memberService.queryBySettingId(setting.getId());
        setting.setMembers(members);

        return setting;
    }

    @GetMapping("/update")
    public Object update(Integer batchNumber, HttpSession session){
        User user = (User) session.getAttribute("user");
        return settingService.update(batchNumber, user.getId());
    }

}
