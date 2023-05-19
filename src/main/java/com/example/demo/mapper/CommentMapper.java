package com.example.demo.mapper;

import java.util.List;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;

import com.example.demo.domain.Comment;

@Mapper
public interface CommentMapper {

	
	@Select("""
			select * from Comment where boardId = #{boardId}
			""")
	List<Comment> selectAllByBoardId(Integer boardId);

	
	
}
