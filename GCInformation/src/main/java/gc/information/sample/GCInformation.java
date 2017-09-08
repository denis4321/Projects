/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package gc.information.sample;

import java.lang.management.GarbageCollectorMXBean;
import java.lang.management.ManagementFactory;
import java.util.List;

/**
 *
 * @author Администратор
 */
public class GCInformation {

    public static void main(String[] args) {
        try {
            List<GarbageCollectorMXBean> gcMxBeans = ManagementFactory.getGarbageCollectorMXBeans();
            for (GarbageCollectorMXBean gcMxBean : gcMxBeans) {
                System.out.println("########################");
                System.out.println("Name:"+gcMxBean.getName());
                //System.out.println("ObjectName:"+gcMxBean.getObjectName());
                System.out.println("Number of Collections:"+gcMxBean.getCollectionCount());
                System.out.println("Time for Collection:"+gcMxBean.getCollectionTime()+" ms");
                System.out.println("Poll names:");
                String[] memmoryPoolNames = gcMxBean.getMemoryPoolNames();
                for(String s: memmoryPoolNames){
                    System.out.println("\t"+s);
                }
            }
           // Thread.sleep(1);
        } catch (RuntimeException re) {
            throw re;
        } catch (Exception exp) {
            throw new RuntimeException(exp);
        }
    }
}
