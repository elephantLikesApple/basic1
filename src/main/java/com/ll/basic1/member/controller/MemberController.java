package com.ll.basic1.member.controller;

import com.ll.basic1.base.resData.ResData;
import com.ll.basic1.base.resData.Rq;
import com.ll.basic1.member.entity.Member;
import com.ll.basic1.member.service.MemberService;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller
@AllArgsConstructor
public class MemberController {
    private MemberService memberService;
    private final Rq rq;

    @GetMapping("/member/login")
    public String showLogin() {
        return "/usr/member/login.html";
    }

    @GetMapping("/member/doLogin")
    @ResponseBody
    public ResData login(String username, String password) {
        if(username == null || username.trim().length() == 0)
            return ResData.of("F-3", "username(을)를 입력해주세요.");

        if(password == null || password.trim().length() == 0)
            return ResData.of("F-4", "password(을)를 입력해주세요.");

        ResData response = memberService.tryLogin(username, password);

        if(response.isSuccess()){
            rq.setSession("loginedMemberId", response.getData());
        }
        return response;
    }

    @GetMapping("/member/logout")
    @ResponseBody
    public ResData logout() {
        rq.removeSession("loginedMemberId");
        return ResData.of("S-1", "로그아웃 되었습니다.");
    }

    @GetMapping("/member/me")
    @ResponseBody
    public ResData showMe() {
        long loginedMemberId = rq.getSessionAsLong("loginedMemberId", 0);
        boolean isLogined = loginedMemberId > 0;

        if(!isLogined) {
            return ResData.of("F-1", "로그인 후 이용해주세요.");
        }

        Member member = memberService.findById(loginedMemberId);
        return ResData.of("S-1", "당신의 username(은)는 %s 입니다.".formatted(member.getUsername()));
    }
}
