package com.practice.jwttokens.controller.article

import com.practice.jwttokens.model.Article
import java.util.UUID

data class ArticleResponse(
    val id: UUID,
    val title: String,
    val content: String
)

fun Article.toResponse() = ArticleResponse(
    id = id,
    title = title,
    content = content
)