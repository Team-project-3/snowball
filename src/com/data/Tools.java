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
			list.remove(label);
		}
		else {
			System.out.println("标签列表中没有这个标签对象！");
		}
		db.setLabelList(list);
	}
	
	public Map<Label, ArrayList<Integer>> analyse() {
		Map<Label, ArrayList<Integer>> table = new HashMap<>();
		ArrayList<Comment> comments = this.db.getCommentList();
		ArrayList<Label> labels = this.db.getLabelList();
		
		int size = labels.size();
		for (int i=0; i<size; ++i) {
			// 鍒濆鍖杔abelSum
			ArrayList<Integer> labelSum = new ArrayList<>();
			int len = labels.get(i).getOptions().size();
			for(int j=0; j < len; ++j) {
				labelSum.add(0);
			}
			
			// 缁熻
			for (Comment comment : comments) {
				labelSum.set(comment.getLabelList().get(i), labelSum.get(comment.getLabelList().get(i))+1);
			}
			
			table.put(labels.get(i), labelSum);
		}
		
		return table;
	}

}
