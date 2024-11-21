package net.micg.plantcare.domain.article

import net.micg.plantcare.data.article.ArticlesRepository
import net.micg.plantcare.data.models.article.Article

class GetAllArticlesUseCase(private val repository: ArticlesRepository) {
    suspend operator fun invoke(): List<Article> = repository.getAll()
}
