package com.example.demo.mapper;

import java.util.List;

import org.apache.ibatis.annotations.Delete;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.Update;

import com.example.demo.domain.Board;

@Mapper
public interface BoardMapper {
	
	@Select(
			"""
			select 
					id,
					title,
					writer,
					inserted
			  from Board 
			  ORDER BY id desc;
					"""
			)
	List<Board> selectAll();


	@Select("""
			SELECT 
				*
				from Board
				WHERE id = #{id} 
			""")
	Board selectById(Integer id);


	
	@Update("""
			update Board
			SET
				title = #{title},
				body = #{body},
				writer = #{writer}
			WHERE id = #{id}
			""")
	int update(Board board);


	
	
	@Delete("""
			delete from Board
			where id = #{id}
			
			""")
	int delecteById(Integer id);
	
	
	
	
	
	
	
	
	
	

}
