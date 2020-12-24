package ru.job4j.grabber;

import java.util.Date;
import java.util.Objects;

public class Post {
    private String description;
    private String link;
    private Date date;
    private String nameVac;

    public Post(String nameVac, String description, String link, Date date) {
        this.description = description;
        this.link = link;
        this.date = date;
        this.nameVac = nameVac;
    }

    public Post() {
    }

    public String getDescription() {
        return description;
    }

    public String getLink() {
        return link;
    }

    public Date getDate() {
        return date;
    }

    public String getNameVac() {
        return nameVac;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public void setLink(String link) {
        this.link = link;
    }

    public void setDate(Date date) {
        this.date = date;
    }

    public void setNameVac(String nameVac) {
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

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Post post = (Post) o;
        return link.equals(post.link) && date.equals(post.date) && nameVac.equals(post.nameVac);
    }

    @Override
    public int hashCode() {
        return Objects.hash(link, date, nameVac);
    }
}
