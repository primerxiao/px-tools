package mapper;

import mapper.entity.User;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Result;
import org.apache.ibatis.annotations.Results;
import org.apache.ibatis.annotations.Select;

import java.util.List;

/**
 * @author primerxiao
 * @date 2019/6/9
 */
public interface UserMapper {

    /**
     * 查询所有用户
     *
     * @return
     */
    @Select("select * from user ")
    @Results({
            @Result(property = "name", column = "name")
    })
    List<User> getAll();

    /**
     * 根据id查询用户
     * @param id
     * @return
     */
    @Select("select * from user where id=#{id}")
    User getById(@Param("id") int id);
}
