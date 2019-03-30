package ru.tds.sqlitetodoapp.entities;

/**
 * Class for creating object "Meet"
 * <p>
 * Created by Trushenkov Dmitry on 29.03.2019
 */
public class Meet {

    // fields
    private int id;
    private String date;
    private int duration;
    private String type;
    private String comment;

    public Meet(int id, String date, int duration, String type, String comment) {
        this.id = id;
        this.date = date;
        this.duration = duration;
        this.type = type;
        this.comment = comment;
    }

    public Meet(String date, String type) {
        this.date = date;
        this.type = type;
    }

    public Meet() {
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public int getDuration() {
        return duration;
    }

    public void setDuration(int duration) {
        this.duration = duration;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getComment() {
        return comment;
    }

    public void setComment(String comment) {
        this.comment = comment;
    }

    @Override
    public String toString() {
        return "Meet{" +
                "date='" + date + '\'' +
                ", duration=" + duration +
                ", type='" + type + '\'' +
                ", comment='" + comment + '\'' +
                '}';
    }
}
