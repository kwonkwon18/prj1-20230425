package com.example.demo.mapper;

import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;

import com.example.demo.domain.Member;

@Mapper
public interface MemberMapper {

	@Insert("""
			insert into Member
			(id, password, nickName, email)
			values (#{id}, #{password}, #{nickName}, #{email})
			""")
	int insert(Member member);

}
