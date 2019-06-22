package pxt.gui.uicomponents;

import com.jfoenix.controls.JFXButton;
import common.utils.JfxAlertUtil;
import io.datafx.controller.ViewController;
import io.datafx.controller.ViewNode;
import io.datafx.controller.flow.action.ActionMethod;
import io.datafx.controller.flow.action.ActionTrigger;
import javafx.fxml.FXML;

/**
 * @author primerxiao
 */
@ViewController(value = "/fxml/ui/Button.fxml", title = "Material Design Example")
public class ButtonController {

    @ViewNode
    @ActionTrigger("myAction")
    private JFXButton test;

    @ActionMethod("myAction")
    public void onAction(){
        JfxAlertUtil.alert(test,"","adfasdf");
    }
}
