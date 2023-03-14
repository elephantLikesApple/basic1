package com.ll.basic1;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.ArrayList;
import java.util.List;

@Controller
public class HomeController {
    private int increase = -1;
    private int id = 0;
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
        id++;
        Person target = new Person(id, name, age);
        personList.add(target);
        return id+"번 사람이 추가되었습니다.";
    }

    @GetMapping("/home/people")
    @ResponseBody
    public List<Person> showPeople() {
        return personList;
    }
}

@AllArgsConstructor
@Getter
@Setter
class Person {
    int id;
    String name;
    int age;
}