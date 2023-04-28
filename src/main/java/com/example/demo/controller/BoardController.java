package com.example.demo.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.example.demo.domain.Board;
import com.example.demo.service.BoardService;

@Controller
@RequestMapping("/")
public class BoardController {

	// mapper와 직접 연결되어 있는게 아니므로 필요 없다.
	// 직접 연결되는 건 service 로직에서 연결하고
	// 그 service 객체를 사용해서 작업을 처리하는 것이다.
//	@Autowired
//	private BoardMapper mapper;

	// 서비스 단과 연결할 것이다.
	@Autowired
	private BoardService service;

	// 경로 : http://localhost:8080?page=3
	// 경로 : http://localhost:8080/list?page=5
	// 게시물 목록
//	@RequestMapping(value = {"/", "list"}, method = RequestMethod.GET)
	@GetMapping({ "/", "list" }) // / 로도 들어갈 수 있고, list 로도 들어갈 수 있다.
	public String list(@RequestParam(value = "page", defaultValue = "1") Integer page ,Model model) { // String을 해줘서 / 와 list중 어디로 보낼것인지 명시

		// 2. business logic 처리
		// pagination 에 따라서 7개씩 끊어서 보여주기 
		List<Board> list = service.listBoard(page); // ==> 전체 게시물 보여주기

		// 3. add attribute
		model.addAttribute("boardList", list);

//		System.out.println(list.size());
		
		// pagination 
		// list에는 10개의 레코드씩 보여줄거야
		// pagination 에는 8개 페이지씩 끊을거야
		// 기본 page는 1로 할거야
		
		// 맨 왼쪽 페이지에 들어갈 숫자
		Integer leftPageNumber = (page - 1) / 8 * 8 + 1 ;
		// 맨 오른쪽 페이지에 들어갈 숫자
		Integer rightPageNumber = leftPageNumber + 7; 
		
		// 이전 페이지
		Integer prevPageNumber = leftPageNumber - 8 ; 
		// 다음 페이지 
		Integer nextPageNumber = rightPageNumber + 1;
		
		// 마지막 레코드 구하기
		Integer lastRecordNumber = service.countAll();
//		System.out.println(lastRecordNumber);
		// 마지막 페이지 구하기 
		Integer lastpageNumber = (lastRecordNumber - 1) / 10 + 1 ; 
		
		// rightPageNumber 은 마지막 페이지와 비교해서 구해야함
		// 그렇게 하지 않으면 정보가 없는 페이지도 출력하게됨
		rightPageNumber = Math.min(rightPageNumber, lastpageNumber);
		System.out.println(lastpageNumber);
		
		// 현재 페이지를 전송해줘야함 ==> active에 쓸 예정
		model.addAttribute("currentPage", page);
		
		// 현재까지 구한 숫자들을 모델에 담아서 보내줘야함 
		model.addAttribute("leftPageNumber", leftPageNumber);
		model.addAttribute("rightPageNumber", rightPageNumber);
		model.addAttribute("prevPageNumber", prevPageNumber);
		model.addAttribute("nextPageNumber", nextPageNumber);
		model.addAttribute("lastpageNumber", lastpageNumber);
		
		// 4. forward/redirect
		return "list";

	}

	@GetMapping("/id/{id}")
	public String board(@PathVariable("id") Integer id, Model model) {
		// 1. request param

		// 2. business logic
		// service 에서 일을 시킬 것
		Board board = service.getBoard(id);

		// 3. add attribute
		model.addAttribute("board", board);
		System.out.println(board);

		// 4. forward / redirect

		return "get";
	}

	// get으로는 우선 조회해서 기존 데이터를 보여줘야함
	// id를 queryString으로 받고
	// 그것은 아규먼트로 쓸 때는 @PathVariable()을 쓴다.
	@GetMapping("/modify/{id}")
	public String modifyForm(@PathVariable("id") Integer id,
			Model model) {

		// 조회 후 수정해야함
		model.addAttribute("board", service.getBoard(id));
		return "modify";
	}

	// get형식으로 modify에 줬던 경로를 그대로 가져오기 때문에
	// modifyForm 과 경로가 똑같아야한다.
//	@RequestMapping(value = "/modify/{id}", method = RequestMethod.POST)
	@PostMapping("/modify/{id}")
	public String modifyProcess(Board board, RedirectAttributes rttr) {

		boolean ok = service.modify(board);

		if (ok) {
			// 해당 게시물 보기로 리디렉션
			// 쿼리스트링에 붙어서 넘어감
			// 이 쿼리스트링을 param으로 받아줘서 여러가지 처리를 해줄 수 있음
//			rttr.addAttribute("success", "success");
			rttr.addFlashAttribute("message", board.getId() + " 번이 수정되었습니다.");
			return "redirect:/id/" + board.getId();
		} else {
			// 수정 form 으로 리디렉션
//			rttr.addAttribute("fail", "fail");
			return "redirect:/modify/" + board.getId();
		}

	}

	@PostMapping("remove")
	public String remove(Integer id, RedirectAttributes rttr) {
		boolean ok = service.remove(id);

		if (ok) {
			// 쿼리스트링에 추가
//			rttr.addAttribute("success", "remove");
			
			// 모델을 넘겨줌
			rttr.addFlashAttribute("message", id + " 번 게시물이 삭제되었습니다.");
			return "redirect:/list";
		} else {
			// 안되면 계속 남아있게
			rttr.addAttribute("faile", "remove");
			return "redirect:id/";
		}

	}

	// insert 연습해보기
	@GetMapping("add")
	public void addForm() {
		// 게시물 작성 form (새로운 게시물 입력폼)

//		List<Board> list = null;
//		model.addAttribute("board", list);
//		return "add";
		// 위 작업들은 해줄 필요없음
		// void로 해놓으면 그냥 날아가고 별도의 list를 만들 필요도 없음
		
		
	}

	@PostMapping("add")
	public String addProcess(Board board, RedirectAttributes rttr) {

		// 비즈니스 로직 함수
		// RedirectAttribtes 를 활용하여 queryString으로 받을수 있도록 함
		// top-down 방식으로 진행 하였다.
		// boolean 으로 값을 줘 추후 값을 응용할 수 있게 하였음
		// dto의 각 프로퍼티를 받는 것이므로 dto객체를 대입해줄 수 있다.
		boolean ok = service.insertBoard(board);

		if (ok) {
			rttr.addFlashAttribute("message", board.getId() + " 번 게시물이 추가되었습니다.");
			return "redirect:/id/" + board.getId(); // Mapper 에 @Options(useGeneratedKeys = true, keyProperty = "id")
													// 설정해줘서 가져옴
		} else {
			rttr.addFlashAttribute("board", board);
			return "add";
		}

	}
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	

}
