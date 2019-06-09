package pxt.mapper;

import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;
import pxt.entity.MenuItem;

import java.util.List;

/**
 * @author primerxiao
 * @date 2019/6/9
 */
public interface MenuItemMapper {
    /**
     * 根据group查询所有菜单
     *
     * @param group
     * @return
     */
    @Select("select * from menu_item where menu_group =#{group} ")
    List<MenuItem> listByGroup(@Param("group") String group);

    /**
     * 根据group和type查询所有列表
     * @param group
     * @param type
     * @return
     */
    @Select("select * from menu_item where menu_group =#{group} and menu_type=#{type} order by menu_index")
    List<MenuItem> listByGroupAndType(@Param("group") String group, @Param("type") String type);


}
