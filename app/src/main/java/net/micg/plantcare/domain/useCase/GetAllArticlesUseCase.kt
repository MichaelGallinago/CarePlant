package net.micg.plantcare.domain.useCase

import net.micg.plantcare.data.models.HttpResponseState
import net.micg.plantcare.data.article.models.Article

interface GetAllArticlesUseCase {
    suspend operator fun invoke(): HttpResponseState<List<Article>>
}
