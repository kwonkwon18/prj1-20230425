$("#likeIcon").click(function() {
	// 게시물 번호 request body에 추가
	const boardId = $("#boardIdText").text().trim();
	// const data = {boardId : boardId};
	const data = { boardId };
	console.log(data)

	$.ajax("/like", {
		method: "post",
		contentType: "application/json",
		// 두 데이터는 이름만 같지 다른 데이터 변수다. 
		// 여기서 받는건 const로 선언한 data (내가 선언한)
		data: JSON.stringify(data),

		// 여기서 받는건 controller에서 넘어온 data
		success: function(data) {
			if (data.like) {
				//꽉 찬 하트
				$("#likeIcon").html(`<i class="fa-solid fa-heart"></i>`);
				console.log(data);
			} else {
				// 빈 하트
				$("#likeIcon").html(`<i class="fa-regular fa-heart"></i>`);
			}

		}

	});
});