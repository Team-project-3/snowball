package com.data;

import java.util.ArrayList;

public class Comment {
    private  int id;
    private String content;
    private ArrayList<Integer> labelList;

    public Comment() {
    	labelList = new ArrayList<>();
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    @Override
    public String toString() {
        return "Comment{" +
                "id=" + id +
                ", content='" + content + '\'' +
                '}';
    }

    public ArrayList<Integer> getLabelList() {
        return labelList;
    }

//    public void setLabelArrayList(ArrayList<Int> labelArrayList) {
//        this.labelArrayList = labelArrayList;
//    }
}
