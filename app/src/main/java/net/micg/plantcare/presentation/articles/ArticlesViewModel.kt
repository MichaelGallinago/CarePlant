package net.micg.plantcare.presentation.articles

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import net.micg.plantcare.data.models.HttpResponseState
import net.micg.plantcare.data.article.models.Article
import net.micg.plantcare.domain.useCase.GetAllArticlesUseCase
import net.micg.plantcare.domain.useCase.GetErrorMessageUseCase
import javax.inject.Inject

class ArticlesViewModel @Inject constructor(
    private val getAllArticlesUseCase: GetAllArticlesUseCase,
    private val getErrorMessageUseCase: GetErrorMessageUseCase,
) : ViewModel() {
    private var allArticles: List<Article> = emptyList()
    private val _articles = MutableLiveData<List<Article>>()
    val articles: LiveData<List<Article>> get() = _articles

    private val _errorMessage = MutableLiveData<String?>()
    val errorMessage: LiveData<String?> get() = _errorMessage

    var filter: String = ""
        set(query) {
            field = query
            filterArticles()
        }

    fun loadArticles() = viewModelScope.launch(Dispatchers.IO) {
        with(getAllArticlesUseCase()) {
            when (this) {
                is HttpResponseState.Success -> {
                    _errorMessage.postValue(null)
                    allArticles = this.value
                    filterArticles()
                }

                is HttpResponseState.Failure -> {
                    _errorMessage.postValue(getErrorMessageUseCase(this.type))
                }
            }
        }
    }

    fun onDestroyArticlesFragment() = _errorMessage.postValue(null)

    private fun filterArticles() = _articles.postValue(
        if (filter.isEmpty()) {
            allArticles
        } else {
            allArticles.filter { it.title.contains(filter, ignoreCase = true) }
        }
    )
}
