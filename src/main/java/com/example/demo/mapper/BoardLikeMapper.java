package com.example.demo.mapper;

import org.apache.ibatis.annotations.Delete;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;

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

	
	@Select("""
			select count(*) from BoardLike
			where boardId = #{boardId}
			""")
	Integer countByBoardId(Integer boardId);

	
	@Select("""
			select *
			from BoardLike
			where boardId = #{boardId}
			and memberId = #{memberId}
			""")
	Like select(Integer boardId, String memberId);
	
}
