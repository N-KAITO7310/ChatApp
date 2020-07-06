package model;

import java.io.Serializable;

public class User implements Serializable {
  private int id;
  private String user_name;
  private String email;
  private String user_password;
  private String nickname;
  private String date_of_birth;
  private String sex;
  private String introduction;
  private String profile_image_url;

  public User() {
  }

  public User(String user_name, String email, String user_password,
		  	  String nickname, String date_of_birth, String sex,
		  	  String introduction, String profile_image_url) {
    this.user_name = user_name;
    this.email = email;
    this.user_password = user_password;
    this.nickname = nickname;
    this.date_of_birth = date_of_birth;
    this.sex = sex;
    this.introduction = introduction;
    this.profile_image_url = profile_image_url;
  }
  public User(int id,String user_name, String email, String user_password,
	  	  String nickname, String date_of_birth, String sex,
	  	  String introduction, String profile_image_url) {
	  this.id = id;
this.user_name = user_name;
this.email = email;
this.user_password = user_password;
this.nickname = nickname;
this.date_of_birth = date_of_birth;
this.sex = sex;
this.introduction = introduction;
this.profile_image_url = profile_image_url;
}

  public int getId() {
	    return this.id;
	  }

	  public void setId(int id) {
	    this.id = id;
	  }

  public String getUser_name() {
    return user_name;
  }

  public void setName(String user_name) {
    this.user_name = user_name;
  }

  public String getEmail() {
    return email;
  }

  public void setEmail(String email) {
    this.email = email;
  }

  public String getUser_password() {
	return user_password;
  }

  public void setUser_password(String user_password) {
	this.user_password = user_password;
  }

  public String getNickname() {
	return nickname;
  }

  public void setNickname(String nickname) {
    this.nickname = nickname;
  }

  public String getDate_of_birth() {
	return date_of_birth;
  }

  public void setDate_of_birth(String date_of_birth) {
	this.date_of_birth = date_of_birth;
  }

  public String getSex() {
	return sex;
  }

  public void set(String sex) {
	this.sex = sex;
  }

  public String getIntroduction() {
	return introduction;
  }

  public void setIntroduction(String introduction) {
    this.introduction = introduction;
  }

  public String getProfile_image_url() {
	return profile_image_url;
  }

  public void setProfile_image_url(String profile_image_url) {
	this.profile_image_url = profile_image_url;
  }
}
