package com.example.demo.controller;

import java.util.List;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller
@RequestMapping("comment")
public class CommentController {

	@GetMapping("/list")
	@ResponseBody
	public List<String> list(@RequestParam("board") Integer boardId) {
		return List.of("댓1", "댓2", "댓3");

	}

}
