package com.bee.sample.ch3.conf;

import java.text.ParseException;
import java.util.Locale;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.context.annotation.Configuration;
import org.springframework.format.Formatter;
import org.springframework.format.FormatterRegistry;
import org.springframework.format.datetime.DateFormatter;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.ViewControllerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

import com.bee.sample.ch3.entity.User;

/**
 * @author 14530
 */
@CrossOrigin
@Configuration
public class MvcConfigurer implements WebMvcConfigurer {

	/**
	 * 添加拦截器
	 * @param registry
	 */
    @Override
    public void addInterceptors(InterceptorRegistry registry) {
        registry.addInterceptor(new SessionHandlerInterceptor()).addPathPatterns("/user/**");
    }

	/**
	 * 跨域访问配置
	 * @CrossOrigin
	 * @param registry
	 */
	@Override
    public void addCorsMappings(CorsRegistry registry) {
		registry.addMapping("/api/**")
				.allowedOrigins("http://domain2.com")
				.allowedMethods("POST", "GET");
    }

	/**
	 * 格式化
	 * @param registry
	 */
	@Override
	public void addFormatters(FormatterRegistry registry) {
        registry.addFormatter(new DateFormatter("yyyy-MM-dd HH:mm:ss"));

    }

	/**
	 * URI到视图的映射
	 * @param registry
	 */
	@Override
	public void addViewControllers(ViewControllerRegistry registry) {
        registry.addViewController("/index.html").setViewName("/index.btl");
        registry.addRedirectViewController("/**/*.do", "/index.html");
    }

    /**
     * 检查用户是否已经登录，如果未登录，重定向到登录页面
     */
    class SessionHandlerInterceptor implements HandlerInterceptor {

    	@Override
		public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
            User user = (User) request.getSession().getAttribute("user");
            if (user == null) {
            	// 如果没有登录，重定向到login.html
                response.sendRedirect("/login.html");
                return false;
            }
            return true;
        }

		/**
		 * Controller 方法处理完毕后，调用此方法
		 * @param request
		 * @param response
		 * @param handler
		 * @param modelAndView
		 * @throws Exception
		 */
		@Override
		public void postHandle(HttpServletRequest request, HttpServletResponse response, Object handler, ModelAndView modelAndView) throws Exception {
//			HandlerInterceptor.super.postHandle(request, response, handler, modelAndView);
		}

		/**
		 * 页面渲染完毕后调用此方法，通常用来清楚某些资源，类似java语法的finally
		 * @param request
		 * @param response
		 * @param handler
		 * @param ex
		 * @throws Exception
		 */
		@Override
		public void afterCompletion(HttpServletRequest request, HttpServletResponse response, Object handler, Exception ex) throws Exception {
//			HandlerInterceptor.super.afterCompletion(request, response, handler, ex);
		}
	}


}
