<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>Insert title here</title>
</head>
<body>
<form action="/launched" method="POST">
	<input type="text" name="shopId"/>
	<select name="charSet">
		<option value="euc-kr">euc-kr</option>
		<option value="UTF-8">UTF-8</option>
	</select>
	<input type="submit" value="launch"/>
</form>
</body>
</html>