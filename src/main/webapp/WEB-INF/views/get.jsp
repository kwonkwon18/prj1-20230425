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

	<my:alert></my:alert>

	<my:navBar />

	<%-- 	<h1>${board.id }번게시물보기</h1>

	<div>제목 : ${board.title }</div>
	<div>본문 : ${board.body }</div>
	<div>작성자 : ${board.writer }</div>
	<div>작성일시 : ${board.inserted }</div> --%>


	<div class="container-lg">
		<div class="row justify-content-center">
			<!-- 행 내에서 가운데 정렬함  -->
			<div class="col-12 col-md-8 col-lg-6">
				<!-- 반응형으로 큰화면에서는 12 중간 8 작은 6 화면은 나타낸다   -->
				<h1>${board.id }번게시물</h1>
				<div class="mb-3">
					<label for="titleInput" class="form-label">제목</label>
					<input type="text" id="titleInput" name="title" value="${board.title }" class="form-control" readonly />
				</div>
				<div class="mb-3 ">
					<label for="bodyInput" class="form-label">본문</label>
					<textarea rows="10 name=" body" id="bodyInput" class="form-control" readonly rows="10">${board.body }</textarea>
				</div>
				<div class="mb-3 ">
					<label for="writerInput" class="form-label">글쓴이</label>
					<input type="text" id="writerInput" name="writer" value="${board.writer }" class="form-control" readonly />
				</div>
				<div class="mb-3 ">
					<label for="insertInput" class="form-label">글쓴이</label>
					<input type="text" id="" insertInput"" name="writer" value="${board.inserted }" class="form-control" readonly />
				</div>
				<br />
			</div>
		</div>
	</div>


	<div class="container-lg ">

		<div class="row flex-container">
			<div class="col">
					<!-- 수정폼 화면으로 이동해야함  -->
					<a class="btn btn-secondary" href="/modify/${board.id }"> 수정 </a>
					<!-- 삭제화면 이동 -->
					<!-- 모달 트리거  -->
				<button id="removeButton" class="btn btn-danger" data-bs-toggle="modal" data-bs-target="#deleteConfirmModal">삭제</button>
			</div>

		</div>
	</div>
	</div>

	
	<div class="d-none">
		<form action="/remove" method="post" id="removeForm">
			<input type="hidden" name="id" value="${board.id }" />
		</form>
	</div>

	<!-- 삭제하기 모달 -->
	<div class="modal fade" id="deleteConfirmModal" tabindex="-1" aria-labelledby="exampleModalLabel" aria-hidden="true">
		<div class="modal-dialog">
			<div class="modal-content">
				<div class="modal-header">
					<h1 class="modal-title fs-5" id="exampleModalLabel">삭제 확인</h1>
					<button type="button" class="btn-close" data-bs-dismiss="modal" aria-label="Close"></button>
				</div>
				<div class="modal-body">삭제 하시겠습니까?</div>
				<div class="modal-footer">
					<button type="button" class="btn btn-secondary" data-bs-dismiss="modal">닫기</button>
					<button type="submit" class="btn btn-danger" form="removeForm">삭제</button>
				</div>
			</div>
		</div>
	</div>

	<script src="https://cdn.jsdelivr.net/npm/bootstrap@5.3.0-alpha3/dist/js/bootstrap.bundle.min.js" integrity="sha384-ENjdO4Dr2bkBIFxQpeoTz1HIcje39Wm4jDKdf19U8gI4ddQ3GYNS7NTKfAdVQSZe" crossorigin="anonymous"></script>
	<script src="https://cdnjs.cloudflare.com/ajax/libs/jquery/3.6.4/jquery.min.js" integrity="sha512-pumBsjNRGGqkPzKHndZMaAG+bir374sORyzM3uulLV14lN5LyykqNk8eEeUlUkB3U0M4FApyaHraT65ihJhDpQ==" crossorigin="anonymous" referrerpolicy="no-referrer"></script>

	<!-- 	<script>
		$("#removeButton").click(function(e) {
			// 서브밋 진행 이벤트 막기
			e.preventDefault();

			const res = confirm("삭제 하시겠습니까?");
			if (res) {
				// 서브밋 실행
				$("#removeForm").submit();
			}
		}); -->
	<%-- 	</script>

	<c:if test="${not empty param.success }">
		<script>
			alert("게시물이 수정되었습니다.")
		</script>
	</c:if> --%>
</body>
</html>
