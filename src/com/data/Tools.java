package com.data;

import manager.Manager;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import jxl.Cell;
import jxl.Sheet;
import jxl.Workbook;
import jxl.read.biff.BiffException;
import jxl.write.WritableCell;
import jxl.write.WritableSheet;
import jxl.write.WritableWorkbook;
import jxl.write.WriteException;
import jxl.write.biff.RowsExceededException;

public class Tools {
	private DataBank db;

	public Tools(DataBank db) {
		this.db = db;
	}
	
	public void downloadData(String ID) throws InterruptedException, BiffException, IOException {
		Manager t = new Manager(ID,db);
		t.start();

	}
	
	public Map<String, String> getDownloading() {
		Manager t = new Manager();
		return t.getStates();
	}
	
	public void downloadDelete(String ID) {
		Manager t = new Manager();
		t.delCodeAndState(ID);
	}
	
	public void importData(String file_path) throws BiffException, IOException {
		 DataBank db = DataBank.getInstence();
		 
		 //1. 获得XLS文件
		 Workbook workbook=Workbook.getWorkbook(new File(file_path)); 
	     //2:获取第一个工作表sheet
	     Sheet sheetComment=workbook.getSheet(0);
	     Sheet sheetLabel = workbook.getSheet(1);
	     //3:获取数据
	     
	     //3.1 当db内容不为空时
	     if(db.getLabelList().size()!=0&&db.getCommentList().size()!=0) {
	    	//3.1.1.获取db中内容
	    	 ArrayList<Comment> commentList = db.getCommentList();	
	 		 ArrayList<com.data.Label> labelList = db.getLabelList();
	 		 
	 		 //3.1.2判断标签类是否一致，如果不一致，直接退出，不做db更新
	    	 for(int i=0;i<sheetLabel.getRows();i++){
		    	 Cell labelContent =sheetLabel.getCell(0,i); //标签内容
		    	 if(!(labelList.get(i).getContent().equals(labelContent.getContents()))){
		    		 return;
		    	 }
		     }
	    	 
	    	 //3.1.3 标签类一致，如果评论内容不一致，不做db更新；如果一致判断标注，标注一致不处理，不一致设为-1
	    	  for(int i=1;i<sheetComment.getRows();i++){
	 	    	 ArrayList<Integer> commentOptions = new ArrayList<Integer>();
	 	    	 Cell commentContent=sheetComment.getCell(0,i);//评论内容
	 	    	 //评论内容不一致,不做处理，保留DB数据
	 	    	 if(!(commentContent.getContents().equals(commentList.get(i-1).getContent()))) {
	 	    		 continue;
	 	    	 }
	 	    	 
	 	    	 //评论内容一致，判断标注
	 	    	 for(int j=1;j<sheetComment.getColumns();j++) {
	 	    		 Cell commentOption = sheetComment.getCell(j,i);
	 	    		 //标注不一致设为，更改db相应评论的标注为-1
	 	    		 if(Integer.parseInt(commentOption.getContents())!=commentList.get(i-1).getLabelList().get(j-1)) {
	 	    			commentList.get(i-1).getLabelList().set(j-1, -1);
	 	    			System.out.println("标注不一致,位置("+i+","+j+")");
	 	    		 }
	 	    	 } 	    
	 	     } 
	    	 return ;		
		 }
	     
	     
	     //3.2 当db内容为空时，直接更新db
	     //3.2.1 获取标签 
	     for(int i=0;i<sheetLabel.getRows();i++){
	    	 com.data.Label label = new com.data.Label();
	    	 ArrayList<String> labelOptions = new ArrayList<String>();//标签选项
	 
	    	 Cell labelContent =sheetLabel.getCell(0,i); //标签内容
	    	 System.out.print(labelContent.getContents()+"  ");
	         for(int j=0;j<sheetLabel.getColumns()-1;j++){
	              Cell celloption=sheetLabel.getCell(j+1,i);
	              if(celloption.getContents().equals("")) {
	            	  break;
	              }
	              labelOptions.add(celloption.getContents());
	              System.out.print(celloption.getContents()+"  ");
	          }
	         System.out.println();
	          label.setContent(labelContent.getContents());//标签内容
	          label.setOptions(labelOptions);
	          db.addLabel(label);
	     }  
	     
	     //3.2.2 获取内容
	     for(int i=1;i<sheetComment.getRows();i++){
	    	 Comment comment = new Comment();
	    	 ArrayList<Integer> commentOptions = new ArrayList<Integer>();
	    	 Cell commentContent=sheetComment.getCell(0,i);//评论内容
	    	 System.out.print(commentContent.getContents()+"  ");
	    	 for(int j=0;j<sheetComment.getColumns()-1;j++) {
	    		 Cell commentOption = sheetComment.getCell(j+1,i);
	    		 commentOptions.add(Integer.parseInt(commentOption.getContents()));
	    		 System.out.print(commentOption.getContents()+"  ");
	    	 }
	    	 System.out.println();
	    	 comment.setContent(commentContent.getContents());
	    	 comment.setLabelArrayList(commentOptions);
	    	 db.addComment(comment);
	     }  
	     //4.关闭工作簿
	      workbook.close();	
	}
	
