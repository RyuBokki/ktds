package com.ktds.common;

import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.web.servlet.handler.HandlerInterceptorAdapter;

import com.nhncorp.lucy.security.xss.XssFilter;


public class XssInterceptor extends HandlerInterceptorAdapter {

	@Override
	public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler)
			throws Exception {
		
		Map<String, String[]> requestParams = request.getParameterMap();
		
		XssFilter filter = XssFilter.getInstance("lucy-xss-superset.xml");
		
		requestParams.entrySet().stream().forEach(entry -> {
			entry.getValue()[0] = filter.doFilter(entry.getValue()[0]);
			
			System.out.println(entry.getKey());
			System.out.println(entry.getValue()[0]);
			System.out.println("========================");
		});
		
		return true;
	}
	
}
