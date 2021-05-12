package com.data;

import manager.Manager;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import jxl.Workbook;
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
	
	public int downloadData(String ID) {
		Manager t1 = new Manager(ID);
		t1.start();
		try {
			t1.join();
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return t1.getResult();
		
	}
	
	public void getDownloading(String ID) {
		System.out.println(downloadData(ID));
	}
	
	public void importData(String file_path) {
		
	}
	
	public void exportData(String dir_path, String filename) throws IOException, RowsExceededException, WriteException {
		DataBank db = new DataBank();
		ArrayList<Comment> commentList = new ArrayList<Comment>();
		//����
		ArrayList<Integer> optionsComment1 = new ArrayList<Integer>();
		ArrayList<Integer> optionsComment2 = new ArrayList<Integer>();
		optionsComment1.add(0);
		optionsComment1.add(0);
		optionsComment2.add(0);
		optionsComment2.add(2);
		commentList.add(new Comment("��Ʊ���ƺܺ�",optionsComment1));
		commentList.add(new Comment("��Ʊ���ƻ����",optionsComment2));
	
		//��ǩ
		ArrayList<com.data.Label> labelList = db.getLabelList();
		ArrayList<String> optionslabel1 = new ArrayList<String>();
		ArrayList<String> optionslabel2 = new ArrayList<String>();
		optionslabel1.add("��");
		optionslabel1.add("��");
		optionslabel2.add("����");
		optionslabel2.add("����");
		optionslabel2.add("����");
		labelList.add(new com.data.Label(1,"�����Ƿ����Ʊ��أ�",optionslabel1));
		labelList.add(new com.data.Label(2,"���������桢���Ի��Ǹ��棿",optionslabel2));
		
		//��������Ϊ.xls�ļ�
		File filepath = new File(dir_path);
		filepath.mkdirs();
		File file =new File(filepath,filename);
		WritableWorkbook wwb = Workbook.createWorkbook(file);
		WritableSheet ws = wwb.createSheet("commentData", 0);
		
		//1.д���ͷ
		Label CommentHead = new Label(0, 0, "����");
		ws.addCell((WritableCell) CommentHead);
		for(int i=0;i<labelList.size();i++) {
			Label labelHead = new Label(i+1,0, labelList.get(i).getContent());
			ws.addCell((WritableCell) labelHead);
		}
		
		//2.д������
		
		for(int i =0;i<commentList.size();i++) { 
			Label commentContent = new Label(0,i+1,commentList.get(i).getContent()); 
			ws.addCell((WritableCell) commentContent); 
		} 
		
		for(int i =0;i<commentList.size();i++){ 
			for(int j=0;j<labelList.size();j++) { 
				Label labelContent = new Label(j+1,i+1,labelList.get(i).getOptions().get(commentList.get(i).getLabelList().get(j) ));
				ws.addCell((WritableCell) labelContent); 
			} 
		}

		wwb.write();// д������
		wwb.close();
	}
	
	public void addLabel(Label label) {
		
	}

	public void removeLabel(Label label) {
		
	}
	
	public ArrayList<Integer> analyse(com.data.Label analyseLabel) {
		ArrayList<Comment> comments = this.db.getCommentList();
		ArrayList<com.data.Label> labels = this.db.getLabelList();
		int index = labels.indexOf(analyseLabel);
		
		// 初始化label_sum
		ArrayList<Integer> label_sum = new ArrayList<>();
		for(int i=0; i < analyseLabel.getOptions().size(); ++i) {
			label_sum.add(0);
		}
		
		// 统计
		for(Comment comment : comments) {
			ArrayList<Integer> arrayList = comment.getLabelList();
			int option = arrayList.get(index);
			label_sum.set(option, label_sum.get(option)+1);
		}
		
		return label_sum;
	}

}
