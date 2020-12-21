package ru.job4j.grabber;

import java.util.Date;

public class Post {
    private final String description;
    private final String link;
    private final Date date;
    private final String nameVac;

    public Post(String description, String author, String link, Date date, String nameVac) {
        this.description = description;
        this.link = link;
        this.date = date;
        this.nameVac = nameVac;
    }

    @Override
    public String toString() {
        return "Post{"
                + "description='" + description + '\''
                + ", link='" + link + '\''
                + ", date=" + date
                + ", nameVac='" + nameVac + '\''
                + '}';
    }
}
