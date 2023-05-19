package com.example.demo.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.example.demo.domain.Comment;
import com.example.demo.mapper.CommentMapper;

@Service
@Transactional(rollbackFor = Exception.class)
public class CommentService {
	
	@Autowired
	private CommentMapper mapper;
	

	public List<Comment> list(Integer boardId) {
		
		return mapper.selectAllByBoardId(boardId);
	}


	public void add(Comment comment) {
		comment.setMemberId("kwon");
		mapper.insert(comment);
		
	}


	public void remove(Integer id) {
		mapper.deleteMyId(id);
		
	}


	public Comment get(Integer id) {
		
		return mapper.selectById(id);
	}

	
	
	
}
