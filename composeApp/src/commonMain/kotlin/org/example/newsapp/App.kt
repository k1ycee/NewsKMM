package org.example.newsapp

import Model.NewsArticleModel
import androidx.compose.animation.AnimatedVisibility
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.material.Button
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import dev.icerock.moko.mvvm.compose.getViewModel
import dev.icerock.moko.mvvm.compose.viewModelFactory
import io.kamel.image.KamelImage
import io.kamel.image.asyncPainterResource
import io.ktor.client.HttpClient
import io.ktor.client.call.body
import io.ktor.client.plugins.contentnegotiation.ContentNegotiation
import io.ktor.client.request.get
import io.ktor.serialization.kotlinx.json.json
import org.jetbrains.compose.ui.tooling.preview.Preview
import viewmodel.NewsViewModel

@Composable
@Preview
fun App() {
    MaterialTheme {
        val newsViewModel = getViewModel(Unit, viewModelFactory { NewsViewModel() })
        NewsPage(newsViewModel)
    }
}





@Composable
fun NewsPage(viewModel: NewsViewModel){
    val uiState by  viewModel.uiState.collectAsState()
AnimatedVisibility(visible = uiState.news.isNotEmpty()){
    LazyVerticalGrid(
        columns = GridCells.Fixed(2),
        horizontalArrangement = Arrangement.spacedBy(5.dp),
        verticalArrangement = Arrangement.spacedBy(5.dp),
        modifier = Modifier.fillMaxSize().padding(horizontal = 5.dp),
        content = {

            items(uiState.news){
                Column {
                    it.urlToImage?.let { it1 -> asyncPainterResource(it1) }
                        ?.let { it2 -> KamelImage(it2, it.description, contentScale = ContentScale.Crop, modifier = Modifier.fillMaxWidth().aspectRatio(1.0f)) }
                    it.title?.let { it1 -> Text(text = it1, fontWeight = FontWeight(600) ) }
                    it.author?.let { it1 -> Text(text = it1) }
                }
            }

        }
    )
}


}

