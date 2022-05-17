package com.dumi.jsoup;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;

public class HackerNews {
    public static void main(String[] args) throws IOException {
        String url = "https://news.ycombinator.com/";
        String userAgent = "Mozilla/5.0 (X11; Linux x86_64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/51.0.2704.103 Safari/537.36";
        Document doc = Jsoup.connect(url).userAgent(userAgent).timeout(9000).get();
        Elements body = doc.select("table.itemlist");

        System.out.println("\nHacker News \n");

        ArrayList<String> titles = new ArrayList<>();
        ArrayList<String> details = new ArrayList<>();

        for (Element e: body.select("td.title a.titlelink")) {
            String title = e.text();
            titles.add(title);
        }

        for (Element e: body.select("td.subtext")) {
            String detail = e.text();
            details.add(detail);
        }

        int index = 0;
        while (index < titles.size()) {
            System.out.println(titles.get(index));
            System.out.println(details.get(index) + "\n");
            index+=1;
        }

        try {
            BufferedWriter writer = new BufferedWriter(new FileWriter("src/com/dumi/jsoup/reports/outputHackerNews.txt"));

            for (int i = 0; i < titles.size(); ) {
                writer.write("\n\n" + titles.get(i) + "\n" + details.get(i));
                i++;
            }
            writer.close();

        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
