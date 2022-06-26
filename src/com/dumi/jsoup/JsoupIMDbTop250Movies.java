package com.dumi.jsoup;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;
import java.io.IOException;
import java.io.BufferedWriter;
import java.io.FileWriter;
import java.time.LocalDate;
import java.util.Random;

public class JsoupIMDbTop250Movies {
    public static void main(String[] args) throws IOException {

        String path = "src/com/dumi/jsoup/reports/";
        LocalDate date = LocalDate.now();
        Random random = new Random();
        String filename = "IMDB_TOP250Movies_" + date + "_" + Math.abs(random.nextInt()) + ".txt";

        String userAgent = "Mozilla/5.0 (Macintosh; Intel Mac OS X 10_11_2) AppleWebKit/601.3.9 (KHTML, like Gecko) Version/9.0.2 Safari/601.3.9";
        Document doc = Jsoup.connect("https://www.imdb.com/chart/top/?ref_=nv_mv_250").userAgent(userAgent).timeout(5000).get();
        Elements body = doc.select("tbody.lister-list");
        System.out.println("\nIMDb Top 250 Movies\n");
        System.out.print("\nSize of the queried movies: ");
        System.out.println(body.select("tr").size());
        System.out.println();

        try {
            BufferedWriter writer = new BufferedWriter(new FileWriter(path + filename));
            writer.write("\nIMDb Top 250 Movies\n\n");

            for (Element e: body.select("tr")) {
                String title = e.select("td.titleColumn").text();
                String rating = e.select("td.ratingColumn.imdbRating strong").attr("title");
                String img = e.select("td.posterColumn img").attr("src");
                System.out.println(title);
                System.out.println("IMDb rating: " + rating);
                System.out.println(img + "\n");

                writer.write(title + "\n");
                writer.write("IMDb rating: " + rating + "\n");
                writer.write(img + "\n\n");
            }

            writer.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
