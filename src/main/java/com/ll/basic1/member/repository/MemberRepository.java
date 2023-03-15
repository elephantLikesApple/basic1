package com.ll.basic1.member.repository;

import com.ll.basic1.base.resData.ResData;
import com.ll.basic1.member.entity.Member;
import org.springframework.stereotype.Repository;

import java.util.ArrayList;
import java.util.List;

@Repository
public class MemberRepository {
    List<Member> memberList;

    public MemberRepository() {
        memberList = new ArrayList<>();
        init();
    }

    private void init() {
        memberList.add(new Member(1, "user1", "1234"));
        memberList.add(new Member(2, "abc","12345"));
        memberList.add(new Member(3, "test","123456"));
        memberList.add(new Member(4, "love","12347"));
        memberList.add(new Member(5, "like","12348"));
        memberList.add(new Member(6, "giving","12349"));
        memberList.add(new Member(7, "thanks","123410"));
        memberList.add(new Member(8, "hello","123411"));
        memberList.add(new Member(9, "good","123412"));
        memberList.add(new Member(10, "peace","123413"));
    }

    public Member findByUsername(String username) {
        return memberList.stream().filter(member -> member.getUsername().equals(username)).findFirst().orElse(null);
    }
}
