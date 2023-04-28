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

	<my:navBar />

	<!-- action 을 안주면 다시 왔던 곳으로 돌아감   -->
<%-- 	<div class="container-lg">
		<h1>${board.id }게시물수정</h1>
		<form method="post">
			<div>
				<input type="hidden" name="id" value=${board.id } />
			</div>
			<div>
				제목 :
				<input type="text" name="title" value=${board.title } />
			</div>
			<div>
				본문 :
				<input type="text" name="body" value=${board.body } />
			</div>
			<div>
				작성자 :
				<input type="text" name="writer" value=${board.writer } />
			</div>
			<div>
				작성일시
				<input type="text" value=${board.inserted } />
			</div>
			<hr />
			<div>
				<input type="submit" value="수정" />
			</div>
		</form> --%>


		<div class="container-lg">
			<div class="row justify-content-center">
				<!-- 행 내에서 가운데 정렬함  -->
				<div class="col-12 col-md-8 col-lg-6">
					<!-- 반응형으로 큰화면에서는 12 중간 8 작은 6 화면은 나타낸다   -->
					<h1>${board.id }번 게시물 수정</h1>
					<form method="post">
						<div class="mb-3">
							<label for="titleInput" class="form-label">제목</label>
							<input type="text" id="titleInput" name="title" value="${board.title }" class="form-control" />
						</div>
						<div class="mb-3 ">
							<label for="bodyInput" class="form-label">본문</label>
							<textarea rows="10" name="body" id="bodyInput" class="form-control"></textarea>
						</div>
						<div class="mb-3 ">
							<label for="writerInput" class="form-label">글쓴이</label>
							<input type="text" id="writerInput" name="writer" value="${board.writer }" class="form-control" />
						</div>
<%-- 						<div class="mb-3 ">
							<label for="insertInput" class="form-label">작성일시</label>
							<input type="text" id="insertInput" name="writer" value="${board.inserted }" class="form-control" />
						</div> --%>
						<br />
						<input type="submit" value="수정" class="btn btn-primary" />
					</form>
				</div>
			</div>
		</div>



<%-- 		<c:if test="${not empty param.fail }">
			<script>
				alert("게시물이 수정되지 않았습니다. ")
			</script>
		</c:if> --%>

	</div>

	<script src="https://cdn.jsdelivr.net/npm/bootstrap@5.3.0-alpha3/dist/js/bootstrap.bundle.min.js" integrity="sha384-ENjdO4Dr2bkBIFxQpeoTz1HIcje39Wm4jDKdf19U8gI4ddQ3GYNS7NTKfAdVQSZe" crossorigin="anonymous"></script>
</body>
</html>