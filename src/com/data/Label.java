package com.data;

public class Label {
    private int id;
    private String content;
    private String[] options;


    public Label() {
    }

    public Label(int id, String content, String[] options) {
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

    public String[] getOptions() {
        return options;
    }

    public void setOptions(String[] options) {
        this.options = options;
    }

}
