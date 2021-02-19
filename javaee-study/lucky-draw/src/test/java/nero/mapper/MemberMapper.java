package nero.mapper;

import nero.base.BaseMapper;
import nero.model.Member;
import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface MemberMapper extends BaseMapper<Member> {
}