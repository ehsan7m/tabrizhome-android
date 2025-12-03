package com.tabrizhome.app.ui.screens

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Search
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.CompositionLocalProvider
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalLayoutDirection
import androidx.compose.ui.text.input.TextFieldValue
import androidx.compose.ui.unit.LayoutDirection
import androidx.compose.ui.unit.dp
import androidx.core.text.HtmlCompat
import com.tabrizhome.app.ui.components.PropertyCard
import com.tabrizhome.app.ui.viewmodel.PropertyListUiState
import com.tabrizhome.app.ui.viewmodel.PropertyListViewModel

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun PropertyListScreen(
    viewModel: PropertyListViewModel,
    padding: PaddingValues,
    layoutDirection: LayoutDirection,
    onPropertyClick: (Long) -> Unit
) {
    val state = viewModel.state.collectAsState()
    val query = remember { mutableStateOf(TextFieldValue(state.value.query)) }

    LaunchedEffect(state.value.query) {
        if (state.value.query != query.value.text) {
            query.value = query.value.copy(text = state.value.query)
        }
    }

    CompositionLocalProvider(LocalLayoutDirection provides layoutDirection) {
        Scaffold(
            modifier = Modifier
                .fillMaxSize()
                .padding(padding),
            topBar = {
                OutlinedTextField(
                    value = query.value,
                    onValueChange = { query.value = it },
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(16.dp),
                    placeholder = { Text(text = "جست‌وجوی آگهی") },
                    singleLine = true,
                    trailingIcon = {
                        IconButton(onClick = { viewModel.load(query = query.value.text) }) {
                            Icon(imageVector = Icons.Default.Search, contentDescription = null)
                        }
                    }
                )
            }
        ) { innerPadding ->
            val combinedPadding = PaddingValues(
                start = innerPadding.calculateStartPadding(layoutDirection),
                top = innerPadding.calculateTopPadding(),
                end = innerPadding.calculateEndPadding(layoutDirection),
                bottom = innerPadding.calculateBottomPadding()
            )
            PropertyListContent(
                uiState = state.value,
                padding = combinedPadding,
                onRetry = { viewModel.load(query = query.value.text) },
                onPropertyClick = onPropertyClick
            )
        }
    }
}

@Composable
private fun PropertyListContent(
    uiState: PropertyListUiState,
    padding: PaddingValues,
    onRetry: () -> Unit,
    onPropertyClick: (Long) -> Unit
) {
    when {
        uiState.isLoading -> {
            Column(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(padding),
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                CircularProgressIndicator(modifier = Modifier.padding(top = 32.dp))
            }
        }

        uiState.error != null -> {
            ErrorState(message = uiState.error, onRetry = onRetry, padding = padding)
        }

        else -> {
            LazyColumn(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(padding)
            ) {
                items(uiState.items, key = { it.id }) { property ->
                    PropertyCard(
                        property = property.copy(
                            title = property.title.fromHtml(),
                            excerpt = property.excerpt.fromHtml()
                        ),
                        onClick = { onPropertyClick(property.id) }
                    )
                }
            }
        }
    }
}

@Composable
private fun ErrorState(message: String, onRetry: () -> Unit, padding: PaddingValues) {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(padding),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Text(text = message, modifier = Modifier.padding(16.dp))
        IconButton(onClick = onRetry) {
            Icon(imageVector = Icons.Default.Search, contentDescription = null)
        }
    }
}

private fun String.fromHtml(): String =
    HtmlCompat.fromHtml(this, HtmlCompat.FROM_HTML_MODE_COMPACT).toString()
