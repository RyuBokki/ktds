package com.ktds.board.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;

import com.ktds.board.dao.BoardDao;
import com.ktds.board.vo.BoardSearchVO;
import com.ktds.board.vo.BoardVo;
import com.ktds.common.exceptions.PolicyViolationException;
import com.ktds.member.biz.MemberBiz;
import com.ktds.member.vo.MemberVo;
import com.ktds.reply.dao.ReplyDao;
import com.ktds.reply.vo.ReplyVO;

import io.github.seccoding.web.pager.Pager;
import io.github.seccoding.web.pager.PagerFactory;
import io.github.seccoding.web.pager.explorer.ClassicPageExplorer;
import io.github.seccoding.web.pager.explorer.PageExplorer;

@Service
public class BoardServiceImpl implements BoardService{

	
//	private BoardDao boardDao;
	@Autowired
	@Qualifier("boardDaoImplMyBatis")
	private BoardDao boardDao;
	
	// 게시글 조회시 댓글을 조회하기 위해 멤버변수
	@Autowired
	private ReplyDao replyDao;
	
//  없더라도 문제 발생 안함. point주고 받는 것 밖에 없으므로
//	@Autowired
//	@Qualifier("memberDaoImplMybatis")
//	private MemberDao memberDao;
	
	@Autowired
	private MemberBiz memberBiz;
	
	
	@Override
	public boolean createBoard( BoardVo boardVo , MemberVo memberVo) {
		
		// 업로드를 했다면
		// BoardVo의 생성자를 만들어줬기 때문에
		// 이부분을 수정 해 주어야함.
//		boolean isUploadFile = (boardVo.getOriginFileName() != null);
		boolean isUploadFile = (boardVo.getOriginFileName().length() > 0);
		
		int point = 10;
		
		if ( isUploadFile ) {
			point += 10;
		}
		
		this.memberBiz.updatePoint(memberVo.getEmail(), point);
		int memberPoint = memberVo.getPoint();
		memberPoint += point;
		memberVo.setPoint(memberPoint);
		
		return this.boardDao.insertBoard(boardVo) > 0;
	}

	@Override
	public boolean updateBoard( BoardVo boardVo ) {
		return this.boardDao.updateBoard(boardVo) > 0;
	}

	@Override
	public BoardVo readOneBoard(int id, MemberVo memberVo) {
		
		BoardVo boardVo = this.readOneBoard(id);
		
		List<ReplyVO> replyList = this.replyDao.selectAllReplies(id);
		boardVo.setReplyList(replyList);
		
		if ( !boardVo.getEmail().equals(memberVo.getEmail()) ) {
			if ( memberVo.getPoint() < 2 ) {
				throw new PolicyViolationException("포인트가 부족합니다", "/board/list");
			}
			this.memberBiz.updatePoint(memberVo.getEmail(), -2);
			
			int point = memberVo.getPoint();
			point -= 2;
			memberVo.setPoint(point);
		}
		return boardVo;	
	}
	
	@Override
	public BoardVo readOneBoard(int id) {
		
		return this.boardDao.selectOneBoard(id);
	}

	@Override
	public boolean deleteBoard(int id) {
		return this.boardDao.deleteOneBoard(id) > 0;
	}

	@Override
	public PageExplorer readAllBoards(BoardSearchVO boardSearchVO) {
		
		int totalCount = this.boardDao.selectAllBoardsCount(boardSearchVO);
		
		Pager pager = PagerFactory.getPager(Pager.ORACLE
											, boardSearchVO.getPageNo() + "");
		
		pager.setTotalArticleCount(totalCount);
		
		// classicPageExplorer 일반적인 형태
		// listPageExplorer 현재 선택한 페이지가 가운데 오게 하는것
		
		// boardSearchVo쓴이유 몇번 부터 시작해서 몇번까지 가져와라를 쓰기위해
		PageExplorer pageExplorer = pager.makePageExplorer(ClassicPageExplorer.class, boardSearchVO);
		
		pageExplorer.setList(this.boardDao.selectAllBoards(boardSearchVO));
		
		return pageExplorer;
	}
	
	
}
