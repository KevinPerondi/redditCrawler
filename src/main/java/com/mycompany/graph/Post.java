package com.mycompany.graph;

import java.util.Date;

public class Post {
    private String author;
    private String id;
    private Date postDate;

    public Post(String author, String id, Date posDate) {
        this.author = author;
        this.id = id;
        this.postDate = posDate;
    }

    public String getAuthor() {
        return author;
    }

    public void setAuthor(String author) {
        this.author = author;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public Date getPostData() {
        return postDate;
    }

    public void setPostData(Date postData) {
        this.postDate = postData;
    }
    
    public void printPost(){
        System.out.println(this.getId()+" "+this.getAuthor()+" "+this.getPostData());
    }
}
