package com.mycompany.graph;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.PrintWriter;
import java.text.ParseException;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Collections;
import java.util.Date;
import java.util.List;

public class Graph {

    //cada vértice será o autor de uma publicação ou comentário na rede
    private List<Edge> edges;
    private final List<Post> posts;
    private final List<Comment> comments;
    private final String communityName;
    private List<Post> postsTotal;
    private List<Comment> commentsTotal;

    public Graph(List<Post> posts, List<Comment> comments, String communityName) {
        this.edges = new ArrayList<>();
        this.posts = posts;
        this.comments = comments;
        this.communityName = communityName;
        this.postsTotal = new ArrayList<>();
        this.commentsTotal = new ArrayList<>();
        this.revertPostsList();
    }

    public String getCommunityName() {
        return communityName;
    }

    public List<Post> getPosts() {
        return posts;
    }

    public List<Comment> getComments() {
        return comments;
    }

    public List<Post> getPostsTotal() {
        return postsTotal;
    }

    public void setPostsTotal(List<Post> postsTotal) {
        this.postsTotal = postsTotal;
    }

    public List<Comment> getCommentsTotal() {
        return commentsTotal;
    }

    public void setCommentsTotal(List<Comment> commentsTotal) {
        this.commentsTotal = commentsTotal;
    }

    public List<Edge> getEdges() {
        return edges;
    }

    public void setEdges(List<Edge> edges) {
        this.edges = edges;
    }

    public boolean containsEdge(String source, String target) {
        for (Edge e : this.getEdges()) {
            if (e.getSource().equals(source) && e.getTarget().equals(target)) {
                return true;
            }
        }
        return false;
    }

    public void addEdge(String source, String target) {
        Edge e = new Edge(source, target);
        this.edges.add(e);
    }

    public List<Comment> getCommentsByPostID(String pID) {
        List<Comment> commts = new ArrayList<>();
        for (Comment c : this.getCommentsTotal()) {
            if (c.getPostID().equals(pID)) {
                commts.add(c);
            }
        }
        return commts;
    }

    public String getAuthorNameByParentID(List<Comment> cmts, String parentID) {
        for (Comment c : cmts) {
            if (c.getCommentID().equals(parentID)) {
                return c.getAuthor();
            }
        }
        return "";
    }

    public void revertPostsList() {
        Collections.reverse(this.getPosts());
    }

    public void removeFromList() {
        for (Post p : this.getPostsTotal()) {
            if (this.getPosts().contains(p)) {
                this.getPosts().remove(p);
            }
        }
    }

    public void printPostsDate() {
        for (Post p : this.getPosts()) {
            System.out.println(p.getPostData());
        }
    }

    public void getPostsByDate(Date d) {
        for (Post p : this.getPosts()) {
            if (p.getPostData().compareTo(d) == 0) {
                this.postsTotal.add(p);
            }
        }
    }

    public List<Post> concatLists(List<Post> l1, List<Post> l2) {
        if (!l2.isEmpty()) {
            for (Post p : l2) {
                l1.add(p);
            }
        }
        return l1;
    }

    public void get30days() throws ParseException {
        Post head = this.getPosts().remove(0);
        this.postsTotal.add(head);
        Date postDate = head.getPostData();
        Date dateInit = postDate;
        Date dateFinal = this.increseDate(postDate, 30);
        for (int i = 0; i < 30; i++) {
            this.getPostsByDate(postDate);
            postDate = increseDate(postDate, 1);
        }
        this.removeFromList();
        this.getCommentsByDateBetween(dateInit, dateFinal);
    }

    public void removeComments(List<Comment> comments) {
        for (Comment c : comments) {
            this.getComments().remove(c);
        }
    }

    public void getCommentsByDateBetween(Date dateInit, Date dateFinal) {
        for (Comment c : this.getComments()) {
            if (c.getCommentData().after(dateInit) && c.getCommentData().before(dateFinal)) {
                this.commentsTotal.add(c);
            }
        }
        this.removeComments(this.getCommentsTotal());
    }

    public void removeAllEdgesFromList(){
        this.edges.clear();
    }
    
    public void creatingEdges() {
        for (Post p : this.getPostsTotal()) {
            List<Comment> cmts = this.getCommentsByPostID(p.getId());
            if (!cmts.isEmpty()) {
                for (Comment c : cmts) {
                    if (!c.isIsAnswer()) {
                        this.addEdge(c.getAuthor(), p.getAuthor());
                    } else {
                        String targetName = this.getAuthorNameByParentID(cmts, c.getParentID());
                        if (!targetName.isEmpty()) {
                            this.addEdge(c.getAuthor(), targetName);
                        }
                    }
                }
            } else {
                this.addEdge(p.getAuthor(), "");
            }

        }
    }

    public void printEdges() {
        for (Edge e : this.getEdges()) {
            System.out.println(e.getSource() + "->" + e.getTarget());
        }
    }

    public void procedure() throws ParseException, FileNotFoundException{
        int count = 30;
        for (int i = 0; i < 12; i++) {
            this.get30days();
            this.creatingEdges();
            this.graphToCSV(this.getCommunityName() + count + "days");
            this.removeAllEdgesFromList();
            count += 30;
        }
    }
    
    public void graphToCSV(String fileName) throws FileNotFoundException {

        System.out.println("starting " + this.getCommunityName() + " to csv");

        //PrintWriter pw = new PrintWriter(new File("/home/todos/alunos/cm/a1552287/Downloads/" + fileName + "-graph.csv"));
        PrintWriter pw = new PrintWriter(new File("/home/suporte/Downloads/" + fileName + ".csv"));

        StringBuilder sb = new StringBuilder();

        for (Edge e : this.getEdges()) {
            if (e.getTarget().equals("")) {
                sb.append(e.getSource() + "\n");
            } else {
                sb.append(e.getSource() + ";" + e.getTarget() + "\n");
            }
        }
        pw.write(sb.toString());
        pw.close();
    }

    private Date increseDate(Date postDate, int valor) throws ParseException {
        Calendar c = Calendar.getInstance();
        c.setTime(postDate);
        c.add(Calendar.DATE, valor);
        Date dt = c.getTime();
        return dt;
    }

}
