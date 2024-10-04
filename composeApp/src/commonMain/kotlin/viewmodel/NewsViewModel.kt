package viewmodel

import Model.Article
import Model.NewsArticleModel
import dev.icerock.moko.mvvm.viewmodel.ViewModel
import io.ktor.client.HttpClient
import io.ktor.client.call.body
import io.ktor.client.plugins.contentnegotiation.ContentNegotiation
import io.ktor.client.request.get
import io.ktor.serialization.kotlinx.json.json
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch


data class NewsUIState(

    val news: List<Article> = emptyList()
)
class NewsViewModel: ViewModel() {

    private val _uiState = MutableStateFlow<NewsUIState>(NewsUIState())
    val uiState = _uiState.asStateFlow()



    private val httpClient = HttpClient {
        install(ContentNegotiation) {
            json()
        }
    }

    override fun onCleared() {
        httpClient.close()
        super.onCleared()
    }

    private fun updateNews(){
        viewModelScope.launch {
           val news =  getNews()
            _uiState.update { it.copy(
                news = news
            ) }

        }
    }

    init {
        updateNews()
    }

    private suspend fun getNews(): List<Article> {

 val news =  httpClient.get("https://newsapi.org/v2/everything?q=tesla&from=2024-09-04&sortBy=publishedAt&apiKey=0033eec1ce834b8f93dbd041496980c9")
      .body<NewsArticleModel>()
        return  news.articles
    }

}