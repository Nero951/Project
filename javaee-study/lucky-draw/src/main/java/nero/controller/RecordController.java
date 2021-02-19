package nero.controller;

import nero.service.RecordService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/record")
public class RecordController {
    @Autowired
    private RecordService recordService;

    @PostMapping("/add/{awardId}")
    public Object add(@PathVariable Integer awardId, @RequestBody List<Integer> memberIds){
        recordService.add(awardId, memberIds);
        return null;
    }

    //业务上，一个人只能抽一个奖
    //如果是一个人抽多个将==>通过memberId+awardId删除
    @GetMapping("/delete/member")
    public Object deleteByMemberId(Integer id){
        recordService.deleteByMemberId(id);
        return null;
    }

    @GetMapping("/delete/award")
    public Object deleteByAwardId(Integer id){
        recordService.deleteByAwardId(id);
        return null;
    }
}
