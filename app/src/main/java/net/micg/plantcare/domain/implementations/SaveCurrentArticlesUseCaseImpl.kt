package net.micg.plantcare.domain.implementations

import net.micg.plantcare.data.article.ArticlesRepository
import net.micg.plantcare.data.models.article.Article
import net.micg.plantcare.domain.usecase.SaveCurrentArticlesUseCase
import javax.inject.Inject

class SaveCurrentArticlesUseCaseImpl @Inject constructor(
    private val repository: ArticlesRepository
) : SaveCurrentArticlesUseCase {
    override suspend operator fun invoke(articles: List<Article>) = with(repository) {
        clear()
        insertAll(articles)
    }
}
