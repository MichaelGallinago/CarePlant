package net.micg.plantcare.presentation.articles

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import net.micg.plantcare.data.models.HttpResponseState
import net.micg.plantcare.data.models.article.Article
import net.micg.plantcare.domain.implementations.GetErrorMessageUseCaseImpl
import net.micg.plantcare.domain.implementations.GetAllArticlesUseCaseImpl
import javax.inject.Inject

class ArticlesViewModel @Inject constructor(
    private val getAllArticlesUseCase: GetAllArticlesUseCaseImpl,
    private val getErrorMessageUseCase: GetErrorMessageUseCaseImpl,
) : ViewModel() {
    private val _articles = MutableLiveData<List<Article>>()
    val articles: LiveData<List<Article>> get() = _articles

    private val _errorMessage = MutableLiveData<String>()
    val errorMessage: LiveData<String> get() = _errorMessage

    fun loadArticles() = viewModelScope.launch(Dispatchers.IO) {
        with(getAllArticlesUseCase()) {
            when (this) {
                is HttpResponseState.Success -> _articles.postValue(this.value)
                is HttpResponseState.Failure -> _errorMessage.postValue(
                    getErrorMessageUseCase(this.type)
                )
            }
        }
    }
}
