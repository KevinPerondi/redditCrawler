package com.mycompany.graph;

public class Comment {
    private String author;
    private String commentID;
    private String postID;

    public Comment(String author, String commentID, String postID) {
        this.author = author;
        this.commentID = commentID;
        this.postID = postID;
    }

    public String getAuthor() {
        return author;
    }

    public void setAuthor(String author) {
        this.author = author;
    }

    public String getCommentID() {
        return commentID;
    }

    public void setCommentID(String commentID) {
        this.commentID = commentID;
    }

    public String getPostID() {
        return postID;
    }

    public void setPostID(String postID) {
        this.postID = postID;
    }
    
}
