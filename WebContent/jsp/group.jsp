<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ page import="model.*, java.util.*" %>
<%
	Chatroom thisRoom = (Chatroom)request.getAttribute("thisRoom");
	List<User> groupUsers = (List<User>)request.getAttribute("groupUsers");
	User self = (User)session.getAttribute("self");
	int selfId = self.getId();
	int thisRoomHostId = thisRoom.getHost_user_id();
%>
<!DOCTYPE html>
<html>
<head>
	<meta charset="UTF-8">
	<link rel="stylesheet" href="/ChatApp/css/group.css">
	<title>GamesChat</title>
</head>
<body>
<jsp:include page="./component/header2.jsp" />

<div class="wrapper">
	<h2 class="group-name">グループ名：<%= thisRoom.getGroup_name() %></h2>
	<div class="btn-wrapper">
		<a class="btn btn-success chat-link" href="/ChatApp/ChatroomServlet?room_id=<%= thisRoom.getId() %>">チャットルームへ</a>
		<% if(selfId == thisRoomHostId){ %>
		<form action="/ChatApp/GroupServlet" method="POST">
			<input type="hidden" value="<%= thisRoom.getId() %>" name="room_id">
			<input class="submit btn btn-danger" type="submit" value="グループを解散する">
		</form>
		<% } %>
	</div>


	<div class="group-users">
		<h3 class="member-heading">メンバー</h3>
		<% for(User user : groupUsers){ %>
			<div class="user">
				<img class="user-icon" src="<%= user.getProfile_image_url() %>" width="80" height="80">
				<p class="user-name"><%= user.getUser_name() %></p>
				<a class="user-link" href="/ChatApp/UserServlet?userId=<%= user.getId() %>">ユーザー詳細へ</a>
			</div>
		<% } %>
	</div>
</div>


<jsp:include page="./component/footer.jsp" />
</body>
</html>