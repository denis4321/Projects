/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package memmory.allocation.sample;

import java.util.Random;

/**
 *
 * @author Администратор
 */
public class AllocationOverview {

    public static void main(String[] args) throws InterruptedException {
        int arraySize = 1000000;
        GCMe[] gcmes = new GCMe[arraySize];
        int count = 0;
        Random random = new Random();
        while (true) {
            gcmes[random.nextInt(arraySize)] = new GCMe();
            if (count % 2000000 == 0) {
                System.out.print(".");
            }
            count++;
            Thread.sleep(10);
        }
    }

}
