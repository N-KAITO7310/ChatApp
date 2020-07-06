<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@page import="model.*, java.util.*" %>
<%
	List<Chatroom> communities = (List<Chatroom>) request.getAttribute("community");
	Game game = (Game) request.getAttribute("game");
	User self = (User) session.getAttribute("self");
%>

<!DOCTYPE html>
<html>
<head>
	<meta charset="UTF-8">
	<link rel="stylesheet" href="/ChatApp/css/game.css">
	<title>GamesChat</title>
</head>
<body>
<jsp:include page="./component/header2.jsp" />

<div class="wrapper">
	<div class="game-wrapper">
		<h1 class="game-title"><%= game.getGame_title() %></h1>
		<div class="game-info">
			<div class="game-info_img-wrapper">
				<img class="game-info_img" src="<%= game.getGame_img_url() %>" width="500" height="300"></img>
			</div>
			<div class="game-info_description-wrapper">
				<p class="game-info_description"><%= game.getInfo() %></p>
			</div>
		</div>
	</div>
	<div class="communities-wrapper">
		<h2 class="community-heading">コミュニティ一覧</h2>
		<% for(Chatroom community : communities){ %>
			<div class="community">
				<h4 class="community_name"><%= community.getGroup_name() %></h4>
				<a class="community_link" href="/ChatApp/CommunityServlet?room_id=<%= community.getId() %>">コミュニティ詳細へ</a>
			</div>
		<% } %>
	</div>
	<div class="form-wrapper">
		<h2 class="community-form-heading">コミュニティ作成</h2>
		<form class="form-group" action="/ChatApp/GameServlet" method="POST">
		<input type="hidden" value="<%= game.getId() %>" name="gameId">
		<input class="form-control" type="text" name="communityName" placeholder="コミュニティ名を入力してください">
		<input class="btn btn-success submit" type="submit" value="コミュニティを作成する">
	</form>
	</div>

</div>

<jsp:include page="./component/footer.jsp" />
</body>
</html>