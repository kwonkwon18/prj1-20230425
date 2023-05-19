package com.example.demo.mapper;

import java.util.List;

import org.apache.ibatis.annotations.Delete;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;

import com.example.demo.domain.Comment;

@Mapper
public interface CommentMapper {

	@Select("""
			select * from Comment where boardId = #{boardId}
			""")
	List<Comment> selectAllByBoardId(Integer boardId);

	@Insert("""
			insert into Comment (boardId, content, memberId)
			values (#{boardId}, #{content}, #{memberId})

			""")
	Integer insert(Comment comment);

	@Delete("""
			delete from Comment where id = #{id}
			""")
	Integer deleteMyId(Integer id);

	
	@Select("""
			select * from Comment
			where id = #{id}
			""")
	Comment selectById(Integer id);

}
