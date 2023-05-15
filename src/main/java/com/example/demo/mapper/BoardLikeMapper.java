package com.example.demo.mapper;

import org.apache.ibatis.annotations.Delete;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;

import com.example.demo.domain.Like;

@Mapper
public interface BoardLikeMapper {

	@Insert("""
			insert into BoardLike
			values (#{boardId}, #{memberId})
			""")
	int insert(Like like);

	
	@Delete("""
			delete from BoardLike
			where boardId = #{boardId} 
			and memberId = #{memberId}
			""")
	int delete(Like like);
	
}
