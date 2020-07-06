package model;

import java.io.Serializable;
import java.sql.Time;
import java.util.Date;

public class Chat implements Serializable{
	private int id;
    private int user_id;
    private int room_id;
    private String comment;
    private String post_time;
    private int like_count;

    public Chat() {

    }
    public Chat(int user_id, int room_id, String comment, Date date, Time time) {
    	this.user_id = user_id;
    	this.room_id = room_id;
    	this.comment = comment;

    	String dateStr = date.toString();
    	String timeStr = time.toString();
    	this.post_time = dateStr + "：" + timeStr;
    }
    public Chat(int user_id, int room_id, String comment, Date date, Time time, int like_count) {
    	this.user_id = user_id;
    	this.room_id = room_id;
    	this.comment = comment;

    	String dateStr = date.toString();
    	String timeStr = time.toString();
    	this.post_time = dateStr + "：" + timeStr;
    	this.like_count = like_count;
    }
    public Chat(int id ,int user_id, int room_id, String comment, Date date, Time time) {
    	this.id = id;
    	this.user_id = user_id;
    	this.room_id = room_id;
    	this.comment = comment;

    	String dateStr = date.toString();
    	String timeStr = time.toString();
    	this.post_time = dateStr + "：" + timeStr;
    }
    public Chat(int id ,int user_id, int room_id, String comment, Date date, Time time, int like_count) {
    	this.id = id;
    	this.user_id = user_id;
    	this.room_id = room_id;
    	this.comment = comment;

    	String dateStr = date.toString();
    	String timeStr = time.toString();
    	this.post_time = dateStr + "：" + timeStr;
    	this.like_count = like_count;
    }

    public int getId() {
    	return this.id;
    }
    public int getUser_id() {
    	return this.user_id;
    }
    public int room_id() {
    	return this.room_id;
    }
    public String getComment() {
    	return this.comment;
    }
    public String getPost_time() {
    	return this.post_time;
    }

    public void setId(int id) {
    	this.id = id;
    }
    public void setUser_id(int user_id) {
    	this.user_id = user_id;
    }
    public void setRoom_id(int room_id) {
    	this.room_id = room_id;
    }
    public void setComment(String comment) {
    	this.comment = comment;
    }
    public void setPost_time(Date date, Time time) {
    	String dateStr = date.toString();
    	String timeStr = time.toString();
    	this.post_time = dateStr + "：" + timeStr;
    }
    public int getLike_count() {
    	return this.like_count;
    }
    public void setLike_count(int like_count) {
    	this.like_count = like_count;
    }

}
