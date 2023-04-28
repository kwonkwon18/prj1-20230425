package com.example.demo.mapper;

import java.util.List;

import org.apache.ibatis.annotations.Delete;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Options;
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
			  ORDER BY id desc
			  limit #{firstIndex}, 8;
					"""
			)
	List<Board> selectAll(Integer firstIndex);


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


	
	
	// insert문 추가
	// title, body, writer 만 추가하면 됨 (나머지는 자동추가)
	// #{] 를 활용하여 값을 넣어줄 준비를 함
	// dto Board 를 넣어 값을 받아줄 수 있게 하였다. 
	@Insert("""
			insert into Board
			(title, body, writer)
			values (#{title}, #{body}, #{writer})
			""")
	@Options(useGeneratedKeys = true, keyProperty = "id")
	int insert(Board board);


	@Select("""
			SELECT count(*) From Board;
			""")
	Integer countAll();
	
	
	
	
	
	
	
	
	
	

}
