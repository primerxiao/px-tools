package common.container;

import org.springframework.context.ApplicationContext;

/**
 * @author primerxiao
 * @date 2019/6/9
 */
public class PxtContainer {
    /**
     * spring容器
     */
    public static ApplicationContext applicationContext;

    /**
     * 常量类
     */
    public static class Constant{
        public static String CONTENT_FLOW_HANDLER="ContentFlowHandler";
        public static String CONTENT_FLOW="ContentFlow";
        public static String CONTENT_PANE="ContentPane";
    }

}
