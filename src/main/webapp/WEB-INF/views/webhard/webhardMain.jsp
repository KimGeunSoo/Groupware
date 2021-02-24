<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<!DOCTYPE html>
<html>
<style>




</style>
<head>
<meta charset="UTF-8">
<title>웹하드</title>
<script src="https://code.jquery.com/jquery-3.5.1.js"></script>
<!-- 아이콘 fontawesome -->
<script src="https://kit.fontawesome.com/b1e233372d.js"></script>
<!-- VENDOR CSS -->
<link rel="stylesheet"
	href="/assets/vendor/bootstrap/css/bootstrap.min.css">
<link rel="stylesheet"
	href="/assets/vendor/font-awesome/css/font-awesome.min.css">
<link rel="stylesheet" href="/assets/vendor/linearicons/style.css">
<link rel="stylesheet"
	href="/assets/vendor/chartist/css/chartist-custom.css">
<!-- MAIN CSS -->
<link rel="stylesheet" href="/assets/css/main.css">
<!-- FOR DEMO PURPOSES ONLY. You should remove this in your project -->
<link rel="stylesheet" href="/assets/css/demo.css">
<!-- GOOGLE FONTS -->
<link
	href="https://fonts.googleapis.com/css?family=Source+Sans+Pro:300,400,600,700"
	rel="stylesheet">
<!-- ICONS -->
<link rel="apple-touch-icon" sizes="76x76"
	href="/assets/img/apple-icon.png">
<link rel="icon" type="image/png" sizes="96x96"
	href="/assets/img/favicon.png">
