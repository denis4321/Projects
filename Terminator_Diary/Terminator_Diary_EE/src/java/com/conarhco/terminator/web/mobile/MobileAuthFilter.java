/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package com.conarhco.terminator.web.mobile;

import java.io.IOException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import javax.annotation.Resource;
import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.sql.DataSource;

/**
 *
 * @author Конарх
 */
public class MobileAuthFilter implements Filter {
    private FilterConfig filterConfig = null;
    @Resource(name="jdbc/arny")
    private DataSource db;

    public MobileAuthFilter() {
    } 

    public void doFilter(ServletRequest request, ServletResponse response,
                         FilterChain chain) throws IOException, ServletException {
        System.out.println("mobile filter called");
        HttpServletRequest req = (HttpServletRequest)request;
        String login = req.getParameter("login");
        String pass = req.getParameter("pass");
        System.out.println("Calling user with ("+login+", "+pass+")");
        try {
            boolean auth = authorize(login, pass);
            if (auth) {
                System.out.println("user authorized with ("+login+", "+pass+"), calling servlet " + req.getRequestURI());
                chain.doFilter(request, response);
                System.out.println("user authorized, returning from servlet " + req.getRequestURI());
                return;
            }
        } catch (SQLException ex) {
            this.getFilterConfig().getServletContext().log("SQL error", ex);
        }
        ((HttpServletResponse)response).sendError(HttpServletResponse.SC_FORBIDDEN);
    }

    private boolean authorize(String login, String pass) throws SQLException{
        Connection conn = db.getConnection();
        try{
            PreparedStatement st = conn.prepareStatement("SELECT ID FROM users WHERE login=? AND pass=MD5(?)");
            st.setString(1, login);
            st.setString(2, pass);
            ResultSet res = st.executeQuery();
            return res.next();
        } finally{
            conn.close();
        }
    }
    
    /**
     * Return the filter configuration object for this filter.
     */
    public FilterConfig getFilterConfig() {
	return (this.filterConfig);
    }

    /**
     * Set the filter configuration object for this filter.
     *
     * @param filterConfig The filter configuration object
     */
    public void setFilterConfig(FilterConfig filterConfig) {
	this.filterConfig = filterConfig;
    }

    /**
     * Destroy method for this filter 
     */
    public void destroy() { 
    }

    /**
     * Init method for this filter 
     */
    public void init(FilterConfig filterConfig) { 
	this.filterConfig = filterConfig;
    }

}
