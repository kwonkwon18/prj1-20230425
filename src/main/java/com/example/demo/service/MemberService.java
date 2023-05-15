package com.example.demo.service;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.example.demo.domain.Member;
import com.example.demo.mapper.MemberMapper;

@Service
@Transactional(rollbackFor = Exception.class)
public class MemberService {

	@Autowired
	private MemberMapper mapper;
	
	@Autowired
	private BoardService boardservice;
	
	// 패스워드 인코더를 주입해준다.
	@Autowired
	private PasswordEncoder passwordEncoder;

	public boolean signup(Member member) {
		
		// 암호를 새롭게 세팅해 준다.
		// plain은 입력해서 받아들여지는 암호
		// setPasswordEncoder를 통해서 다시 password를 set
		String plain = member.getPassword();
		member.setPassword(passwordEncoder.encode(plain));
		

		int cnt = mapper.insert(member);

		return cnt == 1;

	}

	public List<Member> listMember() {

		return mapper.selectAll();
	}

	public Member get(String id) {

		return mapper.selectByMemberId(id);
	}

	public boolean remove(Member member) {
		Member oldMember = mapper.selectByMemberId(member.getId());
		int cnt = 0;
		
		// 암호화 이후
		// // matches(평문, 암호화된 문자) 
		if(passwordEncoder.matches(member.getPassword(), oldMember.getPassword())) {
			// 이 회원이 작성한 게시물 row 삭제 => 먼저 삭제 안해주면 외래키 법칙에 어긋난다 
			boardservice.removeByWriter(member.getId());
			
			
			// 회원테이블 삭제
			cnt = mapper.deleteById(member.getId());
		}
		
		// 암호화 이전
//		if (oldMember.getPassword().equals(member.getPassword())) {
//			// 암호가 같으면?
//			cnt = mapper.deleteById(member.getId());
//		}
		return cnt == 1;

	}

	public boolean modifyMember(Member member, String oldPassword) {
		
		// 패스워드를 바꾸기 위해 입력했다면.. 
		if(!member.getPassword().isBlank()) {
			String plain = member.getPassword();
			member.setPassword(passwordEncoder.encode(plain));
		}

		Member oldMember = mapper.selectByMemberId(member.getId());
		
		int cnt = 0;
		
		// matches(평문, 암호화된 문자) 
		if (passwordEncoder.matches(oldPassword, oldMember.getPassword())) {
			// 기존 암호와 같으면
			cnt = mapper.updateMemberById(member);
		} 
		
		if(oldMember.getPassword().equals(oldPassword)) {
			cnt = mapper.updateMemberById(member);
		}
		
		System.out.println(cnt);


		return cnt == 1;
	}

	public Map<String, Object> checkId(String id) {
		
		Member member = mapper.selectByMemberId(id);
		
		// null 일 때 트루를 반환한다. ==> 없어야 들어가는 것이기 때문에  
		return Map.of("available", member == null);
	}

	public Map<String, Object> nickName(String nickName) {
		
		Member member = mapper.selectByMemberNickName(nickName);
		
		return Map.of("available", member == null);
	}

	public Map<String, Object> checkEmail(String email) {
		
		Member member = mapper.selectByMemberEmail(email);
		
		return Map.of("available" , member == null);
	}
}
