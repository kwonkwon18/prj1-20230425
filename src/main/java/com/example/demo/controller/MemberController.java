package com.example.demo.controller;

import org.eclipse.tags.shaded.org.apache.regexp.recompile;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.example.demo.domain.Member;
import com.example.demo.service.MemberService;

@Controller
@RequestMapping("member")
public class MemberController {
	
	@Autowired
	private MemberService service;
	

	@GetMapping("signup")
	public void signupForm() {

	}
	
	@PostMapping("signup")
	public String signupProcess(Member member, RedirectAttributes rttr) {
		
		boolean ok = service.signup(member);
		
		if(ok) {
			return "redirect:/list";
		} else {
			return "redirect:/member/signup";
		}
		
		
	}
}
