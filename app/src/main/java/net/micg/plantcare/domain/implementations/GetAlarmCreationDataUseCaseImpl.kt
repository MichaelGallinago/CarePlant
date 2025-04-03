package net.micg.plantcare.domain.implementations

import net.micg.plantcare.data.article.repository.ArticlesRepository
import net.micg.plantcare.domain.useCase.GetAlarmCreationDataUseCase
import javax.inject.Inject

class GetAlarmCreationDataUseCaseImpl @Inject constructor(
    private val repository: ArticlesRepository,
): GetAlarmCreationDataUseCase {
    override suspend operator fun invoke(locale: String, fileName: String) =
        repository.getAlarmCreationData(locale, fileName)
}
