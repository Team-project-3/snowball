package com.data;

import java.util.ArrayList;

public class DataBank {
    private ArrayList<Comment> commentList;
    private ArrayList<Label> labelList;
    
    public DataBank() {
    	commentList = new ArrayList<>();
    	labelList = new ArrayList<>();
    }
    
    public ArrayList<Comment> getCommentList() {
    	return commentList;
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
    
    public void setLabelList(ArrayList<Label> labelList) {
    	this.labelList=labelList;
    }
    
    public boolean addLabel(Label label) {
    	return labelList.add(label);
    }
    
    public boolean removeLabel(Label label) {
    	return labelList.remove(label);
    }
}
