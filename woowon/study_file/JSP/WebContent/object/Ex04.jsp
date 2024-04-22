<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>입력 폼</title>
</head>
<body>
<form action="Ex04_result.jsp" method="post">
	<input type="text" name="name" placeholder="Name"><br>
	<input type="text" name="address" placeholder="address"><br>
	동물<br>
	<input type="checkbox" name="pet" value="dog">강아지<br>
	<input type="checkbox" name="pet" value="cat">고양이<br>
	<input type="checkbox" name="pet" value="bird">새<br>
	<input type="submit" value="전송">
</form>
</body>
</html>