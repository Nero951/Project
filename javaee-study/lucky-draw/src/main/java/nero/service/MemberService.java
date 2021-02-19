package nero.service;

import nero.mapper.MemberMapper;
import nero.model.Member;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
public class MemberService {

    @Autowired
    private MemberMapper memberMapper;

    public List<Member> query(Member member) {
        return memberMapper.selectByCondition(member);
    }
    @Transactional
    public void add(Member member){
        memberMapper.insertSelective(member);
    }
}
