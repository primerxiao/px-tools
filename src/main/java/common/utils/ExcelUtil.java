package common.utils;


import lombok.Getter;
import lombok.Setter;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.util.CellRangeAddress;
import org.apache.poi.xssf.usermodel.XSSFCell;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import pxt.bean.esbToJavaFile.Result;

import java.io.File;
import java.io.FileInputStream;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;

/**
 * @author primerxiao
 * @date 2019/5/26
 */
@Getter
@Setter
public class ExcelUtil {

    /**
     * 获取工作簿
     * @param filePath
     * @return
     * @throws Exception
     */
    public static XSSFWorkbook loadExcel(String filePath) throws Exception {
        try (InputStream inputStream=new FileInputStream(new File(filePath))){
            return new XSSFWorkbook(inputStream);
        }
    }

    /**
     * 获取所有sheet
     * @param hssfWorkbook
     * @return
     */
    public static List<XSSFSheet> loadAllSheet(XSSFWorkbook hssfWorkbook){
        List<XSSFSheet> hssfSheets = new ArrayList<>();
        int numberOfSheets = hssfWorkbook.getNumberOfSheets();
        for (int i = 0; i < numberOfSheets; i++) {
            hssfSheets.add(hssfWorkbook.getSheetAt(i));
        }
        return hssfSheets;
    }

    public static String getStrValue(XSSFCell cell){
        switch (cell.getCellType()) {
            case BLANK:
            case STRING:
                return cell.getStringCellValue();
            case BOOLEAN:
                return Boolean.toString(cell.getBooleanCellValue());
            case FORMULA:
            case NUMERIC:
                return String.valueOf(cell.getNumericCellValue());
            case ERROR:
            case _NONE:
            default:
                return "";
        }

    }


    public static Result isMergedRegion(Sheet sheet, int row , int column) {
        int sheetMergeCount = sheet.getNumMergedRegions();
        for (int i = 0; i < sheetMergeCount; i++) {
            CellRangeAddress range = sheet.getMergedRegion(i);
            int firstColumn = range.getFirstColumn();
            int firstRow = range.getFirstRow();
            int lastColumn = range.getLastColumn();
            int lastRow = range.getLastRow();
            if(row >= firstRow && row <= lastRow){
                if(column >= firstColumn && column <= lastColumn){
                    return new Result(true,firstRow,lastRow,firstColumn,lastColumn);
                }
            }
        }
        return new Result(false,0,0,0,0);
    }


}
