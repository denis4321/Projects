/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package com.conarhco.terminator.web.mobile;

import java.io.IOException;
import java.io.PrintWriter;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.annotation.Resource;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.sql.DataSource;

/**
 *
 * @author Конарх
 */
public class GetWorkoutServlet extends HttpServlet {
    @Resource(name="jdbc/arny")
    private DataSource db;

    public GetWorkoutServlet() {
    }

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
    throws ServletException, IOException {
        response.setContentType("text/plain; charset=cp1251");
        PrintWriter out = response.getWriter();
        String user = request.getParameter("login");
        out.print(getWorkoutName(user));
    }


    private String getWorkoutName(String author){
        try {
            Connection con = getConnection();
            String query = "SELECT workouts.id, workouts.name, workouts.number FROM workouts JOIN users ON (users.id=workouts.usr AND users.login=?) ORDER BY workouts.number";
            java.sql.PreparedStatement ps = con.prepareStatement(query);
            ps.setString(1, author);
            ResultSet rs = ps.executeQuery();
            StringBuilder sb = new StringBuilder();
            while (rs.next()) {
                sb.append(rs.getInt("id")+"=" +rs.getInt("number")+"."+ rs.getString("name")  +  "\n");
            }
            return sb.toString();
        } catch (SQLException ex) {
            return ex.getMessage();
        }
        
    }



    private Connection getConnection() throws SQLException{
        return db.getConnection();
        /*
        try {
            DriverManager.registerDriver(new com.mysql.jdbc.Driver());
            return DriverManager.getConnection("jdbc:mysql://127.0.0.1/terminator", "root", "111111");
        } catch (SQLException ex) {
            //Logger.getLogger(DataModel.class.getName()).log(Level.SEVERE, null, ex);
        }
        throw new IllegalStateException();
        */
    }

}


