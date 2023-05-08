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

	public boolean remove(Member member) {
		Member oldMember = mapper.selectByMemberId(member.getId());
		int cnt = 0;
		if (oldMember.getPassword().equals(member.getPassword())) {
			// 암호가 같으면?
			cnt = mapper.deleteById(member.getId());
		}
		return cnt == 1;

	}

	public boolean modifyMember(Member member, String oldPassword) {

		Member oldmember = mapper.selectByMemberId(member.getId());
		int cnt = 0;

		if (oldmember.getPassword().equals(oldPassword)) {
			mapper.updateMemberById(member);
			cnt = 1;
		}

		return cnt == 1;
	}
}
