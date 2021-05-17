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
	
	public void downloadData(String ID) {
		Manager t = new Manager(ID);
		t.start();

		
	}
	
	public Map<String, String> getDownloading() {
		Manager t = new Manager();
		return t.getStates();
	}
	
	public void importData(String file_path) throws BiffException, IOException {
		
		//1. ���XLS�ļ�
		 Workbook workbook=Workbook.getWorkbook(new File(file_path)); 
	     //2:��ȡ��һ�����sheet
	     Sheet sheetComment=workbook.getSheet(0);
	     Sheet sheetLabel = workbook.getSheet(1);
	     //3:��ȡ���
	     
	     //3.1 ��ȡ��ǩ 
	     for(int i=0;i<sheetLabel.getRows();i++){
	    	 com.data.Label label = new com.data.Label();
	    	 ArrayList<String> labelOptions = new ArrayList<String>();//��ǩѡ��
	 
	    	 Cell labelContent =sheetLabel.getCell(0,i); //��ǩ����
	    	System.out.print(labelContent.getContents()+"  ");
	         for(int j=0;j<sheetLabel.getColumns()-1;j++){
	              Cell celloption=sheetLabel.getCell(j+1,i);
	              if(celloption.getContents()==null) {
	            	  break;
	              }
	              labelOptions.add(celloption.getContents());
	              System.out.print(celloption.getContents()+"  ");
	          }
	         System.out.println();
	          label.setContent(labelContent.getContents());//��ǩ����
	          label.setOptions(labelOptions);
	          db.addLabel(label);
	     }  
	     
	     //3.2 ��ȡ����
	     for(int i=1;i<sheetComment.getRows();i++){
	    	 Comment comment = new Comment();
	    	 ArrayList<Integer> commentOptions = new ArrayList<Integer>();
	    	 Cell commentContent=sheetComment.getCell(0,i);//��������
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
	     //4.�رչ���
	      workbook.close();	
	}
	
	public void exportData(String dir_path, String filename) throws IOException, RowsExceededException, WriteException {
		DataBank db = new DataBank();
		ArrayList<Comment> commentList = new ArrayList<Comment>();
		//评论
		ArrayList<Integer> optionsComment1 = new ArrayList<Integer>();
		ArrayList<Integer> optionsComment2 = new ArrayList<Integer>();
		optionsComment1.add(0);
		optionsComment1.add(0);
		optionsComment2.add(0);
		optionsComment2.add(2);
		commentList.add(new Comment("股票涨势很好",optionsComment1));
		commentList.add(new Comment("股票涨势还差点",optionsComment2));
	
		//标签
		ArrayList<com.data.Label> labelList = db.getLabelList();
		ArrayList<String> optionslabel1 = new ArrayList<String>();
		ArrayList<String> optionslabel2 = new ArrayList<String>();
		optionslabel1.add("是");
		optionslabel1.add("否");
		optionslabel2.add("正面");
		optionslabel2.add("中性");
		optionslabel2.add("负面");
		labelList.add(new com.data.Label(1,"评论是否与股票相关？",optionslabel1));
		labelList.add(new com.data.Label(2,"评论是正面、中性还是负面？",optionslabel2));
		
		//导出数据为.xls文件
		File filepath = new File(dir_path);
		filepath.mkdirs();
		File file =new File(filepath,filename);
		WritableWorkbook wwb = Workbook.createWorkbook(file);
		WritableSheet ws = wwb.createSheet("commentData", 0);
		
		//1.写入表头
		jxl.write.Label CommentHead = new jxl.write.Label(0, 0, "评论");
		ws.addCell((WritableCell) CommentHead);
		for(int i=0;i<labelList.size();i++) {
			jxl.write.Label labelHead = new jxl.write.Label(i+1,0, labelList.get(i).getContent());
			ws.addCell((WritableCell) labelHead);
		}
		
		//2.写入内容
		
		for(int i =0;i<commentList.size();i++) { 
			jxl.write.Label commentContent = new jxl.write.Label(0,i+1,commentList.get(i).getContent()); 
			ws.addCell((WritableCell) commentContent); 
		} 
		
		for(int i =0;i<commentList.size();i++){ 
			for(int j=0;j<labelList.size();j++) { 
				jxl.write.Label labelContent = new jxl.write.Label(j+1,i+1,labelList.get(i).getOptions().get(commentList.get(i).getLabelList().get(j) ));
				ws.addCell((WritableCell) labelContent); 
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
		
		int size = labels.size();
		for (int i=0; i<size; ++i) {
			// 初始化labelSum
			ArrayList<Integer> labelSum = new ArrayList<>();
			int len = labels.get(i).getOptions().size();
			for(int j=0; j < len; ++j) {
				labelSum.add(0);
			}
			
			// 统计
			for (Comment comment : comments) {
				labelSum.set(comment.getLabelList().get(i), labelSum.get(comment.getLabelList().get(i))+1);
			}
			
			table.put(labels.get(i), labelSum);
		}
		
		return table;
	}

}
