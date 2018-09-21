package com.ktds.board.web;

import java.io.File;
import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.util.UUID;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import javax.validation.Valid;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.validation.Errors;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.SessionAttribute;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.ModelAndView;

import com.ktds.board.service.BoardService;
import com.ktds.board.vo.BoardSearchVO;
import com.ktds.board.vo.BoardVo;
import com.ktds.common.exceptions.PolicyViolationException;
import com.ktds.common.session.Session;
import com.ktds.common.web.DownloadUtil;
import com.ktds.member.vo.MemberVo;
import com.nhncorp.lucy.security.xss.XssFilter;

import io.github.seccoding.web.mimetype.ExtFilter;
import io.github.seccoding.web.mimetype.ExtensionFilter;
import io.github.seccoding.web.mimetype.ExtensionFilterFactory;
import io.github.seccoding.web.pager.explorer.PageExplorer;


@Controller
public class BoardController {
	
	//	private Logger logger = LoggerFactory.getLogger(BoardController.class);
	private Logger statisticsLogger = LoggerFactory.getLogger("list.statistics");
	
	// parameter와 method의 결과를 찍고 싶은 log
	private Logger paramLogger = LoggerFactory.getLogger(BoardController.class);
	
	/**
	 * 이 객체만 쓰라고 특정시켜줘야함
	 * boardService 타입이 두개이므로
	 * impl1 , impl2
	 * 
	 * 따라서 에러 발생
	 * 
	 * 특정시키는 것 qualifier
	 */
	// properties에 있는 내용 얻어오기위해서는 @Value
	@Value("${upload.path}")
	private String uploadPath;
	
	@Autowired
	@Qualifier("boardServiceImpl")
	private BoardService boardService;
	
	/*
	 * 총 여섯개의 객체
	 * controller1
	 * 
	 */
	
	// 검색을 초기화
	// 하는 이유 접속자 수가 많을 때 boardSearchVO를 유지시키면 메모리를 많이 차지
	// 따라서 사용
	@RequestMapping("/board/list/init")
	// session을 지우기 위해 HttpSession을 파라미터로
	public String viewBoardListPageForInitiate(HttpSession session) {
		// search를 가지고 있는 Session의 attribute를 지워라
		session.removeAttribute(Session.SEARCH);
		return "redirect:/board/list";
	}
	
	@RequestMapping("/board/list")
	public ModelAndView viewBoardListPage(
			@ModelAttribute BoardSearchVO boardSearchVO 
			,HttpServletRequest request
			,HttpSession session) {
		
		// 전체검색 or 상세 -> 목록 or 글쓰기
		// 이럴 때 session에 있는걸 꺼내서 주자
		if( boardSearchVO.getSearchKeyword() == null) {
			boardSearchVO = (BoardSearchVO) session.getAttribute(Session.SEARCH);
			// 초기에 boardSearchVO는 null값을 가짐
			if ( boardSearchVO == null ) {
				boardSearchVO = new BoardSearchVO();
				boardSearchVO.setPageNo(0);
			}
		}
		
		
		//query 확인하기 위해 적었음.
//		this.boardService.createBoard(null);
		PageExplorer pageExplorer = this.boardService.readAllBoards(boardSearchVO);
		
		// 날짜, ip, 전체 게시글 건수를 log로 찍어보자
		statisticsLogger.info("URL : /board/list , IP : " 
		+ request.getRemoteAddr() 
		+ ", List Size : " + pageExplorer.getList().size());
		
		
		
		
		
		session.setAttribute(Session.SEARCH, boardSearchVO);
		
		// view는 string, model은 map
		// model and view 클래스는 단일 값으로 model과 view 값을 담을 수 있음.
		// 객체 생성시 view값을 부여
		
		ModelAndView view = new ModelAndView("/board/list");
		// map의 형태로 model 추가
		
		view.addObject("boardVoList", pageExplorer.getList());
		view.addObject("pagenation", pageExplorer.make());
		view.addObject("size", pageExplorer.getTotalCount());
		view.addObject("boardSearchVO", boardSearchVO);
		return view;
	}
	
