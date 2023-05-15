let checkEmail = true;
let checkNickName = true;


// 이메일 중복
$("#checkEmail").click(function() {

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
		 complete: enableSubmit
		
	})
}

)

$("#inputEmail").keyup(function(){
	checkEmail = false;
	enableSubmit();
})

$("#inputNickName").keyup(function(){
	checkNickName = false;
	enableSubmit();
})








// 닉네임 중복 확인 버튼 클릭 시
$("#checkNickNameBtn").click(function() {
	const nickName = $("#inputNickName").val();

	$.ajax("/member/checkNickName/" + nickName, {
		
		success: function(data) {

			if (data.available) {
				$("#availableNickName").removeClass("d-none");
				$("#notavailableNickName").addClass("d-none");
				checkNickName = true;
			} else {
				$("#notavailableNickName").removeClass("d-none");
				$("#availableNickName").addClass("d-none");
				checkNickName = false;
			}


		},
		complete: enableSubmit
	})

});


function enableSubmit(){
	if(checkEmail && checkNickName){
		$("#modifyButton").removeAttr("disabled");
	} else {
		$("#modifyButton").attr("disabled" , "");
	}
}

// input 아이디에 키보드입력 발생 시 
$("#inputNickName").keyup(function(){
	// 아이디 중복 확인 다시
	checkNickName = false;
	$("#availableNickName").addClass("d-none");
	$("#notavailableNickName").addClass("d-none");
	
	// submit 버튼 활성화
	enableSubmit();
	
})

function enableSubmit(){
	if(checkEmail && checkNickName){
		$("#modifyButton").removeAttr("disabled");
	} else {
		$("#modifyButton").attr("disabled" , "");
	}
}

// input 아이디에 키보드입력 발생 시 
$("#inputEmail").keyup(function(){
	// 아이디 중복 확인 다시
	checkEmail = false;
	$("#availableEmail").addClass("d-none");
	$("#notavailableEmail").addClass("d-none");
	
	// submit 버튼 활성화
	enableSubmit();
	
})