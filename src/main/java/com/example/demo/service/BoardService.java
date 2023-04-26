package com.example.demo.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.demo.domain.Board;
import com.example.demo.mapper.BoardMapper;

// 이 객체를 활용해서 spring bean 을 만들어줘라..
//@Component
@Service // => 서비스 역할을 하는 컴포넌트라느 표시 (컴포넌트 어노테이션 포함)
public class BoardService {

	@Autowired
	private BoardMapper mapper;

	public List<Board> listBoard() {
		
		List<Board> list = mapper.selectAll();
		return list;
	}

	public Board getBoard(Integer id) {
		return mapper.selectById(id);
	}

	public boolean modify(Board board) {

		int cnt = mapper.update(board);
		return cnt == 1;

	}

	public boolean remove(Integer id) {

		int cnt = mapper.delecteById(id);
		return cnt == 1;
	}

}
