package com.its.memberproject.test;

import com.its.memberproject.dto.MemberDTO;
import com.its.memberproject.service.MemberService;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.Rollback;

import javax.transaction.Transactional;

import static org.assertj.core.api.Assertions.*;
@SpringBootTest
public class TestClass {
    @Autowired
    private MemberService memberService;

    @Test
    @Transactional
    @Rollback(value = true)
    @DisplayName("save 테스트")
    public void saveTest(){
        MemberDTO memberDTO = new MemberDTO("test@test.email","testpassword","testname",0,"testphone");
        Long id = memberService.save(memberDTO);
        MemberDTO resultDTO = memberService.findById(id);
        System.out.println(resultDTO);
        assertThat(resultDTO.getMemberEmail()).isEqualTo(memberDTO.getMemberEmail());
    }

    @Test
    @Transactional
    @Rollback(value = true)
    @DisplayName("login 테스트")
    public void loginTest(){
        MemberDTO memberDTO = new MemberDTO("test@test.email","testpassword","testname",0,"testphone");
        memberService.save(memberDTO);
        MemberDTO loginDTO = new MemberDTO("test@test.email","testpassword");
        boolean result = memberService.login(loginDTO);
        assertThat(result).isTrue();
    }
}
