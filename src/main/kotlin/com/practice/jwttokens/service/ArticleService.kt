package com.practice.jwttokens.service

import com.practice.jwttokens.model.Article
import com.practice.jwttokens.repository.ArticleRepository
import org.springframework.stereotype.Service

@Service
class ArticleService(
    private val articleRepository: ArticleRepository
) {
    fun findAll(): List<Article> = articleRepository.findAll()
}