const boardId = $("#boardIdText").text().trim();
$.ajax("/comment/list?board=" + boardId, {
	
	method : "get",
	success : function(data){
		console.log(data);
		
	}
	
})