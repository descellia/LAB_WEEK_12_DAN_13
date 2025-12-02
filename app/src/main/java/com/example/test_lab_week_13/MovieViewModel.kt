package com.example.test_lab_week_13

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.test_lab_week_13.model.Movie
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.launch
import java.util.Calendar

class MovieViewModel(private val movieRepository: MovieRepository) : ViewModel() {

    private val _popularMovies = MutableStateFlow<List<Movie>>(emptyList())
    val popularMovies: StateFlow<List<Movie>> = _popularMovies.asStateFlow()

    private val _error = MutableStateFlow("")
    val error: StateFlow<String> = _error.asStateFlow()

    init {
        fetchPopularMovies()
    }

    private fun fetchPopularMovies() {
        viewModelScope.launch(Dispatchers.IO) {
            movieRepository.fetchMovies()
                .catch { throwable ->
                    Log.e("MovieViewModel", "Error fetching movies", throwable)
                    _error.value = "An exception occurred: ${throwable.message}"
                }
                .map { movies ->
                    // To fix the "String?" error and ensure data shows up:
                    // 1. Use safe call ?.
                    // 2. For now, let's allow ALL movies to verify data loading, 
                    //    or un-comment the filter line to restore filtering safely.
                    
                    // val currentYear = Calendar.getInstance().get(Calendar.YEAR).toString()
                    movies
                        // .filter { it.releaseDate?.startsWith(currentYear) == true } // Safe filtering
                        .sortedByDescending { it.popularity }
                }
                .flowOn(Dispatchers.Default)
                .collect { filteredMovies ->
                    Log.d("MovieViewModel", "Movies loaded: ${filteredMovies.size}")
                    _popularMovies.value = filteredMovies
                }
        }
    }
}