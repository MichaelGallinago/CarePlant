package net.micg.plantcare.domain.implementations

import net.micg.plantcare.data.article.repository.ArticlesRepository
import net.micg.plantcare.domain.useCase.GetAllArticlesUseCase
import javax.inject.Inject

class GetAllArticlesUseCaseImpl @Inject constructor(
    private val repository: ArticlesRepository,
) : GetAllArticlesUseCase {
    override suspend operator fun invoke() = repository.getAll()
}
