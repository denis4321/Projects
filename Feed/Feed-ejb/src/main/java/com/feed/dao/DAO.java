/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.feed.dao;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.List;

/**
 *
 */
public interface DAO {

    public void save(List<AppData> dataList, Connection con) throws SQLException;

}
