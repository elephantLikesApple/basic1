package com.ll.basic1.member.service;

import com.ll.basic1.base.resData.ResData;
import com.ll.basic1.member.entity.Member;
import com.ll.basic1.member.repository.MemberRepository;
import org.springframework.stereotype.Service;

import java.util.LinkedHashMap;

@Service
public class MemberService {
    private final MemberRepository memberRepository;
    public MemberService() {
        memberRepository = new MemberRepository();
    }
    public ResData tryLogin(String username, String password) {
        Member member = memberRepository.findByUsername(username);

        if(member == null)
            return ResData.of("F-2", "%s(은)는 존재하지 않는 회원입니다.".formatted(username));

        if(! member.getPassword().equals(password))
            return ResData.of("F-1", "비밀번호가 일치하지 않습니다.");

        return ResData.of("S-1", "%s 님 환영합니다.".formatted(username));
    }
}
