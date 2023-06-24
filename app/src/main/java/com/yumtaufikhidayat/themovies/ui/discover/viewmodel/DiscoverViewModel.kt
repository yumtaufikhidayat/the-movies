package com.yumtaufikhidayat.themovies.ui.discover.viewmodel

import androidx.lifecycle.ViewModel
import com.yumtaufikhidayat.themovies.data.TheMovieRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class DiscoverViewModel @Inject constructor(
    private val repository: TheMovieRepository
): ViewModel() {
    fun discoverMovie(query: String) = repository.discoverMovie(query)
}