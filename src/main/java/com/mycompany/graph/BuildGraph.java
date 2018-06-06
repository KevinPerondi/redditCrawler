package com.mycompany.graph;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class BuildGraph {

    public static void main(String[] args) throws FileNotFoundException {

        String filePath = "/home/suporte/Downloads/Reddit-Output-all/";
        String[] communityNames = {"cpp", "csharp", "golang", "java", "julia", "kotlin", "php", "python", "ruby", "scala"};

        Scanner postScan;
        Scanner commentScan;

        boolean skipFirstLine = true;

        for (String communityName : communityNames) {

            String pathPost = filePath + communityName + "-posts.csv";
            String pathComment = filePath + communityName + "-comments.csv";
            postScan = new Scanner(new File(pathPost));
            commentScan = new Scanner(new File(pathComment));

            List<Post> posts = new ArrayList<>();
            List<Comment> comments = new ArrayList<>();

            String line = new String();

            while (postScan.hasNextLine()) {
                if (skipFirstLine) {
                    skipFirstLine = false;
                } else {
                    //post atributos
                    //0-Id 1-Author 2-Date 3-IsArchived 4-IsLocked 5-IsNsfw 6-IsSelfPost 7-SelfText
                    line = postScan.nextLine();
                    String[] lineSplit = line.split(",");
                    Post novoPost = new Post(lineSplit[1], lineSplit[0]);
                    posts.add(novoPost);
                }
            }

            skipFirstLine = true;

            while (commentScan.hasNextLine()) {
                if (skipFirstLine) {
                    skipFirstLine = false;
                } else {
                    //comments atributos
                    //0-Id 1-PostId 2-ParentId 3-IsAnswer 4-Author 5-Date 6-IsArchived 7-IsControversial 8-Score 9-Content
                    line = commentScan.nextLine();
                    String[] lineSplit = line.split(",");

                    boolean status;

                    if (lineSplit[3].equals("true")) {
                        status = true;
                    }else {
                    status = false;
                    }

                    Comment novoComment = new Comment(lineSplit[2], lineSplit[0], lineSplit[1], lineSplit[2], status);
                    comments.add(novoComment);
                }
            }
            skipFirstLine = true;

            Graph graph = new Graph(posts,comments);
            //proximo passo: para cada post, pegar os comentarios e gerar as arestas...(dentro da classe Graph)
        }

    }
}
