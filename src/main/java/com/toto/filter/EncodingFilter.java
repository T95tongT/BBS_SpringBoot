package com.toto.filter;

import javax.servlet.*;
import java.io.IOException;

/**
 * Created by Administrator on 2017/5/11 0011.
 */
public class EncodingFilter  implements Filter {
    String encoder=null;
    @Override
    public void init(FilterConfig filterConfig) throws ServletException {
        encoder=filterConfig.getInitParameter("encoding");
    }

    @Override
    public void doFilter(ServletRequest servletRequest, ServletResponse servletResponse, FilterChain filterChain) throws IOException, ServletException {
        servletResponse.setCharacterEncoding(encoder);
        filterChain.doFilter(servletRequest,servletResponse);
    }

    @Override
    public void destroy() {

    }
}
