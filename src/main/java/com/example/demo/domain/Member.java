package com.example.demo.domain;

import java.time.LocalDateTime;
import java.util.List;

import lombok.Data;

@Data
public class Member {
	
	private String id;
	private String password;
	private String nickName;
	private String email;
	private LocalDateTime inserted;
	private List<String> authority;

}
