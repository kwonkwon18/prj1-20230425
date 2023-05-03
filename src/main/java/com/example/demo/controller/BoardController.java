package com.example.demo.controller;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.example.demo.domain.Board;
import com.example.demo.service.BoardService;

@Controller
@RequestMapping("/")
public class BoardController {

	@Autowired
	private BoardService service;

	// 경로 : http://localhost:8080?page=3
	// 경로 : http://localhost:8080/list?page=5
	// 게시물 목록
//	@RequestMapping(value = {"/", "list"}, method = RequestMethod.GET)
	@GetMapping({ "/", "list" })
	public String list(Model model,
			@RequestParam(value = "page", defaultValue = "1") Integer page,
			@RequestParam(value = "search", defaultValue = "") String search,
			@RequestParam(value = "type", required = false) String type) {
		// 1. request param 수집/가공
		// 2. business logic 처리
		// List<Board> list = service.listBoard(); // 페이지 처리 전
		Map<String, Object> result = service.listBoard(page, search, type); // 페이지 처리

		// 3. add attribute
//		model.addAttribute("boardList", result.get("boardList"));
//		model.addAttribute("pageInfo", result.get("pageInfo"));
		model.addAllAttributes(result);

		// 4. forward/redirect
		return "list";
	}

	@GetMapping("/id/{id}")
	public String board(@PathVariable("id") Integer id, Model model) {
		// 1. request param
		// 2. business logic
		Board board = service.getBoard(id);
		System.out.println(board);
		// 3. add attribute
		model.addAttribute("board", board);
		// 4. forward/redirect
		return "get";
	}

	@GetMapping("/modify/{id}")
	public String modifyForm(@PathVariable("id") Integer id, Model model) {
		model.addAttribute("board", service.getBoard(id));
		return "modify";
	}

//	@RequestMapping(value = "/modify/{id}", method = RequestMethod.POST)
	@PostMapping("/modify/{id}")
	public String modifyProcess(Board board, RedirectAttributes rttr,
			@RequestParam(value = "removeFiles", required = false) List<String> removeFileNames) {
//		System.out.println(removeFileNames); 확인용
		
		// removeFileNames 로 넘어온 파일명을 찾아서 삭제 해줌
		
		
		// 새로 입력받은 file 폴더를 만들어줌
		
		
		// 변경된 것들을 테이블에 수정해줌
		
		boolean ok = service.modify(board, removeFileNames);
		
		

		if (ok) {
			// 해당 게시물 보기로 리디렉션 ==> 다시 일을 해줘야하기 때문에 
//			rttr.addAttribute("success", "success");
			rttr.addFlashAttribute("message", board.getId() + "번 게시물이 수정되었습니다.");
			return "redirect:/id/" + board.getId();
		} else {
			// 수정 form 으로 리디렉션
//			rttr.addAttribute("fail", "fail");
			rttr.addFlashAttribute("message", board.getId() + "번 게시물이 수정되지 않았습니다.");
			return "redirect:/modify/" + board.getId();
		}
	}

	@PostMapping("remove")
	public String remove(Integer id, RedirectAttributes rttr) {
		boolean ok = service.remove(id);
		if (ok) {
			// query string에 추가
//			rttr.addAttribute("success", "remove");

			// 모델에 추가
			rttr.addFlashAttribute("message", id + "번 게시물이 삭제되었습니다.");

			return "redirect:/list";
		} else {
			return "redirect:/id/" + id;
		}
	}

	@GetMapping("add")
	public void addForm() {
		// 게시물 작성 form (view)로 포워드
	}

	@PostMapping("add")
	public String addProcess(
			// @requestParam("files") 를 해주는 이유는 input name을 files로 해서 보내주기 때문
			@RequestParam("files") MultipartFile[] files,
			Board board, RedirectAttributes rttr) throws Exception {
		// 새 게시물 db에 추가
		// 1.
		// 2.
		boolean ok = service.addBoard(board, files);
		// 3.
		// 4.
		if (ok) {
			rttr.addFlashAttribute("message", board.getId() + "번 게시물이 등록되었습니다.");
			return "redirect:/id/" + board.getId();
		} else {
			rttr.addFlashAttribute("message", "게시물 등록 중 문제가 발생하였습니다.");
			rttr.addFlashAttribute("board", board);
			return "redirect:/add";
		}
	}
}