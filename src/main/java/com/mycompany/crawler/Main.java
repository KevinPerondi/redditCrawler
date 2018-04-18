package com.mycompany.crawler;

import java.util.Scanner;

public class Main {

    public static void main(String[] args) {
        System.out.println("<username-password-clientID-clientSecret>");
        Scanner scan = new Scanner(System.in);
        String line = scan.nextLine();
        line = line.trim();
        String[] spliter = line.split("-");
        Client redditClient = new Client(spliter[0], spliter[1], spliter[2], spliter[3]);
    }
}
