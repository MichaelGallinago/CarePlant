package net.micg.plantcare.domain.usecase

import net.micg.plantcare.data.models.article.Article
import retrofit2.Callback

interface LoadArticlesUseCase {
    operator fun invoke(callback: Callback<List<Article>>)
}
