package Model

import kotlinx.serialization.Serializable

@Serializable
data class NewsArticleModel(
    val articles: List<Article>,
    val status: String,
    val totalResults: Int
)