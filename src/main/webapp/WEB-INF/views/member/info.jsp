<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="jakarta.tags.core"%>
<%@ taglib prefix="my" tagdir="/WEB-INF/tags"%>

<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<meta name="viewport" content="width=device-width, initial-scale=1">
<title>Insert title here</title>
<link rel="stylesheet" href="https://cdnjs.cloudflare.com/ajax/libs/font-awesome/6.4.0/css/all.min.css" integrity="sha512-iecdLmaskl7CVkqkXNQ/ZH/XLlvWZOJyj7Yy7tcenmpD1ypASozpmT/E0iPtmFIB46ZmdtAc9eNBvH0H/ZpiBw==" crossorigin="anonymous" referrerpolicy="no-referrer" />
<script src="https://cdnjs.cloudflare.com/ajax/libs/jquery/3.6.4/jquery.min.js" integrity="sha512-pumBsjNRGGqkPzKHndZMaAG+bir374sORyzM3uulLV14lN5LyykqNk8eEeUlUkB3U0M4FApyaHraT65ihJhDpQ==" crossorigin="anonymous" referrerpolicy="no-referrer"></script>
<link href="https://cdn.jsdelivr.net/npm/bootstrap@5.3.0-alpha3/dist/css/bootstrap.min.css" rel="stylesheet" integrity="sha384-KK94CHFLLe+nY2dmCWGMq91rCGa5gtU4mk92HdvYe+M/SXH301p5ILy+dN9+nJOZ" crossorigin="anonymous">
</head>
<body>

	<my:navBar></my:navBar>


	<my:alert></my:alert>


	<div class="container-lg">
		<!-- .row.justify-content-center>.col-12.col-md-8.col-lg-6 -->
		<div class="row justify-content-center">
			<div class="col-12 col-md-8 col-lg-6">
				<h1>${member.id }상세정보</h1>
				<div>
					<div class="mb-3">
						<label for="" class="form-label">아이디</label> <input type="text" class="form-control" value="${member.id }" readonly />
					</div>
					<div class="mb-3">
						<label for="" class="form-label">패스워드</label> <input type="text" class="form-control" value="${member.password }" readonly />
					</div>
					<div class="mb-3">
						<label for="" class="form-label">닉네임</label> <input type="text" class="form-control" value="${member.nickName }" readonly />
					</div>
					<div class="mb-3">
						<label for="" class="form-label">이메일</label> <input type="email" class="form-control" value="${member.email }" readonly />
					</div>
					<div class="mb-3">
						<label for="" class="form-label">작성일시</label> <input type="text" readonly class="form-control" value="${member.inserted }" />
					</div>
					<div>

						<a class="btn btn-secondary" href="/member/modify/${member.id }">수정</a>
						<button id="removeButton" class="btn btn-danger" data-bs-toggle="modal" data-bs-target="#deleteConfirmModal">삭제</button>
					</div>
				</div>
			</div>
		</div>
	</div>

	<div class="d-none">
		<form action="/member/remove" method="post" id="removeForm">
			<input type="text" name="id" value="${member.id }" />
		</form>
	</div>




	<script src="https://cdn.jsdelivr.net/npm/bootstrap@5.3.0-alpha3/dist/js/bootstrap.bundle.min.js" integrity="sha384-ENjdO4Dr2bkBIFxQpeoTz1HIcje39Wm4jDKdf19U8gI4ddQ3GYNS7NTKfAdVQSZe" crossorigin="anonymous"></script>
</body>
</html>