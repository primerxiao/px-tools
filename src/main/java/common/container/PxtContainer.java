package common.container;

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


    public static void setAutowired(Object object) {
        if (!Objects.isNull(object)) {
            Field[] declaredFields = object.getClass().getDeclaredFields();
            for (Field declaredField : declaredFields) {
                Autowired annotation = declaredField.getAnnotation(Autowired.class);
                if (Objects.isNull(annotation)) {
                    continue;
                }
                declaredField.setAccessible(true);
                try {
                    declaredField.set(object, Objects.isNull(PxtContainer.applicationContext)?null:PxtContainer.applicationContext.getBean(declaredField.getType()));
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
