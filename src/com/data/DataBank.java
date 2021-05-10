package com.data;

import java.util.ArrayList;

public class DataBank {
<<<<<<< HEAD
    private int commentSize = 0;
    private ArrayList<Comment> commentArrayList = new ArrayList<>();//存储评论数据
    private ArrayList<Label> labelArrayList = new ArrayList<>();

    public DataBank() {
=======
    private ArrayList<Comment> commentList;
    private ArrayList<Label> labelList;
    
    public ArrayList<Comment> getCommentList() {
    	return commentList;
>>>>>>> dev
    }
    
    public boolean addComment(Comment comment) {
    	return commentList.add(comment);
    }
<<<<<<< HEAD

    public void setCommentSize(int commentSize) {
        this.commentSize = this.commentArrayList.size();
=======
    
    public boolean removeComment(Comment comment) {
    	return commentList.remove(comment);
>>>>>>> dev
    }
    
    public ArrayList<Label> getLabelList() {
    	return labelList;
    }
    
    public boolean addLabel(Label label) {
    	return labelList.add(label);
    }
    
<<<<<<< HEAD
    public ArrayList<Label> getLabelList() {
		return this.labelArrayList;
    }

    public boolean deleteLabel(Label label){
        return true;
=======
    public boolean removeLabel(Label label) {
    	return labelList.remove(label);
>>>>>>> dev
    }
}
