package org.example.dao;

import org.apache.poi.ss.usermodel.Workbook;
import org.example.entity.CsvEntity;
import org.example.entity.OrdersXMLEntity;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.List;

public interface FileDAO {

    List<CsvEntity> getCsvInfo (File csv) throws FileNotFoundException, IOException;
    void createTestXML (File file,String xml) throws IOException;

    void createExcelFileInDisk (Workbook workbook, String path) throws IOException;
    List<OrdersXMLEntity> getXmlInfo (File csv) throws  IOException;
}
