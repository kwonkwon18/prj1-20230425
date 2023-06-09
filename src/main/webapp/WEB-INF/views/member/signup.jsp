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
<link href="https://cdn.jsdelivr.net/npm/bootstrap@5.3.0-alpha3/dist/css/bootstrap.min.css" rel="stylesheet" integrity="sha384-KK94CHFLLe+nY2dmCWGMq91rCGa5gtU4mk92HdvYe+M/SXH301p5ILy+dN9+nJOZ" crossorigin="anonymous">

</head>
<body>

	<my:navBar current="signup"></my:navBar>

	<my:alert></my:alert>

	<div class="container-lg">
		<div class="row justify-content-center">
			<div class="col-12 col-md-8 col-lg-6">
				<h1>회원가입</h1>
				<form method="post">
					<div class="mb-3">
						<label for="inputId" class=form-label">아이디</label> <input id="inputId" type="text" class="form-control" name="id" value="${member.id }" />
						<button type="button" id="checkIdBtn">중복확인</button>
						<div id="availableIdMessage">사용 가능한 아이디 입니다.</div>
						<div id="notAvailableIdMessage">사용 불가능한 아이디 입니다.</div>
					</div>


					<div class="mb-3">
						<label for="inputPassword" class=form-label">패스워드</label> 
						<input id="inputPassword" type="password" class="form-control" name="password" />
					</div>

					<div class="mb-3">
						<label for="inputPasswordCheck" class=form-label">패스워드 확인</label> <input id="inputPasswordCheck" type="password" class="form-control" />
					</div>

					<div id="passwordSuccessText" class="form-text text-primary d-none">패스워드가 일치합니다.</div>

					<div id="passwordFailText" class="form-text text-danger">패스워드가 일치하지 않습니다.</div>

					<div class="mb-3">
						<label for="inputNickName" class=form-label">별명</label> 
						<input id="inputNickName" type="text" class="form-control" name="nickName" value="${member.nickName }" />
						<button type ="button" id = "nickNameBtn">닉네임 중복 확인</button>
						<div id = "nickNameOk">사용 가능한 닉네임 입니다. </div>
						<div id = "nickNameBad">사용 불가능한 닉네임 입니다. </div>
						
					</div>
					<div class="mb-3">
						<label for="inputEmail" class=form-label">이메일</label>
						<input id="inputEmail" type="email" class="form-control" name="email" value="${member.email }" />
						<button id = "checkEmailBtn" type = "button">이메일 중복 확인</button>
						<div id = "availableEmail">사용가능한 이메일 입니다.  </div>
						<div id = "notavailableEmail">중복된 이메일 입니다.   </div>
					</div>
					<div class="mb-3">
						<input id="signupSubmit" class="btn btn-success" disabled type="submit" value="가입">
					</div>
				</form>
			</div>
		</div>
	</div>

	<script src="https://cdnjs.cloudflare.com/ajax/libs/jquery/3.6.4/jquery.min.js" integrity="sha512-pumBsjNRGGqkPzKHndZMaAG+bir374sORyzM3uulLV14lN5LyykqNk8eEeUlUkB3U0M4FApyaHraT65ihJhDpQ==" crossorigin="anonymous" referrerpolicy="no-referrer"></script>
	<script src="https://cdn.jsdelivr.net/npm/bootstrap@5.3.0-alpha3/dist/js/bootstrap.bundle.min.js" integrity="sha384-ENjdO4Dr2bkBIFxQpeoTz1HIcje39Wm4jDKdf19U8gI4ddQ3GYNS7NTKfAdVQSZe" crossorigin="anonymous"></script>

	<script src="/js/member/signup.js"></script>


</body>
</html>