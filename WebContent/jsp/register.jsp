<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
	<link rel="stylesheet" href="../css/register.css">
	<title>GamesChat</title>
</head>
<body>
<jsp:include page="./component/header1.jsp" />

<div class="wrapper">
	<h1 class="heading">新規登録</h1>
	<form action="/ChatApp/ConfirmServlet" method="post" class="form-group">
		<label for="user_name" class="label">名前：</label>
		<input type="text" name="user_name" size="40" id="user_name" required class="form-control">
		<label for="nickname" class="label">ニックネーム：</label>
		<input type="text" name="nickname" size="40" id="nickname" required class="form-control">
		<label for="email" class="label">メールアドレス（ログインID）：</label>
		<input type="text" name="email" size="40" id="email" required class="form-control">
		<label for="year" class="label">生年月日：</label>
		<input type="number" name="year" size="10" id="year" required class="form-control">年
		<input type="number" name="month" size="10" id="month" required class="form-control">月
		<input type="number" name="day" size="10" id="day" required class="form-control">日<br>
		<label class="label">性別：</label>
		<div class="form-check">

			<input class="form-check-input" type="radio" name="sex" id="sex1" value="男" checked>
			<label class="form-check-label" for="sex1">男性</label><br>
			<input class="form-check-input" type="radio" name="sex" id="sex2" value="女">
			<label class="form-check-label" for="sex2">女性</label>
		</div>
		<label for="introduction" class="label">自己紹介：</label>
		<input type="text" name="introduction" size="120" id="introduction" class="form-control">
		<label for="file" class="label">プロフィール画像：</label>
		<input id="file" type="file" name="profile_image_url" class="form-control-file">
		<img class="preview" id="file-preview" src="" width="100" height="100">
		<p><label for="pass" class="label">パスワード：</label></p>
		<input type="text" name="user_password" size="40" required id="pass" class="form-control">
		<input type="submit" value="確認画面へ" class="submit btn btn-primary">
	</form>

</div>

<jsp:include page="./component/footer.jsp" />
<script>
	document.getElementById('file').addEventListener('change', function (e) {
	    // 1枚だけ表示する
	    var file = e.target.files[0];

	    // ファイルのブラウザ上でのURLを取得する
	    var blobUrl = window.URL.createObjectURL(file);

	    // img要素に表示
	    var img = document.getElementById('file-preview');
	    img.src = blobUrl;
	});
</script>
</body>
</html>