	public void exportData(String dir_path, String filename) throws IOException, RowsExceededException, WriteException {
		//从DataBank中获取数据
				DataBank db = DataBank.getInstence();
				ArrayList<Comment> commentList = db.getCommentList();	
				ArrayList<com.data.Label> labelList = db.getLabelList();
				
				//1.创建工作溥，导出数据为.xls文件
				File filepath = new File(dir_path);
				filepath.mkdirs();
				File file =new File(filepath,filename);
				WritableWorkbook wwb = Workbook.createWorkbook(file);
				WritableSheet wsComment = wwb.createSheet("commentData", 0);//存评论
				WritableSheet wsLabel = wwb.createSheet("labelData", 1);//存标签
				
				//1.导出标签
				for(int i=0;i<labelList.size();i++) {
					jxl.write.Label labelContent = new jxl.write.Label(0,i, labelList.get(i).getContent());
					wsLabel.addCell((WritableCell) labelContent);
					for(int j=0;j<labelList.get(i).getOptions().size();j++) {
						jxl.write.Label labelOption = new jxl.write.Label(j+1,i, labelList.get(i).getOptions().get(j));
						wsLabel.addCell((WritableCell) labelOption);
					}
				}
				
				//2.导出评论
				//2.1.写入表头
				jxl.write.Label CommentHead = new jxl.write.Label(0, 0, "评论");
				wsComment.addCell((WritableCell) CommentHead);
				for(int i=0;i<labelList.size();i++) {
					String sb  = labelList.get(i).getContent();
					for(int j=0;j<labelList.get(i).getOptions().size();j++) {
						sb = sb +"  "+j+"."+labelList.get(i).getOptions().get(j);
					}
					jxl.write.Label labelHead = new jxl.write.Label(i+1,0, sb);
					wsComment.addCell((WritableCell) labelHead);
				}
				
				//2.2.写入内容
				for(int i =0;i<commentList.size();i++) { 
					jxl.write.Label commentContent = new jxl.write.Label(0,i+1,commentList.get(i).getContent()); 
					wsComment.addCell((WritableCell) commentContent); 
				} 
				
				for(int i =0;i<commentList.size();i++){ 
					for(int j=0;j<labelList.size();j++) { 
						jxl.write.Label labelContent = new jxl.write.Label(j+1,i+1,commentList.get(i).getLabelList().get(j).toString());
						wsComment.addCell((WritableCell) labelContent); 
					} 
				}

				wwb.write();// 写入数据
				wwb.close();
	}
	
	public void addLabel(Label label) {
		ArrayList<Label> list = new ArrayList<>();
		list = db.getLabelList();
		list.add(label);
		db.setLabelList(list);
		
		ArrayList<Integer> list_1 = new ArrayList<>();
		ArrayList<Comment> list_2 = new ArrayList<>();
		list_2=db.getCommentList();
		for(int i = 0 ; i < list_2.size() ; i++) {
			list_1=list_2.get(i).getLabelList();
			list_1.add(-1);
			list_2.get(i).setLabelArrayList(list_1);
		}
		db.setCommentList(list_2);
	}

	public void removeLabel(Label label) {
		ArrayList<Label> list = new ArrayList<>();
		list=db.getLabelList();
		if(list!=null) {
			ArrayList<Comment> list_1 = new ArrayList<>();
			list_1=db.getCommentList();
			ArrayList<Integer> list_2 = new ArrayList<>();
			for(int i = 0 ; i < list.size(); i++) {
				if(list.get(i)==label) {
					for(int j = 0 ; j < list_1.size(); j++) {
						list_2=list_1.get(j).getLabelList();
						list_2.remove(i);
						list_1.get(j).setLabelArrayList(list_2);
					}
				}
			}
			list.remove(label);
		}
		else {
			System.out.println("没有这个标签！");
		}
		db.setLabelList(list);
	}
	

	public Map<Label, ArrayList<Integer>> analyse() {
		Map<Label, ArrayList<Integer>> table = new HashMap<>();
		ArrayList<Comment> comments = this.db.getCommentList();
		ArrayList<Label> labels = this.db.getLabelList();
		if (labels.size() < 1 || comments.size() < 1) return table;
		
		
		int size = labels.size();
		for (int i=0; i<size; ++i) {
			// 初始化labelSum
			ArrayList<Integer> labelSum = new ArrayList<>();
			int len = labels.get(i).getOptions().size();
			for(int j=0; j <= len; ++j) {
				labelSum.add(0);
			}
			
			// 统计
			for (Comment comment : comments) {
				int option = comment.getLabelList().get(i);
				if (option < 0) {
					labelSum.set(len, labelSum.get(len)+1);
					continue;
				}
				labelSum.set(comment.getLabelList().get(i), labelSum.get(option)+1);
			}
			
			table.put(labels.get(i), labelSum);
		}
		
		return table;
	}

}
