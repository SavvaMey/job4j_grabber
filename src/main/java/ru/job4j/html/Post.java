package ru.job4j.html;

import java.util.Date;

public class Post {
    private String description;
    private String author;
    private String link;
    private Date date;

    public Post(String description, String author, String link, Date date) {
        this.description = description;
        this.author = author;
        this.link = link;
        this.date = date;
    }

    public String getLink() {
        return link;
    }

    public void setLink(String link) {
        this.link = link;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getAuthor() {
        return author;
    }

    public void setAuthor(String author) {
        this.author = author;
    }

    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
        this.date = date;
    }

    @Override
    public String toString() {
        return "Post{" +
                "description='" + description + '\''
                + ", author='" + author + '\''
                + ", link='" + link + '\''
                + ", date=" + date
                + '}';
    }
}
