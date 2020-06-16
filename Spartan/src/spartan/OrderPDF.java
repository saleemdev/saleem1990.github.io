/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package spartan;

/**
 *
 * @author owner
 */
import java.io.FileOutputStream;
import java.io.IOException;

import com.itextpdf.text.Document;
import com.itextpdf.text.DocumentException;
import com.itextpdf.text.Element;
import com.itextpdf.text.Font;
import com.itextpdf.text.Paragraph;
import com.itextpdf.text.Phrase;
import com.itextpdf.text.pdf.PdfPCell;
import com.itextpdf.text.pdf.PdfPTable;
import com.itextpdf.text.pdf.PdfWriter;
import java.awt.Desktop;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import javax.swing.JTabbedPane;
import javax.swing.JTable;
import javax.swing.table.TableModel;

/**
 * First iText example: Hello World.
 */
public class OrderPDF {

    /**
     * Path to the resulting PDF file.
     */
    public static String pdfcontent = "";

    public static String userName = "";

    public static int orderno = 99;
    public static JTable jtm = new JTable();

    public OrderPDF(String file, JTable tbm, String username) throws DocumentException, IOException { //constructor

        jtm = tbm;
        userName = username;

        String RESULT
                = file;

        createPdf(RESULT);
    }

//        public static final String RESULT
//            = "results/part1/chapter01/hello.pdf";
    /**
     * Creates a PDF file: hello.pdf
     *
     * @param args no arguments needed
     */
    public static void main(String[] args)
            throws DocumentException, IOException {
    }

    public int incrementOrdrNo() {

        orderno = orderno + 1;
        return orderno;
    }

    /**
     * Creates a PDF document.
     *
     * @param filename the path to the new PDF document
     * @throws DocumentException
     * @throws IOException
     */
    public Object[] item(String username) {
        Object[] customerinfo = new Object[]{};

        String userdir = System.getProperty("user.dir");
        System.err.println(userdir);;
        String filepath = userdir + "\\StoreData.txt";
        System.err.println(filepath);

        File file = new File(filepath);
        
        
        try {
            BufferedReader bR = new BufferedReader(new FileReader(file));
            String firstline = bR.readLine().trim();

            Object[] data = bR.lines().toArray();
            for (int i = 0; i < data.length; i++) {
                String lineItem = data[i].toString().trim();
                System.err.println(lineItem);

                String[] columndata = lineItem.split("\t");

                if (columndata[0].equals(username)) {
                    customerinfo = columndata;
                }

            }

        } catch (Exception ex) {
            ex.printStackTrace();
        }
        return customerinfo;
    }

    public void createPdf(String filename)
            throws DocumentException, IOException {
        // step 1
        Document document = new Document();

        // step 2
        PdfWriter.getInstance(document, new FileOutputStream(filename));
        // step 3
        document.open();
        // step 4

        //Let's get customer info
        Object[] customerinfo = item(userName);

        Paragraph paragraph = new Paragraph();
        paragraph.setAlignment(Element.ALIGN_CENTER);
        paragraph.add("Order Confirmation\nOrder#: " + incrementOrdrNo() + "  \nCustomer #: " + customerinfo[0] + "\nCustomer Name: " + customerinfo[1] + "\nAddress: " + customerinfo[2] + "\n\n");
        document.add(paragraph);

        //Let us put the data in a table, BUT FIRST CREATE THE TABLE
        PdfPTable table = new PdfPTable(5);

        PdfPCell cell;
        Phrase phrase;
        int headerwidthstable[] = {5, 5, 15, 10, 10};

        cell = new PdfPCell(new Phrase("Quantity"));
        table.addCell(cell);

        cell = new PdfPCell(new Phrase("Item#"));
        table.addCell(cell);

        cell = new PdfPCell(new Phrase("Description"));
        table.addCell(cell);

        cell = new PdfPCell(new Phrase("Amount"));
        table.addCell(cell);
        

        cell = new PdfPCell(new Phrase("Total"));
        table.addCell(cell);

        /*We just added the header, now let us loop through the table and add data*/
        Double grosstotal = 0.0;
        for (int i = 0; i < jtm.getModel().getRowCount(); i++) {

            Double qty = Double.valueOf(jtm.getValueAt(i, 0).toString()), price = Double.valueOf(jtm.getValueAt(i, 1).toString());

            Double total = qty * price;

            cell = new PdfPCell(new Phrase(jtm.getValueAt(i, 0).toString()));
            table.addCell(cell);

            cell = new PdfPCell(new Phrase(jtm.getValueAt(i, 2).toString()));
            table.addCell(cell);

            cell = new PdfPCell(new Phrase(jtm.getValueAt(i, 3).toString()));
            table.addCell(cell);

            cell = new PdfPCell(new Phrase(jtm.getValueAt(i, 1).toString()));
            table.addCell(cell);

            cell = new PdfPCell(new Phrase(total.toString()));
            table.addCell(cell);

            grosstotal = grosstotal + total;

        }
        //Let us set the total figure

        //Let us set the total figure
        phrase = new Phrase("Total: ");
        cell = new PdfPCell(phrase);
        cell.setColspan(4);
        cell.setHorizontalAlignment(Element.ALIGN_RIGHT);
        table.addCell(cell);

        phrase = new Phrase("$ " + grosstotal.toString());
        cell = new PdfPCell(phrase);
        cell.setColspan(1);
        cell.setHorizontalAlignment(Element.ALIGN_RIGHT);
        table.addCell(cell);

        document.add(table);

        document.close();

        Desktop.getDesktop().open(new File(filename));

        ShippingWindow.showGUI(String.valueOf(orderno), jtm, userName);

    }
}
