package pxt.gui.main;

import com.jfoenix.controls.*;
import com.jfoenix.controls.JFXPopup.PopupHPosition;
import com.jfoenix.controls.JFXPopup.PopupVPosition;
import common.container.PxtContainer;
import io.datafx.controller.ViewController;
import io.datafx.controller.flow.Flow;
import io.datafx.controller.flow.FlowHandler;
import io.datafx.controller.flow.context.FXMLViewFlowContext;
import io.datafx.controller.flow.context.ViewFlowContext;
import javafx.animation.Transition;
import javafx.application.Platform;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.layout.StackPane;
import javafx.util.Duration;
import org.springframework.beans.factory.annotation.Autowired;
import pxt.datafx.ExtendedAnimatedFlowContainer;
import pxt.gui.sidemenu.SideMenuController;
import pxt.gui.uicomponents.ButtonController;
import pxt.mapper.MenuItemMapper;
import pxt.service.MainService;

import javax.annotation.PostConstruct;

import static io.datafx.controller.flow.container.ContainerAnimations.SWIPE_LEFT;

/**
 * @author primerxiao
 */
@ViewController(value = "/fxml/Main.fxml", title = "Material Design Example")
public final class  MainController {

    private MainService mainService= PxtContainer.applicationContext.getBean(MainService.class);

    @FXMLViewFlowContext
    private ViewFlowContext context;
    @FXML
    private StackPane root;
    @FXML
    private StackPane titleBurgerContainer;
    @FXML
    private JFXHamburger titleBurger;
    @FXML
    private StackPane optionsBurger;
    @FXML
    private JFXRippler optionsRippler;
    @FXML
    private JFXDrawer drawer;

    @Autowired
    private MenuItemMapper menuItemMapper;

    private JFXPopup toolbarPopup;

    private JFXPopup popup;

    /**
     * init fxml when loaded.
     */
    @PostConstruct
    public void init() throws Exception {

        final JFXTooltip burgerTooltip = new JFXTooltip("Open drawer");

        drawer.setOnDrawerOpening(e -> {
            final Transition animation = titleBurger.getAnimation();
            burgerTooltip.setText("Close drawer");
            animation.setRate(1);
            animation.play();
        });
        drawer.setOnDrawerClosing(e -> {
            final Transition animation = titleBurger.getAnimation();
            burgerTooltip.setText("Open drawer");
            animation.setRate(-1);
            animation.play();
        });
        titleBurgerContainer.setOnMouseClicked(e -> {
            if (drawer.isClosed() || drawer.isClosing()) {
                drawer.open();
            } else {
                drawer.close();
            }
        });

        FXMLLoader loader = new FXMLLoader(getClass().getResource("/fxml/ui/popup/MainPopup.fxml"));
        loader.setController(new InputController());
        toolbarPopup = new JFXPopup(loader.load());

        optionsBurger.setOnMouseClicked(e ->
            toolbarPopup.show(optionsBurger,
                PopupVPosition.TOP,
                PopupHPosition.RIGHT,
                -12,
                15));
        JFXTooltip.setVisibleDuration(Duration.millis(3000));

        context = new ViewFlowContext();
        // set the default controller
        Flow innerFlow = new Flow(ButtonController.class);

        final FlowHandler flowHandler = innerFlow.createHandler(context);
        context.register(PxtContainer.Constant.CONTENT_FLOW_HANDLER, flowHandler);
        context.register(PxtContainer.Constant.CONTENT_FLOW, innerFlow);
        final Duration containerAnimationDuration = Duration.millis(320);
        drawer.setContent(flowHandler.start(new ExtendedAnimatedFlowContainer(containerAnimationDuration, SWIPE_LEFT)));
        context.register(PxtContainer.Constant.CONTENT_PANE, drawer.getContent().get(0));

        Flow sideMenuFlow = new Flow(SideMenuController.class);
        final FlowHandler sideMenuFlowHandler = sideMenuFlow.createHandler(context);
        drawer.setSidePane(sideMenuFlowHandler.start(new ExtendedAnimatedFlowContainer(containerAnimationDuration,
            SWIPE_LEFT)));
    }

    public static final class InputController {
        @FXML
        private JFXListView<?> toolbarPopupList;

        @FXML
        private void submit() {
            if (toolbarPopupList.getSelectionModel().getSelectedIndex() == 1) {
                Platform.exit();
            }
        }
    }



}
