package ru.job4j.html;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.select.Elements;

import java.text.DateFormatSymbols;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

public class ParseDateRu {

    private static DateFormatSymbols rusDateFormatSymbols = new DateFormatSymbols() {
        @Override
        public String[] getMonths() {
            return new String[]{"янв", "фев", "мар", "апр", "май", "июн",
                    "июл", "авг", "сен", "окт", "ноя", "дек"};
        }
    };

    private static Date yesterday() {
        final Calendar cal = Calendar.getInstance();
        return cal.getTime();
    }

    private static Date today() {
        final Calendar cal = Calendar.getInstance();
        return cal.getTime();
    }

    public static Date getDateFromRusFormat(String dateStr, String pattern) {
        SimpleDateFormat formatter = new SimpleDateFormat(pattern, rusDateFormatSymbols);
        try {
            if (dateStr.contains("сегодня")) {
                return today();
            } else if (dateStr.contains("вчера")) {
                return yesterday();
            }
            return formatter.parse(dateStr);
        } catch (ParseException e) {
            e.printStackTrace();
        }
        return null;
    }

    public static void main(String[] args) throws Exception {

        Document doc = Jsoup.connect("https://www.sql.ru/forum/job-offers").get();
        Elements date = doc.select(".altCol");
        for (int i = 7; i < date.size(); i += 2) {
            System.out.println(getDateFromRusFormat(date.get(i).text().replace(
                    ",", ""), "d MMM yy HH:mm"));
        }
    }
}
