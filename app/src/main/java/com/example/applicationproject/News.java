package com.example.applicationproject;

import java.util.Date;

// more of just a struct for news to be stored
public class News {
    private String header;
    private String body;
    private String date;

    /**
     * (H)eader will be the title above the news.
     * (B)ody is the text of the news itself.
     * And (D)ate will be automatic (no input from user): the date it was written.
     * @param h
     * @param b
     * @param d
     */
    News(String h, String b, String d){
        header = h;
        body = b;
        date = d;
    }

    public String getHeader() {
        return header;
    }

    public void setHeader(String header) {
        this.header = header;
    }

    public String getBody() {
        return body;
    }

    public void setBody(String body) {
        this.body = body;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }
}
