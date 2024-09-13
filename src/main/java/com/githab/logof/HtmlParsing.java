package com.githab.logof;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;
import java.io.File;
import java.io.IOException;

public class HtmlParsing {

    public String parse(String fileName) throws IOException {
        File input = new File(fileName);
        Document document = Jsoup.parse(input, "UTF-8", "http://localhost/");

        replacePBrWithBr(document);
        normalizeHtml(document);
        fixTableStructure(document);

        String markdown = convertToMarkdown(document);

        System.out.println(markdown);
        return markdown;
    }

    public static void replacePBrWithBr(Document document) {
        Elements pElements = document.select("p");
        for (Element p : pElements) {
            if (p.html().equals("<br>")) {
                p.replaceWith(new Element("br"));
            }
        }
    }

    public static void normalizeHtml(Document document) {
        // Remove class and style attributes from all elements
        Elements elements = document.select("*");
        for (Element element : elements) {
            element.removeAttr("class");
            element.removeAttr("style");
        }

        // Move text from <span>, <strong>, and <em> to parent element
        Elements spans = document.select("span, strong, em");
        for (Element span : spans) {
            span.parent().append(span.text());
            span.remove();
        }

        // Remove <colgroup> and <col> from tables
        Elements colgroups = document.select("colgroup");
        for (Element colgroup : colgroups) {
            colgroup.remove();
        }

        // Move text from <p> inside <td> to the parent <td>
        Elements tds = document.select("td");
        for (Element td : tds) {
            Elements ps = td.select("p");
            for (Element p : ps) {
                td.append(p.text());
                p.remove();
            }
        }

        // Move text from <p> inside <th> to the parent <th>
        Elements ths = document.select("th");
        for (Element th : ths) {
            Elements ps = th.select("p");
            for (Element p : ps) {
                th.append(p.text());
                p.remove();
            }
        }

        // Change <th> to <td>
        /*for (Element th : ths) {
            th.tagName("td");
        }*/
    }

    public static void fixTableStructure(Document document) {
        Elements tables = document.select("table");
        for (Element table : tables) {
            // Remove <thead> and <tbody>
            Elements theads = table.select("thead");
            for (Element thead : theads) {
                thead.unwrap();
            }
            Elements tbodies = table.select("tbody");
            for (Element tbody : tbodies) {
                tbody.unwrap();
            }
        }
    }

    public static String convertToMarkdown(Document document) {
        StringBuilder markdown = new StringBuilder();
        Elements elements = document.body().children();

        for (Element element : elements) {
            switch (element.tagName()) {
                case "p":
                    markdown.append(element.text()).append(System.lineSeparator());
                    break;
                case "a":
                    markdown.append("[").append(element.text()).append("](").append(element.attr("href"))
                            .append(")").append(System.lineSeparator()).append(System.lineSeparator());
                    break;
                case "ul":
                    for (Element li : element.children()) {
                        markdown.append("- ").append(li.text()).append(System.lineSeparator());
                    }
                    markdown.append(System.lineSeparator());
                    break;
                case "table":
                    for (Element tr : element.select("tr")) {
                        for (Element td : tr.select("th")) {
                            markdown.append("| **").append(td.text()).append("** ");
                        }
                        for (Element td : tr.select("td")) {
                            markdown.append("| ").append(td.text()).append(" ");
                        }
                        markdown.append("|").append(System.lineSeparator());
                    }
                    markdown.append(System.lineSeparator());
                    break;
                case "br":
                    markdown.append(System.lineSeparator());
                    break;
                default:
                    // Handle other tags if needed
                    break;
            }
        }

        return markdown.toString();
    }
}