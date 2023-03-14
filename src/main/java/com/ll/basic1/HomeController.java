package com.ll.basic1;

import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

@Controller
public class HomeController {
    private int increase = -1;
    List<Person> personList = new ArrayList<>();
    @GetMapping("/home/main")
    @ResponseBody
    public String showMain() {
        return "안녕하세요.";
    }

    @GetMapping("/home/main2")
    @ResponseBody
    public String showMain2() {
        return "반갑습니다.";
    }

    @GetMapping("/home/main3")
    @ResponseBody
    public String showMain3() {
        return "즐거웠습니다.";
    }

    @GetMapping("/home/increase")
    @ResponseBody
    public String showIncrease() {
        increase++;
        return increase+"";
    }

    @GetMapping("/home/plus")
    @ResponseBody
    public int showPlus(@RequestParam(defaultValue = "0") int a, @RequestParam(defaultValue = "0") int b) {
        return a + b;
    }

    @GetMapping("/home/addPerson")
    @ResponseBody
    public String showAddPerson(@RequestParam(defaultValue = "unknown") String name, @RequestParam(defaultValue = "0") int age) {
        Person target = new Person(age, name);
        personList.add(target);
        return target.getId()+"번 사람이 추가되었습니다.";
    }

    @GetMapping("/home/people")
    @ResponseBody
    public List<Person> showPeople() {
        return personList;
    }

    @GetMapping("/home/removePerson")
    @ResponseBody
    public String removePerson(@RequestParam int id) {
        Person target = personList.stream().filter(e -> e.getId() == id).findFirst().orElse(null);
        if(target == null) return id + "번 사람이 존재하지 않습니다.";
        personList.remove(target);
        return id + "번 사람이 삭제되었습니다.";
    }

    @GetMapping("/home/modifyPerson")
    @ResponseBody
    public String modifyPerson(@RequestParam int id, @RequestParam String name, @RequestParam int age) {
        Person target = personList.stream().filter(e -> e.getId() == id).findFirst().orElse(null);
        if(target == null) return id + "번 사람이 존재하지 않습니다.";
        target.setName(name);
        target.setAge(age);
        return id + "번 사람이 수정되었습니다.";
    }

    @GetMapping("/home/reqAndRes")
    @ResponseBody
    public void showreqAndRew(HttpServletRequest req, HttpServletResponse res) throws IOException {
        int age = Integer.parseInt(req.getParameter("age"));
        res.getWriter().append("Hello, you are %d years old.".formatted(age));
    }

    @GetMapping("/home/cookie/increase")
    @ResponseBody
    public int showCookieIncrease(HttpServletRequest req, HttpServletResponse res) throws IOException {
        int countIntCookie = 0;
        if(req.getCookies() != null) {
            countIntCookie = Arrays.stream(req.getCookies())
                    .filter(cookie -> cookie.getName().equals("count"))
                    .mapToInt(cookie -> Integer.parseInt(cookie.getValue()))
                    .findFirst()
                    .orElse(0);
        }
        res.addCookie(new Cookie("count", (countIntCookie+1)+""));
        return countIntCookie+1;
    }
}

@AllArgsConstructor
@Getter
@Setter
class Person {
    private static int lastId = 0;
    private final int id;
    private int age;
    private String name;
    Person(int age, String name) {
        this(++lastId, age, name);
    }
}