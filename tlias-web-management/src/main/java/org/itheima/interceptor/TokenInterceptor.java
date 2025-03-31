package org.itheima.interceptor;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.extern.slf4j.Slf4j;
import org.itheima.utils.JwtUtil;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.HandlerInterceptor;

/**
 * 令牌校验拦截器
 */
@Slf4j
@Component
public class TokenInterceptor implements HandlerInterceptor {
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
//        //1.获取请求路径
//        String requestURI = request.getRequestURI();// /employee/login
//        //2.判断请求路径是否是登录请求，如果包含/login，说明是登录操作，放行
//        if (requestURI.contains("/login")) {
//            log.info("登录请求，放行");
//            return true;
//        }
        //1.获取请求头中的token
        String token = request.getHeader("token");
        //2.判断token是否为空,不存在说明用户还没登录，返沪错误信息，相应401
        if (token == null || token.isEmpty()) {
            //3.如果为空，返回错误信息
            log.info("请求头中没有token令牌,相应401");
            response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
            return false;
        }
        //4.token存在，则解析token令牌，解析失败-》返回错误信息（相应401状态码）
        try {
            JwtUtil.parseJwt(token);
        } catch (Exception e) {
            log.info("解析token令牌失败，相应401");
            response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
            return false;
        }
        //6.放行，校验通过
        log.info("放行，令牌合法");
        return true;
    }
}
