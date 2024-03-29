package pxt.gui.uicomponents;

import com.jfoenix.controls.JFXButton;
import common.utils.JfxAlertUtil;
import io.datafx.controller.ViewController;
import io.datafx.controller.ViewNode;
import io.datafx.controller.flow.action.ActionMethod;
import io.datafx.controller.flow.action.ActionTrigger;
import io.datafx.controller.flow.context.ActionHandler;
import io.datafx.controller.flow.context.FXMLViewFlowContext;
import io.datafx.controller.flow.context.FlowActionHandler;
import io.datafx.controller.flow.context.ViewFlowContext;
import javafx.fxml.FXML;

import javax.annotation.PostConstruct;

/**
 * @author primerxiao
 */
@ViewController(value = "/fxml/ui/Button.fxml", title = "Material Design Example")
public class ButtonController {

    @FXMLViewFlowContext
    private ViewFlowContext flowContext;

    @ActionHandler
    private FlowActionHandler flowActionHandler;

    @FXML
    @ActionTrigger("myAction")
    private JFXButton test;

    @PostConstruct
    public void init() {

    }

    @ActionMethod("myAction")
    public void onAction(){
        JfxAlertUtil.alert(test,"","adfasdf");
    }
}
