package com.mycompany.graph;

import java.io.File;
import java.io.FileNotFoundException;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Scanner;

public class BuildGraph {

    public static void main(String[] args) throws FileNotFoundException {

        String filePath = "/home/suporte/Downloads/Reddit-Output-all/";
        //String filePath = "/home/todos/alunos/cm/a1552287/Downloads/Reddit-Output-all/";
        //String[] communityNames = {"cpp", "csharp", "golang", "java", "julia", "kotlin", "php", "python", "ruby", "scala"};
        String[] communityNames = {"csharp"};

        Scanner postScan;
        Scanner commentScan;

        DateFormat df = new SimpleDateFormat("yyyy-MM-dd");

        boolean skipFirstLine = true;

        for (String communityName : communityNames) {

            String pathPost = filePath + communityName + "-posts.csv";
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
                } else {
                    //post atributos
                    //0-Id 1-Author 2-Date 3-IsArchived 4-IsLocked 5-IsNsfw 6-IsSelfPost 7-SelfText
                    String[] lineSplit = line.split(",");
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
            }

            skipFirstLine = true;
            while (commentScan.hasNextLine()) {
                line = commentScan.nextLine();
                if (skipFirstLine) {
                    skipFirstLine = false;
                } else {
                    //comments atributos
                    //0-Id 1-PostId 2-ParentId 3-IsAnswer 4-Author 5-Date 6-IsArchived 7-IsControversial 8-Score 9-Content
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

            System.out.println("Creating Graph for {" + communityName + "}");
            Graph graph = new Graph(posts, comments, communityName);
            graph.creatingEdges();
            //graph.printEdges();
            graph.graphToCSV();
        }

    }
}
