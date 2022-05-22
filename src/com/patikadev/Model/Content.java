package com.patikadev.Model;

import com.patikadev.helper.DBConnector;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;

public class Content {

    private int id;
    private String topic;
    private String explanation;
    private int course_id;
    private String ytubeUrl;

    private Course course;

    public Content(int id, String topic, String explanation, int course_id, String ytubeUrl) {
        this.id = id;
        this.topic = topic;
        this.explanation = explanation;
        this.course_id = course_id;
        this.ytubeUrl = ytubeUrl;
        this.course = Course.getFetch(course_id);
    }

    public Content(){

    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getTopic() {
        return topic;
    }

    public void setTopic(String topic) {
        this.topic = topic;
    }

    public String getExplanation() {
        return explanation;
    }

    public void setExplanation(String explanation) {
        this.explanation = explanation;
    }

    public int getCourse_id() {
        return course_id;
    }

    public void setCourse_id(int course_id) {
        this.course_id = course_id;
    }

    public String getYtubeUrl() {
        return ytubeUrl;
    }

    public void setYtubeUrl(String ytubeUrl) {
        this.ytubeUrl = ytubeUrl;
    }

    public Course getCourse() {
        return course;
    }

    public void setCourse(Course course) {
        this.course = course;
    }

    public static Content getFetch(int id) {
        Content obj = null;
        String query = "SELECT * FROM content WHERE id = ?";
        try {
            PreparedStatement pr = DBConnector.getInstance().prepareStatement(query);
            pr.setInt(1,id);
            ResultSet rs = pr.executeQuery();
            if (rs.next()){
                obj = new Content();
                obj.setId(rs.getInt("id"));
                obj.setTopic(rs.getString("topic"));
                obj.setExplanation(rs.getString("explanation"));
                obj.setCourse_id(rs.getInt("course_id"));
                obj.setYtubeUrl(rs.getString("ytube_url"));

            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return obj;
    }

    public static ArrayList<Content> getList(){
        ArrayList<Content> contentList = new ArrayList<>();
        Content obj;
        try {
            Statement st = DBConnector.getInstance().createStatement();
            ResultSet rs = st.executeQuery("SELECT * FROM content");
            while (rs.next()){
                int id = rs.getInt("id");
                String topic = rs.getString("topic");
                String explanation = rs.getString("explanation");
                int course_id = rs.getInt("course_id");
                String ytubeUrl = rs.getString("ytube_url");
                obj = new Content(id, topic, explanation, course_id, ytubeUrl);
                contentList.add(obj);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return contentList;
    }

// girilen anahtar kelimeye göre search metodu
    public static ArrayList<Content> searchContentList(String query){
        ArrayList<Content> contentList = new ArrayList<>();
        Content obj;
        try {
            Statement st = DBConnector.getInstance().createStatement();
            ResultSet rs = st.executeQuery(query);
            while (rs.next()){
                int id = rs.getInt("id");
                String topic = rs.getString("topic");
                String explanation = rs.getString("explanation");
                int course_id = rs.getInt("course_id");
                String ytubeUrl = rs.getString("ytube_url");
                obj = new Content(id, topic, explanation, course_id, ytubeUrl);
                contentList.add(obj);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return contentList;
    }

    public static boolean add(String topic, String explanation, int course_id, String ytubeUrl) {
        String query = "INSERT INTO content (topic, explanation, course_id, ytube_url) VALUES (?,?,?,?)";
        try {
            PreparedStatement pr = DBConnector.getInstance().prepareStatement(query);
            pr.setString(1,topic);
            pr.setString(2,explanation);
            pr.setInt(3,course_id);
            pr.setString(4, ytubeUrl);
            return pr.executeUpdate() != -1;
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return true;
    }

    public static boolean delete(int id) {
        String query = "DELETE FROM content WHERE id = ?";
        try {
            PreparedStatement pr = DBConnector.getInstance().prepareStatement(query);
            pr.setInt(1,id);
            return pr.executeUpdate() != -1;
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return true;
    }
}
