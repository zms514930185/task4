package com.jnshu.uitl;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.web.servlet.handler.HandlerInterceptorAdapter;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.Arrays;
import java.util.Objects;

import static org.springframework.web.util.WebUtils.getCookie;

public class ApiInterceptor extends HandlerInterceptorAdapter {
    private final Logger logger = LogManager.getLogger(this.getClass());
    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        /*获取请求头里的token*/
        Cookie userTokenCookie = getCookie(request, "userToken");
        if(userTokenCookie==null){
            return false;
        }
        String userToken=Objects.requireNonNull(userTokenCookie).getValue();
        logger.info("拦截器获取到的Cookie:{}",userToken);
        /*解密token的值*/
        return true;
    }
        /*String authorization=request.getHeader("Authorization");
        if(authorization == null || ! authorization.startsWith("Bearer ")){
            this.setErrorResponse(response,"未携带token");
            return false;
        }
        String token=authorization.substring(7);
        try {
            request.setAttribute("user",Jwt.parseJwt(token));
        }catch(Exception e) {
            this.setErrorResponse(response,e.getMessage());
            return  false;
        }
       return  true;
    }
    protected void setErrorResponse(HttpServletResponse response,String message) throws IOException {
        response.setHeader("Content-type", "text/html;charset=UTF-8");
        response.setCharacterEncoding("UTF-8");
        response.getWriter().write(message);
        response.getWriter().flush();
        response.getWriter().close();
    }*/
}