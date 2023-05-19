package com.example.demo.service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

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

	public Map<String, Object> add(Comment comment) {
		comment.setMemberId("kwon");

		var res = new HashMap<String, Object>();

		int cnt = mapper.insert(comment);

		if (cnt == 1) {
			res.put("message", "댓글이 등록되었습니다. ");

		} else {
			res.put("message", "댓글이 등록되지 않았습니다. ");

		}

		return res;
	}

	public Map<String, Object> remove(Integer id) {
		int cnt = mapper.deleteMyId(id);

		var res = new HashMap<String, Object>();

		if (cnt == 1) {
			res.put("message", "삭제되었습니다.");
		} else {
			res.put("message", "삭제되지 않았습니다..");
		}

		return res;

	}

	public Comment get(Integer id) {

		return mapper.selectById(id);
	}

	public Map<String, Object> update(Comment comment) {

		int cnt = mapper.update(comment);

		var res = new HashMap<String, Object>();
		if (cnt == 1) {
			res.put("message", "수정되었습니다..");
		} else {
			res.put("message", "수정되지 않았습니다. . ");
		}

		return res;
	}

}
