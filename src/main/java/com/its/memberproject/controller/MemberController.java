package com.its.memberproject.controller;


import com.its.memberproject.dto.MemberDTO;
import com.its.memberproject.service.MemberService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpSession;
import java.util.List;

@Controller
@RequiredArgsConstructor
@RequestMapping("/member")
public class MemberController {

    private final MemberService memberService;

    @GetMapping("/save-form")
    public String saveForm(){
        return "/memberPages/save";
    }

    @PostMapping("/save")
    public String save(@ModelAttribute MemberDTO memberDTO){
        memberService.save(memberDTO);
        return "index";
    }

    @GetMapping("/login-form")
    public String loginForm(){
        return "/memberPages/login";
    }

    @PostMapping("/login")
    public String login(@ModelAttribute MemberDTO memberDTO,
                        HttpSession session){
        MemberDTO loginDTO = memberService.login(memberDTO);
        if(loginDTO != null){
            session.setAttribute("loginEmail", loginDTO.getMemberEmail());
            session.setAttribute("id", loginDTO.getId());
            return "/memberPages/main";
        }else {
            return "index";
        }
    }

    @GetMapping("/")
    public String list(Model model){
        List<MemberDTO> memberDTOList = memberService.findAll();
        model.addAttribute("memberList", memberDTOList);
        return "/memberPages/list";
    }

    @GetMapping("/{id}")
    public String findById(@PathVariable("id") Long id,
                           Model model){
        MemberDTO memberDTO = memberService.findById(id);
        model.addAttribute("memberDTO", memberDTO);
        return "/memberPages/findById";
    }

    @GetMapping("/ajax/{id}")
    public @ResponseBody MemberDTO findAjax(@PathVariable("id") Long id){
        MemberDTO memberDTO = memberService.findById(id);
        return memberDTO;
    }

    @GetMapping("/delete/{id}")
    public String delete(@PathVariable("id") Long id){
        memberService.delete(id);
        return "redirect:/member/";
    }

    @DeleteMapping("/{id}")
    public ResponseEntity adelete(@PathVariable("id") Long id){
        memberService.delete(id);
        return new ResponseEntity<>(HttpStatus.OK);
    }

    @GetMapping("/update")
    public String updateForm(HttpSession session,
                             Model model){
        Long id = (Long) session.getAttribute("id");
        MemberDTO updateDTO = memberService.findById(id);
        model.addAttribute("updateDTO", updateDTO);
        return "/memberPages/update";
    }

    @PostMapping("/update")
    public String update(@ModelAttribute MemberDTO memberDTO){
        memberService.update(memberDTO);
        return "redirect:/member/update";
    }

    @PutMapping("/update")
    public ResponseEntity aupdate(@ModelAttribute MemberDTO memberDTO){
        memberService.update(memberDTO);
        return new ResponseEntity<>(HttpStatus.OK);
    }

    @GetMapping("/mypage")
    public String mypage (){
        return "/memberPages/mypage";
    }

    // 이메일 중복체크
    @PostMapping("/emailCheck")
    public @ResponseBody String emailCheck(@RequestParam String memberEmail){
        String checkResult = memberService.emailCheck(memberEmail);
        return checkResult;
    }
}
