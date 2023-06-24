package com.yumtaufikhidayat.themovies.ui.details.viewmodel

import androidx.lifecycle.ViewModel
import com.yumtaufikhidayat.themovies.data.TheMovieRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class DetailsViewModel @Inject constructor(
    private val repository: TheMovieRepository
) : ViewModel() {

    fun getDetailMovies(movieId: Int) = repository.getDetailMovie(movieId)

    fun getMovieVideo(movieId: Int) = repository.getMovieVideo(movieId)

    fun getMovieCast(movieId: Int) = repository.getMovieCast(movieId)

    fun getMovieReviews(movieId: Int) = repository.getMovieReviews(movieId)
}