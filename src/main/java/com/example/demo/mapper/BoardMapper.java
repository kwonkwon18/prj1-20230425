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

	@Select("""
			SELECT
				id,
				title,
				writer,
				inserted
			FROM Board
			ORDER BY id DESC
			""")
	List<Board> selectAll();

	@Select("""
			SELECT *
			FROM Board
			WHERE id = #{id}
			""")
	Board selectById(Integer id);

	@Update("""
			UPDATE Board
			SET
				title = #{title},
				body = #{body},
				writer = #{writer}
			WHERE
				id = #{id}
			""")
	int update(Board board);

	@Delete("""
			DELETE FROM Board
			WHERE id = #{id}
			""")
	int deleteById(Integer id);

	@Insert("""
			INSERT INTO Board (title, body, writer)
			VALUES (#{title}, #{body}, #{writer})
			""")
	@Options(useGeneratedKeys = true, keyProperty = "id")
	int insert(Board board);

	@Select("""
			<script>
			<bind name="pattern" value="'%' + search + '%'" />
			SELECT
				id,
				title,
				writer,
				inserted
			FROM Board
			WHERE
				title LIKE #{pattern}
				OR body LIKE #{pattern}
				OR writer LIKE #{pattern}
			ORDER BY id DESC
			LIMIT #{startIndex}, #{rowPerPage}
			</script>
			""")
	List<Board> selectAllPaging(Integer startIndex, Integer rowPerPage, String search);

	@Select("""
			<script>
			<bind name="pattern" value="'%' + search + '%'" />
			SELECT COUNT(*)
			FROM Board
			WHERE
				title LIKE #{pattern}
				OR body LIKE #{pattern}
				OR writer LIKE #{pattern}
			</script>
			""")
	Integer countAll(String search);

}
