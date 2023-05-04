package com.example.demo.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.example.demo.domain.Member;
import com.example.demo.mapper.MemberMapper;

@Service
@Transactional(rollbackFor = Exception.class)
public class MemberService {

	@Autowired
	private MemberMapper mapper;

	public boolean signup(Member member) {

		int cnt = mapper.insert(member);

		return cnt == 1;

	}

	public List<Member> listMember() {

		return mapper.selectAll();
	}

	public Member get(String id) {
		
		return mapper.selectByMemberId(id);
	}

	






	
	

}
