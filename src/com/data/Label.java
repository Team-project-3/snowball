package com.data;

import java.util.ArrayList;

public class Label {
    private int id;
    private String name;
    private ArrayList<String> options;


    public Label() {
    }

    public Label(int id, String content, ArrayList<String> options) {
        this.id = id;
        this.name = content;
        this.options = options;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String content) {
        this.name = content;
    }

    public ArrayList<String> getOptions() {
        return options;
    }

    public void setOptions(ArrayList<String> options) {
        this.options = options;
    }

}
