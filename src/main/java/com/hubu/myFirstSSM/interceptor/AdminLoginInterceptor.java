package com.hubu.myFirstSSM.interceptor;

import com.hubu.myFirstSSM.pojo.Administrator;
import org.apache.commons.lang.StringUtils;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.handler.HandlerInterceptorAdapter;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.util.Arrays;

public class AdminLoginInterceptor extends HandlerInterceptorAdapter {
    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        HttpSession session = request.getSession();
        String contextPath  = session.getServletContext().getContextPath();
        String noNeedAuthPath[] = {
                "login"
        };

        String uri = request.getRequestURI();
        uri = StringUtils.remove(uri,contextPath);
        if(uri.startsWith("/admin_")){
            String method = StringUtils.substringAfterLast(uri,"/admin_");
            if(!Arrays.asList(noNeedAuthPath).contains(method)) {
                Administrator admin = (Administrator) session.getAttribute("admin");
                if (null == admin) {
                    response.sendRedirect("admin");
                    return false;
                }
            }
        }
        return true;
    }

    @Override
    public void postHandle(HttpServletRequest request, HttpServletResponse response, Object handler, ModelAndView modelAndView) throws Exception {

    }

    @Override
    public void afterCompletion(HttpServletRequest request, HttpServletResponse response, Object handler, Exception ex) throws Exception {

    }
}
