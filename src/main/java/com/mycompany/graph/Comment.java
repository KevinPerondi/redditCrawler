package com.mycompany.graph;

public class Comment {
    private String author;
    private String commentID;
    private String postID;
    private String parentID;
    private boolean isAnswer;

    public Comment(String author, String commentID, String postID, String parentID, boolean isAnswer) {
        this.author = author;
        this.commentID = commentID;
        this.postID = postID;
        this.parentID = parentID;
        this.isAnswer = isAnswer;
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

    public String getParentID() {
        return parentID;
    }

    public void setParentID(String parentID) {
        this.parentID = parentID;
    }

    public boolean isIsAnswer() {
        return isAnswer;
    }

    public void setIsAnswer(boolean isAnswer) {
        this.isAnswer = isAnswer;
    }
    
}
