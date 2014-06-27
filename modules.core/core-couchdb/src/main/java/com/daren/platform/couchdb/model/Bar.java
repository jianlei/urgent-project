package com.daren.platform.couchdb.model;

/**
 * Created by dell on 14-3-20.
 */
import org.lightcouch.Document;
public class Bar extends Document {
    private String bar;

    public String getBar() {
        return bar;
    }

    public void setBar(String bar) {
        this.bar = bar;
    }

    @Override
    public String toString() {
        return "Bar [bar=" + bar + "]";
    }
}