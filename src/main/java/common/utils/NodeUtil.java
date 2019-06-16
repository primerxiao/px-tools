package common.utils;

import javafx.scene.Node;
import javafx.scene.Parent;
import org.springframework.util.StringUtils;

import java.util.Objects;

/**
 * 节点工具类
 * @author primerxiao
 * @date 2019/6/16
 */
public class NodeUtil {
    /**
     * 查找指定名称的父节点
     * @param parentName 父节点名称
     * @param node 当前节点
     * @return
     */
    public static Node findParent(String parentName, Node node) {
        Parent parent = node.getParent();
        if (!Objects.isNull(parent)) {
            if (!StringUtils.isEmpty(parent.getId())) {
                if (parentName.equals(parent.getId())) {
                    return parent;
                }
            }
            return findParent(parentName, parent);
        }
        return null;
    }
}
