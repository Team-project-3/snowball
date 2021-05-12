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
	
	public ArrayList<Integer> analyse(Label analyseLabel) {
		ArrayList<Comment> comments = this.db.getCommentList();
		ArrayList<Label> labels = this.db.getLabelList();
		int index = labels.indexOf(analyseLabel);
		
		// 鍒濆鍖杔abel_sum
		ArrayList<Integer> label_sum = new ArrayList<>();
		for(int i=0; i < analyseLabel.getOptions().size(); ++i) {
			label_sum.add(0);
		}
		
		// 缁熻
		for(Comment comment : comments) {
			ArrayList<Integer> arrayList = comment.getLabelList();
			int option = arrayList.get(index);
			label_sum.set(option, label_sum.get(option)+1);
		}
		
		return label_sum;
	}

}
