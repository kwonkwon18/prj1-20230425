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
						<a class = "btn btn-primary" href="/member/modify?id=${member.id }">수정</a>
						<button class="btn btn-danger" type = "button" data-bs-target="#confirmModal" data-bs-toggle="modal">탈퇴</button>


					</div>
				</div>
			</div>
		</div>
	</div>

<!-- Modal -->
<div class="modal fade" id="confirmModal" tabindex="-1" aria-labelledby="exampleModalLabel" aria-hidden="true">
  <div class="modal-dialog">
    <div class="modal-content">
      <div class="modal-header">
        <h1 class="modal-title fs-5" id="exampleModalLabel">삭제 하시겠습니까?</h1>
        <button type="button" class="btn-close" data-bs-dismiss="modal" aria-label="Close"></button>
      </div>
      <div class="modal-body">
       			<form action="/member/remove" method="post" id="removeForm">
			<input type="hidden" name="id" value="${member.id }" />
			<label for="passwordInput1">암호</label>
			<input id = "" type="password" name = "password" class = "form-control"  />
		</form>
      </div>
      <div class="modal-footer">
        <button type="button" class="btn btn-secondary" data-bs-dismiss="modal">닫기</button>
        <button type="submit" form = "removeForm" class="btn btn-danger">확인</button>
      </div>
    </div>
  </div>
</div>




	<script src="https://cdn.jsdelivr.net/npm/bootstrap@5.3.0-alpha3/dist/js/bootstrap.bundle.min.js" integrity="sha384-ENjdO4Dr2bkBIFxQpeoTz1HIcje39Wm4jDKdf19U8gI4ddQ3GYNS7NTKfAdVQSZe" crossorigin="anonymous"></script>
</body>
</html>