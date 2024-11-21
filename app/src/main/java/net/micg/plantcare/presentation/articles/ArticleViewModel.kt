package net.micg.plantcare.presentation.articles

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.launch
import net.micg.plantcare.data.alarm.AlarmsRepository
import net.micg.plantcare.data.article.ArticlesApi
import net.micg.plantcare.data.article.ArticlesRepository
import net.micg.plantcare.data.models.article.Article
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import javax.inject.Inject

class ArticleViewModel @Inject constructor(
    private val repository: ArticlesRepository
) : ViewModel() {
    private val retrofit =
        Retrofit.Builder().baseUrl(URL).addConverterFactory(GsonConverterFactory.create()).build()

    private val api = retrofit.create(ArticlesApi::class.java)

    private val _articles = MutableLiveData<List<Article>>()
    val articles: LiveData<List<Article>> get() = _articles

    private val _errorMessage = MutableLiveData<String>()
    val errorMessage: LiveData<String> get() = _errorMessage


    fun loadArticles() {
        api.getArticles().enqueue(object : Callback<List<Article>> {
            override fun onResponse(
                call: Call<List<Article>>, response: Response<List<Article>>
            ) {
                viewModelScope.launch {
                    if (!response.isSuccessful) {
                        handleFailure()
                        return@launch
                    }

                    (response.body() ?: emptyList()).sortedBy { it.title }.also {
                        _articles.value = it
                        repository.clear()
                        repository.insertAll(it)
                    }
                }
            }

            override fun onFailure(call: Call<List<Article>>, t: Throwable) {
                handleFailure()
            }
        })
    }

    private fun handleFailure() = viewModelScope.launch {
        val cachedArticles = repository.getAll()
        if (cachedArticles.isNotEmpty()) {
            _articles.value = cachedArticles
        } else {
            _errorMessage.value = "Ошибка загрузки"
        }
    }

    @Deprecated("Debug only")
    private fun handleFailure(message: String?) = viewModelScope.launch {
        val cachedArticles = repository.getAll()
        if (cachedArticles.isNotEmpty()) {
            _articles.value = cachedArticles
            _errorMessage.value = "Ошибка загрузки: $message"
        } else {
            _errorMessage.value =
                "Ошибка загрузки и отсутствуют данные в кэше: $message"
        }
    }

    companion object {
        const val URL = "https://michaelgallinago.github.io/plant-app-web-storage/"
    }
}
