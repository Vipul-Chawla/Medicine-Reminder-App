package com.example.project7;

public class yogamodel {
    private String engName;
    private String sanName;
    private String link;

    public yogamodel(String engName, String sanName, String link) {
        this.engName = engName;
        this.sanName = sanName;
        this.link = link;
    }

    public String getEngName() {
        return engName;
    }

    public void setEngName(String engName) {
        this.engName = engName;
    }

    public String getSanName() {
        return sanName;
    }

    public void setSanName(String sanName) {
        this.sanName = sanName;
    }

    public String getLink() {
        return link;
    }

    public void setLink(String link) {
        this.link = link;
    }
}
