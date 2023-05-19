package com.example.demo.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.example.demo.domain.Comment;
import com.example.demo.service.CommentService;

@Controller
@RequestMapping("comment")
public class CommentController {
	
	@Autowired
	private CommentService commentService;
	

	@GetMapping("/list")
	@ResponseBody
	public List<Comment> list(@RequestParam("board") Integer boardId) {
		
//		return List.of("댓1", "댓2", "댓3");
		return commentService.list(boardId);

	}

}
