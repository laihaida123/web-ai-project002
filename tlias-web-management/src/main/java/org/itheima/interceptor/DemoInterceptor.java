package org.itheima.interceptor;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.ModelAndView;

import java.awt.event.HierarchyBoundsListener;
@Slf4j
@Component
public class DemoInterceptor implements HandlerInterceptor {
    //在目标资源方法运行之前运行，返回值：true放行，false拦截
    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        log.info("prehandle");
        return HandlerInterceptor.super.preHandle(request, response, handler);
    }
    //在目标运行之后运行，返回值：true放行，false拦截

    @Override
    public void postHandle(HttpServletRequest request, HttpServletResponse response, Object handler, ModelAndView modelAndView) throws Exception {
        log.info("postHandle");
        HandlerInterceptor.super.postHandle(request, response, handler, modelAndView);
    }
    //试图渲染完毕后运行

    @Override
    public void afterCompletion(HttpServletRequest request, HttpServletResponse response, Object handler, Exception ex) throws Exception {
        log.info("afterCompletion");
        HandlerInterceptor.super.afterCompletion(request, response, handler, ex);
    }
}
