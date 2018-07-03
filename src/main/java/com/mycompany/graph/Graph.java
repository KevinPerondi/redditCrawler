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
    private Date startDate;
    private Date momentDate;
    private List<Node> nodeLists;
    private int start;
    private int end;

    public Graph(List<Post> posts, List<Comment> comments, String communityName) {
        this.edges = new ArrayList<>();
        this.posts = posts;
        this.comments = comments;
        this.communityName = communityName;
        this.postsTotal = new ArrayList<>();
        this.commentsTotal = new ArrayList<>();
        this.nodeLists = new ArrayList<>();
        this.start = 0;
        this.end = 30;
        this.revertPostsList();
        this.getStartDateFromPosts();
    }

    public String getCommunityName() {
        return communityName;
    }

    public void getStartDateFromPosts() {
        Post head = this.getPosts().remove(0);
        this.postsTotal.add(head);
        Node node = new Node(head.getAuthor(), this.start, this.end);
        this.nodeLists.add(node);
        this.setStartDate(head.getPostData());
        this.setMomentDate(head.getPostData());
    }

    public List<Node> getNodeLists() {
        return nodeLists;
    }

    public void setNodeLists(List<Node> nodeLists) {
        this.nodeLists = nodeLists;
    }

    public int getStart() {
        return start;
    }

    public void setStart(int start) {
        this.start = start;
    }

    public int getEnd() {
        return end;
    }

    public void setEnd(int end) {
        this.end = end;
    }

    public boolean containsPostOnTotal(String author) {
        for (Node n : this.getNodeLists()) {
            if (n.getName().equals(author)) {
                return true;
            }
        }
        return false;
    }

    public Date getStartDate() {
        return startDate;
    }

    public void setStartDate(Date startDate) {
        this.startDate = startDate;
    }

    public Date getMomentDate() {
        return momentDate;
    }

    public void setMomentDate(Date momentDate) {
        this.momentDate = momentDate;
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

    public void increseEdgeWeight(String source, String target) {
        for (Edge e : this.getEdges()) {
            if (e.getSource().equals(source) && e.getTarget().equals(target)) {
                e.setWeight(e.getWeight() + 1);
            }
        }
    }

    public void addEdge(String source, String target) {
        if (!this.containsEdge(source, target)) {
            Edge e = new Edge(source, target, this.start, this.end);
            this.edges.add(e);
        } else {
            this.increseEdgeWeight(source, target);
        }
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

    public boolean compareDates(Date d1, Date d2) {
        int d1Dia = d1.getDate();
        int d2Dia = d2.getDate();

        int d1Mes = d1.getMonth();
        int d2Mes = d2.getMonth();

        int d1Ano = d1.getYear();
        int d2Ano = d2.getYear();

        if (d1Dia == d2Dia && d1Mes == d2Mes && d1Ano == d2Ano) {
            return true;
        } else {
            return false;
        }
    }

    public void getPostsByDate(Date d) {

        for (Post p : this.getPosts()) {
            if (this.compareDates(p.getPostData(), d)) {
                this.postsTotal.add(p);
                if (!this.containsPostOnTotal(p.getAuthor())) {
                    Node n = new Node(p.getAuthor(), this.start, this.end);
                    this.nodeLists.add(n);
                }
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
        Date dateInit = this.getMomentDate();
        for (int i = 0; i < 30; i++) {
            this.getPostsByDate(this.getMomentDate());
            this.setMomentDate(this.increseDate(this.getMomentDate(), 1));
        }
        Date dateFinal = this.getMomentDate();
        //this.removeFromList();
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
                if (!this.containsPostOnTotal(c.getAuthor())) {
                    Node n = new Node(c.getAuthor(), this.start, this.end);
                    this.nodeLists.add(n);
                }
            }
        }
        //this.removeComments(this.getCommentsTotal());
    }

    public void removeAllEdgesFromList() {
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
            }

        }
    }

    public void printEdges() {
        for (Edge e : this.getEdges()) {
            System.out.println(e.getSource() + "->" + e.getTarget());
        }
    }

    public void procedure() throws ParseException, FileNotFoundException {
        int count = 30;
        for (int i = 0; i < 12; i++) {
            this.get30days();
            this.nodesToCSV(this.getCommunityName() + count + "Nodes", i * 30, (i + 1) * 30);
            this.creatingEdges();
            this.relationsToCSV(this.getCommunityName() + count + "Relations", i * 30, (i + 1) * 30);
            //this.removeAllEdgesFromList();
            count += 30;
            this.start += 30;
            this.end += 30;
        }
    }

    public void relationsToCSV(String fileName, int start, int end) throws FileNotFoundException {

        System.out.println("starting " + fileName + " to csv");

        //PrintWriter pw = new PrintWriter(new File("/home/todos/alunos/cm/a1552287/Downloads/" + fileName + ".csv"));
        //PrintWriter pw = new PrintWriter(new File("/home/kevin/Downloads/" + fileName + ".csv"));
        PrintWriter pw = new PrintWriter(new File("/home/suporte/Downloads/" + this.getCommunityName() + "/" + fileName + ".csv"));

        StringBuilder sb = new StringBuilder();

        sb.append("Source;Target;Weight;Type;Start;End\n");

        for (Edge e : this.getEdges()) {
            sb.append(e.getSource() + ";" + e.getTarget() + ";" + e.getWeight() + ";\"directed\";" + e.getStart() + ";" + e.getEnd() + "\n");
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

    private void nodesToCSV(String fileName, int start, int end) throws FileNotFoundException {
        System.out.println("starting " + fileName + " to csv");

        //PrintWriter pw = new PrintWriter(new File("/home/todos/alunos/cm/a1552287/Downloads/" + fileName + ".csv"));
        //PrintWriter pw = new PrintWriter(new File("/home/kevin/Downloads/" + fileName + ".csv"));
        PrintWriter pw = new PrintWriter(new File("/home/suporte/Downloads/" + this.getCommunityName() + "/" + fileName + ".csv"));

        StringBuilder sb = new StringBuilder();

        sb.append("Id;Label;Start;End\n");

        for (Node node : this.getNodeLists()) {
            sb.append(node.getName() + ";" + node.getName() + ";" + node.getStart() + ";" + node.getEnd() + "\n");
        }

        pw.write(sb.toString());
        pw.close();
    }

}
