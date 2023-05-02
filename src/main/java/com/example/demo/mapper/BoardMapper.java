package com.example.demo.mapper;

import java.util.List;

import org.apache.ibatis.annotations.Delete;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Options;
import org.apache.ibatis.annotations.ResultMap;
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
			select 
				b.id,
				b.title,
				b.body,
				b.inserted,
				b.writer,
				f.fileName
			from Board b left Join FileName f on b.id = f.boardid
			where b.id = #{id}
			""")
	@ResultMap("boardResultMap")
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
			
				<where>
				
				<if test = "(type eq 'all') or (type eq 'title')">
				title LIKE #{pattern}
				</if>
				

				<if test = "(type eq 'all') or (type eq 'writer')">
				OR writer LIKE #{pattern}
				</if>
				
				<if test = "(type eq 'all') or (type eq 'body')">
				OR body LIKE #{pattern}
				</if>
				
				</where> 
				
			ORDER BY id DESC
			LIMIT #{startIndex}, #{rowPerPage}
			</script>
			""")
	List<Board> selectAllPaging(Integer startIndex, Integer rowPerPage, String search, String type);

	@Select("""
			<script>
			<bind name="pattern" value="'%' + search + '%'" />
			SELECT COUNT(*)
			FROM Board
			
				<where>
				
				<if test = "(type eq 'all') or (type eq 'title')">
				title LIKE #{pattern}
				</if>
				

				<if test = "(type eq 'all') or (type eq 'writer')">
				OR writer LIKE #{pattern}
				</if>
				
				<if test = "(type eq 'all') or (type eq 'body')">
				OR body LIKE #{pattern}
				</if>
				
				</where> 
			</script>
			""")
	Integer countAll(String search, String type);

	@Insert("""
			insert into FileName (boardId, fileName)
			values (#{boardId}, #{fileName})
			""")
	void insertFileName(Integer boardId, String fileName);

}
