package com.data;

import java.util.ArrayList;

public class Label {
    private int id;
    private String content;
    private ArrayList<String> options;


    public Label() {
    	options = new ArrayList<String>();
    }

    public Label(int id, String content, ArrayList<String> options) {
        this.id = id;
        this.content = content;
        this.options = options;
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

    public ArrayList<String> getOptions() {
        return options;
    }

//    public void setOptions(ArrayList<String> options) {
//        this.options = options;
//    }

}
