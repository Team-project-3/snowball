package com.data;

import java.util.ArrayList;

public class DataBank {
    private int commentSize = 0;
    private ArrayList<Comment> commentArrayList = new ArrayList<>();//存储评论数据

    public DataBank() {
    }

    public int getCommentSize() {
        return commentSize;
    }

    public void setCommentSize(int commentSize) {
        this.commentSize = commentSize;
    }


    public ArrayList<Comment> getCommentArrayList() {
        return commentArrayList;
    }

    public void setCommentArrayList(ArrayList<Comment> commentArrayList) {
        this.commentArrayList = commentArrayList;
        this.commentSize = commentArrayList.size();
    }

    public boolean deleteLabel(Label label){
        return true;
    }
}
