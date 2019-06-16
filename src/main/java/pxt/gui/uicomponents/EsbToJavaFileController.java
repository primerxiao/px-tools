package pxt.gui.uicomponents;

import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXListView;
import common.container.PxtContainer;
import io.datafx.controller.ViewController;
import io.datafx.controller.flow.context.FXMLViewFlowContext;
import io.datafx.controller.flow.context.ViewFlowContext;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.layout.StackPane;
import lombok.Data;
import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.annotation.Autowired;
import pxt.service.EsbToJavaFileService;

import javax.annotation.PostConstruct;

/**
 * @author primerxiao
 * @date 2019/6/16
 */
@Log4j2
@Data
@ViewController(value = "/fxml/ui/EsbToJavaFile.fxml", title = "esb治理文档代码生成")
public class EsbToJavaFileController {

    @FXMLViewFlowContext
    private ViewFlowContext context;

    @FXML
    private JFXListView<Label> sheetList;

    @FXML
    private JFXButton loanEsbExcel;

    @FXML
    private Label esbExcelPath;

    @FXML
    private StackPane root;

    @Autowired
    private EsbToJavaFileService esbToJavaFileService;

    @PostConstruct
    public void init(){
        PxtContainer.setAutowired(this);
        esbToJavaFileService.init();
    }
}
