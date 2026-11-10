package com.example.obsidian.model;

public class Note {

    private Long id;

    private String title;

    private String content;

    private double x;

    private double y;

    private String color;

    public Note() {
    }

    public Note(Long id, String title, String content, double x, double y, String color) {
        this.id = id;
        this.title = title;
        this.content = content;
        this.x = x;
        this.y = y;
        this.color = color;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public double getX() {
        return x;
    }

    public void setX(double x) {
        this.x = x;
    }

    public double getY() {
        return y;
    }

    public void setY(double y) {
        this.y = y;
    }

    public String getColor() {
        return color;
    }

    public void setColor(String color) {
        this.color = color;
    }

    @Override
    public String toString() {
        return "Note{"
                + "id=" + id
                + ", title='" + title + '\''
                + ", content='" + content + '\''
                + ", x=" + x
                + ", y=" + y
                + ", color='" + color + '\''
                + '}';
    }
}