<script src="/assets/vendor/jquery/jquery.min.js"></script>
<script src="/assets/vendor/bootstrap/js/bootstrap.min.js"></script>
<script src="/assets/vendor/jquery-slimscroll/jquery.slimscroll.min.js"></script>
<script src="/assets/vendor/chartist/js/chartist.min.js"></script>
<script src="/assets/scripts/klorofil-common.js"></script>
</head>
<body>
	<!-- WRAPPER -->
	<div id="wrapper">
		<!-- NAVBAR -->
		<!-- 		<nav class="navbar navbar-default navbar-fixed-top"> -->
		<%-- 			<jsp:include page="/WEB-INF/views/commonPage/top.jsp" /> --%>
		<!-- 		</nav> -->
		<!-- END NAVBAR -->
		<!-- LEFT SIDEBAR -->
		<div class="sidebar" id="sidebar-nav">
			<jsp:include page="/WEB-INF/views/webhard/leftWebhard.jsp" />
		</div>
		<!-- END LEFT SIDEBAR -->
		<!-- MAIN -->
		<div class="main">
			<!-- MAIN CONTENT -->
			<div class="main-content">
				<div class="container-fluid">
					<h3 class="page-title">Webhard</h3>
					<div class="panel panel-headline demo-icons">
						<div class="panel-heading">
							<form method="post" enctype="multipart/form-data" action="/webhard/uploadFile.webhard?dirSeq=${dirSeq }" id="uploadForm">
								<input type="button" value="Upload" id="upload-btn" class="btn btn-primary btn-xs">
								<input type="button" value="To parent folder" id="parentFolder-btn" class="btn btn-default btn-xs">		
								<input type="button" value="New Folder" id="newFolder-btn" class="btn btn-default btn-xs">		
								<input type="button" value="Download" id="download-btn" class="btn btn-default btn-xs">
								<input type="button" value="Delete" id="checkDel-btn" class="btn btn-default btn-xs">
								
								
								<input type="file" name="attfiles" id="attfiles" multiple style="display:none">
							</form>
						</div>
						<hr>
						<!-- pannel 내부의 제목 작성 div-->
						<div class="panel-body">
							<table class="table table-hover">
								<thead>
									<tr>
										<th scope="col"><input class="form-check-input checkAll" type="checkbox" value="" id="flexCheckDefault"></th>
										<th scope="col">형식</th>
										<th scope="col">파일명</th>
										<th scope="col">업로드일</th>
									</tr>
								</thead>
								<tbody>
									<!-- 폴더 표현 부분 -->
									<c:forEach varStatus="j" var="folderList" items="${dirFolderList}">
										<tr>
											<th scope="row"><input class="form-check-input checkObj checkFolder" type="checkbox" value="${folderList.wh_dir_seq}" name="chkList"></th>
											<td><span class="glyphicon glyphicon-folder-open" aria-hidden="true"></span></td>
											<td>
											<a href = "/webhard/webhardMain.webhard?dirSeq=${folderList.wh_dir_seq}"> ${folderList.wh_dir_name}</a>
											
											</td>
											<td><!-- 폴더생성일 없음 --></td>
										</tr>
									</c:forEach>
									<!-- 파일 표현 부분 -->
									<c:forEach var="i" items="${dirFileList}">
										<tr>
											<th scope="row"><input class="form-check-input checkObj checkFile" type="checkbox" value="${i.wh_files_seq}" name="chkList"></th>
											<td><span class="glyphicon glyphicon-file" aria-hidden="true"></span></td>
											<td>
											<a href = "/webhard/attFilesDown.webhard?wh_files_seq=${i.wh_files_seq }&wh_ori_name=${i.wh_ori_name}&wh_saved_name=${i.wh_saved_name}"> ${i.wh_ori_name}</a>
											
											</td>
											<td>${i.wh_upload_date}</td>
										</tr>
									</c:forEach>
								</tbody>
							</table>
						</div>
						
						
					</div>
				</div>
			</div>
			<!-- END MAIN CONTENT -->
		</div>
		<!-- END MAIN -->
		<div class="clearfix"></div>
		<jsp:include page="/WEB-INF/views/commonPage/footer.jsp" />
	</div>
	<!-- END WRAPPER -->


	<script>
		// 강제로 panel 최상단으로 끌어올리기
		$(".main").css({
			"padding-top": "0px"
		});
		
		// 전체체크 버튼 동작
		$(".checkAll").on("click",function(){
            if ($(".checkAll").is(':checked')) {
                $("input[type=checkbox]").prop("checked", true);
            } else {
                $("input[type=checkbox]").prop("checked", false);
            }
		});
		
		// 단일체크 동작 시 전체체크 해제
		$(".checkObj").on("click",function(){
			$(".checkAll").prop("checked", false);
		});
		
		// 체크 후 다운로드 버튼 클릭 시 동작
		$("#download-btn").on("click",function(){
			  var chked_val = "";
			  $(":checkbox[name='chkList']:checked").each(function(pi,po){
			    chked_val += ","+po.value;
			  });
			  if(chked_val != "") {
				  chked_val = chked_val.substring(1);
			  }
			  alert(chked_val)
// 			  return chked_val;
		});
		
		// 새폴더 만들기
		$("#newFolder-btn").on("click",function(){
			var newFolderName = prompt('새 폴더 이름을 입력해 주세요.'); 
			if ((newFolderName != null) && (newFolderName != "")) {
			// 해당 디렉토리에 중복되는 폴더 이름이 있는지 확인
				$.ajax({
					url:'/webhard/mkdirOverlapCheck.webhard',
					type:'post',
					data:{dirSeq:"${dirSeq }", newFolderName:newFolderName},
					success:function(resp){
						if (resp > 0) {
							alert(newFolderName + "과(와) 동일한 이름을 가진 \n파일 또는 폴더가 이미 존재합니다.");
							return 1;
						}else{
							location.href = "/webhard/mkdirProcess.webhard?dirSeq=" +${dirSeq }+ "&newFolderName=" +newFolderName;
						}
					}
					
				});
			}
		});
		
		// 체크된 폴더 및 파일 삭제
		$("#checkDel-btn").click(function(){
			var confirm_val = confirm("정말 삭제하시겠습니까?");
				
				// 확인 버튼 클릭 시
				if(confirm_val){
					// 폴더의 체크값 어레이 선언
					var chkArrFolder = new Array();
					// 파일의 체크값 어레이 선언
					var chkArrFile = new Array();
					
					// 체크된 폴더 리스트 값 담기
					$("input[class='form-check-input checkObj checkFolder']:checked").each(function(){
						chkArrFolder.push($(this).attr("value"));
					});					
					chkArrFolder.push(-1);
					
					// 체크된 파일 리스트 값 담기
					$("input[class='form-check-input checkObj checkFile']:checked").each(function(){
						chkArrFile.push($(this).attr("value"));
					});
					chkArrFile.push(-1);
					
					$.ajax({
						url : "/webhard/delCheckObject.webhard",
						type : "post",
						data : {"chkArrFolder" : chkArrFolder, "chkArrFile" : chkArrFile},
					}).done(function(result){
						location.reload();
					})
				}
		});
		
		// 파일 컴포넌트가 변경되었을경우 (파일을 첨부하였다면) form submit 하기
		document.getElementById("attfiles").onchange = function() {
		    document.getElementById("uploadForm").submit();
		};
		
		// 업로드 버튼 누를시 file 컴포넌트 누르는 것처럼 연결
		$('#upload-btn').on('click',function(){
			$('#attfiles').click();
		})
		
		// 체크 후 부모폴더로 이동
		$('#parentFolder-btn').on('click',function(){
			// 접근 가능 최상위 디렉토리 리스트 만들기
			var topAccessDirList = [
				<c:forEach varStatus="i" items="${topAccessDirList}" var="list">
				${list}
				<c:if test="${!i.last}">,</c:if>
				</c:forEach>
			];
			// 현재 디렉토리가 최상위 디렉토리인지 확인
			for (var i = 0; i < topAccessDirList.length; i++) {
				if (${dirSeq } == topAccessDirList[i]) {
					alert("접근 가능한 최상위 폴더입니다.");
					return 0;
				}
			}
			// 현재 디렉토리 부모 디렉토리로 진짜 이동
			location.href = "/webhard/goToParentDir.webhard?dirSeqChild="+${dirSeq };
		
		
		})
		
		
		
	</script>
</body>
</html>