package kr.inhatc.spring.member.controller;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import kr.inhatc.spring.member.dto.MemberFormDto;
import kr.inhatc.spring.member.entity.Member;
import kr.inhatc.spring.member.service.MemberService;
import lombok.extern.slf4j.Slf4j;

@Controller
@RequestMapping(value = "/member")
@Slf4j
public class MemberController {

    @Autowired
    private MemberService memberService;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @GetMapping("/login")
    public String login() {
        return "member/memberLoginForm"; 
    }
    
    @GetMapping("/login/error")
    public String loginError(Model model) {
        model.addAttribute("loginErrorMsg", "아이디 또는 비밀번호를 확인해주쇼");
        return "member/memberLoginForm";//에러가 발생하면서 다시 로그인 폼으로 돌아간다 
    }
    
    @GetMapping(value = "/new")
    public String memberForm(Model model) {
        model.addAttribute("memberFormDto", new MemberFormDto());
        return "member/memberForm";
    }

//    restfull 방식. 이름이 똑같아도 어노테이션이 다르니까 ㄱㅊ다 
//     하지만 오버로딩이 안 되면 에러 뜹니다 오버로딩 해줘야 함!
    @PostMapping(value = "/new")
    public String memberForm(@Valid MemberFormDto memberFormDto, BindingResult bindingResult, Model model){
        
//        annotation에서 에러가 있으면 다시 memberform으로 돌아간다 -> 바인딩 에러 시 처리
        if(bindingResult.hasErrors()) {
            return "member/memberForm";
        }
        try {  //회원가입을 처리하는 구문
            Member member = Member.createMember(memberFormDto, passwordEncoder);
            memberService.saveMember(member);
        }catch(IllegalStateException e) { //회원가입 처리 시 문제가 생기면 에러메세지 띄우기
            model.addAttribute("errorMessage", e.getMessage());
            //문제가 있으면 회원가입으로 돌아감
            return "member/memberForm";
        }
     
        return "redirect:/";
    }

}
