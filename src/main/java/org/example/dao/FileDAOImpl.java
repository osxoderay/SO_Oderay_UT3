package org.example.dao;

import org.apache.poi.ss.usermodel.Workbook;
import org.example.configuration.Configuration;
import org.example.entity.CsvEntity;
import org.example.entity.OrdersXMLEntity;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.xml.sax.SAXException;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import java.io.*;
import java.util.ArrayList;
import java.util.List;

public class FileDAOImpl implements FileDAO{

    // METODOS DEL EJERCICIO 1

    /**
     * Metodo que obtiene la informacion del invoice_202009.csv
     * @param csv
     * @return
     * @throws FileNotFoundException
     * @throws IOException
     */
    @Override
    public List<CsvEntity> getCsvInfo(File csv) throws FileNotFoundException, IOException {
        List<CsvEntity> entities = new ArrayList<>();

        try(FileReader fileReader = new FileReader(csv); BufferedReader bReader = new BufferedReader(fileReader);){
            String line;
            bReader.readLine();
            while ((line = bReader.readLine()) != null){
                String[] divLine = line.split(Configuration.DELIMITER);
                String articulo = divLine[0];
                String tipo = divLine[1];
                String fechaVenta = divLine[2];
                String precio = divLine[3];
                String costeDerivados = divLine[4];
                String costeProduccion = divLine[5];
                String impuestos = divLine[6];

                CsvEntity csvEntity = new CsvEntity(articulo,tipo,fechaVenta,precio,costeDerivados,costeProduccion,impuestos);
                entities.add(csvEntity);
            }

            bReader.close();
            fileReader.close();

        }
        return entities;
    }

    /**
     *  Metodo que crea un XML
     * @param file
     * @param xml
     * @throws IOException
     */
    @Override
    public void createTestXML(File file, String xml) throws IOException {
        if (!file.exists()){
            file.createNewFile();
        }
        try (FileWriter fileWriter = new FileWriter(file)){
            fileWriter.write(xml);
        }
    }

    // METODOS DEL EJERCICIO 2

    /**
     * Metodo que crea el archivo de Excel
     * @param workbook
     * @param path
     * @throws IOException
     */
    @Override
    public void createExcelFileInDisk(Workbook workbook, String path) throws IOException {
        try (FileOutputStream invoiceFile = new FileOutputStream(path)){
            workbook.write(invoiceFile);
            System.out.println("Creando fichero en: " + path);
        }
    }

    /**
     * Metodo que obtiene la información del XML
     * @param ordersXML
     * @return
     * @throws IOException
     */
    @Override
    public List<OrdersXMLEntity> getXmlInfo(File ordersXML) throws IOException {

        List<OrdersXMLEntity> ordersXMLEntities = new ArrayList<>();

        DocumentBuilderFactory dbFactory = DocumentBuilderFactory.newInstance();
        DocumentBuilder dBuilder = null;

        try{
            dBuilder = dbFactory.newDocumentBuilder();
            Document doc = dBuilder.parse(ordersXML);

            NodeList nList = doc.getElementsByTagName("orders");

            for (int i = 0; i <nList.getLength() ; i++) {
                Node node = nList.item(i);
                if (node.getNodeType() == Node.ELEMENT_NODE) {
                    Element element = (Element) node;
                    NodeList article = element.getElementsByTagName("article");

                    for (int j = 0; j < article.getLength(); j++) {
                        Node articleNode = article.item(j);
                        if (articleNode.getNodeType() == Node.ELEMENT_NODE) {
                            Element articleElement = (Element) articleNode;
                            String name = articleElement.getElementsByTagName("name").item(0).getTextContent();
                            String type = articleElement.getElementsByTagName("type").item(0).getTextContent();
                            String saleDate = articleElement.getElementsByTagName("saleDate").item(0).getTextContent();
                            String deliverDate = articleElement.getElementsByTagName("deliverDate").item(0).getTextContent();
                            String status = articleElement.getElementsByTagName("status").item(0).getTextContent();

                            OrdersXMLEntity ordersXMLEntity = new OrdersXMLEntity(name,type,saleDate,deliverDate,status);
                            ordersXMLEntities.add(ordersXMLEntity);
                        }
                    }
                }
            }

        } catch (ParserConfigurationException | SAXException e) {
            throw new RuntimeException(e);
        }
        return ordersXMLEntities;
    }


}
