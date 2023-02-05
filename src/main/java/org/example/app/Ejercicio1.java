package org.example.app;

import org.example.configuration.Configuration;
import org.example.service.FileService;

public class Ejercicio1 {
    public static void main(String[] args) {
        FileService fileService = new FileService();
        fileService.buildXMLEstructure(Configuration.CSV_PATH, Configuration.XML_PATH);
    }
}
