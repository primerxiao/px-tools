package pxt.gui.uicomponents;

import com.jfoenix.controls.JFXButton;
import common.utils.JfxAlertUtil;
import io.datafx.controller.ViewController;
import io.datafx.controller.ViewNode;
import io.datafx.controller.flow.action.ActionMethod;
import io.datafx.controller.flow.action.ActionTrigger;
import io.datafx.controller.flow.action.LinkAction;
import io.datafx.controller.flow.context.ActionHandler;
import io.datafx.controller.flow.context.FXMLViewFlowContext;
import io.datafx.controller.flow.context.FlowActionHandler;
import io.datafx.controller.flow.context.ViewFlowContext;
import javafx.fxml.FXML;

import javax.annotation.PostConstruct;

/**
 * @author primerxiao
 */
@ViewController(value = "/fxml/ui/navigation.fxml", title = "导航")
public class NavigationController {

    @FXMLViewFlowContext
    private ViewFlowContext flowContext;

    @ActionHandler
    private FlowActionHandler flowActionHandler;

    @ViewNode
    @LinkAction(EsbToJavaFileController.class)
    private JFXButton linksManage;

    @PostConstruct
    public void init() {
        
    }

}
