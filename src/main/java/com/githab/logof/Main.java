package com.githab.logof;

import com.githab.logof.domain.Table;
import java.io.IOException;
import java.util.Arrays;

public class Main {
    public static void main(String[] args) {
        if (args.length == 0) {
            System.out.println("Please provide at least one HTML file as an argument.");
            return;
        }


        HtmlParsing htmlParsing = new HtmlParsing();
        MarkDownParsing markDownParsing = new MarkDownParsing();

        Arrays.asList(args).forEach(file -> {
            try {
                Table table = markDownParsing.parsing(htmlParsing.parse(file));

                System.out.println(CreateSqlScript.generateSql(table));
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        });
    }
}