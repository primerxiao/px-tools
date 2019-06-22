package common.container;

import com.jfoenix.controls.JFXDrawer;
import common.service.CommonService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;

import java.lang.reflect.Field;
import java.util.Objects;

/**
 * @author primerxiao
 * @date 2019/6/9
 */
public class PxtContainer {
    /**
     * spring容器
     */
    public static ApplicationContext applicationContext;

    public static  JFXDrawer drawer;

    public static void setAutowired(Object object) {
        if (!Objects.isNull(object)) {
            Field[] declaredFields = object.getClass().getDeclaredFields();
            for (Field declaredField : declaredFields) {
                Autowired annotation = declaredField.getAnnotation(Autowired.class);
                if (Objects.isNull(annotation)) {
                    continue;
                }
                if (PxtContainer.applicationContext == null) {
                    continue;
                }
                declaredField.setAccessible(true);
                CommonService bean = (CommonService) PxtContainer.applicationContext.getBean(declaredField.getType());

                bean.setController(object);
                try {
                    declaredField.set(object, bean);
                } catch (IllegalAccessException e) {
                    e.printStackTrace();
                }
            }
        }
    }
    /**
     * 常量类
     */
    public static class Constant{
        public static String CONTENT_FLOW_HANDLER="ContentFlowHandler";
        public static String CONTENT_FLOW="ContentFlow";
        public static String CONTENT_PANE="ContentPane";
    }

}
