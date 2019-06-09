package pxt.entity;


import lombok.Data;

/**
 * @author primerxiao
 */
@Data
public class MenuItem {

  private int menuId;
  private String menuType;
  private String menuText;
  private int menuParentId;
  private int menuIndex;
  private String menuIcon;
  private int menuIconSize;
  private String menuStyleClass;
  private int menuGroup;

}
