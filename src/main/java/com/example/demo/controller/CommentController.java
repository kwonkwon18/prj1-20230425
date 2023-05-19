package com.example.demo.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
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
	
	@PostMapping("/add")
	@ResponseBody
	public String add(@RequestBody Comment comment) {
		commentService.add(comment);
		
		return "ok";
	}
	
	@DeleteMapping("id/{id}")
	@ResponseBody
	public String remove(@PathVariable("id") Integer id) {
		commentService.remove(id);
		return "ok";
	}
	
	@GetMapping("id/{id}")
	@ResponseBody
	public Comment get(@PathVariable("id") Integer id) {
		return commentService.get(id);
	}
	

}
