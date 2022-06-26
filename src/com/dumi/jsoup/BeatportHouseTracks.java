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

public class BeatportHouseTracks {

    public static void main(String[] args) throws  IOException{

        String path = "src/com/dumi/jsoup/reports/";
        LocalDate date = LocalDate.now();
        Random random = new Random();
        String filename = "HouseTracks_" + date + "_" + Math.abs(random.nextInt()) + ".txt";

        String url = "https://www.beatport.com/genre/house/5/tracks?per-page=150";
        String userAgent = "Mozilla/5.0 (Macintosh; Intel Mac OS X 10_11_2) AppleWebKit/601.3.9 (KHTML, like Gecko) Version/9.0.2 Safari/601.3.9";
        Document doc = Jsoup.connect(url).userAgent(userAgent).timeout(5000).get();
        String docTitle = doc.title();
        System.out.println();
        System.out.println(docTitle);
        System.out.println("Parsed url: " + url);

        Elements body = doc.select("ul.bucket-items.ec-bucket");
        System.out.println("Titles: " + body.select("li").size() + "\n");

        ArrayList<String> container = new ArrayList<>();

        for (Element e: body.select("li.bucket-item.ec-item.track")) {
            String title = e.select("p.buk-track-title").text();
            String artist = e.select("p.buk-track-artists").text();
            String remixers = e.select("p.buk-track-remixers").text();
            if (remixers == "") {
                remixers = "-";
            }
            String label = e.select("p.buk-track-labels").text();

            System.out.println("\nTitle: " + title);
            System.out.println("Artist: " + artist);
            System.out.println("Remixers: " + remixers);
            System.out.println("Label: " + label);

            container.add("\nTitle: " + title + "\nArtist: " + artist + "\nRemixers: " + remixers + "\nLabel: " + label + "\n");
        }

        try {
            BufferedWriter writer = new BufferedWriter(new FileWriter(path + filename));
            writer.write("\n"+ docTitle);
            writer.write("\nParsed url: " + url + "\n");

            for (String block: container) {
                writer.write(block);
            }
            writer.close();
        }
        catch (IOException e) {
            e.printStackTrace();
        }
    }
}
