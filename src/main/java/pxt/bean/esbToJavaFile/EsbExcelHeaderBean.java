package pxt.bean.esbToJavaFile;

import lombok.Data;
import lombok.ToString;

/**
 * @author primerxiao
 * @date 2019/5/26
 */
@Data
@ToString
public class EsbExcelHeaderBean {
    /**
     * 交易码
     */
    private String headerBizCode;
    /**
     * 服务名称
     */
    private String headerServiceName;
    /**
     * 交易名称
     */
    private String headerBizName;
    /**
     * 服务场景名称
     */
    private String headerServiceSceneName;
    /**
     * 交易描述
     */
    private String headerBizDesc;
    /**
     *服务描述
     */
    private String headerServiceDesc;
    /**
     * 服务场景描述
     */
    private String headerServiceSceneDesc;
}
