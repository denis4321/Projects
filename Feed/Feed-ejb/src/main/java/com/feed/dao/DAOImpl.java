/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.feed.dao;

import java.sql.CallableStatement;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import oracle.sql.ARRAY;
import oracle.sql.ArrayDescriptor;
import oracle.sql.STRUCT;
import oracle.sql.StructDescriptor;

public class DAOImpl implements DAO {
    
    @Override
    public void save(List<AppData> dataList, Connection con) throws SQLException {
        List<List<Object>> data = new ArrayList();
        for (AppData item : dataList) {
            data.add(item.translate());
        }
        call(data, "MY_USER.my_bulk_type".toUpperCase(), "MY_USER.my_type".toUpperCase(), "{call MY_USER.my_pkg.my_sp(?)}", con);
    }
 

    private void call(List<List<Object>> list, String oracleBulkTypeName, String oracleTypeName, String sql, Connection con) throws SQLException {        
            ARRAY array = convert(list, oracleBulkTypeName, con, oracleTypeName);
            CallableStatement cs = con.prepareCall(sql);
            cs.setObject(1, array);
            cs.execute();        
    }

    private ARRAY convert(List<List<Object>> list, String oracleBulkTypeName, Connection c, String oracleTypeName) throws SQLException {
        ArrayDescriptor arrDesc = ArrayDescriptor.createDescriptor(oracleBulkTypeName, c);
        Object[] data = new Object[list.size()];
        for (int i = 0; i < list.size(); i++) {
            data[i] = convert(list.get(i), oracleTypeName, c);
        }
        return new ARRAY(arrDesc, c, data);
    }

    private STRUCT convert(List<Object> data, String oracleTypeName, Connection c) throws SQLException {
        StructDescriptor structdesc = StructDescriptor.createDescriptor(oracleTypeName, c);
        return new STRUCT(structdesc, c, data.toArray());
    }

}
