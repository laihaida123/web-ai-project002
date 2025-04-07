package org.itheima.filter;

import io.jsonwebtoken.Claims;
import jakarta.servlet.*;
import jakarta.servlet.annotation.WebFilter;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.extern.slf4j.Slf4j;
import org.itheima.utils.CurrentHolder;
import org.itheima.utils.JwtUtil;

import javax.crypto.Cipher;
import java.io.IOException;
@Slf4j
//@WebFilter(urlPatterns = "/*")//
public class TokenFilter implements Filter {
    @Override
    public void doFilter(ServletRequest servletRequest, ServletResponse servletResponse, FilterChain filterChain) throws IOException, ServletException {
        HttpServletRequest request = (HttpServletRequest) servletRequest;
        HttpServletResponse response = (HttpServletResponse) servletResponse;
        //1.获取请求路径
        String requestURI = request.getRequestURI();// /employee/login

        //2.判断请求路径是否是登录请求，如果是/login放行
        if(requestURI.contains("/login")){
            log.info("登录请求，放行");
            filterChain.doFilter(request,response);
            return;
        }

        //3.获取请求头中的token令牌
        String token = request.getHeader("token");
        //4.判断token是否存在，不存在则返回错误信息（相应408状态码）
        if (token==null || token.isEmpty()){
            log.info("请求头中没有token令牌,相应401");
            response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
            return;
        }
        //5.如果存在，则解析token令牌，解析失败-》返回错误信息（相应401状态码）
        try {
            Claims claims = JwtUtil.parseJwt(token);
            System.out.println("111111111111111111111111");
            Integer empid = Integer.valueOf(claims.get("id").toString());
            CurrentHolder.setCurrentId(empid);//存入
            log.info("解析token令牌成功，当前用户id为：{},将其存入thredlocal",empid);
        } catch (Exception e) {
            System.out.println("111111111111111111111111");
            log.info("解析token令牌失败，相应401");
            response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
            return ;
        }
        //6.放行，校验通过
        log.info("放行，令牌合法");
        filterChain.doFilter(request, response);

        //7.删除threadlocal的数据
        CurrentHolder.remove();
    }
}
