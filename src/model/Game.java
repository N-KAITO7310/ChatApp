package model;

import java.io.Serializable;

public class Game implements Serializable {
	private int id;
	private String game_title;
	private String info;
	private String game_img_url;

	public Game() {
	}

	public Game(int id,  String game_title, String info, String game_img_url) {
		this.id = id;
		this.game_title = game_title;
		this.info = info;
		this.game_img_url = game_img_url;
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getGame_title() {
	    return game_title;
	}

	public void setGame_title(String game_title) {
	    this.game_title = game_title;
	}

	public String getInfo() {
		return info;
	}

	public void setInfo(String info) {
		this.info = info;
	}

	public String getGame_img_url() {
		return game_img_url;
	}

	public void setGame_img_url(String game_img_url) {
	    this.game_img_url = game_img_url;
	}
}
