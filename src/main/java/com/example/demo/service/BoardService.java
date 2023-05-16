package com.example.demo.service;

import java.io.File;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import com.example.demo.domain.Board;
import com.example.demo.domain.Like;
import com.example.demo.mapper.BoardLikeMapper;
import com.example.demo.mapper.BoardMapper;

import software.amazon.awssdk.core.sync.RequestBody;
import software.amazon.awssdk.services.s3.S3Client;
import software.amazon.awssdk.services.s3.model.DeleteObjectRequest;
import software.amazon.awssdk.services.s3.model.ObjectCannedACL;
import software.amazon.awssdk.services.s3.model.PutObjectRequest;

// 이 객체를 활용해서 spring bean 을 만들어줘라..
//@Component
@Service
// 보통 하나의 트랜젝션 이므로 붙혀주는게 좋다.
@Transactional(rollbackFor = Exception.class)
public class BoardService {

	// s3 객체를 통해서 upload (put)과 delete 해주면 된다.
	@Autowired
	private S3Client s3;

	@Value("${aws.s3.bucketName}")
	private String bucketName;

	@Autowired
	private BoardMapper mapper;

	@Autowired
	private BoardLikeMapper likeMapper;

	public Board getBoard(Integer id, Authentication auth) {
		Board board = mapper.selectById(id);
		// 현재 로그인한 사람이 이 게시물에 좋아요을 했는지?
		if (auth != null) {
			Like like = likeMapper.select(id, auth.getName());
			if (like != null) {
				board.setLiked(true);
			}
		}

		return board;
	}

	public boolean modify(Board board, List<String> removeFileNames, MultipartFile[] addFiles) throws Exception {
		// FileName 테이블 삭제
		if (removeFileNames != null && !removeFileNames.isEmpty()) {
			for (String fileName : removeFileNames) {

				String objectKey = "board/" + board.getId() + "/" + fileName;

				DeleteObjectRequest dor = DeleteObjectRequest.builder()
						.bucket(bucketName)
						.key(objectKey)
						.build();

				s3.deleteObject(dor);

				// 로컬 하드디스크에서 삭제
//				String path = "C:\\study\\upload\\" + board.getId() + "\\" + fileName;
//				File file = new File(path);
//				if (file.exists()) {
//					file.delete();
//				}

				// 테이블에서 삭제
				mapper.deleteFileNameByBoardIdAndFileName(board.getId(), fileName);
			}
		}

		// 새 파일 추가
		for (MultipartFile newFile : addFiles) {
			if (newFile.getSize() > 0) {
				// 테이블에 파일명 추가
				mapper.insertFileName(board.getId(), newFile.getOriginalFilename());

				// s3에 파일(객체) 업로드
				String objectKey = "board/" + board.getId() + "/" + newFile.getOriginalFilename();

				// s3 첫번째 파라미터
				PutObjectRequest por = PutObjectRequest.builder()
						.bucket(bucketName)
						.key(objectKey)
						.acl(ObjectCannedACL.PUBLIC_READ)
						.build();

				// s3 두번째 파라미터
				RequestBody rb = RequestBody.fromInputStream(newFile.getInputStream(), newFile.getSize());

				s3.putObject(por, rb);

				// ** 로컬에 저장
//				String fileName = newFile.getOriginalFilename();
//				String folder = "C:\\study\\upload\\" + board.getId();
//				String path = "C:\\study\\upload\\" + board.getId() + "\\" + fileName;
//
//				// 디렉토리 없으면 만들기
//				File dir = new File(folder);
//				if (!dir.exists()) {
//					dir.mkdirs();
//				}
//
//				// 파일을 하드디스크에 저장
//				File file = new File(path);
//				newFile.transferTo(file);

			}
		}

		// Board 테이블을 업데이트 해줌
		int cnt = mapper.update(board);

		return cnt == 1;
	}

	public boolean remove(Integer id) {

		// 파일명 조회(파일명 알아야 하드디스크 파일 지울 수 있다)
		List<String> fileNames = mapper.selectFileNamesByBoardId(id);

		System.out.println(fileNames);

		// fileName 테이블의 데이터 지우기
		mapper.deleteFileNameByBoardId(id);

		// 하드디스크의 파일(객체) 지우기
		for (String fileName : fileNames) {

			String objectKey = "board/" + id + "/" + fileName;

			DeleteObjectRequest dor = DeleteObjectRequest.builder()
					.bucket(bucketName)
					.key(objectKey)
					.build();

			s3.deleteObject(dor);

			// ** 로칼에서 진행
//			String path = "C:\\study\\upload\\" + id + "\\" + fileName;
//			File file = new File(path);
//			if (file.exists()) {
//				file.delete();
//			}
		}

		// 게시물 테이블의 데이터 지우기
		int cnt = mapper.deleteById(id);
		return cnt == 1;
	}

