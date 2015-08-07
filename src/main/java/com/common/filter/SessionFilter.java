package com.common.filter;
import java.io.IOException;
import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.springframework.web.filter.OncePerRequestFilter;

public class SessionFilter extends OncePerRequestFilter {
   
    @Override
    protected void doFilterInternal(HttpServletRequest request,
                                    HttpServletResponse response, FilterChain filterChain)
            throws ServletException, IOException {
        // 不过滤的uri

        String[] notFilter = new String[] { "/login.do", "/logout.do", "/tobind.do" , "/binding.do" ,"/noAccessRight.do"};



        // 请求的uri
        String uri = request.getRequestURI();
        String basePath=request.getContextPath();
        // uri中包含background时才进行过滤
        if (uri.indexOf("do") != -1) {
            // 是否过滤
            boolean doFilter = true;
            for (String s : notFilter) {
                if (uri.indexOf(s) != -1) {
                    // 如果uri中包含不过滤的uri，则不进行过滤
                    doFilter = false;
                    break;
                }
            }
            if (doFilter) {
                // 执行过滤
                // 从session中获取登录者实体
                Object obj = request.getSession().getAttribute("user");
                // 如果session中不存在登录者实体，则弹出框提示重新登录
                // 设置request和response的字符集，防止乱码
                request.setCharacterEncoding("UTF-8");
                response.setCharacterEncoding("UTF-8");
                if (obj==null) {
                    if(uri.startsWith("/wx/pt")){
                    	//重定向到绑定页
                    	
                    	response.sendRedirect(basePath+"/pt/tobind.do");
                    }
                    else{
                    	//重定向到管理员登录页
                    	response.sendRedirect(basePath+"/admin/login.do");
                    }
                } else {                
                	boolean hasAccess=false;
                	if(uri.startsWith("/wx/pt") && obj.equals("pt")){
                		hasAccess=true;
                	}
                    else if(uri.startsWith("/wx/admin") && obj.equals("admin")){
                		hasAccess=true;
                	}
                    else if(uri.startsWith("/wx/pt") && obj.equals("admin")){
                		hasAccess=true;
                	}
                    if(hasAccess){
                    	filterChain.doFilter(request, response);
                    }
                    else{
                    	//重定向到禁止访问页 
                    	request.getRequestDispatcher("/noAccessRight.jsp").forward(request, response);
                    }
                }
            } else {
                // 如果不执行过滤，则继续
                filterChain.doFilter(request, response);
            }
        } else {
            // 如果uri中不包含do，则继续
            filterChain.doFilter(request, response);
        }
    }
}