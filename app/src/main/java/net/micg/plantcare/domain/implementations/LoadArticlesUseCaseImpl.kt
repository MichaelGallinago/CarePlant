package net.micg.plantcare.domain.implementations

import net.micg.plantcare.data.article.ArticlesApi
import net.micg.plantcare.data.models.article.Article
import net.micg.plantcare.domain.usecase.LoadArticlesUseCase
import retrofit2.Callback
import javax.inject.Inject

class LoadArticlesUseCaseImpl @Inject constructor(
    private val api: ArticlesApi
) : LoadArticlesUseCase {
    override operator fun invoke(callback: Callback<List<Article>>) =
        api.getArticles().enqueue(callback)
}
