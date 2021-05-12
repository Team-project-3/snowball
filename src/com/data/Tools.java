package com.data;

import manager.Manager;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class Tools {
	private DataBank db;

	public Tools(DataBank db) {
		this.db = db;
	}
	
	public void downloadData(String ID) {
		Manager t = new Manager(ID);
		t.start();
		//return t.getResult();
		
	}
	
	public void getDownloading(String ID) {
		Manager t = new Manager();
		t.printCode();
		t.printState();
	}
	
	public void importData(String file_path) {
		
	}
	
	public void exportData(String dir_path, String filename) {
		
	}
	
	public void addLabel(Label label) {
		
	}

	public void removeLabel(Label label) {
		
	}
	
	public ArrayList<Integer> analyse(Label analyseLabel) {
		ArrayList<Comment> comments = this.db.getCommentList();
		ArrayList<Label> labels = this.db.getLabelList();
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
