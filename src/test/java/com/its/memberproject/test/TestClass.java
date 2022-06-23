package com.its.memberproject.test;

import com.its.memberproject.dto.MemberDTO;
import com.its.memberproject.service.MemberService;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.Rollback;

import javax.transaction.Transactional;

import java.util.List;

import static org.assertj.core.api.Assertions.*;
@SpringBootTest
public class TestClass {
    @Autowired
    private MemberService memberService;

    public MemberDTO newMember() {
        MemberDTO memberDTO = new MemberDTO("test@test.email", "testpassword", "testname", 0, "testphone");
        return memberDTO;
    }

    @Test
    @Transactional
    @Rollback(value = true)
    @DisplayName("save 테스트")
    public void saveTest(){
        MemberDTO memberDTO = newMember();
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
        MemberDTO memberDTO = newMember();
        memberService.save(memberDTO);
        MemberDTO loginDTO = new MemberDTO("test@test.email","testpassword");
        MemberDTO resultDTO = memberService.login(loginDTO);
        assertThat(resultDTO).isNotNull();
    }

    @Test
    @Transactional
    @Rollback(value = true)
    @DisplayName("findAll 테스트")
    public void findAllTest(){
        for (int i = 0; i < 3; i++){
            MemberDTO memberDTO = new MemberDTO("test@test.email" + i,"testpassword" + i,"testname" + i,0,"testphone" + i);
            memberService.save(memberDTO);
        }
        List<MemberDTO> memberDTOList = memberService.findAll();
        assertThat(memberDTOList.size()).isEqualTo(3);
    }

    @Test
    @Transactional
    @Rollback(value = true)
    @DisplayName("delete 테스트")
    public void deleteById(){
        MemberDTO memberDTO = newMember();
        Long id = memberService.save(memberDTO);
        memberService.delete(id);
        MemberDTO resultDTO = memberService.findById(id);
        assertThat(resultDTO).isNull();
    }

    @Test
    @Transactional
    @Rollback(value = true)
    @DisplayName("update 테스트")
    public void updateTest(){
        MemberDTO memberDTO = newMember();
        Long id = memberService.save(memberDTO);
        MemberDTO findDTO = memberService.findById(id);
        memberDTO.setMemberName("업데이트이름");
        memberService.update(findDTO);
        MemberDTO resultDTO = memberService.findById(id);
        assertThat(findDTO.getMemberName()).isEqualTo(resultDTO.getMemberName());
    }
}
