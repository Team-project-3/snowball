package com.data;

import java.util.ArrayList;

public class DataBank {
	private static DataBank db = new DataBank();
    private ArrayList<Comment> commentList = new ArrayList<>();
    private ArrayList<Label> labelList = new ArrayList<>();
    private ArrayList<Conflict> conflictList = new ArrayList<>();
    
    private DataBank() {}
    public static DataBank getInstence() {
    	return db;
    }
    
    public ArrayList<Comment> getCommentList() {
    	return commentList;
    }
    
    public void setCommentList(ArrayList<Comment> commentList) {
    	this.commentList = commentList;
    }
    
   
    public boolean addComment(Comment comment) {
    	return commentList.add(comment);
    }
    
    public boolean removeComment(Comment comment) {
    	return commentList.remove(comment);
    }
    
    public ArrayList<Label> getLabelList() {
    	return labelList;
    }
    
    public void  setLabelList(ArrayList<Label> labelList ) {
    	this.labelList = labelList;
    }
    
    public boolean addLabel(Label label) {
    	return labelList.add(label);
    }
    
    public boolean removeLabel(Label label) {
    	return labelList.remove(label);
    }
    
    public boolean addConflict(Conflict conflict) {
    	return conflictList.add(conflict);
    }
    
    public boolean removeConflict(Conflict conflict) {
    	return conflictList.remove(conflict);
    }
    
	public ArrayList<Conflict> getConflictList() {
		return conflictList;
	}
	public void setConflictList(ArrayList<Conflict> conflictList) {
		this.conflictList = conflictList;
	}
}
