package com.mycompany.graph;

import java.io.File;
import java.io.FileNotFoundException;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Scanner;

public class BuildGraph {

    public static void main(String[] args) throws FileNotFoundException, ParseException {

        String filePath = "/home/suporte/Downloads/DataSet-pushshift-full/";
        //String filePath = "/home/todos/alunos/cm/a1552287/Downloads/DataSet-pushshift-full/";
        //String[] communityNames = {"cpp", "csharp", "golang", "java", "julia", "kotlin", "php", "python", "ruby", "scala"};
        String[] communityNames = {"csharp"};

        Scanner postScan;
        Scanner commentScan;

        DateFormat df = new SimpleDateFormat("yyyy-MM-dd");

        boolean skipFirstLine = true;

        for (String communityName : communityNames) {

            String pathPost = filePath + communityName + "-submissions.csv";
            String pathComment = filePath + communityName + "-comments.csv";
            postScan = new Scanner(new File(pathPost));
            commentScan = new Scanner(new File(pathComment));

            List<Post> posts = new ArrayList<>();
            List<Comment> comments = new ArrayList<>();

            String line = new String();

            List<String> deletedPostsID = new ArrayList<>();

            while (postScan.hasNextLine()) {
                line = postScan.nextLine();
                if (skipFirstLine) {
                    skipFirstLine = false;
                } else if (line.isEmpty()) {
                    continue;
                } else {
                    //post atributos
                    //0-Id 1-Author 2-Date 3-IsLocked 4-IsSelfPost 5-Score 6-SelfText
                    try {
                        String[] lineSplit = line.split(",");
                        if (lineSplit[0].length() == 6) {
                            if ((!lineSplit[1].equals("[deleted]")) && (!lineSplit[1].equals("AutoModerator"))) {
                                try {
                                    Date d = df.parse(lineSplit[2]);
                                    Post novoPost = new Post(lineSplit[1], lineSplit[0], d);
                                    posts.add(novoPost);
                                } catch (Exception e) {
                                    System.out.println(e.getMessage());
                                }
                            } else {
                                deletedPostsID.add(lineSplit[0]);
                            }
                        }
                    } catch (Exception e) {
                        System.out.println(e.getMessage());
                    }
                }
            }

            skipFirstLine = true;
            while (commentScan.hasNextLine()) {
                line = commentScan.nextLine();
                if (skipFirstLine) {
                    skipFirstLine = false;
                } else if (line.isEmpty()) {
                    continue;
                } else {
                    //comments atributos
                    //0-Id 1-PostId 2-ParentId 3-IsAnswer 4-Author 5-Date 6-Score 7-Content
                    String[] lineSplit = line.trim().split(",");

                    if (lineSplit.length >= 10 && (!deletedPostsID.contains(lineSplit[1])) && (!lineSplit[4].equals("[deleted]"))
                            && (!lineSplit[4].equals("AutoModerator"))) {
                        boolean status;

                        if (lineSplit[3].equals("true")) {
                            status = true;
                        } else {
                            status = false;
                        }

                        try {
                            Date d = df.parse(lineSplit[5]);
                            Comment novoComment = new Comment(lineSplit[4], lineSplit[0], lineSplit[1], lineSplit[2], status, d);
                            comments.add(novoComment);
                        } catch (Exception e) {
                            System.out.println(e.getMessage());
                        }

                    }

                }
            }
            skipFirstLine = true;
            System.out.println(posts.get(0).getPostData());
            System.out.println("Creating Graph for {" + communityName + "}");
            System.out.println("Post size: " + posts.size());
            System.out.println("Comments size: " + comments.size());

            Graph graph = new Graph(posts, comments, communityName);

            graph.procedure();
            
            /*graph.get30days();
            graph.creatingEdges();
            graph.graphToCSV("csharp-30days");
            graph.removeAllEdgesFromList();

            graph.get30days();
            graph.creatingEdges();
            graph.graphToCSV("csharp-60days");
            graph.removeAllEdgesFromList();*/
            
            
        }

    }
}
