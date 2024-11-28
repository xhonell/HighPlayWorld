package com.xhonell.filter;

import javax.servlet.*;
import javax.servlet.annotation.WebFilter;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * <p>Project:test_01 - indexFilter
 * <p>POWER by xhonell on 2024-11-22 17:48
 * <p>description：
 * <p>idea：
 *
 * @author xhonell
 * @version 1.0
 * @since 1.8
 */
@WebFilter({"/page/*", "/servlet/index/*"})
public class IndexFilter implements Filter {
    @Override
    public void init(FilterConfig filterConfig) throws ServletException {

    }

    @Override
    public void doFilter(ServletRequest servletRequest, ServletResponse servletResponse, FilterChain filterChain) throws IOException, ServletException {
        servletRequest.setCharacterEncoding("utf-8");
        servletResponse.setContentType("application/json;charset=utf-8");
        Object player = ((HttpServletRequest) servletRequest).getSession().getAttribute("player");
        if (player == null) {
            ((HttpServletResponse)servletResponse).sendRedirect("../login.html");
        }else{
            filterChain.doFilter(servletRequest, servletResponse);
        }
    }

    @Override
    public void destroy() {

    }
}
