package model;

import java.io.Serializable;

public class Group implements Serializable {
	private int id;
	private String group_name;
	private int host_user_id;

	public Group() {
	}

	public Group(int id, String group_name, int host_user_id) {
		this.id = id;
		this.group_name = group_name;
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

	public int getHost_user_id() {
		return host_user_id;
	}

	public void setHost_user_id(int host_user_id) {
		this.host_user_id = host_user_id;
	}
}
