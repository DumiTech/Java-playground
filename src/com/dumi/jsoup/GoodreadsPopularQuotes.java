package com.dumi.jsoup;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Random;


public class GoodreadsPopularQuotes {

    public static void main(String[] args) {

        String path = "src/com/dumi/jsoup/reports/";
        LocalDate date = LocalDate.now();
        Random random = new Random();
        String filename = "goodreadsQuotes_" + date + "_" + Math.abs(random.nextInt()) + ".txt";

        String baseUrl = "https://www.goodreads.com/quotes?page=";
        String userAgent = "Mozilla/5.0 (Macintosh; Intel Mac OS X 10_11_2) AppleWebKit/601.3.9 (KHTML, like Gecko) Version/9.0.2 Safari/601.3.9";

//        int maxPages = 100;
        int maxPages = 3;
        int count = 1;

        ArrayList<String> container = new ArrayList();

        try {
            while (count < maxPages) {

                String url = baseUrl + count;

                Document doc = Jsoup.connect(url).userAgent(userAgent).timeout(30000).get();

                Elements body = doc.select("div.quotes");

                for (Element e: body.select("div.quote")) {
                    String quote = e.select("div.quoteText").text();
                    String author = e.select("span.authorOrTitle").text();
                    System.out.println(quote);
                    System.out.println(author);
                    System.out.println();

                    container.add("\n\n" + quote + "\n" + author);
                }
                count += 1;
            }
        } catch (IOException e) {
            e.printStackTrace();
        }

        try {
            BufferedWriter writer = new BufferedWriter(new FileWriter(path+filename));
            for (String block: container) {
                writer.write(block);
            }
            writer.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
