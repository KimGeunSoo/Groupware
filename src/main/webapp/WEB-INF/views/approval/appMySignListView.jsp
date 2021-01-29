<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>결재할 문서함</title>
<script src="https://code.jquery.com/jquery-3.5.1.js"></script>
    <!-- 아이콘 fontawesome -->
    <script src="https://kit.fontawesome.com/b1e233372d.js"></script>
	<!-- VENDOR CSS -->
	<link rel="stylesheet" href="/assets/vendor/bootstrap/css/bootstrap.min.css">
	<link rel="stylesheet" href="/assets/vendor/font-awesome/css/font-awesome.min.css">
	<link rel="stylesheet" href="/assets/vendor/linearicons/style.css">
	<link rel="stylesheet" href="/assets/vendor/chartist/css/chartist-custom.css">
	<!-- MAIN CSS -->
	<link rel="stylesheet" href="/assets/css/main.css">
	<!-- FOR DEMO PURPOSES ONLY. You should remove this in your project -->
	<link rel="stylesheet" href="/assets/css/demo.css">
	<!-- GOOGLE FONTS -->
	<link href="https://fonts.googleapis.com/css?family=Source+Sans+Pro:300,400,600,700" rel="stylesheet">
	<!-- ICONS -->
	<link rel="apple-touch-icon" sizes="76x76" href="/assets/img/apple-icon.png">
    <link rel="icon" type="image/png" sizes="96x96" href="/assets/img/favicon.png">
    <script src="/assets/vendor/jquery/jquery.min.js"></script>
	<script src="/assets/vendor/bootstrap/js/bootstrap.min.js"></script>
	<script src="/assets/vendor/jquery-slimscroll/jquery.slimscroll.min.js"></script>
	<script src="/assets/vendor/chartist/js/chartist.min.js"></script>
	<script src="/assets/scripts/klorofil-common.js"></script>
	<style>
		table{width:100%;text-align:center;}
		th{text-align:center;}
	</style>
</head>
<body>
	<!-- WRAPPER -->
	<div id="wrapper">
		<!-- NAVBAR -->
		<nav class="navbar navbar-default navbar-fixed-top">
			<jsp:include page="/WEB-INF/views/commonPage/top.jsp"/>
		</nav>
		<!-- END NAVBAR -->
		<!-- LEFT SIDEBAR -->
		<div class="sidebar" id="sidebar-nav">
			<jsp:include page="/WEB-INF/views/commonPage/left.jsp"/>
		</div>
		<!-- END LEFT SIDEBAR -->
		<!-- MAIN -->
		<div class="main">
			<!-- MAIN CONTENT -->
			<div class="main-content">
				<div class="container-fluid">
					<h3 class="page-title">결재 문서함</h3>
					<div class="panel">
						<div class='panel-heading'>
							<h3 class='panel-title'>결재할 문서함</h3>
							<!--pannel의 최소화 및 닫기 버튼-->
							<div class="right">
								<button type="button" class="btn-toggle-collapse">
									<i class="lnr lnr-chevron-up"></i>
								</button>
							</div>
						</div>
						<div class="panel-body">
							<table>
								<thead>
									<tr>
										<th scope="row" class="align-middle">문서번호</th>
										<th scope="row" class="align-middle">분류</th>
										<th scope="row" class="align-middle">문서명</th>
										<th scope="row" class="align-middle">기안자</th>
										<th scope="row" class="align-middle">기안일</th>
									</tr>
									
								</thead>
								<tbody>
								<c:choose >
									<c:when test="${empty toBeSignList}">
										<tr>
											<td colspan=7>
												결재할 문서가 없습니다. 
											</td>
										</tr>
									</c:when>
									<c:otherwise>
									<c:forEach items="${toBeSignList}" var="i">
										<tr>
											<td>${i.app_seq}_${i.app_docs_num}</td>
											<td>${i.app_type_name}</td>
											<td>${i.app_title}</td>
											<td>${i.name}</td>
											<td>${i.app_reg_date}</td>
										</tr>
									</c:forEach>
									</c:otherwise>
								</c:choose>
									
								</tbody> 
							</table>
							
						</div>
					</div>
					<div class="panel">
						<div class='panel-heading'>
							<h3 class='panel-title'>결재한 문서함</h3>
							<!--pannel의 최소화 및 닫기 버튼-->
							<div class="right">
								<button type="button" class="btn-toggle-collapse">
									<i class="lnr lnr-chevron-up"></i>
								</button>
							</div>
						</div>
						<div class="panel-body">
							<table>
								<thead>
									<tr>
										<th scope="row" >문서번호</th>
										<th scope="row" >분류</th>
										<th scope="row" >내 결재상태</th>
										<th scope="row" >문서명</th>
										<th scope="row" >기안자</th>
										<th scope="row" >기안일</th>
										<th scope="row" >최종 결재상태</th>
									</tr>
									
								</thead>
								<tbody>
								<c:choose >
									<c:when test="${empty signedList}">
										<tr>
											<td colspan=7>
												결재한 문서가 없습니다. 
											</td>
										</tr>
									</c:when>
									<c:otherwise>
									<c:forEach items="${signedList}" var="i">
										<tr>
											<td>${i.app_docs_num}</td>
											<td>${i.app_type_code}</td>
											<td>
												<c:choose>
													<c:when test="${i.app_sign_accept=='N'}">반려</c:when>
													<c:otherwise>결재완료</c:otherwise>
												</c:choose>
											</td>
											<td>${i.app_title}</td>
											<td>${i.name}</td>
											<td>${i.app_reg_date}</td>
											<td>${i.app_status_name}</td>
										</tr>
									</c:forEach>
									</c:otherwise>
								</c:choose>
								</tbody> 
							</table>
						</div>
					</div>
				</div>
				<!-- END MAIN CONTENT -->
			</div>
		</div>
		</div>
		<!-- END MAIN -->
		<div class="clearfix"></div>
		<footer>
			<div class="container-fluid">
				<p class="copyright">&copy; 2017 <a href="https://www.themeineed.com" target="_blank">Theme I Need</a>. All Rights Reserved.</p>
			</div>
		</footer>
	<!-- END WRAPPER -->
</body>
</html>