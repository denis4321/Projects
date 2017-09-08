/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.dao;

import com.model.Address;
import com.model.Customer;
import com.model.Item;
import com.model.OrderData;
import java.util.List;

/**
 *
 * @author Denis
 */
public interface DAO {
    public int INIT_DATA_COUNT = 10;
    
    public List<Item> getItemsInStock();
    
    public List<Customer> getAllCustomes();
    
    public Address getAddressOfCustomer(String customerName);
    
    public List<OrderData> getAllOrders();
    
    public List<OrderData> getOrdersOfCustomer(String customerName);
    
    public void createOrder(OrderData order);   
    
    public void initData();
}
