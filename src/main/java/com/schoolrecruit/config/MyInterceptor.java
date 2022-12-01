package com.schoolrecruit.config;

import org.springframework.web.servlet.HandlerInterceptor;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public class MyInterceptor implements HandlerInterceptor {
    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        Object object = request.getSession().getAttribute("USER");
        try {
            if(object==null){
                System.err.println(object);
                request.getRequestDispatcher("/page/wdl").forward(request,response);
                //response.sendRedirect("/page/loginErr");
                return false;
            }
        }catch (Exception e){
            throw e;
        }
        return true;
    }
}
