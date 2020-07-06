<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@page import="model.*, java.util.*" %>
<%
	List<Game> games = (List<Game>)request.getAttribute("gameList");

%>
<!DOCTYPE html>
<html>
<head>
	<meta charset="UTF-8">
	<link rel="stylesheet" href="/ChatApp/css/gameList.css">
	<title>GamesChat</title>
</head>
<body>
<jsp:include page="./component/header2.jsp" />

<div class="wrapper">
	<h1 class="heading">ゲームタイトル一覧</h1>
	<% for(Game game : games){ %>
		<div class="game">
			<p class="game_title"><%= game.getGame_title() %></p>
			<a class="game_link" href="/ChatApp/GameServlet?gameId=<%= game.getId() %>">詳細・コミュニティ一覧へ</a>
		</div>
	<% } %>
</div>

<jsp:include page="./component/footer.jsp" />
</body>
</html>