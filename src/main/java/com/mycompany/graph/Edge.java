package com.mycompany.graph;

public class Edge {

    private String source;
    private String target;
    private int weight;

    public Edge(String source, String target) {
        this.source = source;
        this.target = target;
        this.weight = 1;
    }

    public int getWeight() {
        return weight;
    }

    public void setWeight(int weight) {
        this.weight = weight;
    }

    public void incresesWeight() {
        this.weight += 1;
    }

    public String getSource() {
        return source;
    }

    public void setSource(String source) {
        this.source = source;
    }

    public String getTarget() {
        return target;
    }

    public void setTarget(String target) {
        this.target = target;
    }
}
