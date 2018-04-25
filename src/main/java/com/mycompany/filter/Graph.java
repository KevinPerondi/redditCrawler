package com.mycompany.filter;

import java.util.ArrayList;
import java.util.List;

public class Graph {

    //cada vértice será o autor de uma publicação ou comentário na rede
    private List<Edge> edges;
    private List<Vertex> vertex;

    public Graph() {
        this.edges = new ArrayList<>();
        this.vertex = new ArrayList<>();
    }

    public List<Vertex> getVertex() {
        return vertex;
    }

    public void setVertex(List<Vertex> vertex) {
        this.vertex = vertex;
    }

    public List<Edge> getEdges() {
        return edges;
    }

    public void setEdges(List<Edge> edges) {
        this.edges = edges;
    }

    public boolean containsOnVertex(String authorName) {
        for (Vertex v : this.getVertex()) {
            if (v.getAuthor().equals(authorName)) {
                return true;
            }
        }
        return false;
    }

    public void addVertex(String authorName) {
        if (!this.containsOnVertex(authorName)) {
            Vertex v = new Vertex(authorName);
            this.vertex.add(v);
        }
    }

    public boolean containsOnEdges(String firstAuthorName, String secondAuthorName) {
        for (Edge e : this.getEdges()) {
            if (e.getV1().getAuthor().equals(firstAuthorName) && e.getV2().getAuthor().equals(secondAuthorName)) {
                return true;
            } else if (e.getV1().getAuthor().equals(secondAuthorName) && e.getV2().getAuthor().equals(firstAuthorName)) {
                {
                    return true;
                }
            }
        }
        return false;
    }

    public void addEdge(Vertex firstVertex, Vertex secondVertex) {
        if (!this.containsOnEdges(firstVertex.getAuthor(), secondVertex.getAuthor())) {
            Edge e = new Edge(firstVertex, secondVertex);
            this.edges.add(e);
        }
    }

}
