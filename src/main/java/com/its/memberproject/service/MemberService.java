package com.its.memberproject.service;


import com.its.memberproject.dto.MemberDTO;
import com.its.memberproject.entity.MemberEntity;
import com.its.memberproject.repository.MemberRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class MemberService {

    private final MemberRepository memberRepository;

    public Long save(MemberDTO memberDTO) {
        MemberEntity memberEntity = MemberEntity.toEntity(memberDTO);
        Long id = memberRepository.save(memberEntity).getId();
        return id;
    }

    public MemberDTO findById(Long id){
        Optional<MemberEntity> optionalMemberEntity = memberRepository.findById(id);
        if(optionalMemberEntity.isPresent()){
            MemberEntity memberEntity = optionalMemberEntity.get();
            return MemberDTO.toDTO(memberEntity);
        }else{
            return null;
        }
    }

    public MemberDTO login(MemberDTO memberDTO) {
        /**
         *  login.html 에서 이메일, 비번을 받아오고
         *  DB로 부터 해당 이메일의 정보를 가져와서
         *  입력받은 비번과 DB에서 조회한 비번의 일치여부를 판단하여
         *  일치하면 로그인 성공, 일치하지 않으면 로그인 실패로 처리
         */
        Optional<MemberEntity> byMemberEmail = memberRepository.findByMemberEmail(memberDTO.getMemberEmail());
        System.out.println(byMemberEmail);
        if(byMemberEmail.isPresent()){
            MemberDTO loginDTO = MemberDTO.toDTO(byMemberEmail.get());
            if(loginDTO.getMemberPassword().equals(memberDTO.getMemberPassword())){
                return loginDTO;
            }else{
                return null;
            }
        }else{
            return null;
        }
    }

    public List<MemberDTO> findAll() {
        List<MemberEntity> entityList = memberRepository.findAll();
        List<MemberDTO> memberDTOList = new ArrayList<>();
        for(int i = 0; i < entityList.size(); i++){
            memberDTOList.add(MemberDTO.toDTO(entityList.get(i)));
        }
        return memberDTOList;
    }

    public void delete(Long id) {
        memberRepository.deleteById(id);
    }

    public void update(MemberDTO memberDTO) {
        MemberEntity memberEntity = MemberEntity.toUpdateEntity(memberDTO);
        memberRepository.save(memberEntity).getId();
    }

    public String emailCheck(String memberEmail) {
        Optional<MemberEntity> optionalMemberEntity = memberRepository.findByMemberEmail(memberEmail);
        if(optionalMemberEntity.isEmpty()){
            return "ok";
        }else{
            return "no";
        }
    }
}
