<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ page import="java.util.*, model.*" %>
<%
	User self = (User)session.getAttribute("self");
	List<User> friends = (List<User>)request.getAttribute("friends");
	List<Chatroom> groups = (List<Chatroom>)request.getAttribute("groups");
	List<Chatroom> communities = (List<Chatroom>)request.getAttribute("communities");
%>
<!DOCTYPE html>
<html>
<head>
	<meta charset="UTF-8">
	<link rel="stylesheet" href="/ChatApp/css/home.css">
	<title>GamesChat</title>
</head>
<body>
<jsp:include page="./component/header2.jsp" />

<div class="wrapper">
	<div class="heroArea">
		<nav class="hero-nav">
			<p class="hero-p hero-userList"><a href="/ChatApp/UsersListServlet" class="hero-a">ユーザー一覧</a></p>
			<p class="hero-p hero-groupCreate"><a href="/ChatApp/GroupCreateServlet" class="hero-a">グループ作成</a></p>
			<p class="hero-p hero-games"><a href="/ChatApp/GameListServlet" class="hero-a">作品タイトルからコミュニティを探す</a></p>
		</nav>
	</div>
	<section class="section section-friends">
		<div class="friends-wrapper">
			<h2 class="heading heading-friends">友達一覧</h2>
			<%
				if(!(friends == null || friends.size() == 0)){
				for(User friend : friends){
			%>
			<!-- 以下friendsリストでの拡張forで繰り返し -->
			<div class="item friend">
				<p class="name friend-name">友達名：<%= friend.getNickname() %></p>
				<a class="link friend-link" href="/ChatApp/UserServlet?userId=<%= friend.getId() %>">ユーザー詳細へ</a>
				<%
					//該当友達ユーザーとのチャットルームのidを取得する必要があるので、ここで取得する。
					//自分のidと特定ユーザーidのidから１対１でのroomのidを取得する必要がある。
					FindRoomIdfromFriendIdLogic bo = new FindRoomIdfromFriendIdLogic();
					int roomIdOfThisFriend = bo.execute(self.getId(), friend.getId());
				%>
				<a class="chat friend-chatLink" href="/ChatApp/ChatroomServlet?room_id=<%= roomIdOfThisFriend %>">チャットルームへ</a>
			</div>
			<%
					}//for
				}//if
				else{
			%>
				<p class="friend-none">チャットを開始している友達はいません。</p>
			<% } %>

		</div>
	</section>
	<section class="section section-groups">
		<div class="groups-wrapper">
			<h2 class="heading heading-groups">グループ一覧</h2>
			<%
				if(!(groups == null || groups.size() == 0)){
				for(Chatroom group : groups){
			%>
			<!-- 以下groupリストでの拡張forで繰り返し -->
			<div class="item group">
				<p class="name group-name">グループ名：<%= group.getGroup_name() %></p>
				<a class="group-link link" href="/ChatApp/GroupServlet?room_id=<%= group.getId() %>">グループ詳細へ</a>
				<a class="group-chatLink chat" href="/ChatApp/ChatroomServlet?room_id=<%= group.getId() %>">チャットルームへ</a>
			</div>
			<%
					}
				}else{
			%>
				<p class="group-none">所属しているグループがありません</p>
			<% } %>
		</div>
	</section>
	<section class="section section-communities">
		<div class="communities-wrapper">
			<h2 class="heading heading-community">ゲームコミュニティ一覧</h2>
			<%
				if(!(communities == null || communities.size() == 0)){
				for(Chatroom community : communities){
			%>
			<!-- 以下groupリストでの拡張forで繰り返し -->
			<div class="item community">
				<p class="name community-name">コミュニティ名：<%= community.getGroup_name() %></p>
				<a class="community-link link" href="/ChatApp/CommunityServlet?room_id=<%= community.getId() %>">コミュニティ詳細へ</a>
				<a class="community-chatLink chat" href="/ChatApp/ChatroomServlet?room_id=<%= community.getId() %>">チャットルームへ</a>
			</div>
			<%
					}
				}else{
			%>
				<p class="community-none">所属しているコミュニティがありません</p>
			<% } %>
		</div>
	</section>
</div>


<jsp:include page="./component/footer.jsp" />
</body>
</html>