package net.micg.plantcare.domain.usecase

import net.micg.plantcare.data.models.article.Article

interface GetAllArticlesUseCase {
    suspend operator fun invoke(): List<Article>
}
