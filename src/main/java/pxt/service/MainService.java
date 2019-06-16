package pxt.service;

import com.jfoenix.controls.JFXDrawer;
import com.jfoenix.controls.JFXHamburger;
import com.jfoenix.controls.JFXPopup;
import io.datafx.controller.flow.FlowException;
import io.datafx.controller.flow.context.ViewFlowContext;
import javafx.scene.layout.StackPane;
import javafx.stage.Stage;

import java.io.IOException;

/**
 * @author primerxiao
 * @date 2019/6/9
 */
public interface MainService {
    /**
     * 加载GlyphsFont
     * @throws IOException
     */
    public void loadGlyphsFont() throws IOException;

    /**
     * 启动程序
     * @param stage
     * @param flowContext
     * @throws IOException
     */
    public void start(Stage stage, ViewFlowContext flowContext) throws IOException, FlowException;
}
