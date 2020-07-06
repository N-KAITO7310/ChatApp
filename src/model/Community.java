package model;

import java.io.Serializable;

public class Community implements Serializable {
	private int id;
	private String group_name;
	private int game_id;
	private int host_user_id;

	public Community() {
	}

	public Community(int id, String group_name, int game_id, int host_user_id) {
		this.id = id;
		this.group_name = group_name;
		this.game_id = game_id;
		this.host_user_id = host_user_id;
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getGroup_name() {
		return group_name;
	}

	public void setGroup_name(String group_name) {
		this.group_name = group_name;
	}

	public int getGame_id() {
		return game_id;
	}

	public void setGame_id(int game_id) {
		this.game_id = game_id;
	}

	public int getHost_user_id() {
		return host_user_id;
	}

	public void setHost_user_id(int host_user_id) {
		this.host_user_id = host_user_id;
	}
}