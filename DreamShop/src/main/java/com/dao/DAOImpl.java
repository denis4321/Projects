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
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;
import javax.persistence.TypedQuery;

/**
 *
 * @author Denis
 */
public class DAOImpl implements DAO {

    private EntityManager em;
    

    public DAOImpl() {
        EntityManagerFactory factory = Persistence.createEntityManagerFactory("dream_shop_unit");
        em = factory.createEntityManager();
    }

    @Override
    public List<Item> getItemsInStock() {
        em.getTransaction().begin();
        TypedQuery<Item> query = em.createNamedQuery("itemsInStock", Item.class);
        List<Item> list = query.getResultList();
        em.getTransaction().commit();
        return list;
    }

    @Override
    public List<Customer> getAllCustomes() {
        em.getTransaction().begin();
        TypedQuery<Customer> query = em.createNamedQuery("customers", Customer.class);
        List<Customer> list = query.getResultList();
        em.getTransaction().commit();
        return list;
    }

    @Override
    public Address getAddressOfCustomer(String customerName) {
        em.getTransaction().begin();
        TypedQuery<Customer> query = em.createNamedQuery("getCustomerByName", Customer.class);
        query.setParameter("customerName", customerName);
        Customer c = query.getSingleResult();
        em.getTransaction().commit();
        return c.getAddress();
    }

    @Override
    public List<OrderData> getAllOrders() {
        em.getTransaction().begin();
        TypedQuery<OrderData> query = em.createNamedQuery("orders", OrderData.class);
        List<OrderData> list = query.getResultList();
        em.getTransaction().commit();
        return list;
    }

    @Override
    public List<OrderData> getOrdersOfCustomer(String customerName) {
        em.getTransaction().begin();
        TypedQuery<OrderData> query = em.createNamedQuery("ordersByCustomerName", OrderData.class);
        query.setParameter("customerName", customerName);
        List<OrderData> list = query.getResultList();
        em.getTransaction().commit();
        return list;
    }

    @Override
    public void createOrder(OrderData order) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public void initData() {
        em.getTransaction().begin();

        List<Item> items = new ArrayList();
        for (int i = 0; i < INIT_DATA_COUNT; i++) {
            Item item = new Item();
            item.setName("Item"+i);
            item.setPrice(i*10);            
            em.persist(item);
            items.add(item);
        }
        
        List<Customer> customers = new ArrayList();
        for (int i = 0; i < INIT_DATA_COUNT; i++) {
            Customer customer = new Customer();
            customer.setName("Customer" + i);

            Address address = new Address();
            address.setCountry("Country" + i);
            address.setCity("city" + i);
            address.setStreet("Street" + i);
            address.setHouse("house" + i);
            address.setAppartment("appartment" + i);
            customer.setAddress(address);
            //em.persist(address);
            //em.persist(customer);
            customers.add(customer);
            
            OrderData order = new OrderData();
            order.setCustomer(customer);
           // order.setName("Order"+i);
            order.setOrderTime(new Timestamp(System.currentTimeMillis()));
            //Customer c=em.find(Customer.class, i+1);
            //System.out.println("c="+c);
           // order.setCustomer(customer);     
            //List<Customer> list = new ArrayList();
            //list.add(customers.get(i));
            order.setItems(Arrays.asList(items.get(i)));
            em.persist(address);
            em.persist(customer);
            em.persist(order);
            
           // em.refresh(customer);
            
         //   em.persist(order);
            
        }
        
        
        
        /*
        for (int i = 0; i < INIT_DATA_COUNT; i++) {
            Order order = new Order();            
            Customer c=em.find(Customer.class, i+1);
            System.out.println("c="+c);
            order.setCustomer(c);     
            //List<Customer> list = new ArrayList();
            //list.add(customers.get(i));
            //order.setItems(Arrays.asList(items.get(i)));
            em.persist(order);
        }*/
        
        em.getTransaction().commit();
    }

}
