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

    // ȥ��Excel�ķ���readExcel���÷�������ڲ���Ϊһ��File����  
    public void readExcel(File file) {  
        try {  
            // ��������������ȡExcel  
            InputStream is = new FileInputStream(file.getAbsolutePath());  
            // jxl�ṩ��Workbook��  
            Workbook wb = Workbook.getWorkbook(is);  
            // Excel��ҳǩ����  
            int sheet_size = wb.getNumberOfSheets();  
            for (int index = 0; index < sheet_size; index++) {  
                // ÿ��ҳǩ����һ��Sheet����  
                Sheet sheet = wb.getSheet(index);  
                // sheet.getRows()���ظ�ҳ��������  
                for (int i = 1; i < sheet.getRows(); i++) {  
                    // sheet.getColumns()���ظ�ҳ��������  
 
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