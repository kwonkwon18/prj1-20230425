package com.example.demo.mapper;

import java.util.List;

import org.apache.ibatis.annotations.Delete;
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
	
	
	
	@Select("""
			select 
				id,
				password,
				nickName,
				email,
				inserted 
			from Member
			ORDER BY inserted DESC
			""")
	List<Member> selectAll();


	@Select("""
			select * from Member where id = #{id}
			""")
	Member selectByMemberId(String id);



	@Delete("""
			delete from Member
			Where id = #{id}
			""")
	int deleteById(String id);

}
