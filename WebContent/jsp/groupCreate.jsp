<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ page import="model.*, java.util.*" %>
<%
	List<User> usersList = (List<User>)request.getAttribute("allUsers");
	User self = (User)session.getAttribute("self");
%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
	<link rel="stylesheet" href="/ChatApp/css/groupCreate.css">
	<title>GamesChat</title>
</head>
<body>
<jsp:include page="./component/header2.jsp" />

<div class="wrapper">
	<h1 class="heading">ユーザー選択</h1>
	<form class="form-group" action="/ChatApp/GroupCreateServlet" method="POST">
	<input class="form-control" type="text" name="group_name" placeholder="作成するグループ名を入力してください">
	<input class="btn btn-primary submit" type="submit" value="グループを作成する">
	<div class="users">
		<% for(User user : usersList){ %>
		<% if(!(user.getId() == self.getId())){ %>
			<div class="userInfo form-check">
				<input id="id<%= user.getId() %>" class="form-check-input input" type="checkbox" name="group_users" value="<%= user.getId() %>">
				<label for="id<%= user.getId() %>" class="form-check-label name"><%= user.getUser_name() %></label>
				<a class="user-link" href="/ChatApp/UserServlet?userId=<%= user.getId() %>">ユーザー詳細へ</a>
			</div>
		<% } %>
	<% } %>
	</div>
	</form>



</div>


<jsp:include page="./component/footer.jsp" />
</body>
</html>