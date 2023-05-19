package com.example.demo.domain;

import java.time.LocalDateTime;

import lombok.Data;

@Data
public class Comment {

	private Integer id;
	private Integer boardId;
	private String content;
	private String memberId;
	private LocalDateTime inserted;
	
}
