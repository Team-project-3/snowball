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
import jxl.write.Label;
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
	
	public void importData(String file_path) {
		
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
		//��DataBank�л�ȡ���
		DataBank db = DataBank.getInstence();
		ArrayList<Comment> commentList = db.getCommentList();	
		ArrayList<com.data.Label> labelList = db.getLabelList();
		
		File filepath = new File(dir_path);
		filepath.mkdirs();
		File file =new File(filepath,filename);
		WritableWorkbook wwb = Workbook.createWorkbook(file);

		WritableSheet wsComment = wwb.createSheet("commentData", 0);//������
		WritableSheet wsLabel = wwb.createSheet("labelData", 1);//���ǩ
		
		//1.�����ǩ
		for(int i=0;i<labelList.size();i++) {
			Label labelContent = new Label(0,i, labelList.get(i).getContent());
			wsLabel.addCell((WritableCell) labelContent);
			for(int j=0;j<labelList.get(i).getOptions().size();j++) {
				Label labelOption = new Label(j+1,i, labelList.get(i).getOptions().get(j));
				wsLabel.addCell((WritableCell) labelOption);
			}
		}
		
		//2.��������
		//2.1.д���ͷ
		Label CommentHead = new Label(0, 0, "����");
		wsComment.addCell((WritableCell) CommentHead);
		for(int i=0;i<labelList.size();i++) {
			String sb  = labelList.get(i).getContent();
			for(int j=0;j<labelList.get(i).getOptions().size();j++) {
				sb = sb +"  "+j+"."+labelList.get(i).getOptions().get(j);
			}
			Label labelHead = new Label(i+1,0, sb);
			wsComment.addCell((WritableCell) labelHead);
		}
		
		//2.2.д������
		for(int i =0;i<commentList.size();i++) { 
			Label commentContent = new Label(0,i+1,commentList.get(i).getContent()); 
			wsComment.addCell((WritableCell) commentContent); 

		} 
		
		for(int i =0;i<commentList.size();i++){ 
			for(int j=0;j<labelList.size();j++) { 
				Label labelContent = new Label(j+1,i+1,commentList.get(i).getLabelList().get(j).toString());
				wsComment.addCell((WritableCell) labelContent); 
			} 
		}

		wwb.write();// д�����

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

	public void removeLabel(Label label) {
		
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
