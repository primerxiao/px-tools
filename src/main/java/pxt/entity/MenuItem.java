package pxt.entity;


import lombok.Data;

/**
 * @author primerxiao
 */
@Data
public class MenuItem {

  /**
   * 菜单id
   */
  private int menuId;
  /**
   * 菜单类型
   */
  private String menuType;
  /**
   * 菜单显示文本
   */
  private String menuText;
  /**
   * 父菜单id
   */
  private int menuParentId;

  /**
   * 排序
   */
  private int menuIndex;
  /**
   * 菜单图标
   */
  private String menuIcon;
  /**
   *  菜单图标大小
   */
  private int menuIconSize;
  /**
   * 菜单样式
   */
  private String menuStyleClass;
  /**
   * 菜单组
   */
  private int menuGroup;

}
