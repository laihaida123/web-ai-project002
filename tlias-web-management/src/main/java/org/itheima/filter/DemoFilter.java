package org.itheima.filter;

import jakarta.servlet.*;
import jakarta.servlet.annotation.WebFilter;
import lombok.extern.slf4j.Slf4j;

import java.io.IOException;
@Slf4j
//@WebFilter(urlPatterns = "/*")//拦截所有请求
public class DemoFilter implements Filter {
    //过滤器核心方法,拦截到请求执行，会执行多次
    @Override
    public void doFilter(ServletRequest servletRequest, ServletResponse servletResponse, FilterChain filterChain) throws IOException, ServletException {
        log.info("拦截到了请求 ，放行前");
        //放行，放行之后，请求会继续执行，直到请求全部执行完，才会执行过滤器链中的下一个过滤器
        filterChain.doFilter(servletRequest,servletResponse);
        log.info("拦截到了请求 放行后。。。");
    }
    //初始化，web服务器启动时执行，只执行一次
    @Override
    public void init(FilterConfig filterConfig) throws ServletException {
        log.info("init初始化方法。。。");
        Filter.super.init(filterConfig);
    }
    //销毁，web服务器关闭时执行，只执行一次
    @Override
    public void destroy() {
        log.info("destroy销毁方法。。。");
        Filter.super.destroy();
    }
}
