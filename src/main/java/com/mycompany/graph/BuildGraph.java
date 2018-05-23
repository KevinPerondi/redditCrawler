package com.mycompany.graph;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class BuildGraph {

    public static void main(String[] args) throws FileNotFoundException {
        String pathPostFile = "/home/todos/alunos/cm/a1552287/Downloads/dados/Reddit-Output/golang-posts.csv";
        String pathCommentsFile = "/home/todos/alunos/cm/a1552287/Downloads/dados/Reddit-Output/golang-comments.csv";

        
        
        Scanner scan = new Scanner(new File(pathPostFile));
        //args[0] posts csv
        //Scanner scan = new Scanner(new File(args[0]));
        boolean skipFirstLine = true;

        List<Post> posts = new ArrayList<>();
        List<Comment> comments = new ArrayList<>();
        
        Graph graph = new Graph();
        
        String line = new String();
        while (scan.hasNextLine()) {
            if (skipFirstLine) {
                skipFirstLine = false;
            } else {
                //post atributos
                //0-Id	1-Author 2-Date	3-IsArchived 4-IsLocked 5-IsNsfw 6-IsSelfPost 7-SelfText
                line = scan.nextLine();
                System.out.println(line);
                String[] lineSplit = line.split(",");
                Post novoPost = new Post(lineSplit[1], lineSplit[0]);
                posts.add(novoPost);
            }
        }

        scan = new Scanner(new File(pathCommentsFile));
        //args[1] comments csv
        //Scanner scan = new Scanner(new File(args[1]));
        skipFirstLine = true;
        
        while (scan.hasNextLine()) {
            if (skipFirstLine) {
                skipFirstLine = false;
            } else {
                //comments atributos
                //0-Id 1-PostId	2-Author 3-Date	4-IsArchived 5-IsControversial 6-Score 7-Content
                line = scan.nextLine();
                System.out.println(line);
                String[] lineSplit = line.split(",");
                Comment novoComment = new Comment(lineSplit[2], lineSplit[0], lineSplit[1]);
                comments.add(novoComment);
            }
        }
        
        for (Post p : posts){
            for (Comment c : comments){
                if (p.getId().equals(c.getPostID())){
                    
                }
            }
        }
        
    }
}
