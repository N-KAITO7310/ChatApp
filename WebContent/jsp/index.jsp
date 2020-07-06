<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
	<meta charset="UTF-8">
	<link rel="stylesheet" href="/ChatApp/css/index.css">
	<title>GamesChat</title>
</head>
<body>
	<jsp:include page="./component/header1.jsp" />
	<div class="wrapper">
		<div class="left">
			<h1 class="heading">GamesChat</h1>
			<p class="intro">「GamesChat」は個人間でのチャット、グループでのチャットを提供するサービスである。<br>
			また、ゲーム作品をテーマとした自由に参加・退出ができるチャットルームを提供し、<br>
			ゲームファンの交流の場として利用することができる。
			</p>
		</div>
		<div class="right">
			<div class="form-wrapper">
				<form class="form" action="/ChatApp/RegisterServlet" method="get">
			<input class="btn btn-primary submit" type="submit" value="新規登録">
			</form>
			<form class="form" action="/ChatApp/LoginServlet" method="get">
				<input class="btn btn-primary submit" type="submit" value="ログイン">
			</form>
		</div>

		</div>
	</div>
	<jsp:include page="./component/footer.jsp" />
</body>
</html>