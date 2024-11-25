package net.micg.plantcare.presentation.articles

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import net.micg.plantcare.data.models.article.Article
import net.micg.plantcare.domain.implementations.GetErrorMessageUseCaseImpl
import net.micg.plantcare.domain.implementations.GetAllArticlesUseCaseImpl
import net.micg.plantcare.domain.implementations.LoadArticlesUseCaseImpl
import net.micg.plantcare.domain.implementations.SaveCurrentArticlesUseCaseImpl
import net.micg.plantcare.domain.utils.ErrorMessageUtils.Type
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import javax.inject.Inject

class ArticlesViewModel @Inject constructor(
    private val getAllArticlesUseCase: GetAllArticlesUseCaseImpl,
    private val saveCurrentArticlesUseCase: SaveCurrentArticlesUseCaseImpl,
    private val loadArticlesUseCase: LoadArticlesUseCaseImpl,
    private val getErrorMessageUseCase: GetErrorMessageUseCaseImpl,
) : ViewModel() {
    private val _articles = MutableLiveData<List<Article>>()
    val articles: LiveData<List<Article>> get() = _articles

    private val _errorMessage = MutableLiveData<String>()
    val errorMessage: LiveData<String> get() = _errorMessage

    //TODO: не прокидывай сукаа
    fun loadArticles() = loadArticlesUseCase(object : Callback<List<Article>> {
        override fun onResponse(call: Call<List<Article>>, response: Response<List<Article>>) {
            viewModelScope.launch(Dispatchers.IO) {
                if (!response.isSuccessful) {
                    handleFailure()
                    return@launch
                }

                (response.body() ?: emptyList()).sortedBy { it.title }.also {
                    _articles.postValue(it)
                    saveCurrentArticlesUseCase(it)
                }
            }
        }

        override fun onFailure(call: Call<List<Article>>, t: Throwable) {
            handleFailure()
        }
    })

    private fun handleFailure() = viewModelScope.launch(Dispatchers.IO) {
        with(getAllArticlesUseCase()) {
            if (isNotEmpty()) {
                _articles.postValue(this)
            } else {
                _errorMessage.postValue(getErrorMessageUseCase(Type.LoadingError))
            }
        }
    }

    @Deprecated("Debug only")
    private fun handleFailure(message: String?) = viewModelScope.launch(Dispatchers.IO) {
        with(getAllArticlesUseCase()) {
            if (isNotEmpty()) {
                _articles.postValue(this)
                _errorMessage.postValue("${getErrorMessageUseCase(Type.LoadingError)}: $message")
            } else {
                _errorMessage.postValue(
                    "${getErrorMessageUseCase(Type.ExtendedLoadingError)}: $message")
            }
        }
    }
}
