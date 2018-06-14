/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.mycompany.graph;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.Instant;
import java.time.LocalDate;
import java.util.Date;

/**
 *
 * @author suporte
 */
public class teste_class {

    public static void main(String[] args) throws ParseException {
        String date = "2018-02-28";

        DateFormat df = new SimpleDateFormat("yyyy-MM-dd");
        Date d = df.parse(date);

        System.out.println(d);

        Date beet = df.parse("2018-03-23");
        
        Date d2 = df.parse(LocalDate.parse(date).plusDays(30).toString());
        System.out.println(d2);
        
        
        System.out.println(beet);
        
        if (beet.after(d) && beet.before(d2)){
            System.out.println("tamemu?");
        }
    }
}
