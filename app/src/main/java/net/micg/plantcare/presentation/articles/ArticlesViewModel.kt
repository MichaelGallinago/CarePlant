package net.micg.plantcare.presentation.articles

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import net.micg.plantcare.data.models.HttpResponseState
import net.micg.plantcare.data.models.article.Article
import net.micg.plantcare.domain.usecase.GetAllArticlesUseCase
import net.micg.plantcare.domain.usecase.GetErrorMessageUseCase
import javax.inject.Inject

class ArticlesViewModel @Inject constructor(
    private val getAllArticlesUseCase: GetAllArticlesUseCase,
    private val getErrorMessageUseCase: GetErrorMessageUseCase,
) : ViewModel() {
    private var allArticles: List<Article> = emptyList()
    private val _articles = MutableLiveData<List<Article>>()
    val articles: LiveData<List<Article>> get() = _articles

    private val _errorMessage = MutableLiveData<String>()
    val errorMessage: LiveData<String> get() = _errorMessage

    fun filterArticles(query: String) = _articles.postValue(
        if (query.isEmpty()) {
            allArticles
        } else {
            allArticles.filter { it.title.contains(query, ignoreCase = true) }
        }
    )

    fun loadArticles() = viewModelScope.launch(Dispatchers.IO) {
        with(getAllArticlesUseCase()) {
            when (this) {
                is HttpResponseState.Success -> {
                    allArticles = this.value
                    _articles.postValue(allArticles)
                }
                is HttpResponseState.Failure -> _errorMessage.postValue(
                    getErrorMessageUseCase(this.type)
                )
            }
        }
    }
}
