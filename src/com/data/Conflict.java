package com.data;

import java.util.ArrayList;

public class Conflict {
	private int commentIndex;
	private ArrayList<ArrayList<Integer>> optionCount;
	
	public Conflict(int commentIndex, ArrayList<ArrayList<Integer>> optionCount) {
		this.commentIndex = commentIndex;
		this.optionCount = optionCount;
	}
	public Conflict() {}
	
	public ArrayList<ArrayList<Integer>> getOptionCount() {
		return optionCount;
	}
	public void setOptionCount(ArrayList<ArrayList<Integer>> optionCount) {
		this.optionCount = optionCount;
	}
	
	public int getCommentIndex() {
		return commentIndex;
	}
	public void setCommentIndex(int commentIndex) {
		this.commentIndex = commentIndex;
	}
	
}
