package com.yumtaufikhidayat.themovies.ui.home.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.paging.cachedIn
import com.yumtaufikhidayat.themovies.data.TheMovieRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class HomeViewModel @Inject constructor(
    private val repository: TheMovieRepository
): ViewModel() {
    fun getMovieNowPlaying() = repository.getMovieNowPlaying().cachedIn(viewModelScope)
}