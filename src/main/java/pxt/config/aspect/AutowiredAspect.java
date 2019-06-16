package pxt.config.aspect;

import lombok.extern.log4j.Log4j2;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.springframework.stereotype.Component;

/**
 * @author primerxiao
 * @date 2019/6/16
 */


@Aspect
@Log4j2
@Component
public class AutowiredAspect {

    //@Pointcut("execution(public * io.datafx.controller.ViewFactory.createByController( ..))")
    @Pointcut("execution(public * pxt.service.MainService.*())")
    public void createByController() {
    }

    @Pointcut("execution(public * pxt.service.impl.MainServiceImpl.*())")
    public void createByController1() {
    }
    /**
     * @Description: 环绕通知,环绕增强，相当于MethodInterceptor
     * @Title: around
     * @author OnlyMate
     * @Date 2018年9月10日 下午3:52:56
     * @param pjp
     * @return
     */
    @Around("createByController1()")
    public Object around(ProceedingJoinPoint pjp) {
        System.out.println("【注解：Around . 环绕前】方法环绕start.....");
        try {
            //如果不执行这句，会不执行切面的Before方法及controller的业务方法
            Object o =  pjp.proceed();
            log.info("【注解：Around. 环绕后】方法环绕proceed，结果是 :" + o);
            return o;
        } catch (Throwable e) {
            e.printStackTrace();
            return null;
        }
    }


}

