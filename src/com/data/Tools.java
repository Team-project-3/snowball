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
	
	public void exportData(String dir_path, String filename) {
		
	}
	
	public void addLabel(Label label) {
		return;
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
