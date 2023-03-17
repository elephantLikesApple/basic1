package com.ll.basic1.article.repository;

import com.ll.basic1.article.entity.Article;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;


public interface ArticleRepository extends JpaRepository<Article, Long> {
}
