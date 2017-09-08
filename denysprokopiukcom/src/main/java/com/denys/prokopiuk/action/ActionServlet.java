/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.denys.prokopiuk.action;

import com.denys.prokopiuk.producer.Producer;
import com.denys.prokopiuk.producer.ProducerSettings;
import java.io.IOException;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 *
 * @author Denys
 */
public class ActionServlet extends HttpServlet {

    @Override
    protected void service(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        ProducerSettings producerSettings = new ProducerSettings();
        Producer producer = new Producer(producerSettings);
        int n = producerSettings.getAmountOfUrls();
        for (int i = 0; i < n; i++) {
            producer.produce(producerSettings.getUrl(i));
        }
    }

}
