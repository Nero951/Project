package nero.mapper;

import nero.base.BaseMapper;
import nero.model.Member;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

@Mapper
public interface MemberMapper extends BaseMapper<Member> {
    List<Member> query(Member member);
}