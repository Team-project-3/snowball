package manager;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.data.Comment;
import com.data.DataBank;

import jxl.Cell;
import jxl.Sheet;
import jxl.Workbook;
import spider.Spider;
import spider.Spider2;

public class Manager extends Thread {

	private static List<String> codes=new ArrayList<String>();
	private static Map<String, String> states = new HashMap<>();//状态 0失败 1成功
	private String code;
	private DataBank db;
	
    
	public Manager(String code, DataBank db){
        this.code = code;
        this.db = db;
        codes.add(code);
        states.put(code,"下载中");
    }
	public Manager(){
		
    }
	
    public void run() {

    	try {
        	if(new Spider2().run(code)==1) states.put(code, "已完成");
        	
        	Workbook workbook=Workbook.getWorkbook(new File("./"+code+".xls")); 
    		Sheet sheetComment=workbook.getSheet(0);
    		for(int i=1;i<sheetComment.getRows();i++){
    	    	 Comment comment = new Comment();
    	    	 Cell commentContent=sheetComment.getCell(3,i);
    	    	 comment.setContent(commentContent.getContents());
    	    	 db.addComment(comment);
    	     }
    		//System.out.println(db.getCommentList());
    		File f=new File("./"+code+".xls");
			f.delete();
        	

        }catch (Exception e){
            e.printStackTrace();

        }


        
    }

    public String getResult() {
		while(states.get(code).equals("下载中"))
			try {
				Thread.sleep(1000);
			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		return states.get(code);
	}
    public List<String> getCodes() {
    	//System.out.println(codes);
    	return codes;
    }
    public Map<String, String> getStates() {
    	//System.out.println(states);
    	return states;
    }
    public String getStates(String code) {
    	//System.out.println(states);
    	return states.get(code);
    }

   
    
}