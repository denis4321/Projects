/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.feed.reactive;

import com.feed.startup.Constants;
import java.sql.CallableStatement;
import java.sql.Connection;
import javax.annotation.Resource;
import javax.ejb.Asynchronous;
import javax.ejb.Singleton;
import javax.ejb.TransactionManagement;
import javax.ejb.TransactionManagementType;
import javax.enterprise.event.Observes;
import javax.sql.DataSource;
import org.apache.log4j.Logger;

/**
 *
 * @author prokden
 */
@Singleton
@TransactionManagement(TransactionManagementType.BEAN)
public class EventListener {

    private final Logger LOG = Logger.getLogger(Constants.LOGGER_NAME);
    @Resource(name = Constants.DESTINATION_STAGING_DATASOURCE_NAME)
    private DataSource stagingDataSource;

    @Asynchronous
    public void onMessage(@Observes String message) {
        LOG.info(message);
    }

    @Asynchronous
    public void onException(@Observes Exception ex) {
        try {
            LOG.error(ex.getMessage(), ex);
            try (Connection c = stagingDataSource.getConnection();) {
                c.setAutoCommit(false);
                CallableStatement cs = c.prepareCall("{call MY_USER.my_pkg.set_context(?)}");
                cs.setString(1, "feed".toUpperCase());
                cs.execute();
                cs = c.prepareCall("{call MY_USER.my_pkg.log(?,?,?)}");
                cs.setString(1, "EXCEPTION");
                cs.setString(2, ex.getMessage());
                cs.setObject(3, null);
                cs.execute();
                c.commit();
            }
        } catch (Exception ex1) {
            throw new RuntimeException(ex1);
        }
    }

}
