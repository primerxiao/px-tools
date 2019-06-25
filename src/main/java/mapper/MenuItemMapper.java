package mapper;

import mapper.entity.MenuItem;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;

import java.util.List;

/**
 * @author primerxiao
 * @date 2019/6/9
 */
public interface MenuItemMapper {
    /**
     * 根据菜单类型查询所有菜单
     *
     * @param menuType
     * @return
     */
    @Select("select * from menu_item where menu_type =#{menuType} order by menu_index asc ")
    List<MenuItem> listByMenuType(@Param("menuType") String menuType);

    /**
     * 根据父id查询所有菜单
     * @param parentId
     * @return
     */
    @Select("select * from menu_item where parent_id =#{parentId} order by menu_index asc ")
    List<MenuItem> listByParentId(@Param("parentId")String parentId);

}
