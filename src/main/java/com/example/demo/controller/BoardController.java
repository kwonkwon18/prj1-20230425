package com.example.demo.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
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

	// 경로 : http://localhost:8080
	// 경로 : http://localhost:8080/list
	// 게시물 목록
//	@RequestMapping(value = {"/", "list"}, method = RequestMethod.GET)
	@GetMapping({ "/", "list" }) // / 로도 들어갈 수 있고, list 로도 들어갈 수 있다.
	public String list(Model model) { // String을 해줘서 / 와 list중 어디로 보낼것인지 명시

		// 1. request param 수집 가공

		// 2. business logic 처리
		List<Board> list = service.listBoard(); // ==> 전체 게시물 보여주기

		// 3. add attribute
		model.addAttribute("boardList", list);

		System.out.println(list.size());
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
			rttr.addAttribute("success", "success");
			return "redirect:/id/" + board.getId();
		} else {
			// 수정 form 으로 리디렉션
			rttr.addAttribute("fail", "fail");
			return "redirect:/modify/" + board.getId();
		}

	}
	
	@PostMapping("remove")
	public String remove(Integer id, RedirectAttributes rttr) {
		boolean ok = service.remove(id);
		
		if(ok) {
			rttr.addAttribute("success", "remove");
			return "redirect:/list";
		} else {
			//안되면 계속 남아있게
			rttr.addAttribute("faile", "remove");
			return "redirect:id/" + id;
		}
		
	}
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	

}
