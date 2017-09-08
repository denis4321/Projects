/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.feed.processor;

import com.feed.dao.DAO;
import com.feed.dao.AppData;
import com.feed.startup.Constants;
import java.sql.CallableStatement;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;
import javax.annotation.Resource;
import javax.enterprise.event.Event;
import javax.inject.Inject;
import javax.sql.DataSource;

public class ProcessorImpl implements Processor {

    @Inject
    private Event<String> textEvent;
    @Resource(name = Constants.DESTINATION_STAGING_DATASOURCE_NAME)
    private DataSource stagingDataSource;
    @Resource(name = Constants.DESTINATION_DATASOURCE_NAME)
    private DataSource dimensionDataSource;
    @Resource(name = Constants.SOURCE_DATASOURCE_NAME)
    private DataSource sourceDataSource;
    @Inject
    private ProcessorUtils gssProcessorUtils;
    @Inject
    private Event<Exception> exceptionEvent;
    @Inject
    private DAO dao;

    @Override
    public boolean processFeedStaging() {
        if (gssProcessorUtils.needToRunOnServer()) {
            try {
                //markStartCall
                try (Connection c = stagingDataSource.getConnection()) {
                    c.setAutoCommit(false);
                    CallableStatement cs = c.prepareCall("{call MY_USER.my_pkg.set_context(?)}");
                    cs.setString(1, "feed".toUpperCase());
                    cs.execute();
                    cs = c.prepareCall("{call MY_USER.my_pkg.log(?,?)}");
                    cs.setString(1, "INFO");
                    cs.setString(2, String.format("%s - START", "FEED".toUpperCase()));
                    cs.execute();
                    c.commit();
                }

                //log
                int count = 0;
                {
                    try (Connection c = sourceDataSource.getConnection();
                            Connection con = stagingDataSource.getConnection();) {
                        c.setAutoCommit(false);
                        con.setAutoCommit(false);
                        PreparedStatement ps = c.prepareStatement("SELECT count(*) FROM my_table");
                        ResultSet rs = ps.executeQuery();
                        while (rs.next()) {
                            count = rs.getInt(1);
                        }
                        c.commit();
                        CallableStatement cs = con.prepareCall("{call MY_USER.my_pkg.set_context(?)}");
                        cs.setString(1, "feed".toUpperCase());
                        cs.execute();
                        cs = con.prepareCall("{call MY_USER.my_pkg.log(?,?,?)}");
                        cs.setString(1, count > 0 ? "INFO" : "WARNING");
                        cs.setString(2, String.format("%s - PREFETCH", "feed".toUpperCase()));
                        cs.setInt(3, count);
                        cs.execute();
                        con.commit();
                    }
                }

                if (count != 0) {
                    //cleanup and process
                    {
                        try (Connection con = stagingDataSource.getConnection();) {
                            con.setAutoCommit(false);
                            con.prepareCall("{call MY_USER.my_pkg.clean()}").execute();

                            {
                                List<AppData> dataList = new ArrayList();
                                try (Connection c = sourceDataSource.getConnection();) {
                                    c.setAutoCommit(false);
                                    PreparedStatement ps = c.prepareStatement("SELECT * FROM mytable1");
                                    ResultSet rs = ps.executeQuery();
                                    textEvent.fire("Before iteration");
                                    int index = 0;
                                    while (rs.next()) {
                                        try {
                                            index++;
                                            String profileUser = rs.getString("PROFILE_USER");
                                            String profileType = rs.getString("PROFILE_TYPE");
                                            String assinedPerson = rs.getString("ASSIGNED_PERSON");
                                            String lastName = rs.getString("LAST_NAME");
                                            String firstName = rs.getString("FIRST_NAME");
                                            AppData data = new AppData();
                                            data.setProfileUser(profileUser);
                                            data.setProfileType(profileType);
                                            data.setAssinedPerson(assinedPerson);
                                            data.setLastName(lastName);
                                            data.setFirstName(firstName);
                                            dataList.add(data);
                                            if (dataList.size() >= Constants.BULK_AMOUNT) {
                                                dao.save(dataList, con);
                                                dataList.clear();
                                            }
                                        } catch (Exception ex) {
                                            exceptionEvent.fire(ex);
                                        }
                                    }
                                    if (!dataList.isEmpty()) {
                                        dao.save(dataList, con);
                                        dataList.clear();
                                    }
                                    textEvent.fire(String.format("@After iteration.Count=%s", index));
                                    c.commit();
                                }

                            }
                            con.commit();
                        }
                    }
                    try (Connection c = dimensionDataSource.getConnection();) {
                        textEvent.fire("@Before dimension");
                        c.setAutoCommit(false);
                        c.prepareCall("{call MY_USER.PKG_DIM_GSS.update()}").execute();
                        c.commit();
                        textEvent.fire("@After dimension");
                    }
                }
            } catch (Exception ex) {
                exceptionEvent.fire(ex);
            }
        }
        return false;
    }

}
