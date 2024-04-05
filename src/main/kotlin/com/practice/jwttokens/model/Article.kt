package com.practice.jwttokens.model

import com.practice.jwttokens.controller.article.ArticleResponse
import java.util.*

data class Article(
    val id: UUID,
    val title: String,
    val content: String
)

