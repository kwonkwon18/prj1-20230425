package com.example.demo.controller;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.example.demo.domain.Board;
import com.example.demo.domain.Like;
import com.example.demo.service.BoardService;
import com.example.demo.service.MemberService;

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
	@PreAuthorize("isAuthenticated() and @customSecurityChecker.checkBoardWriter(authentication, #id)")
	public String modifyForm(@PathVariable("id") Integer id, Model model) {
		model.addAttribute("board", service.getBoard(id));
		return "modify";
	}

//	@RequestMapping(value = "/modify/{id}", method = RequestMethod.POST)
	@PostMapping("/modify/{id}")
	@PreAuthorize("isAuthenticated() and @customSecurityChecker.checkBoardWriter(authentication, #board.id)")
	// 수정하려는 게시물 id : board.getId()
	public String modifyProcess(Board board, RedirectAttributes rttr,
			@RequestParam(value = "removeFiles", required = false) List<String> removeFileNames,
			@RequestParam(value = "files", required = false) MultipartFile[] addFiles) throws Exception {
//		System.out.println(removeFileNames); 확인용
		
		// removeFileNames 로 넘어온 파일명을 찾아서 삭제 해줌
		
		
		// 새로 입력받은 file 폴더를 만들어줌
		
		
		// 변경된 것들을 테이블에 수정해줌
		
		boolean ok = service.modify(board, removeFileNames, addFiles);
		
		

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
	@PreAuthorize("isAuthenticated() and @customSecurityChecker.checkBoardWriter(authentication, #id)")
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
	
	// 로그인한 사람만 들어갈 수 있게 isAuthenticated()
	@GetMapping("add")
	@PreAuthorize("isAuthenticated()")
	public void addForm() {
		// 게시물 작성 form (view)로 포워드
	}

	@PostMapping("add")
	@PreAuthorize("isAuthenticated()")
	public String addProcess(
			// @requestParam("files") 를 해주는 이유는 input name을 files로 해서 보내주기 때문
			@RequestParam("files") MultipartFile[] files,
			Board board, RedirectAttributes rttr, Authentication authentication) throws Exception {
		// 새 게시물 db에 추가

		// 로그인 된 사람의 이름이 들어가게 함 
		board.setWriter(authentication.getName());
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
	
	@PostMapping("/like")
	public ResponseEntity<Map<String, Object>> like(@RequestBody Like like,
			Authentication auth) {
		System.out.println(like);
		System.out.println(auth);
		
		if(auth == null) {
		
			return ResponseEntity
					.status(403)
					.body(Map.of("message", "로그인 후 좋아요 클릭 해주세요"));
			
		} else {
			
			return ResponseEntity.ok().body(service.like(like,auth));
		}
		
		
	}
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
}