package com.tabrizhome.app.ui.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.tabrizhome.app.data.Property
import com.tabrizhome.app.data.PropertyRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Job
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

data class PropertyListUiState(
    val isLoading: Boolean = false,
    val items: List<Property> = emptyList(),
    val error: String? = null,
    val query: String = ""
)

@HiltViewModel
class PropertyListViewModel @Inject constructor(
    private val repository: PropertyRepository
) : ViewModel() {

    private val _state = MutableStateFlow(PropertyListUiState(isLoading = true))
    val state: StateFlow<PropertyListUiState> = _state.asStateFlow()

    private var currentPage = 1
    private var lastJob: Job? = null

    init {
        load()
    }

    fun load(page: Int = 1, query: String? = null) {
        currentPage = page
        lastJob?.cancel()
        lastJob = viewModelScope.launch {
            _state.value = _state.value.copy(isLoading = true, error = null)
            val result = repository.fetchProperties(page = page, perPage = 20, search = query)
            result.onSuccess { list ->
                _state.value = _state.value.copy(
                    isLoading = false,
                    items = list,
                    error = null,
                    query = query.orEmpty()
                )
            }.onFailure {
                _state.value = _state.value.copy(
                    isLoading = false,
                    error = it.localizedMessage ?: "خطا در دریافت داده‌ها"
                )
            }
        }
    }
}
