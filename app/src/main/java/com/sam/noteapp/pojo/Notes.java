package com.sam.noteapp.pojo;

import java.io.Serializable;


/**
 * Created by sam on 3/8/16.
 */


public class Notes implements Serializable {
    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getHeader() {
        return header;
    }

    public void setHeader(String header) {
        this.header = header;
    }

    public String getNote() {
        return note;
    }

    public void setNote(String note) {
        this.note = note;
    }

    private int id;
    private String header;
    private String note;
}
