package pxt.bean.esbToJavaFile;

import lombok.Data;
import lombok.ToString;

import java.util.List;

/**
 * @author primerxiao
 * @date 2019/5/31
 */
@Data
@ToString
public class EsbExcelBean {

    private int reqStartRow;
    private int respStartRow;

    private int srcReqStartCell;
    private int esbReqStartCell;
    private int srcRespStartCell;

    private int reqEndRow;
    private int respEndRow;

    private int srcReqEndCell;
    private int esbReqEndCell;
    private int srcRespEndCell;

    private boolean srcFlag;

    private boolean esbFlag;

    /**
     * 头
     */
    private EsbExcelHeaderBean header;
    /**
     * 请求
     */
    private List<EsbExcelFieldBean> reqBeanFieldList;
    /**
     * 响应
     */
    private List<EsbExcelFieldBean> respBeanFieldList;
    /**
     * 响应体数组
     */
    private List<EsbExcelFieldBean> respArrBeanFieldList;
}
