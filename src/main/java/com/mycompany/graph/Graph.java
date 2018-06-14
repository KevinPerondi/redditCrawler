package com.mycompany.graph;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.PrintWriter;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Date;
import java.util.List;

public class Graph {

    //cada vértice será o autor de uma publicação ou comentário na rede
    private List<Edge> edges;
    private final List<Post> posts;
    private final List<Comment> comments;
    private final String communityName;

    public Graph(List<Post> posts, List<Comment> comments, String communityName) {
        this.edges = new ArrayList<>();
        this.posts = posts;
        this.comments = comments;
        this.communityName = communityName;
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
        for (Comment c : this.getComments()) {
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

    public List<Post> getPostsByDate(Date d) {
        List<Post> postsDay = new ArrayList<>();
        for (Post p : this.getPosts()) {
            if (p.getPostData().equals(d)) {
                postsDay.add(p);
            }
        }
        return postsDay;
    }

    public List<Post> concatLists(List<Post> l1, List<Post> l2) {
        if (!l2.isEmpty()) {
            for (Post p : l2) {
                l1.add(p);
            }
        }
        return l1;
    }

    public void timeLineMovingWindow() {
        int iterator = 0;
        Post head = this.getPosts().remove(iterator);
        Date postDate = head.getPostData();
        List<Post> postsTotal = new ArrayList<>();
        postsTotal.add(head);
        for (int i = 0; i < 30; i++) {
            List<Post> p = getPostsByDate(postDate);
            postDate = increseDate(postDate);
        }

    }

    public void creatingEdges() {
        for (Post p : this.getPosts()) {
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

    public void graphToCSV() throws FileNotFoundException {

        System.out.println("starting " + this.getCommunityName() + " to csv");

        //PrintWriter pw = new PrintWriter(new File("/home/todos/alunos/cm/a1552287/Downloads/" + this.getCommunityName() + "-graph.csv"));
        PrintWriter pw = new PrintWriter(new File("/home/suporte/Downloads/" + this.getCommunityName() + "-graph.csv"));

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

    private Date increseDate(Date postDate) throws ParseException {
        DateFormat df = new SimpleDateFormat("yyyy-MM-dd");
        return df.parse(LocalDate.parse(postDate.toString()).plusDays(1).toString());
    }

}
