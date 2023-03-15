package com.ll.basic1.member.controller;

import com.ll.basic1.base.resData.ResData;
import com.ll.basic1.member.entity.Member;
import com.ll.basic1.member.service.MemberService;
import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import java.io.IOException;
import java.util.Arrays;

@Controller
public class MemberController {
    private MemberService memberService;

    @Autowired
    public MemberController(MemberService memberService) {
        this.memberService = memberService;
    }

    @GetMapping("/member/login")
    @ResponseBody
    public ResData login(String username, String password, HttpServletResponse res) {
        if(username == null || username.trim().length() == 0)
            return ResData.of("F-3", "username(을)를 입력해주세요.");

        if(password == null || password.trim().length() == 0)
            return ResData.of("F-4", "password(을)를 입력해주세요.");
        ResData response = memberService.tryLogin(username, password);
        if(response.isSuccess()){
            long memberId = (long) response.getData();
            res.addCookie(new Cookie("loginedMemberId", memberId+""));
        }
        return response;
    }

    @GetMapping("/member/logout")
    @ResponseBody
    public ResData logout(HttpServletRequest req, HttpServletResponse res) {
        if(req.getCookies() != null) {
            Arrays.stream(req.getCookies())
                    .filter(cookie -> cookie.getName().equals("loginedMemberId"))
                    .forEach(cookie -> {
                        cookie.setMaxAge(0);
                        res.addCookie(cookie);
                    });
        }
        return ResData.of("S-1", "로그아웃 되었습니다.");
    }

    @GetMapping("/member/me")
    @ResponseBody
    public ResData showMe(HttpServletRequest req) {
        long loginedMemberId = 0L;

        if(req.getCookies() != null) {
            loginedMemberId = Arrays.stream(req.getCookies())
                    .filter(cookie -> cookie.getName().equals("loginedMemberId"))
                    .map(Cookie::getValue)
                    .mapToInt(Integer::parseInt)
                    .findFirst()
                    .orElse(0);
        }

        boolean isLogined = loginedMemberId > 0;

        if(!isLogined) {
            return ResData.of("F-1", "로그인 후 이용해주세요.");
        }

        Member member = memberService.findById(loginedMemberId);
        return ResData.of("S-1", "당신의 username(은)는 %s 입니다.".formatted(member.getUsername()));
    }
}
