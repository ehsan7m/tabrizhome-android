package com.tabrizhome.app.ui.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.tabrizhome.app.data.Property
import com.tabrizhome.app.data.PropertyRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

data class PropertyDetailUiState(
    val isLoading: Boolean = true,
    val property: Property? = null,
    val error: String? = null
)

@HiltViewModel
class PropertyDetailViewModel @Inject constructor(
    private val repository: PropertyRepository
) : ViewModel() {
    private val _state = MutableStateFlow(PropertyDetailUiState())
    val state: StateFlow<PropertyDetailUiState> = _state.asStateFlow()

    fun load(id: Long) {
        viewModelScope.launch {
            _state.value = PropertyDetailUiState(isLoading = true)
            val result = repository.fetchProperty(id)
            result.onSuccess { property ->
                _state.value = PropertyDetailUiState(isLoading = false, property = property)
            }.onFailure {
                _state.value = PropertyDetailUiState(
                    isLoading = false,
                    error = it.localizedMessage ?: "خطا در دریافت آگهی"
                )
            }
        }
    }
}
