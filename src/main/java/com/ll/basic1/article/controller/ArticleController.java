package com.ll.basic1.article.controller;

import com.ll.basic1.article.entity.Article;
import com.ll.basic1.article.repository.ArticleRepository;
import com.ll.basic1.article.service.ArticleService;
import com.ll.basic1.base.resData.ResData;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

@RequiredArgsConstructor // final붙은거만 생성자를 만들어준다.
@Controller
@RequestMapping("/article") // 앞으로 이 컨트롤러의 경로 앞엔 /article이 모두 붙는다.
public class ArticleController {
    private final ArticleService articleService;
    @GetMapping("/write")
    @ResponseBody
    public ResData write(String title, String body) {
        if(title == null || title.trim().length() <= 0) {
            return ResData.of("F-1", "제목을 입력해주세요");
        }
        if(body == null || body.trim().length() <= 0) {
            return ResData.of("F-2", "내용을 입력해주세요");
        }

        Article createdArticle = articleService.write(title, body);
        return ResData.of("S-1", "1번글이 생성되었습니다.", createdArticle);
    }
}
