package com.mycompany.graph;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.PrintWriter;
import java.util.ArrayList;
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
        if (!this.containsEdge(source, target)) {
            Edge e = new Edge(source, target);
            this.edges.add(e);
        } else {
            this.increseEdgeWeight(source, target);
        }
    }

    public void increseEdgeWeight(String source, String target) {
        for (Edge e : this.getEdges()) {
            if (e.getSource().equals(source) && e.getTarget().equals(target)) {
                e.incresesWeight();
            }
        }
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
        return null;
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
            }

        }
    }

    public void printEdges() {
        for (Edge e : this.getEdges()) {
            System.out.println(e.getSource() + "->" + e.getTarget() + "{" + e.getWeight() + "}");
        }
    }

    public void graphToCSV() throws FileNotFoundException {

        System.out.println("starting " + this.getCommunityName() + " to csv");

        PrintWriter pw = new PrintWriter(new File("/home/todos/alunos/cm/a1552287/Downloads/" + this.getCommunityName() + "-graph.csv"));

        StringBuilder sb = new StringBuilder();

        for (Edge e : this.getEdges()) {
            //com peso
            //sb.append(e.getSource()+";"+e.getTarget()+";"+e.getWeight()+"\n");
            //sem peso
            sb.append(e.getSource()+";"+e.getTarget()+"\n");
        }
        pw.write(sb.toString());
        pw.close();
    }

}
