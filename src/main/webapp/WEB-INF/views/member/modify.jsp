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
<link rel="stylesheet" href="https://cdnjs.cloudflare.com/ajax/libs/font-awesome/6.4.0/css/all.min.css" integrity="sha512-iecdLmaskl7CVkqkXNQ/ZH/XLlvWZOJyj7Yy7tcenmpD1ypASozpmT/E0iPtmFIB46ZmdtAc9eNBvH0H/ZpiBw==" crossorigin="anonymous" referrerpolicy="no-referrer" />
</head>
<body>

	<my:navBar></my:navBar>

	<my:alert></my:alert>

	<div class="container-lg">
		<div class="row justify-content-center">
			<div class="col-12 col-md-8 col-lg-6">
				<h1>${member.id }회원정보수정</h1>
				<!-- action attr를 명시하면 쿼리스트링으로 붙지 않는다. -->
				<form id="modifyForm" action="/member/modify" method="post">
					<div class="mb-3">
						<label class="form-label" for="inputId">아이디</label> <input id="inputId" class="form-control" type="text" name="id" value="${member.id }" readonly />
					</div>
					<div class="mb-3">
						<label class="form-label" for="inputPassword">패스워드</label> <input id="inputPassword" class="form-control" type="text" name="password" value="" />
					</div>
					<div class="mb-3">
						<label class="form-label" for="inputNickName">닉네임</label> 
						<input id="inputNickName" class="form-control" type="text" name="nickName" value="${member.nickName }" />

						<!-- 중복 확인  -->
						<button type="button" id="checkNickNameBtn">닉네임 중복 확인</button>
						<div id="availableNickName" class = "d-none">사용 가능한 닉네임 입니다.</div>
						<div id="notavailableNickName" class = "d-none">사용 불가능한 닉네임 입니다.</div>
					</div>
					<div class="mb-3">
						<label class="form-label" for="inputEmail">이메일</label> 
						<input id="inputEmail" class="form-control" type="text" name="email" value="${member.email }" />
						
						<!-- 중복 확인  -->
						<button id="checkEmail" type="button">이메일 중복 확인</button>
						<div id="availableEmail" class = "d-none">사용가능한 이메일 입니다.</div>
						<div id="notavailableEmail" class = "d-none">중복된 이메일 입니다.</div>
					</div>
						
					<!-- Button trigger modal -->
					<button type="button" id = "modifyButton" disabled class="btn btn-primary" data-bs-toggle="modal" data-bs-target="#confirmModal">수정</button>
				</form>
			</div>
		</div>
	</div>

	<div class="modal fade" id="confirmModal" tabindex="-1" aria-labelledby="exampleModalLabel" aria-hidden="true">
		<div class="modal-dialog">
			<div class="modal-content">
				<div class="modal-header">
					<h1 class="modal-title fs-5" id="exampleModalLabel">수정하기</h1>
					<button type="button" class="btn-close" data-bs-dismiss="modal" aria-label="Close"></button>
				</div>
				<div class="modal-body">
					<label for="inputOldPassword" class="form-label">이전 암호</label> <input form="modifyForm" id="inputOldPassword" type="text" class="form-control" name="oldPassword" />
				</div>
				<div class="modal-footer">
					<button type="button" class="btn btn-secondary" data-bs-dismiss="modal">닫기</button>
					<button  type="submit" disabled form="modifyForm" class="btn btn-primary">확인</button>
				</div>
			</div>
		</div>
	</div>


	<script src="https://cdnjs.cloudflare.com/ajax/libs/jquery/3.6.4/jquery.min.js" integrity="sha512-pumBsjNRGGqkPzKHndZMaAG+bir374sORyzM3uulLV14lN5LyykqNk8eEeUlUkB3U0M4FApyaHraT65ihJhDpQ==" crossorigin="anonymous" referrerpolicy="no-referrer"></script>
	<script src="https://cdn.jsdelivr.net/npm/bootstrap@5.3.0-alpha3/dist/js/bootstrap.bundle.min.js" integrity="sha384-ENjdO4Dr2bkBIFxQpeoTz1HIcje39Wm4jDKdf19U8gI4ddQ3GYNS7NTKfAdVQSZe" crossorigin="anonymous"></script>

	<script src="/js/member/modify.js"></script>
</body>
</html>