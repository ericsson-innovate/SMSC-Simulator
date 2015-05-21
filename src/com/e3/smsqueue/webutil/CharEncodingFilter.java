package com.e3.smsqueue.webutil;

import java.io.IOException;

import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;

public class CharEncodingFilter implements Filter {
    private String charSet;

    public void init(FilterConfig config) throws ServletException {
        charSet = config.getInitParameter("encoding");
        if (charSet == null && charSet.length() < 1) {
            charSet = "UTF-8";
        }
    }

    public void doFilter(ServletRequest request, ServletResponse response, FilterChain filter) 
        throws IOException,ServletException {
        request.setCharacterEncoding(this.getCharSet());
        response.setCharacterEncoding(this.getCharSet());
        filter.doFilter(request, response);
    }

    public void destroy() {
        this.setCharSet(null);
    }

    public void setCharSet(String charSet) {
        this.charSet = charSet;
    }

    public String getCharSet() {
        return (this.charSet);
    }
}