<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
	<link rel="stylesheet" href="/ChatApp/css/login.css">
	<title>GamesChat</title>
<title>ログインページ</title>
</head>
<body>
<jsp:include page="./component/header1.jsp" />

<div class="wrapper">
	<h1 class="heading">ログイン</h1>
	<form action="/ChatApp/LoginServlet" method="post" class="form-group form">
		<label for="email" class="email-label">メールアドレス：</label>
		<input type="text" name="email" id="email" class="form-control email">
		<label for="password" class="pass-label">パスワード：</label>
		<input type="password" name="user_password" id="password" class="form-control pass"></p>
	<input type="submit" value="ログイン" class="btn btn-primary submit">
	</form>
</div>



<jsp:include page="./component/footer.jsp" />
</body>
</html>