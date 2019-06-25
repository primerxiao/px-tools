package mapper.entity;


import lombok.Data;

/**
 * 菜单
 * @author primerxiao
 */
@Data
public class MenuItem {

  /**
   * 菜单id
   */
  private String id;
  /**
   * 菜单类型
   * menus
   * group
   * menu
   */
  private String menuType;
  /**
   * 菜单显示文本
   */
  private String menuText;
  /**
   * 父菜单id
   */
  private int parentId;
  /**
   * 菜单索引（排序）
   */
  private int menuIndex;
  /**
   * 菜单动作
   * click
   * controller
   */
  private String menuAction;

  /**
   * 菜单动作 controller 对应的classname
   */
  private String controllerName;

}
