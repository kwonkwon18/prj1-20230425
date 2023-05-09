package com.example.demo.mapper;

import java.time.LocalDateTime;
import java.util.List;

import org.apache.ibatis.annotations.Delete;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.Update;

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
			DELETE FROM Member
			WHERE id = #{id}
			""")
	Integer deleteById(String id);



	@Update("""
			<script>
			
			UPDATE Member
			SET 
				<if test="password neq null and password neq ''">
				password = #{password},
				</if>
				
			    nickName = #{nickName},
			    email = #{email}
			WHERE
				id = #{id}
			
			</script>
			""")
	Integer updateMemberById(Member member);

}
