package nero.service;

import nero.exception.BusinessException;
import nero.mapper.SettingMapper;
import nero.model.Award;
import nero.model.Member;
import nero.model.Setting;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
public class SettingService {

    @Autowired
    private SettingMapper settingMapper;

    @Autowired
    private AwardService awardService;

    @Autowired
    private MemberService memberService;

    public Setting query(Integer id) {
        Setting query = new Setting();
        query.setUserId(id);
        //注册用户时需要生成一个Setting数据，如果没有生成代表业务有问题
        Setting setting = settingMapper.selectOne(query);
        if(setting == null){
            throw new BusinessException("SET001","用户设置信息出错");
        }
        //查询奖品列表、人员列表，设置到属性中
        //通过setting_id查询奖品列表
        Award award = new Award();
        award.setSettingId(setting.getId());
        List<Award> awards = awardService.query(award);
        setting.setAwards(awards);

        //通过user_id，查询人员列表
        Member member = new Member();
        member.setUserId(id);
        List<Member> members = memberService.query(member);
        setting.setMembers(members);

        return setting;
    }

    @Transactional
    public void add(Setting setting) {
        settingMapper.insertSelective(setting);
    }
    //Spring事务设置：默认的传播方式为Required，当前没有事务，就创建，有就加入
    @Transactional
    public void update(Integer id, Integer batchNumber) {
        //第一种实现：可以使用mapper已提供单表操作的方法：根据userId查询setting信息，再修改
        //第二种实现：写sql，一次性修改
        int num = settingMapper.updateByUserId(id, batchNumber);
    }
}
