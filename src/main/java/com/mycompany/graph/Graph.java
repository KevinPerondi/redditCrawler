package com.mycompany.graph;

import java.util.ArrayList;
import java.util.List;

public class Graph {

    //cada vértice será o autor de uma publicação ou comentário na rede
    private List<Edge> edges;
    private final List<Post> posts;
    private final List<Comment> comments;

    public Graph(List<Post> posts, List<Comment> comments) {
        this.edges = new ArrayList<>();
        this.posts = posts;
        this.comments = comments;
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
        }
    }

}
