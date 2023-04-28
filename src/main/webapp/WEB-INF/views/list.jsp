<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="jakarta.tags.core"%>
<%@ taglib prefix="my" tagdir="/WEB-INF/tags"%>
<!DOCTYPE html>
<html>
<head>
<style>
</style>
<meta charset="UTF-8">
<meta name="viewport" content="width=device-width, initial-scale=1">
<title>Insert title here</title>
<link rel="stylesheet" href="https://cdnjs.cloudflare.com/ajax/libs/font-awesome/6.4.0/css/all.min.css" integrity="sha512-iecdLmaskl7CVkqkXNQ/ZH/XLlvWZOJyj7Yy7tcenmpD1ypASozpmT/E0iPtmFIB46ZmdtAc9eNBvH0H/ZpiBw==" crossorigin="anonymous" referrerpolicy="no-referrer" />
<link href="https://cdn.jsdelivr.net/npm/bootstrap@5.3.0-alpha3/dist/css/bootstrap.min.css" rel="stylesheet" integrity="sha384-KK94CHFLLe+nY2dmCWGMq91rCGa5gtU4mk92HdvYe+M/SXH301p5ILy+dN9+nJOZ" crossorigin="anonymous">
</head>
<body>
	<script src="https://cdnjs.cloudflare.com/ajax/libs/jquery/3.6.4/jquery.min.js" integrity="sha512-pumBsjNRGGqkPzKHndZMaAG+bir374sORyzM3uulLV14lN5LyykqNk8eEeUlUkB3U0M4FApyaHraT65ihJhDpQ==" crossorigin="anonymous" referrerpolicy="no-referrer"></script>

	<my:navBar current="list" />


	<!-- <div class="d-flex justify-content-between align-items-center">
	<h1 >게시물 목록 보기</h1>
	<button type="button" class="btn btn-success" id = "moveAddForm" onclick = "location.href='/add'">자료추가</button>
</div> -->

	<%-- 	<c:if test="${param.success eq 'add' }">
		<script>
			alert("추가되었습니다");
		</script>
	</c:if> --%>

	<my:alert></my:alert>


	<div class="table-responsive">
		<table class="table table-dark table-hover">
			<thead>
				<tr>
					<th scope="col">아이디</th>
					<th scope="col">제목</th>
					<th scope="col">시간</th>
					<th scope="col">글쓴이</th>
				</tr>
			</thead>
			<tbody>
				<c:forEach items="${boardList }" var="item">
					<tr>
						<td>${item.id }</td>
						<td>
							<a href="/id/${item.id }">${item.title }</a>
						</td>
						<td>${item.inserted }</td>
						<td>${item.writer }</td>
					</tr>
				</c:forEach>
			</tbody>
		</table>
	</div>

	<!-- pagination  -->
	<div class="container-lg">
		<div class="row">
			<nav aria-label="Page navigation example ">
				<ul class="pagination justify-content-center">
					<li class="page-item">
						<c:if test="${prevPageNumber >= 1 }">
							<c:url value="/list" var="pageLink">
								<c:param name="page" value="${prevPageNumber }"></c:param>
							</c:url>
							<a href="${pageLink }" class="page-link"><i class="fa-solid fa-chevron-left"></i></a>
					</li>
					</c:if>

					<c:forEach begin="${leftPageNumber }" end="${rightPageNumber }" var="pageNumber">
						<c:url value="/list" var="PageLink">
							<c:param name="page" value="${pageNumber }"></c:param>
						</c:url>
						<li class="page-item">
							<a class="page-link ${currentPage eq pageNumber ? 'active' : '' }" href="${PageLink }">${pageNumber }</a>
						</li>
					</c:forEach>

					<li class="page-item">
						<c:if test="${nextPageNumber < lastpageNumber }">
							<c:url value="/list" var="pageLink">
								<c:param name="page" value="${nextPageNumber }"></c:param>
							</c:url>
							<a href="${pageLink }" class="page-link"><i class="fa-solid fa-chevron-right"></i></a>
					</li>
					</c:if>
				</ul>
			</nav>
		</div>
	</div>
















	<%-- 	<c:if test="${param.success eq 'remove' }">
		<script>
			alert("게시물이 삭제되었습니다")
		</script>
	</c:if> --%>


	<script src="https://cdn.jsdelivr.net/npm/bootstrap@5.3.0-alpha3/dist/js/bootstrap.bundle.min.js" integrity="sha384-ENjdO4Dr2bkBIFxQpeoTz1HIcje39Wm4jDKdf19U8gI4ddQ3GYNS7NTKfAdVQSZe" crossorigin="anonymous"></script>
</body>
</html>