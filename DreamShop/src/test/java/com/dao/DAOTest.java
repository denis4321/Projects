/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.dao;

import com.model.Address;
import com.model.OrderData;
import java.util.List;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;
import javax.persistence.TypedQuery;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;

/**
 *
 * @author Denis
 */
public class DAOTest {

    private static DAO dao;
    private static EntityManager em;

    @BeforeClass
    public static void setUp() {
        dao = new DAOImpl();
        dao.initData();
        EntityManagerFactory factory = Persistence.createEntityManagerFactory("dream_shop_unit");
        em = factory.createEntityManager();
    }

    @Test
    public void initDataTest() {
        em.getTransaction().begin();
        long countOfCustomer = em.createQuery("SELECT COUNT(c) FROM Customer c", Long.class).getSingleResult();
        long countOfAddress = em.createQuery("SELECT COUNT(a) FROM Address a", Long.class).getSingleResult();
        long countOfItem = em.createQuery("SELECT COUNT(i) FROM Item i", Long.class).getSingleResult();
        long countOfOrdeerData = em.createQuery("SELECT COUNT(orderData) FROM OrderData orderData", Long.class).getSingleResult();
        em.getTransaction().commit();
        assertTrue("customer.count=" + countOfCustomer, countOfCustomer == DAO.INIT_DATA_COUNT);
        assertTrue("address.count=" + countOfAddress, countOfAddress == DAO.INIT_DATA_COUNT);
        assertTrue("item.count=" + countOfItem, countOfItem == DAO.INIT_DATA_COUNT);
        assertTrue("orderData.count=" + countOfOrdeerData, countOfOrdeerData == DAO.INIT_DATA_COUNT);
    }

    @Test
    public void getItemsInStockTest() {
        em.getTransaction().begin();
        long countOfItem = em.createQuery("SELECT COUNT(i) FROM Item i", Long.class).getSingleResult();
        em.getTransaction().commit();
        assertTrue(dao.getItemsInStock().size() == countOfItem);
    }

    @Test
    public void getAllCustomesTest() {
        em.getTransaction().begin();
        long countOfCustomer = em.createQuery("SELECT COUNT(c) FROM Customer c", Long.class).getSingleResult();
        em.getTransaction().commit();
        assertTrue(dao.getAllCustomes().size() == countOfCustomer);
    }

    @Test
    public void getAddressOfCustomer() {
        String customerName = "Customer0";
        Address actualAddress = dao.getAddressOfCustomer(customerName);
        assertNotNull(actualAddress);
        em.getTransaction().begin();
        TypedQuery<Integer> query = em.createQuery("SELECT c.address.id FROM Customer c WHERE c.name=:customerName", Integer.class);
        query.setParameter("customerName", customerName);
        int addressId = query.getSingleResult();
        Address expectedAddress = em.find(Address.class, addressId);
        em.getTransaction().commit();
        assertNotNull(expectedAddress);
        assertNotNull(expectedAddress.getAppartment());
        assertTrue(actualAddress.getAppartment().equals(expectedAddress.getAppartment()));
    }

    @Test
    public void getAllOrdersTest() {
        em.getTransaction().begin();
        long countOfOrderData = em.createQuery("SELECT COUNT(orderData) FROM OrderData orderData", Long.class).getSingleResult();
        em.getTransaction().commit();
        assertTrue(dao.getAllOrders().size() == countOfOrderData);
    }

    @Test
    public void ordersOfCustomerTest() {
        String customerName = "Customer0";
        em.getTransaction().begin();
        TypedQuery<OrderData> typedQuery = em.createQuery("SELECT od FROM OrderData od, Customer c WHERE od.customer=c AND c.name=:customerName", OrderData.class);
        typedQuery.setParameter("customerName", customerName);
        List<OrderData> orderDataList = typedQuery.getResultList();
        em.getTransaction().commit();
        assertTrue(dao.getOrdersOfCustomer(customerName).size() == orderDataList.size());
    }

}