	// 트랜잭션해줘야함 ==> 다 되던가 다 안되던가
	// @Transactional ==> nuchecked는 얘가 잡고 (rollbackFor ) 부분이 checked 를 잡음
	public boolean addBoard(Board board, MultipartFile[] files) throws Exception {
		// 파일 이름을 생성해줘야하기 때문에 얘가 먼저 와야함
		int cnt = mapper.insert(board);

		// 게시물을 입력하고 나서, 프라이머리 키로 그 게시물의 폴더를 만들어줘 저장 할 수 있게 하자
		// 1. 게시물 번호로 폴더 만들기
		// 2. 트랜잭션 처리하기

		for (MultipartFile file : files) {
			if (file.getSize() > 0) {

				String objectKey = "board/" + board.getId() + "/" + file.getOriginalFilename();

				// s3 첫번째 파라미터
				PutObjectRequest por = PutObjectRequest.builder()
						.bucket(bucketName)
						.key(objectKey)
						.acl(ObjectCannedACL.PUBLIC_READ)
						.build();

				// s3 두번째 파라미터
				RequestBody rb = RequestBody.fromInputStream(file.getInputStream(), file.getSize());

				s3.putObject(por, rb);

				// ** 로컬에 저장
//				// 파일 저장 (파일 시스템에)
//				// \\ <== 이거 적당하게 잘 넣어줘야한다.
//				String folder = "C:\\study\\upload\\" + board.getId();
//				// 원하는 폴더를 만들 수 있는 객체 만들기
//				File targetFolder = new File(folder);
//				// 폴더가 없다면.. ==> 생성
//				if (!targetFolder.exists()) {
//					// 폴더 만들기
//					targetFolder.mkdirs();
//				}
//				// 저장할 경로 ==> 위치\\이름 으로 기술하자
//				String path = "C:\\study\\upload\\" + board.getId() + "\\" + file.getOriginalFilename();
//				// 파일을 만들 수 있는 객체 생성
//				File target = new File(path);
//				// transferTo(file 객체) ==> 파일 만들기
//				file.transferTo(target);
				// db에 관련 정보 저장(insert)
				mapper.insertFileName(board.getId(), file.getOriginalFilename());
			}
		}

		// 게시물 insert
//		int cnt = 0; // 실패
		return cnt == 1;
	}

	public Map<String, Object> listBoard(Integer page, String search, String type) {
		// 페이지당 행의 수
		Integer rowPerPage = 15;

		// 쿼리 LIMIT 절에 사용할 시작 인덱스
		Integer startIndex = (page - 1) * rowPerPage;

		// 페이지네이션이 필요한 정보
		// 전체 레코드 수
		// search가 없을 때는 전체의 레코드 수 (search의 기본값이 "" 이기 때문에)
		// search가 있으면 search를 bind 처리 해서 갯수를 구해줌
		Integer numOfRecords = mapper.countAll(search, type);
		// 마지막 페이지 번호
		Integer lastPageNumber = (numOfRecords - 1) / rowPerPage + 1;
		// 페이지네이션 왼쪽번호
		Integer leftPageNum = page - 5;
		// 1보다 작을 수 없음
		leftPageNum = Math.max(leftPageNum, 1);

		// 페이지네이션 오른쪽번호
		Integer rightPageNum = leftPageNum + 9;
		// 마지막페이지보다 클 수 없음
		rightPageNum = Math.min(rightPageNum, lastPageNumber);

		Map<String, Object> pageInfo = new HashMap<>();
		pageInfo.put("rightPageNum", rightPageNum);
		pageInfo.put("leftPageNum", leftPageNum);
		pageInfo.put("currentPageNum", page);
		pageInfo.put("lastPageNum", lastPageNumber);

		// 게시물 목록
		List<Board> list = mapper.selectAllPaging(startIndex, rowPerPage, search, type);

		return Map.of("pageInfo", pageInfo,
				"boardList", list);
	}

	public void removeByWriter(String writer) {

		List<Integer> boardIdList = mapper.selectIdByWriter(writer);

		for (Integer id : boardIdList) {
			remove(id);
		}

	}

	public Map<String, Object> like(Like like, Authentication auth) {

		Map<String, Object> result = new HashMap<>();

		result.put("like", false);

		like.setMemberId(auth.getName());

		Integer deleteCnt = likeMapper.delete(like);

		if (deleteCnt != 1) {
			Integer insertCnt = likeMapper.insert(like);
			result.put("like", true);
		}

		// 좋아요 갯수 넘겨주기
		Integer count = likeMapper.countByBoardId(like.getBoardId());
		result.put("count", count);

		return result;
	}

	public Object getBoard(Integer id) {

		return getBoard(id, null);
	}
}
