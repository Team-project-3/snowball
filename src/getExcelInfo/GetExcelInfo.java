package getExcelInfo;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;

import jxl.Sheet;
import jxl.Workbook;
import jxl.read.biff.BiffException;

public class GetExcelInfo {

    // 去读Excel的方法readExcel，该方法的入口参数为一个File对象  
    public void readExcel(File file) {  
        try {  
            // 创建输入流，读取Excel  
            InputStream is = new FileInputStream(file.getAbsolutePath());  
            // jxl提供的Workbook类  
            Workbook wb = Workbook.getWorkbook(is);  
            // Excel的页签数量  
            int sheet_size = wb.getNumberOfSheets();  
            for (int index = 0; index < sheet_size; index++) {  
                // 每个页签创建一个Sheet对象  
                Sheet sheet = wb.getSheet(index);  
                // sheet.getRows()返回该页的总行数  
                for (int i = 1; i < sheet.getRows(); i++) {  
                    // sheet.getColumns()返回该页的总列数  
 
                        String cellinfo = sheet.getCell(3, i).getContents();  
                        System.out.println(cellinfo);  
                    
                }  
            }  
        } catch (FileNotFoundException e) {  
            e.printStackTrace();  
        } catch (BiffException e) {  
            e.printStackTrace();  
        } catch (IOException e) {  
            e.printStackTrace();  
        }  
    }  
}