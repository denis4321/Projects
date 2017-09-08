/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.denys.prokopiuk.filter;

import java.io.IOException;
import java.io.PrintStream;
import java.io.PrintWriter;
import java.io.StringWriter;
import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.annotation.WebFilter;
import org.apache.log4j.Logger;

/**
 *
 * @author Администратор
 */
@WebFilter(filterName = "LoggingFilter", urlPatterns = {"/*"})
public class LoggingFilter implements Filter {    

    private Logger logger = Logger.getLogger("app-logger");
    
    private FilterConfig filterConfig;
    
    @Override
    public void init(FilterConfig fc) throws ServletException {
        this.filterConfig = fc;
    }
    
    @Override
    public void doFilter(ServletRequest sr, ServletResponse sr1, FilterChain fc) throws IOException, ServletException {
        try {
            logger.info("Before call");
            fc.doFilter(sr, sr1);
            logger.info("After call");
        } catch (Exception ex) {
            logger.error(ex.getMessage(), ex);
        }
    }
    
    @Override
    public void destroy() {
        
    }
    
}
