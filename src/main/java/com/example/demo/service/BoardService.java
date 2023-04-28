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

	public List<Board> listBoard(Integer page) {
		// 화면에 내가 원하는 갯수만을 보여주고 싶을 때, 
		Integer firstIndex = (page - 1) * 10;
		
		List<Board> list = mapper.selectAll(firstIndex);
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

	// Board 값들을 받기 위해서 dto Board 넣어줌
	// insert Query의 리턴 값은 int 이므로 int 를 반환하고
	// boolean 으로 자료형을 줬으므로 int == 1 이면 true 반환되게 함
	public boolean insertBoard(Board board) {
		int cnt = mapper.insert(board);
		return cnt == 1;

	}

	public Integer countAll() {
		return mapper.countAll();
	}

}
