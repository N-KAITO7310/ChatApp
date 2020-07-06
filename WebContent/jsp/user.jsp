<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ page import="model.*" %>
<%
	User thisUser = (User)request.getAttribute("thisUser");
	boolean isSelf = (boolean) request.getAttribute("isSelf");
	boolean isFriend = (boolean) request.getAttribute("isFriend");
	User self = (User)session.getAttribute("self");
%>
<!DOCTYPE html>
<html>
<head>
	<meta charset="UTF-8">
	<link rel="stylesheet" href="/ChatApp/css/user.css">
	<title>GamesChat</title>
</head>
<body>
<jsp:include page="./component/header2.jsp" />

<div class="wrapper">
	<h1 class="heading">ユーザー情報</h1>

	<% if(isSelf){ //自分のユーザー情報を閲覧する場合%>
		<div class="user user-self">
			<p>名前：<%= thisUser.getUser_name() %></p>
			<p>ニックネーム：<%= thisUser.getNickname() %></p>
			<p>メールアドレス：<%= thisUser.getEmail() %></p>
			<p>生年月日：<%= thisUser.getDate_of_birth() %></p>
			<p>性別：<%= thisUser.getSex() %></p>
			<p>自己紹介文：<%= thisUser.getIntroduction() %></p>
			<img src="<%= thisUser.getProfile_image_url() %>" width="80" height="80">プロフ画像</img>
			<p>パスワード：<%= thisUser.getUser_password() %></p>

			<form action="/ChatApp/UserServlet" method="POST">
				<p>自己紹介の変更</p>
				<input type="text" name="introduction">
				<p>画像の変更</p>
				<input type="file" name="profile_image_url">
				<input type="hidden" name="option" value="1">
				<input class="btn btn-primary submit" type="submit" value="登録内容を変更する"><br>
			</form>

		</div>
	<% }else if(isFriend){ //他ユーザーで、すでにチャットを始めている者の情報 %>
		<%
		FindRoomIdfromFriendIdLogic bo = new FindRoomIdfromFriendIdLogic();
		int roomIdOfThisFriend = bo.execute(self.getId(), thisUser.getId());
		%>
		<div class="user user-friend">
			<p>ニックネーム：<%= thisUser.getNickname() %></p>
			<p>自己紹介文：<%= thisUser.getIntroduction() %></p>
			<p>プロフ画像</p>
			<img src="<%= thisUser.getProfile_image_url() %>" width="80" height="80">
			<a class="btn btn-primary submit" href="/ChatApp/ChatroomServlet?room_id=<%= roomIdOfThisFriend %>">チャットルームへ</a>
		</div>

	<% }else{ //他ユーザーで、まだチャットを開始していない場合%>
		<div class="user user-other">
			<p>ニックネーム：<%= thisUser.getNickname() %></p>
			<p>自己紹介文：<%= thisUser.getIntroduction() %></p>
			<p>プロフ画像：</p>
			<img src="<%= thisUser.getProfile_image_url() %>" width="80" height="80">
			<form action="" method="POST">
				<input type="hidden" name="option" value="2">
				<input type="hidden" name="userId" value="<%= thisUser.getId() %>">
				<input type="submit" class="btn btn-primary submit" value="このユーザーとチャットを開始する">
			</form>
		</div>
	<% } %>

</div>


<jsp:include page="./component/footer.jsp" />
</body>
</html>