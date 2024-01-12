package com.chukapoka.server.treemember.sevice;

import com.chukapoka.server.treemember.domain.TreeMember;
import com.chukapoka.server.treemember.dto.TreeMemberDto;
import com.chukapoka.server.treemember.repository.TreeMemberRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class TreeMemberService {

    @Autowired
    private TreeMemberRepository treeMemberRepository;


    public boolean emailCheck(String email) {
        return treeMemberRepository.existsByEmail(email);
    }

}
