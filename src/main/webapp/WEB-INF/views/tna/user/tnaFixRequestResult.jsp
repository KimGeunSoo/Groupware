<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>Insert title here</title>
</head>
<body>
	<script>
	alert("근태 조정 신청 완료되었습니다.");
	window.opener.location.href="/tna/tnaFixHistoryPage.tna?cpage=1";
	window.close();
	</script>
</body>
</html>