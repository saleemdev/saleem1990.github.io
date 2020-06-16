/*    */ package notification;

/*    */
 /*    */ import java.io.FileInputStream;
/*    */ import java.io.PrintStream;
/*    */ import java.util.Iterator;
/*    */ import java.util.Vector;
/*    */ import org.apache.poi.hssf.usermodel.HSSFCell;
/*    */ import org.apache.poi.hssf.usermodel.HSSFRow;
/*    */ import org.apache.poi.hssf.usermodel.HSSFSheet;
/*    */ import org.apache.poi.hssf.usermodel.HSSFWorkbook;
/*    */ import org.apache.poi.poifs.filesystem.POIFSFileSystem;

/*    */
 /*    */
 /*    */
 /*    */
 /*    */
 /*    */
 /*    */ public class ExportData /*    */ {

    /*    */ public static Vector read(String fileName) /*    */ {
        /* 22 */ Vector cellVectorHolder = new Vector();
        /*    */ try /*    */ {
            /* 25 */ FileInputStream myInput = new FileInputStream(fileName);
            /* 26 */ POIFSFileSystem myFileSystem = new POIFSFileSystem(myInput);
            /* 27 */ HSSFWorkbook myWorkBook = new HSSFWorkbook(myFileSystem);
            /* 28 */ HSSFSheet mySheet = myWorkBook.getSheetAt(0);
            /* 29 */ Iterator rowIter = mySheet.rowIterator();
            /* 30 */ while (rowIter.hasNext()) {
                /* 31 */ HSSFRow myRow = (HSSFRow) rowIter.next();
                /* 32 */ Iterator cellIter = myRow.cellIterator();
                /* 33 */ Vector cellStoreVector = new Vector();
                /* 34 */ while (cellIter.hasNext()) {
                    /* 35 */ HSSFCell myCell = (HSSFCell) cellIter.next();
                    /*    */
 /* 37 */ System.out.print(myCell.getCellType() + " -");
                    /* 38 */ if (myCell.getCellType() == 0) {
                        /* 39 */ cellStoreVector.addElement(Double.valueOf(myCell.getNumericCellValue()));
                        /*    */                    } /* 41 */ else if (myCell.getCellType() == 1) {
                        /* 42 */ cellStoreVector.addElement(myCell.getStringCellValue());
                        /*    */                    }
                    
                    /*    */                }
                /*    */
 /*    */
 /* 47 */ System.out.println();
                /* 48 */ cellVectorHolder.addElement(cellStoreVector);
                /*    */            }
            /*    */        } catch (Exception e) {
            /* 51 */ e.printStackTrace();
            /*    */        }
        /* 53 */ return cellVectorHolder;
        /*    */    }
    /*    */ }


/* Location:              C:\Program Files\Trial Folder\SmallPOS.jar!\lib\ExportData.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       0.7.1
 */
