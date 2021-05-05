package com.data;

import java.util.ArrayList;

public class DataBank {
    private int commentSize = 0;
    private ArrayList<Comment> commentArrayList = new ArrayList<>();//存储评论数据
    private ArrayList<Label> labelArrayList = new ArrayList<>();

    public DataBank() {
    }

    public int getCommentSize() {
        return commentSize;
    }

    public void setCommentSize(int commentSize) {
        this.commentSize = this.commentArrayList.size();
    }


    public ArrayList<Comment> getCommentArrayList() {
        return commentArrayList;
    }

    public void setCommentArrayList(ArrayList<Comment> commentArrayList) {
        this.commentArrayList = commentArrayList;
        this.commentSize = commentArrayList.size();
    }
    
    public ArrayList<Label> getLabelList() {
		return this.labelArrayList;
    }

    public boolean deleteLabel(Label label){
        return true;
    }
}
