package pxt.service.impl;

import com.jfoenix.controls.*;
import com.jfoenix.svg.SVGGlyph;
import com.jfoenix.svg.SVGGlyphLoader;
import io.datafx.controller.flow.Flow;
import io.datafx.controller.flow.FlowException;
import io.datafx.controller.flow.container.DefaultFlowContainer;
import io.datafx.controller.flow.context.ViewFlowContext;
import javafx.animation.Transition;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.geometry.Rectangle2D;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.layout.StackPane;
import javafx.stage.Screen;
import javafx.stage.Stage;
import lombok.extern.log4j.Log4j2;
import org.kordamp.ikonli.javafx.FontIcon;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;
import pxt.MainApplication;
import pxt.entity.MenuItem;
import pxt.gui.main.MainController;
import pxt.mapper.MenuItemMapper;
import pxt.service.MainService;

import java.io.IOException;
import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;

/**
 * @author primerxiao
 * @date 2019/6/9
 */
@Log4j2
@Service
public class MainServiceImpl implements MainService {

    @Autowired
    private MenuItemMapper menuItemMapper;

    @Async("asyncServiceExecutor")
    @Override
    public void loadGlyphsFont() throws IOException {

        SVGGlyphLoader.loadGlyphsFont(MainApplication.class.getResourceAsStream("/fonts/icomoon.svg"),
                "icomoon.svg");
    }
    @Override
    public void start(Stage stage,ViewFlowContext flowContext) throws IOException, FlowException {
        loadGlyphsFont();
        Flow flow = new Flow(MainController.class);
        DefaultFlowContainer container = new DefaultFlowContainer();
        flowContext = new ViewFlowContext();
        flowContext.register("Stage", stage);
        flow.createHandler(flowContext).start(container);
        JFXDecorator decorator = new JFXDecorator(stage, container.getView());
        decorator.setCustomMaximize(true);
        decorator.setGraphic(new SVGGlyph(""));
        decorator.setOnCloseButtonAction(() -> System.exit(0));
        stage.setTitle("JFoenix Demo");
        double width = 800;
        double height = 600;
        try {
            Rectangle2D bounds = Screen.getScreens().get(0).getBounds();
            width = bounds.getWidth() / 2.5;
            height = bounds.getHeight() / 1.35;
        }catch (Exception e){ }

        Scene scene = new Scene(decorator, width, height);
        final ObservableList<String> stylesheets = scene.getStylesheets();
        stylesheets.addAll(
                MainApplication.class.getResource("/css/jfoenix-main.css").toExternalForm());
        stage.setScene(scene);
        stage.show();
    }

}
