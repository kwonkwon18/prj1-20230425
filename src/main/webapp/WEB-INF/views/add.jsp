<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="jakarta.tags.core"%>
<%@ taglib prefix="my" tagdir="/WEB-INF/tags"%>

<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<meta name="viewport" content="width=device-width, initial-scale=1">
<title>Insert title here</title>

<link href="https://cdn.jsdelivr.net/npm/bootstrap@5.3.0-alpha3/dist/css/bootstrap.min.css" rel="stylesheet" integrity="sha384-KK94CHFLLe+nY2dmCWGMq91rCGa5gtU4mk92HdvYe+M/SXH301p5ILy+dN9+nJOZ" crossorigin="anonymous">
</head>
<body>

	<!-- post 로 입력한 내용을 보낼 수 있게 해줌   -->
	<!-- dto property를 활용하여 이용  -->
	<!-- id와 inserted 는 자동적으로 기입이 되는 형식이라 히든으로 값만 전해줄 수 있게 함  -->

	<my:navBar current="add" />


	<!-- 같은 주소로 보내기  -->

	<%-- 		<div>
			<input type="hidden" name="id" value="${board.id }" />
		</div> --%>
	<%-- 		<div>
			<input type="hidden" name="inserted" value="${board.inserted }" />
		</div> --%>

	<div class="container-lg">
		<div class="row justify-content-center">
			<!-- 행 내에서 가운데 정렬함  -->
			<div class="col-12 col-md-8 col-lg-6">
				<!-- 반응형으로 큰화면에서는 12 중간 8 작은 6 화면은 나타낸다   -->
				<h1>추가 정보 입력</h1>
				<form method="post">
					<div class="mb-3">
						<label for="titleInput" class="form-label">제목</label>
						<input type="text" id="titleInput" name="title" value="${board.title }" class="form-control" />
					</div>
					<div class="mb-3 ">
						<label for="bodyInput" class="form-label">본문</label>
						<textarea rows="10" name= "body" id="bodyInput" class="form-control"></textarea>
					</div>
					<div class="mb-3 ">
						<label for="writerInput" class="form-label">글쓴이</label>
						<input type="text" id="writerInput" name="writer" value="${board.writer }" class="form-control" />
					</div>
					<br />
					<input type="submit" value="제출" class="btn btn-primary" />
				</form>
			</div>
		</div>
	</div>


	<script src="https://cdn.jsdelivr.net/npm/bootstrap@5.3.0-alpha3/dist/js/bootstrap.bundle.min.js" integrity="sha384-ENjdO4Dr2bkBIFxQpeoTz1HIcje39Wm4jDKdf19U8gI4ddQ3GYNS7NTKfAdVQSZe" crossorigin="anonymous"></script>
</body>
</html>