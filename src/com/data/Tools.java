package com.data;

import manager.Manager;

public class Tools {
	private DataBank db;

	public Tools(DataBank db) {
		this.db = db;
	}
	
	public int downloadData(String ID) {
		Manager t1 = new Manager(ID);
		t1.start();
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
		
	}
	
	public void analyse() {
		
	}

}
