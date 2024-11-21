package net.micg.plantcare.presentation.article

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.launch
import net.micg.plantcare.data.models.article.Article
import net.micg.plantcare.domain.GetErrorMessageUseCase
import net.micg.plantcare.domain.article.GetAllArticlesUseCase
import net.micg.plantcare.domain.article.LoadArticlesUseCase
import net.micg.plantcare.domain.article.SaveCurrentArticlesUseCase
import net.micg.plantcare.service.ErrorMessageService.*
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import javax.inject.Inject

class ArticleViewModel @Inject constructor(
    private val getAllArticlesUseCase: GetAllArticlesUseCase,
    private val saveCurrentArticlesUseCase: SaveCurrentArticlesUseCase,
    private val loadArticlesUseCase: LoadArticlesUseCase,
    private val getErrorMessageUseCase: GetErrorMessageUseCase,
) : ViewModel() {
    private val _articles = MutableLiveData<List<Article>>()
    val articles: LiveData<List<Article>> get() = _articles

    private val _errorMessage = MutableLiveData<String>()
    val errorMessage: LiveData<String> get() = _errorMessage

    fun loadArticles() = loadArticlesUseCase(object : Callback<List<Article>> {
        override fun onResponse(call: Call<List<Article>>, response: Response<List<Article>>) {
            viewModelScope.launch {
                if (!response.isSuccessful) {
                    handleFailure()
                    return@launch
                }

                (response.body() ?: emptyList()).sortedBy { it.title }.also {
                    _articles.value = it
                    saveCurrentArticlesUseCase(it)
                }
            }
        }

        override fun onFailure(call: Call<List<Article>>, t: Throwable) {
            handleFailure()
        }
    })

    private fun handleFailure() = viewModelScope.launch {
        with(getAllArticlesUseCase()) {
            if (isNotEmpty()) {
                _articles.value = this
            } else {
                _errorMessage.value = getErrorMessageUseCase(Type.LoadingError)
            }
        }
    }

    @Deprecated("Debug only")
    private fun handleFailure(message: String?) = viewModelScope.launch {
        with(getAllArticlesUseCase()) {
            if (isNotEmpty()) {
                _articles.value = this
                _errorMessage.value = "${getErrorMessageUseCase(Type.LoadingError)}: $message"
            } else {
                _errorMessage.value =
                    "${getErrorMessageUseCase(Type.ExtendedLoadingError)}: $message"
            }
        }
    }
}
