package ru.job4j.html;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import java.io.IOException;
import java.util.Arrays;
import java.util.Date;
import java.util.stream.Collectors;

public class ParsePost {
    public static Post parse(String link) throws IOException {
        Document doc = Jsoup.connect(link).get();
        Element jData = doc.select(".msgTable").first();
        Element jDescription = jData.select(".msgBody").last();
        Element jAuthor = jData.select(".msgBody").first();
        Elements jDate = jData.select(".msgFooter");
        Elements jNameVac = jData.select(".messageHeader");
        Date date = ParseDateRu.getDateFromRusFormat(Arrays.stream(jDate.text().split(" "))
                .limit(4).collect(Collectors.joining(" ")), "d MMM yy HH:mm");
        String description = jDescription.text();
        String author = jAuthor.text().split(" ")[0];
        String nameVac = jNameVac.text();
        return new Post(description, author, link, date, nameVac);
    }

    public static void main(String[] args) throws IOException {
        String link = "https://www.sql.ru/forum/1325330/"
                + "lidy-be-fe-senior-cistemnye-analitiki-qa-i-devops-moskva-do-200t";
        Post post = parse(link);
        System.out.println(post);
    }

}
