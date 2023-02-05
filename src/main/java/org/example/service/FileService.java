package org.example.service;

import org.example.dao.FileDAO;
import org.example.dao.FileDAOImpl;
import org.example.entity.CsvEntity;
import org.w3c.dom.Document;
import org.w3c.dom.Element;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.transform.*;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.StringWriter;
import java.util.List;

public class FileService {
    FileDAO fileDAO = new FileDAOImpl();

    /**
     * Metodo que crea la estructura del xml
     * @param csvPath  path del csv
     * @param xmlPath path del xml
     */
    public void buildXMLEstructure(String csvPath, String xmlPath){
        try{
            Document document = getDocument();

            Element root = document.createElement("ventas");
            document.appendChild(root);

            List<CsvEntity> csvEntities =  fileDAO.getCsvInfo(new File(csvPath));

            for (int i = 0; i < csvEntities.size() ; i++) {
                Element articulo = document.createElement("articulo");
                root.appendChild(articulo);

                Element nombre = document.createElement("nombre");
                nombre.setTextContent(csvEntities.get(i).getArticulo());
                articulo.appendChild(nombre);

                Element tipo = document.createElement("tipo");
                tipo.setTextContent(csvEntities.get(i).getArticulo());
                articulo.appendChild(tipo);

                Element fecha = document.createElement("fecha");
                fecha.setTextContent(csvEntities.get(i).getFechaVenta());
                articulo.appendChild(fecha);

                Element precio = document.createElement("precio");
                precio.setTextContent(csvEntities.get(i).getPrecio());
                articulo.appendChild(precio);

                //uso el metodo convert para poder hacer la suma
                double costeDouble= convert( csvEntities.get(i).getCosteDerivado())  +  convert(csvEntities.get(i).getCosteProduccion()) + convert(csvEntities.get(i).getImpuestos());

                Element coste = document.createElement("coste");
                coste.setTextContent(String.valueOf(costeDouble));
                articulo.appendChild(coste);

                //uso el metodo convert para poder hacer la resta
                double beneficioDouble = convert(csvEntities.get(i).getPrecio()) - costeDouble;

                Element beneficio = document.createElement("beneficio");
                beneficio.setTextContent(String.valueOf(beneficioDouble));
                articulo.appendChild(beneficio);
            }

            DOMSource domSource = new DOMSource(document);

            Transformer transformer = getTransformer();
            StringWriter sw = new StringWriter();
            StreamResult sr = new StreamResult(sw);
            transformer.transform(domSource, sr);
            System.out.println(sw);

            fileDAO.createTestXML(new File(xmlPath), sw.toString());
        } catch (ParserConfigurationException | IOException | TransformerException e) {
            throw new RuntimeException(e);
        }
    }

    /**
     * Metodo que crea el transformer
     * @return
     * @throws TransformerConfigurationException
     */
    private Transformer getTransformer() throws TransformerConfigurationException {
        TransformerFactory transformerFactory = TransformerFactory.newInstance();

        Transformer transformer = transformerFactory.newTransformer();
        transformer.setOutputProperty(OutputKeys.METHOD, "xml");
        transformer.setOutputProperty(OutputKeys.ENCODING, "UTF-8");
        transformer.setOutputProperty(OutputKeys.INDENT, "yes");
        transformer.setOutputProperty("{http://xml.apache.org/xslt}indent-amount", "4");
        return transformer;
    }

    /**
     * Metodo que convierte los String que tienen decimales con comas en double
     * @param input
     * @return
     */
    private double convert(String input) {
        input = input.replace(',', '.');
        int decimalSeperator = input.lastIndexOf('.');

        if (decimalSeperator > -1) {
            input = input.substring(0, decimalSeperator).replace(".", "") + input.substring(decimalSeperator);
        }

        return Double.valueOf(input);
    }

    /**
     * Metodo que crea el documento
     * @return
     * @throws ParserConfigurationException
     */
    private Document getDocument() throws ParserConfigurationException {
        DocumentBuilderFactory dbf = DocumentBuilderFactory.newInstance();
        DocumentBuilder db = dbf.newDocumentBuilder();
        Document doc = db.newDocument();
        doc.setXmlVersion("1.0");
        return doc;
    }
}
