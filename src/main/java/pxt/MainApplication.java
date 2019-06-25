package pxt;

import com.jfoenix.controls.JFXDecorator;
import com.jfoenix.svg.SVGGlyph;
import com.jfoenix.svg.SVGGlyphLoader;
import common.container.PxtContainer;

import io.datafx.controller.ViewFactory;
import io.datafx.controller.flow.Flow;
import io.datafx.controller.flow.container.DefaultFlowContainer;
import io.datafx.controller.flow.context.FXMLViewFlowContext;
import io.datafx.controller.flow.context.ViewFlowContext;
import javafx.application.Application;
import javafx.collections.ObservableList;
import javafx.geometry.Rectangle2D;
import javafx.scene.Scene;
import javafx.stage.Screen;
import javafx.stage.Stage;
import lombok.extern.log4j.Log4j2;
import org.mybatis.spring.annotation.MapperScan;
import org.springframework.beans.BeansException;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.context.annotation.EnableAspectJAutoProxy;
import org.springframework.scheduling.annotation.EnableAsync;
import org.springframework.scheduling.annotation.EnableScheduling;
import pxt.gui.main.MainController;
import pxt.service.MainService;

import java.io.IOException;

/**
 * 程序主入口
 * @author primerxiao
 */
@Log4j2
@SpringBootApplication
@MapperScan("mapper")
@EnableScheduling
@EnableAsync
@EnableAspectJAutoProxy
public class MainApplication extends Application implements ApplicationContextAware {

    @FXMLViewFlowContext
    private ViewFlowContext flowContext;

    public static void main(String[] args) {
        log.info("系统启动。。。。");
        SpringApplication.run(MainApplication.class, args);
        launch(args);
        log.info("系统启动成功");
    }
    @Override
    public void start(Stage stage) throws Exception {

        new Thread(() -> {
            try {
                SVGGlyphLoader.loadGlyphsFont(MainApplication.class.getResourceAsStream("/fonts/icomoon.svg"),
                        "icomoon.svg");
            } catch (IOException e) {
                e.printStackTrace();
            }
        }).run();
        Flow flow = new Flow(MainController.class);

        DefaultFlowContainer defaultFlowContainer = new DefaultFlowContainer();
        flowContext = new ViewFlowContext();
        flowContext.register("Stage", stage);
        flow.createHandler(flowContext).start(defaultFlowContainer);
        JFXDecorator decorator = new JFXDecorator(stage, defaultFlowContainer.getView());
        decorator.setCustomMaximize(true);
        decorator.setGraphic(new SVGGlyph(""));
        decorator.setOnCloseButtonAction(() -> System.exit(0));
        stage.setTitle("开发助手");
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
    @Override
    public void setApplicationContext(ApplicationContext applicationContext) throws BeansException {

        PxtContainer.applicationContext=applicationContext;
    }

}
