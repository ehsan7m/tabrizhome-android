package com.tabrizhome.app.ui.screens

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.CompositionLocalProvider
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalLayoutDirection
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.unit.LayoutDirection
import androidx.compose.ui.unit.dp
import androidx.core.text.HtmlCompat
import coil.compose.AsyncImage
import com.tabrizhome.app.ui.viewmodel.PropertyDetailUiState
import com.tabrizhome.app.ui.viewmodel.PropertyDetailViewModel

@Composable
fun PropertyDetailScreen(
    id: Long,
    viewModel: PropertyDetailViewModel,
    layoutDirection: LayoutDirection,
    onBack: () -> Unit
) {
    val state = viewModel.state.collectAsState()
    LaunchedEffect(id) { viewModel.load(id) }

    CompositionLocalProvider(LocalLayoutDirection provides layoutDirection) {
        Scaffold(
            topBar = {
                IconButton(onClick = onBack, modifier = Modifier.padding(8.dp)) {
                    Icon(imageVector = Icons.Default.ArrowBack, contentDescription = null)
                }
            }
        ) { padding ->
            PropertyDetailContent(
                uiState = state.value,
                padding = padding
            )
        }
    }
}

@Composable
private fun PropertyDetailContent(
    uiState: PropertyDetailUiState,
    padding: PaddingValues
) {
    when {
        uiState.isLoading -> {
            Text(
                text = "در حال بارگذاری...",
                modifier = Modifier.padding(padding).padding(16.dp)
            )
        }
        uiState.error != null -> {
            Text(
                text = uiState.error,
                modifier = Modifier.padding(padding).padding(16.dp)
            )
        }
        uiState.property != null -> {
            val property = uiState.property
            Column(
                modifier = Modifier
                    .padding(padding)
                    .fillMaxSize()
                    .verticalScroll(rememberScrollState())
                    .padding(16.dp),
                verticalArrangement = Arrangement.spacedBy(12.dp)
            ) {
                AsyncImage(
                    model = property.imageUrl,
                    contentDescription = null,
                    modifier = Modifier
                        .fillMaxWidth(),
                    contentScale = ContentScale.Crop
                )
                Text(text = property.title.fromHtml(), style = MaterialTheme.typography.titleLarge)
                Text(
                    text = property.price?.ifBlank { "تماس بگیرید" } ?: "تماس بگیرید",
                    style = MaterialTheme.typography.titleMedium,
                    color = MaterialTheme.colorScheme.primary
                )
                Text(
                    text = property.content.fromHtml(),
                    style = MaterialTheme.typography.bodyMedium
                )
            }
        }
    }
}

private fun String.fromHtml(): String =
    HtmlCompat.fromHtml(this, HtmlCompat.FROM_HTML_MODE_COMPACT).toString()
