package pxt.service.impl;

import common.container.ExcelContainer;
import common.service.CommonService;
import common.utils.ExcelUtil;
import common.utils.FileChooserUtil;
import common.utils.JfxAlertUtil;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.stage.FileChooser;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.springframework.stereotype.Service;
import pxt.gui.uicomponents.EsbToJavaFileController;
import pxt.service.EsbToJavaFileService;

import java.io.File;
import java.util.List;

/**
 * @author primerxiao
 * @date 2019/6/16
 */
@Service
public class EsbToJavaFileServiceImpl
        extends CommonService<EsbToJavaFileController> implements EsbToJavaFileService {


    @Override
    public void init() {
        controller.getLoanEsbExcel().setOnMouseClicked(event -> {
            JfxAlertUtil.alert(controller.getLoanEsbExcel(),"温馨提示：","你点击了esb治理文本加载按钮");
        });
        controller.getSheetList().setExpanded(true);
        controller.getSheetList().depthProperty().set(1);
    }

    private final void load() throws Exception {
        File file = FileChooserUtil.chooseFile(new FileChooser.ExtensionFilter("xlsx","*.xlsx"));

        if (!file.exists()) {
            JfxAlertUtil.showAndWait(controller.getLoanEsbExcel(),"文件不存在或者非法！！！");
            return;
        }

        //加载头文件
        List<XSSFSheet> sheets = ExcelUtil.loadAllSheet(ExcelUtil.loadExcel(file.getAbsolutePath()));
        if (sheets == null) {
            JfxAlertUtil.showAndWait(controller.getLoanEsbExcel(),"无数据！！！");
            return;
        }

        ObservableList<String> strList = FXCollections.observableArrayList();
        ExcelContainer.sheetsMap.clear();
        sheets.stream().forEach((sheet)->{
            String sheetName = sheet.getSheetName();
            strList.add(sheetName);
            ExcelContainer.sheetsMap.put(sheetName,sheet);
        });
        //leftListView.setItems(strList);
    }
}
