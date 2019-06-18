package common.utils;

import com.jfoenix.animation.alert.JFXAlertAnimation;
import com.jfoenix.controls.JFXAlert;
import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXDialogLayout;
import javafx.scene.Node;
import javafx.scene.control.Label;
import javafx.stage.Modality;

/**
 * @author primerxiao
 * @date 2019/6/16
 */
public class JfxAlertUtil {
    /**
     * 弹出信息提示
     *
     * @param currentNode 当前节点
     * @param title     展示的标题
     * @param msg       展示的文本内容
     */
    public static void alert(Node currentNode, String title, String msg) {
        JFXAlert alert = new JFXAlert(currentNode.getScene().getWindow());
        alert.initModality(Modality.APPLICATION_MODAL);
        alert.setOverlayClose(false);
        JFXDialogLayout layout = new JFXDialogLayout();
        layout.setHeading(new Label(title));
        layout.setBody(new Label(msg));
        JFXButton closeButton = new JFXButton("确定");
        closeButton.getStyleClass().add("dialog-accept");
        closeButton.setOnAction(eve -> alert.hideWithAnimation());
        layout.setActions(closeButton);
        alert.setContent(layout);
        alert.show();
    }

    /**
     * 显示并等待
     * @param currentNode
     * @param msg
     */
    public static void showAndWait(Node currentNode, String msg) {
        JFXDialogLayout layout = new JFXDialogLayout();
        layout.setBody(new Label(msg));
        JFXAlert<Void> alert = new JFXAlert<>(currentNode.getScene().getWindow());
        alert.setOverlayClose(true);
        alert.setAnimation(JFXAlertAnimation.CENTER_ANIMATION);
        alert.setContent(layout);
        alert.initModality(Modality.NONE);
        alert.showAndWait();
    }


}
