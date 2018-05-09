package com.mycompany.graph;

public class Community {

    private String communityName;
    private Graph graph;

    public Community(String communityName) {
        this.communityName = communityName;
        this.graph = new Graph();
    }

    public String getCommunityName() {
        return communityName;
    }

    public void setCommunityName(String communityName) {
        this.communityName = communityName;
    }

    public Graph getGraph() {
        return graph;
    }

    public void setGraph(Graph graph) {
        this.graph = graph;
    }

}
