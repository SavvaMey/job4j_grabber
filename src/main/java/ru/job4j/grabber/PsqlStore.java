package ru.job4j.grabber;

import java.io.IOException;
import java.io.InputStream;
import java.sql.*;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Properties;

public class PsqlStore implements Store, AutoCloseable {
    private final Connection cn;

    public PsqlStore(Properties config) {
        try {
            Class.forName(config.getProperty("driver-class-name"));
            cn = DriverManager.getConnection(
                    config.getProperty("url"),
                    config.getProperty("username"),
                    config.getProperty("password"));
        } catch (Exception e) {
            throw new IllegalStateException(e);
        }
    }

    @Override
    public void close() throws Exception {
        if (cn != null) {
            cn.close();
        }
    }

    @Override
    public void save(Post post) {
        try (PreparedStatement statement = cn.prepareStatement(
                "INSERT INTO post (namevac, description, link, created) VALUES (?, ?, ?, ?)")) {
            statement.setString(1, post.getNameVac());
            statement.setString(2, post.getDescription());
            statement.setString(3, post.getLink());
            statement.setTimestamp(4, new Timestamp(post.getDate().getTime()));

            statement.executeUpdate();
        } catch (SQLException throwable) {
            throwable.printStackTrace();
        }
    }

    @Override
    public List<Post> getAll() {
        List<Post> items = new ArrayList<>();
        try (Statement statement = cn.createStatement();
             ResultSet resultSet = statement.executeQuery("SELECT * FROM post")) {
            while (resultSet.next()) {
                String nameVac = resultSet.getString(2);
                String descr = resultSet.getString(3);
                String link = resultSet.getString(4);
                Timestamp timestamp = resultSet.getTimestamp(5);
                Date date = new Date(timestamp.getTime());
                items.add(new Post(nameVac, descr, link, date));
            }
        } catch (SQLException throwable) {
            throwable.printStackTrace();
        }
        return items;
    }

    @Override
    public Post findById(String id) {
        Post item = new Post();
        try (PreparedStatement statement = cn.prepareStatement(
                "SELECT * FROM post WHERE id=?")) {
            statement.setInt(1, Integer.parseInt(id));
            try (ResultSet resultSet = statement.executeQuery()) {
                if (resultSet.next()) {
                    item.setNameVac(resultSet.getString(2));
                    item.setDescription(resultSet.getString(3));
                    item.setLink(resultSet.getString(4));
                    Timestamp timestamp = resultSet.getTimestamp(5);
                    Date date = new Date(timestamp.getTime());
                    item.setDate(date);
                    return item;
                }
            }
        } catch (SQLException throwable) {
            throwable.printStackTrace();
        }
        return null;
    }

    public static void main(String[] args) {
        Properties cfg = new Properties();
        try (InputStream in = PsqlStore.class.getClassLoader()
                .getResourceAsStream("app.properties")) {
            cfg.load(in);
        } catch (IOException e) {
            e.printStackTrace();
        }
        PsqlStore psqlStore = new PsqlStore(cfg);
        Post postOne = new Post("java",
                "требуются разработчики", "hh.ru#1", new java.util.Date());
        Post postSecond = new Post("java",
                "требуются тестеры", "hh.ru#2", new java.util.Date());
        Post postThird = new Post("C",
                "требуются embedded", "hh.ru#3", new java.util.Date());
        psqlStore.save(postOne);
        psqlStore.save(postSecond);
        psqlStore.save(postThird);
        List<Post> posts = psqlStore.getAll();
        System.out.println(posts.size());
        posts.forEach(System.out::println);
        System.out.println(psqlStore.findById("1"));
    }
}
