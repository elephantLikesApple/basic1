package com.ll.basic1.article.service;

import com.ll.basic1.article.entity.Article;
import com.ll.basic1.article.repository.ArticleRepository;
import com.ll.basic1.base.resData.ResData;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@RequiredArgsConstructor
@Service
public class ArticleService {
    private final ArticleRepository articleRepository;

    public Article write(String title, String body) {
        Article article = Article.builder()
                .title(title)
                .body(body) // body 입력하기 싫으면 안해도 된다.
                .build();
        //위와 동일 Article article = new Article(); article.setTitle(title); article.setBody(body);
        articleRepository.save(article); //insert
        return article;
    }
}
