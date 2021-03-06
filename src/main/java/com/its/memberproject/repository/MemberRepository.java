package com.its.memberproject.repository;


import com.its.memberproject.entity.MemberEntity;
import org.apache.catalina.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface MemberRepository extends JpaRepository<MemberEntity, Long> {
    // select * from member_table where memberEmail = ?
    Optional<MemberEntity> findByMemberEmail(String memberEmail);
}
