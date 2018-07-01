package com.mycompany.graph;

public class Edge {

    private String source;
    private String target;
    private int weight;
    private final int start;
    private final int end;

    public Edge(String source, String target, int start, int end) {
        this.source = source;
        this.target = target;
        this.start = start;
        this.end = end;
        this.weight = 1;
    }

    public int getStart() {
        return start;
    }

    public int getEnd() {
        return end;
    }

    public int getWeight() {
        return weight;
    }

    public void setWeight(int weight) {
        this.weight = weight;
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
