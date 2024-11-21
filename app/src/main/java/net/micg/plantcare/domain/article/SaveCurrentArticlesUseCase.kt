package net.micg.plantcare.domain.article

import net.micg.plantcare.data.article.ArticlesRepository
import net.micg.plantcare.data.models.article.Article

class SaveCurrentArticlesUseCase(private val repository: ArticlesRepository) {
    suspend operator fun invoke(articles: List<Article>) = with(repository) {
        clear()
        insertAll(articles)
    }
}
