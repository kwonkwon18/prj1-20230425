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

	// 데이터를 함께 보여줄 때는 두 테이블을 
	// left join 하여 보여줘야한다. 
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

	// fileName table 의 boardid가 
	// board 테이블의 id 를 참조하고 있음(외래키) 
	// 그냥 삭제하면 외래키 조약 사항에 위배된다. 
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

	// count(f.id) 를 해주는 이유는 한 게시물에 여러개의 id를 가지기 때문에 그 게시물을 
	// 기준으로 left join 할 경우 count(f.id)를 해주면 각 게시글마다 
	// 들어있는 파일의 갯수를 확인 할 수 있따. 
	@Select("""
			<script>
			<bind name="pattern" value="'%' + search + '%'" />
			SELECT
				b.id,
				b.title,
				b.writer,
				b.inserted,
				count(f.id) fileCount
			FROM Board b left join FileName f on b.id = f.boardId
			
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
			
			group by b.id
				
			ORDER BY b.id DESC
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

	
	
	@Select("""
			select fileName from FileName
			where boardId = #{id}
			""")
	List<String> selectFileNamesByBoardId(Integer id);

	
	@Delete("""
			delete from FileName
			where boardId = #{id} 
			""")
	void deleteFileNameByBoardId(Integer id);
	
	
	@Delete("""
			delete from FileName
			where BoardId = #{id} AND fileName = #{fileName}
			""")
	void deleteFileNameByBoardIdAndFileName(Integer id, String fileName);

}
