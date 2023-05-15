let checkId = false;
let checkEmail = false;
let checkNickName = false;
let checkPassword = false;

function enableSubmit(){
	if(checkId && checkEmail && checkNickName && checkPassword){
		$("#signupSubmit").removeAttr("disabled");
	} else {
		$("#signupSubmit").Attr("disabled" , "");
	}
}



$("#inputPassword, #inputPasswordCheck").keyup(function() {
	// 패스워드에 입력한 값
	const pw1 = $("#inputPassword").val();
	// 패스워드확인에 입력한 값이
	const pw2 = $("#inputPasswordCheck").val();

	if (pw1 === pw2) {
		// 같으면
		// submit 버튼 활성화
		// $("#signupSubmit").removeAttr("disabled");
		// 패스워드가 같다는 메세지 출력
		$("#passwordSuccessText").removeClass("d-none");
		$("#passwordFailText").addClass("d-none");
		checkPassword = true;

	} else {
		// 그렇지 않으면
		// submit 버튼 비활성화
		// $("#signupSubmit").attr("disabled", "");
		// 패스워드가 다르다는 메세지 출력
		$("#passwordFailText").removeClass("d-none");
		$("#passwordSuccessText").addClass("d-none");
		checkPassword = false;

	}


})


// 결과적으로 아래 ajax로 들어가는 jsoㅜ은 "available"을 key로 갖는 
// Map 의 한쌍이다. 
$("#checkIdBtn").click(function() {

	const userId = $("#inputId").val();

	// 입력한 ID와 ajax요청 보내서
	$.ajax("/member/checkId/" + userId, {

		success: function(data) {

			// `{"available" : true}` ==> 이런 식으로 json 보내줄 예정임.

			if (data.available) {
				// 사용 가능하다는 메시지 출력
				$("#availableIdMessage").removeClass("d-none");
				$("#notAvailableIdMessage").addClass("d-none");
				checkId = true;

			} else {
				//  사용 불가하다는 메시지 출력 
				$("#availableIdMessage").addClass("d-none");
				$("#notAvailableIdMessage").removeClass("d-none");
				checkId = false;
			}

		}

	})
});

$("#nickNameBtn").click(function() {
	const nickName = $("#inputNickName").val();

	$.ajax("/member/checkNickName/" + nickName, {
		success: function(data) {

			if (data.available) {
				$("#nickNameOk").removeClass("d-none");
				$("#nickNameBad").addClass("d-none");
				checkNickName = true;
			} else {
				$("#nickNameBad").removeClass("d-none");
				$("#nickNameOk").addClass("d-none");
				checkNickName = false;
			}


		}
	})

});


$("#checkEmailBtn").click(function() {

	const email = $("#inputEmail").val();

	$.ajax("/member/checkEmail/" + email, {
		success: function(data) {
			
			if (data.available) {
				$("#availableEmail").removeClass("d-none");
				$("#notavailableEmail").addClass("d-none");
				checkEmail = true;
			} else {
				$("#notavailableEmail").removeClass("d-none");
				$("#availableEmail").addClass("d-none");
				checkEmail = false;

			}
		},
		complete : enableSubmit
	})
}

)

































