package pxt.gui.main;

import com.jfoenix.controls.JFXDrawer;
import com.jfoenix.controls.JFXListView;
import common.container.PxtContainer;
import io.datafx.controller.ViewController;
import io.datafx.controller.flow.context.ActionHandler;
import io.datafx.controller.flow.context.FXMLViewFlowContext;
import io.datafx.controller.flow.context.FlowActionHandler;
import io.datafx.controller.flow.context.ViewFlowContext;
import javafx.application.Platform;
import javafx.fxml.FXML;
import javafx.scene.control.MenuBar;
import javafx.scene.control.MenuItem;
import javafx.scene.layout.StackPane;
import lombok.Data;
import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.annotation.Autowired;
import pxt.service.MainService;

import javax.annotation.PostConstruct;


/**
 * @author primerxiao
 */
@Data
@Log4j2
@ViewController(value = "/fxml/Main.fxml", title = "Material Design Example")
public final class  MainController {

    @FXMLViewFlowContext
    private ViewFlowContext context;

    @ActionHandler
    private FlowActionHandler actionHandler;
    @FXML
    private StackPane root;
    @FXML
    private JFXDrawer drawer;
    @FXML
    private MenuBar mainMenuBar;

    @FXML
    private MenuItem esbCodeHelper;

    @Autowired
    private MainService mainService;
    /**
     * init fxml when loaded.
     */
    @PostConstruct
    public void init() throws Exception {
        PxtContainer.setAutowired(this);


        mainService.init();
    }

}
