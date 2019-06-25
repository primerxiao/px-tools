package pxt.gui.sidemenu;

import com.jfoenix.controls.JFXListView;
import common.container.PxtContainer;
import common.datafx.ExtendedAnimatedFlowContainer;
import common.utils.JfxAlertUtil;
import io.datafx.controller.ViewController;
import io.datafx.controller.ViewNode;
import io.datafx.controller.context.ApplicationContext;
import io.datafx.controller.flow.Flow;
import io.datafx.controller.flow.FlowException;
import io.datafx.controller.flow.FlowHandler;
import io.datafx.controller.flow.action.ActionMethod;
import io.datafx.controller.flow.action.ActionTrigger;
import io.datafx.controller.flow.action.FlowAction;
import io.datafx.controller.flow.action.LinkAction;
import io.datafx.controller.flow.context.FXMLViewFlowContext;
import io.datafx.controller.flow.context.ViewFlowContext;
import io.datafx.controller.util.VetoException;
import javafx.application.Platform;
import javafx.fxml.FXML;
import javafx.scene.Node;
import javafx.scene.control.Label;
import javafx.util.Duration;
import pxt.gui.uicomponents.ButtonController;
import pxt.gui.uicomponents.EsbToJavaFileController;

import javax.annotation.PostConstruct;
import javax.xml.ws.Action;
import java.util.Objects;

import static io.datafx.controller.flow.container.ContainerAnimations.SWIPE_LEFT;

/**
 * @author primerxiao
 */
@ViewController(value = "/fxml/SideMenu.fxml", title = "Material Design Example")
public class SideMenuController {

    @FXMLViewFlowContext
    private ViewFlowContext context;

    @FXML
    private Label button;

    @FXML
    private Label esbToJavaFile;

    @FXML
    private JFXListView<Label> sideList;

    /**
     * init fxml when loaded.
     */
    @PostConstruct
    public void init() {
        PxtContainer.setAutowired(this);
        Objects.requireNonNull(context, "context");
        FlowHandler contentFlowHandler = (FlowHandler) context.getRegisteredObject(PxtContainer.Constant.CONTENT_FLOW_HANDLER);
        sideList.propagateMouseEventsToParent();
        sideList.setOnMouseClicked(event -> {
            Integer integer = sideList.getSelectionModel().getSelectedIndices().get(0);
            try {
                contentFlowHandler.handle(String.valueOf(integer));
            } catch (VetoException e) {
                e.printStackTrace();
            } catch (FlowException e) {
                e.printStackTrace();
            }
        });
        Flow contentFlow = (Flow) context.getRegisteredObject(PxtContainer.Constant.CONTENT_FLOW);
        contentFlow.withGlobalLink("0", ButtonController.class);
        contentFlow.withGlobalLink("1", EsbToJavaFileController.class);

    }


}
