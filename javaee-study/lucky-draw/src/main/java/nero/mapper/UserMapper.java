package nero.mapper;

import nero.base.BaseMapper;
import nero.model.User;
import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface UserMapper extends BaseMapper<User> {
    User login(User user);
}