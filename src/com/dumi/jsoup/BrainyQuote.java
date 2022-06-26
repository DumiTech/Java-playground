package com.dumi.jsoup;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.time.LocalDate;
import java.util.Random;


public class BrainyQuote {
    public static void main(String[] args) throws IOException {

        String path = "src/com/dumi/jsoup/reports/";
        LocalDate date = LocalDate.now();
        Random random = new Random();
        String filename = "BrainyQuote_" + date + "_" + Math.abs(random.nextInt()) + ".txt";

        String url = "https://www.brainyquote.com/authors/elon-musk-quotes";
        String userAgent = "Mozilla/5.0 (X11; Linux x86_64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/51.0.2704.103 Safari/537.36";
        int count = 0;

        Document document = Jsoup.connect(url).userAgent(userAgent).timeout(7000).get();
        Elements body = document.select("div.qbcol-c");

        System.out.println("\n    Elon Musk quotes.\n");

        try {
            BufferedWriter writer = new BufferedWriter(new FileWriter(path + filename));

            for (Element e: body.select("div.grid-item a.b-qt div")) {
                String quote = e.text();
                System.out.println(quote);
                System.out.println();
                writer.write(quote);
                writer.write("\n\n");
            }

            writer.close();

        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
