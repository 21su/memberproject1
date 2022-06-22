package com.its.memberproject.service;


import com.its.memberproject.dto.MemberDTO;
import com.its.memberproject.entity.MemberEntity;
import com.its.memberproject.repository.MemberRepository;
import lombok.RequiredArgsConstructor;
import org.apache.catalina.User;
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

    public boolean login(MemberDTO memberDTO) {
        Optional<MemberEntity> byMemberEmail = memberRepository.findByMemberEmail(memberDTO.getMemberEmail());
        System.out.println(byMemberEmail);
        if(byMemberEmail.isPresent()){
            MemberDTO loginDTO = MemberDTO.toDTO(byMemberEmail.get());
            if(loginDTO.getMemberPassword().equals(memberDTO.getMemberPassword())){
                return true;
            }else{
                return false;
            }
        }else{
            return false;
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
}
