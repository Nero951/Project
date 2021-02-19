package nero.mapper;

import nero.model.User;
import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface Test {

    User query(Integer id);
}