	// get과 post 둘다 처리
	// 하나만 처리하고 싶으면 따로 적으면 됨.
//	@RequestMapping("/detail")
//	public ModelAndView viewBoardDetailPage() {
//		
//		// view 지정
//		ModelAndView view = new ModelAndView("board/detail");
//		
//		// key는 string만 
//		// value는 상관없음
//		view.addObject("message", "Hello, Spring");
//		view.addObject("name", "Spring Web MVC Framework");
//		view.addObject("age", 100);
//		view.addObject("isAgree", false);
//		
//		return view;
//	}
	
	
	
	
	
	
	
	// Spring 4.2이하에서 사용하는 코드
	// @RequestMapping(value="/write", method=RequestMethod.GET ) servlet의 get과 같은 역할
//	@RequestMapping(value="/write", method=RequestMethod.GET )
	// Spring 4.3 이상에서 사용.
	
	// query String으로 받는 것 -> Get
	@GetMapping("/board/write")
	public String viewBoardWritePage(
			// attribute가 없을 때 exception이 아니라 null값이 던져지길 원한다면 false, 반대는 true
			@SessionAttribute(name = Session.USER, required = false) MemberVo memberVo ) {
		
		if ( memberVo == null ) {
			return "redirect:/member/login";
		}
		
		return "board/write";
	}
	
	
	
	
	
	
	// post방식은 do로 시작해서 action으로 끝남.
	
	// form으로 받는 것
	
	@PostMapping("/board/write")
	public ModelAndView doBoardWriteAction(@Valid@ModelAttribute BoardVo boardVo
			, Errors erros
			, @SessionAttribute(Session.CSRF_TOKEN) String sessionToken
			, @SessionAttribute(Session.USER) MemberVo memberVo
			, HttpServletRequest request
			 ) {
		
		ModelAndView view = new ModelAndView("redirect:/board/list/init");
		
		
		if( !boardVo.getToken().equals(sessionToken)) {
			throw new RuntimeException("잘못된 접근입니다.");
		}
		
		// Validation Annotation이 실패했는지 체크
		if ( erros.hasErrors() ) {
			view.setViewName("board/write");
			view.addObject("boardVo", boardVo);
			
			return view;
		}
		
		
		
		MultipartFile uploadFile = boardVo.getFile();
		
		if ( !uploadFile.isEmpty() ) {
			// 실제 파일 이름
			String originFileName = uploadFile.getOriginalFilename();
			// 파일시스템에 저장될 파일이름
			String fileName = UUID.randomUUID().toString();
			
			File uploadDir = new File(this.uploadPath);
			
			// 폴더가 존재하지 않는다면 생성
			if (!uploadDir.exists()) {
				
				uploadDir.mkdirs();
			}
			
			// 파일이 업로드 될 경로 지정
			File destFile = new File(this.uploadPath, fileName);
			
			try {
				// 업로드
				uploadFile.transferTo(destFile);
				// DB에 File 정보 저장하기 위한 정보 셋팅
				boardVo.setOriginFileName(originFileName);
				boardVo.setFileName(fileName);
			} catch (IllegalStateException | IOException e) {
				throw new RuntimeException(e.getMessage(), e);
			} finally {
				if ( destFile.exists() ) {
					ExtensionFilter filter = ExtensionFilterFactory.getFilter(ExtFilter.APACHE_TIKA);
					boolean isImageFile = filter.doFilter(destFile.getAbsolutePath()
															, "img/bmp"
															, "img/png"
															, "img/jpeg"
															, "img/gif");
					if ( !isImageFile ) {
						destFile.delete();
						boardVo.setFileName("");
						boardVo.setOriginFileName("");
					}
							
							
				}
			}
		}
		
		boardVo.setMemberVo(memberVo);
		boardVo.setEmail(memberVo.getEmail());
		
		/*String view = this.boardService.createBoard(boardVo, memberVo) ? 
				"redirect:/board/list" : "redirect:/board/write";*/
		
		
		XssFilter filter = XssFilter.getInstance("lucy-xss-superset.xml");
		boardVo.setSubject(filter.doFilter(boardVo.getSubject()));
		boardVo.setContent(filter.doFilter(boardVo.getContent()));
		
		
		boolean isSuccesses = this.boardService.createBoard(boardVo, memberVo);
		
		String paramFormat = "IP : %s, Param : %s, Result : %s";
		
		paramLogger.debug(String.format(paramFormat
				, request.getRemoteAddr()
				, boardVo.getSubject() + ", "
				+ boardVo.getContent() + ", "
				+ boardVo.getEmail() + ", "
				+ boardVo.getFileName() + ", "
				+ boardVo.getOriginFileName()
				, view.getViewName() // list
				));
		
		
		return view;
	}
	
