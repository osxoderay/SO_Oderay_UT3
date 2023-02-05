package org.example.service;

import org.apache.poi.ss.usermodel.*;
import org.apache.poi.xssf.usermodel.XSSFRichTextString;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.example.configuration.Configuration;
import org.example.dao.FileDAO;
import org.example.dao.FileDAOImpl;
import org.example.entity.OrdersXMLEntity;

import java.io.File;
import java.io.IOException;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.concurrent.TimeUnit;

public class ExcelService {
    FileDAO fileDAO = new FileDAOImpl();

    /**
     * Metodo que crea el excel dado el xml orders.xml
     * @param ordersXMLPath
     * @param excelPath
     * @throws IOException
     * @throws ParseException
     */
    public void createExcelFromXML (String ordersXMLPath, String excelPath) throws IOException, ParseException {
        File ordersXML = new File(ordersXMLPath);

        List<OrdersXMLEntity> xmlEntities = fileDAO.getXmlInfo(ordersXML);

        try (Workbook workbook = new XSSFWorkbook()){

            Sheet sheet = workbook.createSheet();
            createHeader(sheet,xmlEntities,workbook.createCellStyle(),workbook.createFont());
            createRows(xmlEntities, sheet);

            fileDAO.createExcelFileInDisk(workbook, excelPath);
        }
    }

    /**
     * Metodo que crea las filas en el excel
     * @param xmlEntities
     * @param sheet
     * @throws ParseException
     */
    private void createRows(List<OrdersXMLEntity> xmlEntities, Sheet sheet) throws ParseException {
        DateFormat dateFormat = new SimpleDateFormat(Configuration.DATE_FORMAT);

        String sTodaysDate = dateFormat.format(new Date());
        Date todaysDate =new SimpleDateFormat(Configuration.DATE_FORMAT).parse(sTodaysDate);

        for (int i = 0; i < xmlEntities.size(); i++) {

            Row row = sheet.createRow(i + 1);
            Cell cell = row.createCell(0);


            RichTextString textString = new XSSFRichTextString(xmlEntities.get(i).getName());
            cell.setCellValue(textString);

            cell = row.createCell(1);
            textString = new XSSFRichTextString(xmlEntities.get(i).getType());
            cell.setCellValue(textString);

            cell = row.createCell(2);
            textString = new XSSFRichTextString(xmlEntities.get(i).getSaleDate());
            cell.setCellValue(textString);

            Date date1=new SimpleDateFormat(Configuration.DATE_FORMAT).parse(String.valueOf(textString));

            cell = row.createCell(3);
            textString = new XSSFRichTextString(xmlEntities.get(i).getDeliverDate());
            cell.setCellValue(textString);

            Date date2=new SimpleDateFormat(Configuration.DATE_FORMAT).parse(String.valueOf(textString));

            cell = row.createCell(4);
            textString = new XSSFRichTextString(xmlEntities.get(i).getStatus());
            cell.setCellValue(textString);

            cell = row.createCell(5);


            //Para calcular la diferencia entre los dias!!
            long diffInMillies = Math.abs(date2.getTime() - date1.getTime());
            long diff = TimeUnit.DAYS.convert(diffInMillies, TimeUnit.MILLISECONDS);

            if (diff >= 30){
                textString = new XSSFRichTextString("perdido");
                cell.setCellValue(textString);
            }

            else if (date2.after(todaysDate) ) {

                if (xmlEntities.get(i).getStatus().equals("confirmado")){
                    textString = new XSSFRichTextString("origen");
                    cell.setCellValue(textString);
                }

                else if(xmlEntities.get(i).getStatus().equals("pendiente")){
                    textString = new XSSFRichTextString("recogida");
                    cell.setCellValue(textString);
                } else {
                    textString = new XSSFRichTextString("sin procesar"); //Dado que existen casos donde no es ninguna de las opciones he puesto que ponga sin procesar al darse esos casos
                    cell.setCellValue(textString); }

            } else if (date2.before(todaysDate)){ //revisar esto
                if(xmlEntities.get(i).getStatus().equals("pendiente")){
                    textString = new XSSFRichTextString("reparto");
                    cell.setCellValue(textString);
                }
                else if (xmlEntities.get(i).getStatus().equals("finalizado")){
                    textString = new XSSFRichTextString("entregado");
                    cell.setCellValue(textString);
                } else {
                    textString = new XSSFRichTextString("sin procesar"); //Dado que existen casos donde no es ninguna de las opciones he puesto que ponga sin procesar al darse esos casos
                    cell.setCellValue(textString); }

            }

        }
    }

    /**
     * Metodo que crea las cabeceras en el excel
     * @param sheet
     * @param xmlEntities
     * @param cellStyle
     * @param font
     */
    private void createHeader(Sheet sheet, List<OrdersXMLEntity> xmlEntities, CellStyle cellStyle, Font font) {

        cellStyle.setFillForegroundColor(IndexedColors.BLACK.getIndex());
        cellStyle.setFillPattern(FillPatternType.SOLID_FOREGROUND);
        font.setColor(IndexedColors.WHITE.getIndex());
        font.setBold(true);
        cellStyle.setFont(font);

        for (int i = 0; i < xmlEntities.size(); i++) {
            Row row = sheet.createRow(i);
            Cell cell = row.createCell(0);

            RichTextString text = new XSSFRichTextString("Articulo");
            cell.setCellValue(text);
            cell.setCellStyle(cellStyle);

            cell = row.createCell(1);
            text = new XSSFRichTextString("Tipo");
            cell.setCellValue(text);
            cell.setCellStyle(cellStyle);

            cell = row.createCell(2);
            text = new XSSFRichTextString("Fecha de Venta");
            cell.setCellValue(text);
            cell.setCellStyle(cellStyle);

            cell = row.createCell(3);
            text = new XSSFRichTextString("Fecha de entrega");
            cell.setCellValue(text);
            cell.setCellStyle(cellStyle);

            cell = row.createCell(4);
            text = new XSSFRichTextString("Estado del pedido");
            cell.setCellValue(text);
            cell.setCellStyle(cellStyle);

            cell = row.createCell(5);
            text = new XSSFRichTextString("Estado de entrega");
            cell.setCellValue(text);
            cell.setCellStyle(cellStyle);

        }
    }
}
