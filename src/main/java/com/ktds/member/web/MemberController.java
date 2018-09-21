package com.ktds.member.web;



import java.util.HashMap;
import java.util.Map;
import java.util.Random;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.validation.Errors;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import com.ktds.member.service.MemberService;
import com.ktds.member.vo.MemberVo;

import validator.MemberValidator;

@Controller
public class MemberController {
	

	@Autowired
   	private MemberService memberService;
   
   	@GetMapping("member/regist")
   	public String viewMemberJoinPage() {
   		return "member/regist";
   	}
   	
   	@GetMapping("member/logout")
	public String doMemberLogoutAction( HttpSession session ) {
	    // Logout 
   		// 사용자의 session을 다 날려버림
   		session.invalidate();
   		return "redirect:/member/login";
	}
   	
   	
   	@PostMapping("member/check/duplicate")
   	@ResponseBody
   	public Map<String, Object> doCheckDuplicateEmail(@RequestParam String email){
   		
   		Random random = new Random();
   		
   		Map<String, Object> result = new HashMap<>();
   		
   		result.put("status", "OK");
   		result.put("duplicated", random.nextBoolean());
   		
   		return result;
   	}
   	
   	@PostMapping("member/regist")
   	//중괄호를 쓰는 이유 validated가 배열을 받기때문
    public ModelAndView doMemberJoinAction( @Validated({MemberValidator.Regist.class})@ModelAttribute MemberVo memberVo, Errors erros ) {
   		
   		ModelAndView view = new ModelAndView("redirect:/member/login");
   		
   		if ( erros.hasErrors() ) {
   			view.setViewName("member/regist");
   			view.addObject("memberVo", memberVo);
   			
   			return view;
   		}
   		
   		if ( !(boolean)doCheckPasswordPolicy(memberVo.getPassword()).get("violate") ) {
         
	         view.setViewName("member/regist");
	         view.addObject("memberVo", memberVo);
	         
	         return view;
   		}

   		boolean isSuccesses = this.memberService.createNewMember(memberVo);
   		return view;
    }
	
   	
	@PostMapping("member/login")
	public ModelAndView doMemberLoginAction(@Validated({MemberValidator.Login.class})@ModelAttribute MemberVo memberVo
			, Errors erros
			, HttpSession session) {
		
		ModelAndView view = new ModelAndView("redirect:/board/list");
   		
		
   		if ( erros.hasErrors() ) {
   			view.setViewName("member/login");
   			view.addObject("memberVo", memberVo);
   			
   			return view;
   		}
		
   		boolean isBlockAccount = memberService.isBlockUser(memberVo.getEmail());

   		if ( !isBlockAccount ) {
   			MemberVo loginMemberVo = this.memberService.readOneMember(memberVo);
   			session.setAttribute("_USER_", loginMemberVo);
   			
   			this.memberService.unblockUser(memberVo.getEmail());
   		}
   		else {
   			view.setViewName("member/login");
   			view.addObject("memberVo", memberVo);
   			
   			return view;
   		}

		return view;
	}
	
	@GetMapping("member/login")
	public String viewMemberLoginPage() {
	      return "member/login";
	}
	 
	
	@PostMapping("member/check/passwordPolicy")
	   @ResponseBody
	   public Map<String, Object> doCheckPasswordPolicy(@RequestParam String password){
	      
	      Map<String, Object> resultMap = new HashMap<>();
	      
	      String passwordPolicy = "((?=.*[a-zA-Z])(?=.*[0-9])(?=.*[!@#$%^&*()]).{8,})";
	      
	      Pattern pattern = Pattern.compile(passwordPolicy);
	      Matcher matcher = pattern.matcher(password);
	      
	      boolean isMatcher = matcher.matches();
	      
	      resultMap.put("violate", isMatcher);
	      
	      return resultMap;
   }


}