	//http://localhost:8080/HelloSpring/board/detail?id=1
	//?id=1 1번 게시물을 사용하겠다.
	// 원래 url에 있는 것은 전부 string
	// 하지만 우리는 int로 받고 싶다고 하면 @RequestParam 뒤에 원하는 데이터 타입을 써주면 됨.
	
	// 중괄호는 템플릿 
	// url을 적을때는 /board/detail/id번호 요런식으로
	@RequestMapping("/board/detail/{id}")
	// 로그인한 사용자의 정보를 받아야 되므로 
	public ModelAndView viewBoardDetailPage( @PathVariable int id
			, @SessionAttribute(Session.USER) MemberVo memberVo
			, HttpServletRequest request) {
		
		BoardVo boardVo = null;
		
		// 원래 try catch로 쓰면 안됨
		// 이후에 수정할 것임
		
		boardVo = this.boardService.readOneBoard(id, memberVo);
		
		
		ModelAndView view = new ModelAndView("/board/detail");
		view.addObject("boardVo", boardVo);
		
		String paramFormat = "IP:%s, Param:%s, Result:%s";
		
		paramLogger.debug(String.format(paramFormat
				, request.getRemoteAddr()
				, id
				, boardVo.getSubject() + ", "
				+ boardVo.getContent() + ", "
				+ boardVo.getEmail() + ", "
				+ boardVo.getFileName() + ", "
				+ boardVo.getOriginFileName()
				));
		
		return view;
	}
	
	
	
	
	// 삭제하고 보여줄 것 없으므로
	// list page로 이동
	
	@RequestMapping("/board/delete/{id}")
	public String doBoardDeleteAction(@PathVariable int id
			, HttpServletRequest request
			, @SessionAttribute(Session.USER) MemberVo memberVo) {
		boolean isSuccess = this.boardService.deleteBoard(id);
		
		String paramFormat = "IP : %s, Param : %s, Result : %s";
		
		paramLogger.debug(String.format(paramFormat
				, request.getRemoteAddr()
				, memberVo.getEmail()
				, id
				));
		
		return "redirect:/board/list";
	}
	
	@RequestMapping("/board/download/{id}")
	public void fileDownload(@PathVariable int id
			, HttpServletRequest request
			, HttpServletResponse response
			, @SessionAttribute(Session.USER) MemberVo memberVo) {
		if ( memberVo.getPoint() < 5 ) {
			throw new PolicyViolationException("다운로드를 위한 포인트가 부족합니다."
					, "/board/detail/" + id);
			
		}
		
		BoardVo boardVo = this.boardService.readOneBoard(id);
		
		String originFileName = boardVo.getOriginFileName();
		String fileName = boardVo.getFileName();
		
		
		// 윈도우의 경우 |
		// 유닉스/리눅스/맥 경우 /
		try {
			new DownloadUtil(this.uploadPath + File.separator + fileName)
				.download(request, response, originFileName);
			
		} catch (UnsupportedEncodingException e) {
			throw new RuntimeException(e.getMessage(),e);
		} 
	}
}
