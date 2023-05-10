package com.example.demo.security;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Component;

import com.example.demo.domain.Member;
import com.example.demo.mapper.MemberMapper;

@Component
public class CustomUserDetailsService implements UserDetailsService {

	@Autowired
	private MemberMapper mapper;

	@Override
	// username은 form에서 넘어오는 것이다.
	public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {

		// form을 통해서 로그인을 하게 되면 기본적으로 username 과 password는
		// Authentication 객체에 저장된다.
		// 이 과정에서 UserDetailsService 인터페이스를 구현한 CustomUserDetailsService 클래스가
		// 호출되어 사용자 정보를 가져오고 그 정보를 입력한 정보와 비교하여 인증 여부 결정
		// 실제로 해당 클래스를 주석 처리 한 뒤, form으로 값을 넘겨도 변화가 없음을 확인함

		Member member = mapper.selectByMemberId(username);

		if (member == null) {
			throw new UsernameNotFoundException(username + " 회원이 없습니다.");
		}

		List<SimpleGrantedAuthority> authorityList = new ArrayList<>();
		for (String auth : member.getAuthority()) {
			authorityList.add(new SimpleGrantedAuthority(auth));
		}

		UserDetails user = User.builder()
				.username(member.getId())
				.password(member.getPassword())
//				.authorities(member.getAuthority().stream().map(SimpleGrantedAuthority::new).toList())
				.authorities(authorityList)
				.build();

		return user;
	}

}
