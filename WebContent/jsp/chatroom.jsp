<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@page import="model.*, java.util.*" %>
<%
	Chatroom thisRoom = (Chatroom)request.getAttribute("thisRoom");
	List<Chat> chats = (List<Chat>)request.getAttribute("chats");
	User self = (User)session.getAttribute("self");

	String roomName = thisRoom.getGroup_name();
	int friendId = 0;//OneToOneのチャットルームのみで使用
	if(roomName == null){
		//roomNameがnullで、１対１チャットルームの場合、相手のユーザー情報を取得する
		FindUserRelationFromChatroomidLogic bo = new FindUserRelationFromChatroomidLogic();
		int selfId = self.getId();
		int roomId = thisRoom.getId();
		List<User> users = bo.execute(roomId, selfId);
		User thisUser = users.get(0);
		roomName = thisUser.getUser_name();
		friendId = thisUser.getId();
	}else{
		roomName = thisRoom.getGroup_name();
	}
%>


<!DOCTYPE html>
<html>
<head>
	<meta charset="UTF-8">
	<link rel="stylesheet" href="/ChatApp/css/chatroom.css">
	<title>GamesChat</title>
</head>
<body class="body">
<jsp:include page="./component/header2.jsp" />

<div class="wrapper">
	<h1 class="heading">チャットルーム</h1>
	<div class="room-header">
		<h2 class="room-name"><%= roomName %></h2>
	</div>
	<div class="room-wrapper">
		<% if(chats == null){ %>
		<p class="chat-none">チャットがまだ投稿されていません</p>
		<% }else{ %>
		<% for(Chat chat: chats){ %>
			<%
			FindOneUserLogic bo_find = new FindOneUserLogic();
			User postUser = bo_find.execute(chat.getUser_id());
			String chatClass = "you";
			if(self.getId() == chat.getUser_id()){
				chatClass = "me";
			}
			%>
			<div class="chat">
				<div class="<%= chatClass %>">
					<div class="chat-body">
						<img class="icon" src="<%= postUser.getProfile_image_url() %>" width="50" height="50">
						<div class="chat-inner">
							<div class="chat-name-wrapper">
								<span class="name"><%= postUser.getUser_name() %></span>
							</div>
							<div class="chat-comment-wrapper">
								<span class="comment"><%= chat.getComment() %></span>
							</div>
						</div>
					</div>
					<p class="time"><%= chat.getPost_time() %></p>

					<div class="chat-bottom">
						<!-- 既読 -->
						<% if(!(self.getId() == chat.getUser_id())){ //自分の投稿には既読は表示しない。既読は自分の投稿か、既読済みか、既読していないかで表示を変える必要がある%>
							<form class="form-read" action="/ChatApp/WasReadServlet" method="POST">
								<input type="hidden" value="<%= thisRoom.getId() %>" name="room_id">
								<input type="hidden" value="<%= chat.getId() %>" name="chatId">
								<% //既読済みかのロジック
									boolean alreadyRead = false;
									CheckAlreadyRead bo = new CheckAlreadyRead();
									alreadyRead = bo.execute(self.getId(), chat.getId());
								%>
								<% if(alreadyRead){ %>
									<p class="read-status">既読済み</p>
								<% }else{ %>
									<input class="btn btn-outline-dark submit-read" type="submit" value="既読">
								<% } %>
							</form>
						<% }else{//ここから自分の投稿だった場合の既読に関しての表示
							//行う処理は、１対１は既存のCheckAlreadyRead()での判定、その際に相手のユーザーidも得る必要あり
							//group、communityの場合、このチャットのidをもつalready_readのレコードが複数あるはずだから、
							//was_readカラムの値が1のものの数を返すメソッドがあれば、グループでの既読の数を表示できる
							//１対１の場合と、グループの場合でさらに分ける必要がある。

							String room_status = thisRoom.getGroup_status();

							if(room_status.equals("OneToOne")){//OneToOneかの判定
								boolean alreadyRead = false;
								CheckAlreadyRead bo = new CheckAlreadyRead();
								alreadyRead = bo.execute(friendId, chat.getId());

								if(alreadyRead){//相手が既読済みだった場合
						%>
									<p class="read-status">既読済み</p>
								<% }else{//相手が未読の場合%>
									<p class="read-status">未読</p>
								<% }//end　１対１の場合で相手が既読か否かの判定 %>
							<% }else{//group、communityの場合　start71行目
									int alreadyReadCount = 0;
									CheckAlreadyReadForGroup bo = new CheckAlreadyReadForGroup();
									alreadyReadCount = bo.execute(chat.getId());
							%>
								<p class="read-status">既読<%= alreadyReadCount %></p>
							<% }//end OneToOneかの判定 %>
						<% }//end 自分・他者の投稿かの判定 %>

						<!-- いいね -->
						<form class="like-form" action="/ChatApp/LikeServlet" method="POST">
							<% if(!(self.getId() == chat.getUser_id())){ //自分の投稿にはいいね!ボタンを表示しない%>
								<input type="hidden" value="<%= thisRoom.getId() %>" name="room_id">
								<input type="hidden" value="<%= chat.getId() %>" name="chatId">
								<input class="btn btn-outline-dark submit-like" type="submit" value="いいね">
							<% } %>
							<img class="like-icon" src="/ChatApp/img/like.png">
							<span class="like-count"><%= chat.getLike_count() %></span>
						</form>
					</div>
				</div>
			</div>
		<%  } %>
		<% } %>

		<div class="chatForm">
			<form class="form-group" action="/ChatApp/ChatroomServlet" method="POST" id="post">
				<input class="form-control" type="text" name="comment">
				<input type="hidden" name="chatroomId" value="<%= thisRoom.getId() %>">
				<input class="btn btn-primary submit-post" type="submit" value="投稿">
			</form>
		</div>
	</div>


</div>

<jsp:include page="./component/footer.jsp" />
</body>
</html>