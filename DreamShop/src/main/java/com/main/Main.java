/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.main;

import com.convertor.Convertor;
import com.convertor.ConvertorImpl;
import com.dao.DAO;
import com.dao.DAOImpl;
import java.io.ByteArrayOutputStream;
import java.io.PrintStream;
import spark.ExceptionHandlerImpl;
import spark.Request;
import spark.Response;

/**
 *
 * @author Denis
 */
public class Main {

    public static void main(String[] args) {
        spark.Spark.port(8080);
        DAO dao = new DAOImpl();
        Convertor convertor = new ConvertorImpl();
        spark.Spark.get("items", (req, resp) -> convertor.convertToJSON("items", dao.getItemsInStock()));
        spark.Spark.get("customers", (req, resp) -> convertor.convertToJSON("customers", dao.getAllCustomes()));
        spark.Spark.get("address", (req, resp) -> {
            String customerName = req.queryParams("customerName");
            System.out.println("customerName=" + customerName);
            return convertor.convertToJSON(dao.getAddressOfCustomer(customerName));
        });
        spark.Spark.get("orders", (req, resp) -> convertor.convertToJSON("orders", dao.getAllOrders()));
        spark.Spark.get("ordersOfCustomer", (req, resp) -> convertor.convertToJSON("orders", dao.getOrdersOfCustomer(req.queryParams("customerName"))));
        spark.Spark.get("initData", (req, resp) -> {
            dao.initData();
            return "Data Initiated";
        });

        spark.Spark.exception(Exception.class, new ExceptionHandlerImpl(Exception.class) {
            @Override
            public void handle(Exception excptn, Request rqst, Response rspns) {
                try (ByteArrayOutputStream out = new ByteArrayOutputStream();
                        PrintStream ps = new PrintStream(out);) {
                    excptn.printStackTrace(ps);
                    String str = new String(out.toByteArray());
                    rspns.body(str);
                } catch (Exception ex) {
                    throw new RuntimeException(ex);
                }
            }
        });
    }

}
