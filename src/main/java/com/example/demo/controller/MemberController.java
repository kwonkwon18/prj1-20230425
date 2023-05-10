package com.example.demo.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.access.expression.WebExpressionAuthorizationManager;
import org.springframework.security.web.authentication.logout.SecurityContextLogoutHandler;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.example.demo.domain.Member;
import com.example.demo.service.MemberService;

import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

@Controller
@RequestMapping("member")
public class MemberController {

	@Autowired
	private MemberService service;

	// 로그인 하지 않은 사람만 접속 가능
	@GetMapping("signup")
	@PreAuthorize("isAnonymous()")
	public void signupForm() {

	}

	@PostMapping("signup")
	@PreAuthorize("isAnonymous()")
	public String signupProcess(Member member, RedirectAttributes rttr) {

		try {
			service.signup(member);
			rttr.addFlashAttribute("member", member);
			rttr.addFlashAttribute("message", "회원 가입되었습니다 ⭕⭕");
			return "redirect:/list";

		} catch (Exception e) {
			e.printStackTrace();
			rttr.addFlashAttribute("member", member);
			rttr.addFlashAttribute("message", "회원 가입 실패 ❌❌");
			return "redirect:/member/signup";
		}
	}

	@GetMapping("list")
	@PreAuthorize("hasAuthority('admin')")
	public void list(Model model) {

		List<Member> list = service.listMember();

		model.addAttribute("memberList", list);
	}

	// 경로 /member/info?id=kwonkwon
	@GetMapping("info")
	@PreAuthorize("isAuthenticated() and authentication.name eq #id")
	public void get(String id, Model model) {

		Member member = service.get(id);

		model.addAttribute("member", member);

	}

	@PostMapping("remove")
	@PreAuthorize("isAuthenticated() and authentication.name eq #member.id")
	public String remove(Member member, RedirectAttributes rttr, HttpServletRequest request) throws Exception {

		boolean ok = service.remove(member);

		if (ok) {
			rttr.addFlashAttribute("message", "회원 탈퇴 되었습니다. ");
			
			//  로그아웃도 해줘야함 
			request.logout();
			
			return "redirect:/list";
		} else {
			rttr.addFlashAttribute("message", "회원 탈퇴시 문제가 발생되었습니다. ");
			return "redirect:/member/info?id=" + member.getId();
		}

	}

	@GetMapping("modify")
	@PreAuthorize("isAuthenticated() and authentication.name eq #member.id")
	public void modifyForm(Member member, Model model) {

		model.addAttribute("member", service.get(member.getId()));

	}

	@PostMapping("modify")
	@PreAuthorize("isAuthenticated() and authentication.name eq #member.id")
	public String modifyProcess(Member member, RedirectAttributes rttr, String oldPassword) throws Exception {

		boolean ok = service.modifyMember(member, oldPassword);

		if (ok) {
			rttr.addFlashAttribute("message", "수정되었습니다. ");
			
			
			return "redirect:/member/info?id=" + member.getId();
		} else {
			rttr.addFlashAttribute("message", "수정되지 않았습니다.  ");
			return "redirect:/member/info?id=" + member.getId();
		}

	}

	@GetMapping("login")
	@PreAuthorize("isAnonymous()")
	public void loginForm() {

	}

}
