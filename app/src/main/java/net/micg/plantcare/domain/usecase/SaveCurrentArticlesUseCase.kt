package net.micg.plantcare.domain.usecase

import net.micg.plantcare.data.models.article.Article

interface SaveCurrentArticlesUseCase {
    suspend operator fun invoke(articles: List<Article>)
}
