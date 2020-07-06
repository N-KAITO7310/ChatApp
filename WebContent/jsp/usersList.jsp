<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ page import="model.*, java.util.*" %>
<%
	List<User> usersList = (List<User>)request.getAttribute("allUsers");

%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
	<link rel="stylesheet" href="/ChatApp/css/usersList.css">
	<title>GamesChat</title>
</head>
<body>
<jsp:include page="./component/header2.jsp" />

<div class="wrapper">
	<div class="usersList">
	<h1 class="heading">ユーザー一覧</h1>
	<div class="searchForm">
		<form action="/ChatApp/UsersListServlet" method="POST" class="form-group">
			<input class="form-control" type="text" name="searchWord" placeholder="検索ワードを入力してください">
			<input class="btn btn-primary submit" type="submit" value="検索">
		</form>
	</div>


	<% for(User user : usersList){ %>
		<div class="userInfo">
			<img src="<%= user.getProfile_image_url() %>" width="70" height="70">
			<p class="name"><%= user.getUser_name() %></p>
			<a class="user-link" href="/ChatApp/UserServlet?userId=<%= user.getId() %>">ユーザー詳細へ</a>
		</div>
	<% } %>
	</div>
</div>


<jsp:include page="./component/footer.jsp" />
</body>
</html>