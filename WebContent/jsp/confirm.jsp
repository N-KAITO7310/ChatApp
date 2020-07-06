<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
 <%@ page import="model.User" %>

<%
	User user = (User) session.getAttribute("notYetUser");
	String user_name = user.getUser_name();
	String nickname = user.getNickname();
	String email = user.getEmail();
	String date_of_birth = user.getDate_of_birth();
	String sex = user.getSex();
	String introduction = user.getIntroduction();
	String profile_img_url = user.getProfile_image_url();
	String password = user.getUser_password();
%>
<!DOCTYPE html>
<html>
<head>
	<meta charset="UTF-8">
	<link rel="stylesheet" href="/ChatApp/css/confirm.css">
	<title>GamesChat</title>
</head>
<body>
<jsp:include page="./component/header1.jsp" />

	<div class="wrapper">
		<h1 class="heading">入力内容確認</h1>
		<h2 class="intro">下記のユーザーを登録します</h2>
		<div class="confirm">
			<p>名前:<%= user_name %></p>
			<p>ニックネーム:<%= nickname %></p>
	        <p>メールアドレス（ログインID）:<%= email %></p>
	        <p>生年月日:<%= date_of_birth %></p>
	        <p>性別:<%= sex %></p>
	        <p>自己紹介文:<%= introduction %></p>
	        <p>画像:<%= profile_img_url %></p>
	        <img src="<%= profile_img_url %>" width="100" height="100">
	        <p>パスワード:<%= password %></p>
	    </div>
		<div class="form-wrapper">
			<form action="/ChatApp/RegisterServlet" method="POST" class="form">
		    	<input class="btn btn-primary" type="submit" value="登録">
		    </form>
		    <form action="/ChatApp/RegisterServlet" method="GET" class="form">
		    	<input type="submit" class="btn btn-secondary" value="戻る">
		    </form>
		</div>


	</div>



<jsp:include page="./component/footer.jsp" />
</body>
</html>