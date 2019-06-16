package pxt.bean.esbToJavaFile;

import lombok.Data;

/**
 * @author primerxiao
 * @date 2019/5/26
 */
@Data
public class EsbExcelFieldBean {
    /**
     * 英文名称
     */
    private String srcEngName;
    /**
     * 中文名称
     */
    private String srcChnName;
    /**
     * 数据类型
     */
    private String srcFieldType;
    /**
     * 数据长度
     */
    private String srcFieldSize;
    /**
     * 是否必输
     */
    private String srcMust;
    /**
     * 备注
     */
    private String srcRemark;

    /**
     * 英文名称
     */
    private String esbEngName;
    /**
     * 中文名称
     */
    private String esbChnName;
    /**
     * 数据类型
     */
    private String esbFieldType;
    /**
     * 数据长度
     */
    private String esbFieldSize;
    /**
     * 是否必输
     */
    private String esbMust;

    /**
     * 约束条件
     */
    private String esbConstraint;
    /**
     * 备注
     */
    private String esbRemark;
}
