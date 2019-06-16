package pxt;

import common.container.PxtContainer;

import io.datafx.controller.ViewFactory;
import io.datafx.controller.flow.context.FXMLViewFlowContext;
import io.datafx.controller.flow.context.ViewFlowContext;
import javafx.application.Application;
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
import pxt.service.MainService;

import java.io.IOException;

/**
 * 程序主入口
 * @author primerxiao
 */
@Log4j2
@SpringBootApplication
@MapperScan("pxt.mapper")
@EnableScheduling
@EnableAsync
@EnableAspectJAutoProxy
public class MainApplication extends Application implements ApplicationContextAware {

    public static ApplicationContext applicationContext;

    @FXMLViewFlowContext
    private ViewFlowContext flowContext;

    public static void main(String[] args) throws IOException {

        System.out.println("################");
        log.info("系统启动。。。。");
        SpringApplication.run(MainApplication.class, args);
        launch(args);
        log.info("系统启动成功");
    }
    @Override
    public void start(Stage stage) throws Exception {
        MainService mainService = PxtContainer.applicationContext.getBean(MainService.class);
        mainService.start(stage,flowContext);

    }
    @Override
    public void setApplicationContext(ApplicationContext applicationContext) throws BeansException {

        Thread thread = Thread.currentThread();
        System.out.println(thread.getId()+"###"+thread.getName());
        PxtContainer.applicationContext=applicationContext;
        ViewFactory.applicationContext=applicationContext;
        MainApplication.applicationContext=applicationContext;
    }

}
