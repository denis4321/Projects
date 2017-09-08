/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.app;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.util.HashSet;
import java.util.Set;
import javax.websocket.Session;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

/**
 *
 * @author DProkopiuk
 */
public class SingleSessionHandler {

    private static volatile SingleSessionHandler instnace = new SingleSessionHandler();

    private SingleSessionHandler() {
    }

    private Set<Session> sessions = new HashSet();

    public void addSession(Session session) {
        session.setMaxIdleTimeout(10*60*1000);
        sessions.add(session);
    }

    public void makeExport(String message, Session session) {        
        try {
            Thread.sleep(30000);
            byte[] data = getExportData(message);
            try(OutputStream out=session.getBasicRemote().getSendStream()){
                out.write(data);
            }            
            System.out.println("Completed");                        
        } catch (Exception ex) {
            sessions.remove(session);
            ex.printStackTrace();
        }
    }

    public void removeSession(Session session) {
        try {
            sessions.remove(session);
            session.close();
        } catch (IOException ex) {
            ex.printStackTrace();
        }
    }

    public static SingleSessionHandler getInstance() {
        return instnace;
    }

    private byte[] getExportData(String message) {
        try (ByteArrayOutputStream out = new ByteArrayOutputStream()) {
            Workbook workbook = new XSSFWorkbook();
            Sheet sheet = workbook.createSheet();
            for (int i = 0; i < 50; i++) {
                Row row = sheet.createRow(i + 1);
                for (int j = 0; j < 10; j++) {
                    Cell cell = row.createCell(j + 1);
                    cell.setCellValue("Test" + j);
                }
            }
            workbook.write(out);
            return out.toByteArray();
        } catch (Exception ex) {
            throw new RuntimeException(ex);
        }
    }

}
