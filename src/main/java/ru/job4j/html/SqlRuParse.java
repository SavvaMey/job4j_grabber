package ru.job4j.html;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

public class SqlRuParse implements Parse {

    @Override
    public List<Post> list(String link) {
        List<Post> list = new ArrayList<>();
        Document doc = getDocument(link);
        Elements row = doc.select(".postslisttopic");
        for (Element td : row) {
            Element href = td.child(0);
            list.add(detail(href.attr("href")));
        }
        return list;
    }

    @Override
    public Post detail(String link) {
        Document doc = getDocument(link);
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

    public Document getDocument(String link) {
        try {
            return Jsoup.connect(link).get();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }
}
