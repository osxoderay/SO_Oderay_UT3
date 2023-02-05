package org.example.app;

import org.example.configuration.Configuration;
import org.example.service.ExcelService;

import java.io.IOException;
import java.text.ParseException;

public class Ejercicio2 {
    public static void main(String[] args) throws IOException, ParseException {
        ExcelService excelService = new ExcelService();
        excelService.createExcelFromXML(Configuration.XML_ORDERS_PATH, Configuration.EXCEL_PATH);
    }
}
