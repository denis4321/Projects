/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.convertor;

import com.model.Address;
import com.model.Customer;
import com.model.Item;
import com.model.OrderData;
import java.sql.Timestamp;
import java.util.Arrays;
import java.util.List;
import static org.junit.Assert.assertEquals;
import org.junit.Test;

/**
 *
 * @author Denis
 */
public class ConvertorTest {

    @Test
    public void convertItemTest() {
        Convertor<Item> convertor = new ConvertorImpl<Item>();
        Item item = new Item();
        item.setId(1);
        item.setName("Name1");
        item.setPrice(10);
        String res = convertor.convertToJSON(item);
        assertEquals("{\"price\":10,\"name\":\"Name1\",\"id\":1}", res);
    }

    @Test
    public void convertBulkItemTest() {
        Convertor<Item> convertor = new ConvertorImpl<Item>();
        Item item = new Item();
        item.setId(1);
        item.setName("Name1");
        item.setPrice(10);
        Item item1 = new Item();
        item1.setId(2);
        item1.setName("Name2");
        item1.setPrice(20);
        List<Item> items = Arrays.asList(item, item1);
        String res = convertor.convertToJSON("items", items);
        assertEquals("{\"items\":[{\"price\":10,\"name\":\"Name1\",\"id\":1},{\"price\":20,\"name\":\"Name2\",\"id\":2}]}", res);
    }

    @Test
    public void convertBulkAddressTest() {
        Convertor<Address> convertor = new ConvertorImpl<Address>();

        Address address = new Address();
        address.setId(1);
        address.setCountry("c1");
        address.setCity("city1");
        address.setStreet("str1");
        address.setHouse("h1");
        address.setAppartment("ap1");

        Address address1 = new Address();
        address1.setId(2);
        address1.setCountry("c2");
        address1.setCity("city2");
        address1.setStreet("str2");
        address1.setHouse("h2");
        address1.setAppartment("ap2");

        List<Address> list = Arrays.asList(address, address1);

        String res = convertor.convertToJSON("addresses", list);
        assertEquals("{\"addresses\":[{\"country\":\"c1\",\"appartment\":\"ap1\",\"city\":\"city1\",\"street\":\"str1\",\"id\":1,\"house\":\"h1\"},{\"country\":\"c2\",\"appartment\":\"ap2\",\"city\":\"city2\",\"street\":\"str2\",\"id\":2,\"house\":\"h2\"}]}", res);
    }

    @Test
    public void convertAddressTest() {
        Convertor<Address> convertor = new ConvertorImpl<Address>();
        Address address = new Address();
        address.setId(1);
        address.setCountry("c1");
        address.setCity("city1");
        address.setStreet("str1");
        address.setHouse("h1");
        address.setAppartment("ap1");
        String res = convertor.convertToJSON(address);
        assertEquals("{\"country\":\"c1\",\"appartment\":\"ap1\",\"city\":\"city1\",\"street\":\"str1\",\"id\":1,\"house\":\"h1\"}", res);
    }

    @Test
    public void convertCustomerTest() {
        Convertor<Customer> convertor = new ConvertorImpl<Customer>();
        Customer customer = new Customer();
        Address address = new Address();
        address.setId(1);
        address.setCountry("c1");
        address.setCity("city1");
        address.setStreet("str1");
        address.setHouse("h1");
        address.setAppartment("ap1");
        customer.setName("Customer1");
        customer.setId(1);
        customer.setAddress(address);
        String res = convertor.convertToJSON(customer);

        assertEquals("{\"address\":{\"country\":\"c1\",\"appartment\":\"ap1\",\"city\":\"city1\",\"street\":\"str1\",\"id\":1,\"house\":\"h1\"},\"name\":\"Customer1\",\"id\":1}", res);
    }

    @Test
    public void convertBulkCustomerTest() {
        Convertor<Customer> convertor = new ConvertorImpl<Customer>();

        Address address = new Address();
        address.setId(1);
        address.setCountry("c1");
        address.setCity("city1");
        address.setStreet("str1");
        address.setHouse("h1");
        address.setAppartment("ap1");

        Customer customer = new Customer();
        customer.setName("Customer1");
        customer.setId(1);
        customer.setAddress(address);

        Customer customer1 = new Customer();
        customer1.setName("Customer2");
        customer1.setId(2);
        customer1.setAddress(address);

        List<Customer> customers = Arrays.asList(customer, customer1);
        String res = convertor.convertToJSON("customers", customers);
        assertEquals("{\"customers\":[{\"address\":{\"country\":\"c1\",\"appartment\":\"ap1\",\"city\":\"city1\",\"street\":\"str1\",\"id\":1,\"house\":\"h1\"},\"name\":\"Customer1\",\"id\":1},{\"address\":{\"country\":\"c1\",\"appartment\":\"ap1\",\"city\":\"city1\",\"street\":\"str1\",\"id\":1,\"house\":\"h1\"},\"name\":\"Customer2\",\"id\":2}]}", res);
    }

    @Test
    public void convertOredDataTest() {
        Convertor<OrderData> convertor = new ConvertorImpl<OrderData>();

        Customer customer = new Customer();
        Address address = new Address();
        address.setId(1);
        address.setCountry("c1");
        address.setCity("city1");
        address.setStreet("str1");
        address.setHouse("h1");
        address.setAppartment("ap1");
        customer.setName("Customer1");
        customer.setId(1);
        customer.setAddress(address);

        Item item = new Item();
        item.setId(1);
        item.setName("Name1");
        item.setPrice(10);
        Item item1 = new Item();
        item1.setId(2);
        item1.setName("Name2");
        item1.setPrice(20);
        List<Item> items = Arrays.asList(item, item1);

        OrderData orderData = new OrderData();
        orderData.setCustomer(customer);        
        orderData.setId(1);
        orderData.setItems(items);

        String res = convertor.convertToJSON(orderData);
        assertEquals("{\"id\":1,\"items\":[{\"price\":10,\"name\":\"Name1\",\"id\":1},{\"price\":20,\"name\":\"Name2\",\"id\":2}],\"customer\":{\"address\":{\"country\":\"c1\",\"appartment\":\"ap1\",\"city\":\"city1\",\"street\":\"str1\",\"id\":1,\"house\":\"h1\"},\"name\":\"Customer1\",\"id\":1}}", res);
    }

}
