package com.example.demo.service;

import java.io.File;
import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import com.example.demo.domain.Board;
import com.example.demo.mapper.BoardMapper;

// 이 객체를 활용해서 spring bean 을 만들어줘라..
//@Component
@Service
// 보통 하나의 트랜젝션 이므로 붙혀주는게 좋다.
@Transactional(rollbackFor = Exception.class)
public class BoardService {

	@Autowired
	private BoardMapper mapper;

	public Board getBoard(Integer id) {
		return mapper.selectById(id);
	}

	public boolean modify(Board board) {
		int cnt = mapper.update(board);

		return cnt == 1;
	}

	public boolean remove(Integer id) {
		int cnt = mapper.deleteById(id);
		return cnt == 1;
	}

	// 트랜잭션해줘야함 ==> 다 되던가 다 안되던가
	// @Transactional ==> nuchecked는 얘가 잡고 (rollbackFor ) 부분이 checked 를 잡음 
	public boolean addBoard(Board board, MultipartFile[] files) throws Exception {
		// 파일 이름을 생성해줘야하기 때문에 얘가 먼저 와야함 
		int cnt = mapper.insert(board);
		
		
		// 게시물을 입력하고 나서, 프라이머리 키로 그 게시물의 폴더를 만들어줘 저장 할 수 있게 하자
		// 1. 게시물 번호로 폴더 만들기
		// 2. 트랜잭션 처리하기
		
		for (MultipartFile file : files) {
			if (file.getSize() > 0) {
				// 파일 저장 (파일 시스템에)
				String folder = "C:\\study\\upload\\" + board.getId();
				// 원하는 폴더를 만들 수 있는 객체 만들기
				File targetFolder = new File(folder);
				// 폴더가 없다면.. ==> 생성
				if(!targetFolder.exists()) {
					// 폴더 만들기
					targetFolder.mkdirs();
				}
				// 저장할 경로  ==> 위치\\이름 으로 기술하자
				String path = "C:\\study\\upload\\" + board.getId() + "\\" + file.getOriginalFilename();
				// 파일을 만들 수 있는 객체 생성
				File target = new File(path);
				// transferTo(file 객체) ==> 파일 만들기
				file.transferTo(target);
				// db에 관련 정보 저장(insert)
				 mapper.insertFileName(board.getId(), file.getOriginalFilename());
			}
		}

		// 게시물 insert
//		int cnt = 0; // 실패
		return cnt == 1;
	}

	public Map<String, Object> listBoard(Integer page, String search, String type) {
		// 페이지당 행의 수
		Integer rowPerPage = 15;

		// 쿼리 LIMIT 절에 사용할 시작 인덱스
		Integer startIndex = (page - 1) * rowPerPage;

		// 페이지네이션이 필요한 정보
		// 전체 레코드 수
		// search가 없을 때는 전체의 레코드 수 (search의 기본값이 "" 이기 때문에)
		// search가 있으면 search를 bind 처리 해서 갯수를 구해줌
		Integer numOfRecords = mapper.countAll(search, type);
		// 마지막 페이지 번호
		Integer lastPageNumber = (numOfRecords - 1) / rowPerPage + 1;
		// 페이지네이션 왼쪽번호
		Integer leftPageNum = page - 5;
		// 1보다 작을 수 없음
		leftPageNum = Math.max(leftPageNum, 1);

		// 페이지네이션 오른쪽번호
		Integer rightPageNum = leftPageNum + 9;
		// 마지막페이지보다 클 수 없음
		rightPageNum = Math.min(rightPageNum, lastPageNumber);

		Map<String, Object> pageInfo = new HashMap<>();
		pageInfo.put("rightPageNum", rightPageNum);
		pageInfo.put("leftPageNum", leftPageNum);
		pageInfo.put("currentPageNum", page);
		pageInfo.put("lastPageNum", lastPageNumber);

		// 게시물 목록
		List<Board> list = mapper.selectAllPaging(startIndex, rowPerPage, search, type);

		return Map.of("pageInfo", pageInfo,
				"boardList", list);
	}
}
