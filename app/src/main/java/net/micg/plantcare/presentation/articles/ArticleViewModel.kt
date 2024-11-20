package net.micg.plantcare.presentation.articles

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import net.micg.plantcare.data.ArticlesApi
import net.micg.plantcare.data.models.Article
import retrofit2.Call
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import javax.inject.Inject

class ArticleViewModel @Inject constructor() : ViewModel() {
    private val retrofit = Retrofit.Builder()
        .baseUrl(URL)
        .addConverterFactory(GsonConverterFactory.create())
        .build()

    private val api = retrofit.create(ArticlesApi::class.java)

    private val _articles = MutableLiveData<List<Article>>()
    val articles: LiveData<List<Article>> get() = _articles

    private val _errorMessage = MutableLiveData<String>()
    val errorMessage: LiveData<String> get() = _errorMessage

    fun loadArticles() {
        api.getArticles().enqueue(object : retrofit2.Callback<List<Article>> {
            override fun onResponse(
                call: Call<List<Article>>,
                response: retrofit2.Response<List<Article>>
            ) {
                if (response.isSuccessful) {
                    _articles.value = response.body()
                }
            }

            override fun onFailure(call: Call<List<Article>>, t: Throwable) {
                _errorMessage.value = "Ошибка загрузки: ${t.message}"
            }
        })
    }

    companion object {
        const val URL = "https://michaelgallinago.github.io/plant-app-web-storage/"
    }
}
