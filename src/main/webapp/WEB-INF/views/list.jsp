<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="jakarta.tags.core" %>

<!DOCTYPE html>
<html>
<head>
<style>
  table {
    padding: 10px;
    border-spacing: 10px;
    width: 100%;
  }
</style>
<meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1">
<title>Insert title here</title>

 <link href="https://cdn.jsdelivr.net/npm/bootstrap@5.3.0-alpha3/dist/css/bootstrap.min.css" rel="stylesheet" integrity="sha384-KK94CHFLLe+nY2dmCWGMq91rCGa5gtU4mk92HdvYe+M/SXH301p5ILy+dN9+nJOZ" crossorigin="anonymous">
</head>
<body>

	<h1>게시물 목록 보기</h1>
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
						<td><a href="/id/${item.id }">${item.title }</a></td>
						<td>${item.inserted }</td>
						<td>${item.writer }</td>
					</tr>
				</c:forEach>
			</tbody>
		</table>
	</div>
	
	<c:if test = "${param.success eq 'remove' }">
	<script>
		alert("게시물이 삭제되었습니다")
	</script>
	</c:if>


	<script
		src="https://cdn.jsdelivr.net/npm/bootstrap@5.3.0-alpha3/dist/js/bootstrap.bundle.min.js"
		integrity="sha384-ENjdO4Dr2bkBIFxQpeoTz1HIcje39Wm4jDKdf19U8gI4ddQ3GYNS7NTKfAdVQSZe"
		crossorigin="anonymous"></script>
</body>
</html>