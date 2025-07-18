package com.moviestreaming.ui.search

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.paging.PagingData
import androidx.paging.cachedIn
import com.moviestreaming.data.model.TopRateMovieEntity
import com.moviestreaming.domain.usecase.SearchFilter
import com.moviestreaming.domain.usecase.SearchMoviesUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.CancellationException
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.FlowPreview
import kotlinx.coroutines.Job
import kotlinx.coroutines.SupervisorJob
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.debounce
import kotlinx.coroutines.flow.emptyFlow
import kotlinx.coroutines.flow.flowOn
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import javax.inject.Inject

/**
 * View model for the search screen that handles user inputs and presents search results.
 * Uses a debounce mechanism to avoid excessive API calls while typing.
 */
@HiltViewModel
class SearchViewModel @Inject constructor(
    private val searchMoviesUseCase: SearchMoviesUseCase
) : ViewModel() {

    // UI state
    private val _uiState = MutableStateFlow(SearchUiState())
    val uiState: StateFlow<SearchUiState> = _uiState
    
    // Search mechanics with replay to handle configuration changes
    private val searchQueryFlow = MutableSharedFlow<String>(replay = 1)
    private var searchJob: Job? = null
    
    // SupervisorJob to prevent child coroutine failures from cancelling parent
    private val searchSupervisor = SupervisorJob()
    
    // Debounce delay in milliseconds
    private val debounceTime = 500L

    init {
        setupSearchDebounce()
    }
    
    /**
     * Sets up the debounced search flow
     */
    @OptIn(FlowPreview::class)
    private fun setupSearchDebounce() {
        searchQueryFlow
            .debounce(debounceTime)
            .flowOn(Dispatchers.Default) // Move debounce work off the main thread
            .onEach { query -> 
                if (query.isNotBlank()) {
                    executeSearch(query)
                } else {
                    _uiState.update { it.copy(searchResults = emptyFlow()) }
                }
            }
            .catch { error -> 
                // Only catch non-cancellation exceptions
                if (error !is CancellationException) {
                    _uiState.update { it.copy(error = error.message ?: "Unknown error", isLoading = false) }
                }
            }
            .launchIn(viewModelScope)
    }

    /**
     * Handle query changes from the UI
     * @param query The search query entered by the user
     */
    fun onQueryChange(query: String) {
        _uiState.update { it.copy(query = query) }
        submitQuery(query)
    }


    fun onFilterSelected(filter: SearchFilter) {
        _uiState.update { it.copy(selectedFilter = filter) }
        
        // If there's already a query, perform a search with the new filter
        if (_uiState.value.query.isNotBlank()) {
            submitQuery(_uiState.value.query)
        }
    }
    
    /**
     * Submit a search query to the debounce flow
     */
    private fun submitQuery(query: String) {
        viewModelScope.launch {
            try {
                searchQueryFlow.emit(query)
            } catch (e: Exception) {
                // Handle potential MutableSharedFlow emit exceptions
                if (e !is CancellationException) {
                    _uiState.update { it.copy(error = "Search error: ${e.message}") }
                }
            }
        }
    }
    
    /**
     * Execute the actual search after debounce period
     */
    private fun executeSearch(query: String) {
        // Cancel any ongoing search
        searchJob?.cancel()
        
        // Launch with supervisor to prevent crash if child fails
        searchJob = viewModelScope.launch(searchSupervisor + Dispatchers.IO) {
            try {
                // Update UI state on main thread
                withContext(Dispatchers.Main) {
                    _uiState.update { it.copy(isLoading = true, error = null) }
                }
                
                // Perform the search on IO thread
                val results = searchMoviesUseCase(
                    query = query,
                    filter = _uiState.value.selectedFilter
                ).cachedIn(viewModelScope)
                
                // Update UI state on main thread
                withContext(Dispatchers.Main) {
                    _uiState.update { 
                        it.copy(
                            searchResults = results,
                            isLoading = false
                        )
                    }
                }
            } catch (e: Exception) {
                // Only handle non-cancellation exceptions
                if (e !is CancellationException) {
                    // Update UI state on main thread
                    withContext(Dispatchers.Main) {
                        _uiState.update { 
                            it.copy(
                                error = e.message ?: "An error occurred during search",
                                isLoading = false
                            )
                        }
                    }
                }
            }
        }
    }
    
    override fun onCleared() {
        super.onCleared()
        // Cancel all ongoing operations
        searchJob?.cancel()
        searchSupervisor.cancel()
    }
}

/**
 * UI state for the search screen
 */
data class SearchUiState(
    val query: String = "",
    val selectedFilter: SearchFilter = SearchFilter.BY_NAME,
    val searchResults: Flow<PagingData<TopRateMovieEntity>> = emptyFlow(),
    val isLoading: Boolean = false,
    val error: String? = null
)