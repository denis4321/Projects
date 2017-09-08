/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.feed.startup.timer.dao;

import com.feed.startup.Constants;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import javax.annotation.Resource;
import javax.annotation.security.DeclareRoles;
import javax.annotation.security.RunAs;
import javax.ejb.Stateless;
import javax.ejb.TransactionManagement;
import javax.ejb.TransactionManagementType;
import javax.enterprise.event.Event;
import javax.inject.Inject;
import javax.sql.DataSource;

/**
 *
 * @author prokden
 */
@Stateless
@DeclareRoles({"role"})
@RunAs(value = "role")
@TransactionManagement(TransactionManagementType.BEAN)
public class TimerDAOImpl implements TimerDAO {

    @Resource(name = Constants.DESTINATION_DATASOURCE_NAME)
    private DataSource ds;
    
    @Override
    public FeedConfig getTimerConfig() {
        FeedConfig feedConfig = new FeedConfig();
        try (Connection c = ds.getConnection();) {
            PreparedStatement ps = c.prepareStatement("SELECT * FROM MY_USER.MY_CRON_CONFIG WHERE FEED_APP_NAME=?");
            ps.setString(1, Constants.FEED_APP_NAME.toUpperCase());
            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                feedConfig.setDayOfMonth(rs.getString("day_of_month"));
                feedConfig.setDayOfWeek(rs.getString("day_of_week"));
                feedConfig.setMonth(rs.getString("month"));
                feedConfig.setHour(rs.getString("hour"));
                feedConfig.setMinute(rs.getString("minute"));
                feedConfig.setSecond(rs.getString("second"));
                feedConfig.setTargetNodeName(rs.getString("target_node_name"));
                feedConfig.setEnabled(rs.getBoolean("enabled"));
            }
            return feedConfig;
        } catch (Exception ex) {
            throw new RuntimeException(ex);
        }
    }

}
