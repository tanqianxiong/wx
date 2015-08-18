package com.common.filter;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.servlet.handler.HandlerInterceptorAdapter;
import org.springframework.web.util.UrlPathHelper;

import com.entity.BoundInfo;
import com.service.BoundInfoService;

public class BaseInterceptor extends HandlerInterceptorAdapter {


	@Autowired
	public BoundInfoService boundInfoService;
	

	private UrlPathHelper urlPathHelper = new UrlPathHelper();

	private List<String> excludes;

	public void setExcludes(List<String> excludes) {
		this.excludes = excludes;
	}
	
	/**
	 * 在业务处理器处理请求之前被调用
	 */
	@Override
	public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler)
			throws Exception {

		// 获取url
		String url = urlPathHelper.getLookupPathForRequest(request);
		// 控制权限
		if (authorization(url)) {
			// 请求的uri
			String uri = request.getRequestURI();
			String basePath = request.getContextPath();
			Object obj = null;
			String openId = "";
			if (uri.startsWith("/wx/pt")) {
				// 从数据库获取绑定信息
				// String code=request.getParameter("code");
				openId = request.getParameter("openId");// WeChatUtil.httpGetOpenId(code);
				if(openId!=null){
					Map<String, Object> props = new HashMap<String, Object>();
					props.put("openId", openId);
					BoundInfo bi = this.boundInfoService.getByProperties(props);
					obj = bi;
				}
			} else if (uri.startsWith("/wx/admin")) {
				obj = request.getSession().getAttribute("user");
			}
			// 如果session中不存在登录者实体，则弹出框提示重新登录
			// 设置request和response的字符集，防止乱码
			request.setCharacterEncoding("UTF-8");
			response.setCharacterEncoding("UTF-8");
			if (obj == null) {
				if (uri.startsWith("/wx/pt") && openId!=null) {
					// 重定向到绑定页
					request.setAttribute("openId", openId);
					//System.out.println("before="+openId);
					response.sendRedirect(basePath + "/pt/tobind.do?openId="+openId);	
					return false;
				} 
				else if (uri.startsWith("/wx/pt") && openId==null) {
					// 重定向到只供手机访问页
					request.getRequestDispatcher("/onlyWep.jsp").forward(request, response);
					return false;
				}
				else if(uri.startsWith("/wx/admin")){
					// 重定向到管理员登录页
					response.sendRedirect(basePath + "/admin/login.do");
					return false;
				}
			} else {
				boolean hasAccess = false;// {false:没有权限访问，true：正常访问}
				if (uri.startsWith("/wx/pt") && obj.getClass().getName().equals("com.entity.BoundInfo")) {
					request.setAttribute("openId", openId);
					hasAccess = true;
				} else if (uri.startsWith("/wx/admin") && obj.equals("admin")) {
					hasAccess = true;
				}
				if(!hasAccess){
					// 重定向到禁止访问页
					request.getRequestDispatcher("/noAccessRight.jsp").forward(request, response);
					return false;
				}
			}
		}
		if (request.getRequestURI().startsWith("/wx/pt")) {
			//System.out.println(request.getParameter("openId"));
			request.setAttribute("openId", request.getParameter("openId"));
		}
		return true;
	}
	private Boolean authorization(String url) {
		// 默认控制权限
		boolean result = true;

		// 判断排除
		for (String exclude : excludes) {
			Pattern p = Pattern.compile(exclude);
			Matcher m = p.matcher(url);
			if (m.find()) {
				result = false;
				break;
			}
		}

		// 返回
		return result;
	}
}
