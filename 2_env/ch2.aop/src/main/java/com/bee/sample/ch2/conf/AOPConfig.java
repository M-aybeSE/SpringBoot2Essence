package com.bee.sample.ch2.conf;

import java.util.Arrays;

import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.aspectj.lang.annotation.Pointcut;
import org.springframework.context.annotation.Configuration;
/**
 * Controller 的所有方法在执行前后都会进入functionAccessCheck方法
 * @author lijiazhi
 *
 */
@Aspect
@Configuration
public class AOPConfig {
	@Around("@within(org.springframework.stereotype.Controller) ")
	public Object functionAccessCheck(final ProceedingJoinPoint pjp)  throws Throwable {
		try {
			Object[] args = pjp.getArgs();
			System.out.println("args:"+Arrays.asList(args));
			
			Object o = pjp.proceed();
			
			System.out.println("return :"+o);
			return o;
			
		} catch (Throwable e) {
			throw e;
		}
	}

	/**
	 * 所有用到@Component注解的方法
	 */
	@Pointcut("target(org.springframework.stereotype.Component)")
	public void customAspect() {

	}

/*	@Pointcut("@annotation(tech.rongxin.oryx.cgi.common.aspect.stock.ThirdStockAyncAnnotation)")
	public void stockAspect() {
	}

	@Pointcut(value = "execution(public * tech.rongxin.oryx.cgi.*.controller.*.*(..)) ")
    public void pointcut() {
    }

	@Before("stockAspect()")
	public void doBeforeMethod(JoinPoint joinPoint) {

	}*/
}
