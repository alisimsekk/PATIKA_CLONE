package com.patikadev.Model;

import com.patikadev.helper.DBConnector;

import java.sql.PreparedStatement;
import java.sql.SQLException;

public class ReviewContent {
    private int id;
    private String review;
    private String content_topic;
    private int content_id;

    private Content content;

    public ReviewContent(int id, String review, String content_topic, int content_id) {
        this.id = id;
        this.review = review;
        this.content_topic = content_topic;
        this.content_id = content_id;
        this.content = Content.getFetch(content_id);
    }

    public static boolean add(String review, String content_topic, int content_id) {
        String query = "INSERT INTO content_review (review, content_topic, content_id) VALUES (?, ?, ?)";
        try {
            PreparedStatement pr = DBConnector.getInstance().prepareStatement(query);
            pr.setString(1, review);
            pr.setString(2, content_topic);
            pr.setInt(3, content_id);
            return pr.executeUpdate() != -1;
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return true;
    }






    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getReview() {
        return review;
    }

    public void setReview(String review) {
        this.review = review;
    }

    public String getContent_topic() {
        return content_topic;
    }

    public void setContent_topic(String content_topic) {
        this.content_topic = content_topic;
    }

    public int getContent_id() {
        return content_id;
    }

    public void setContent_id(int content_id) {
        this.content_id = content_id;
    }
}
