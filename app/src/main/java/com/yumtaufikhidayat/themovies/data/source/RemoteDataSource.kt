package com.yumtaufikhidayat.themovies.data.source

import androidx.lifecycle.LiveData
import androidx.lifecycle.liveData
import androidx.paging.Pager
import androidx.paging.PagingConfig
import com.yumtaufikhidayat.themovies.data.NetworkResult
import com.yumtaufikhidayat.themovies.data.paging.MoviePagingSource
import com.yumtaufikhidayat.themovies.data.remote.ApiService
import com.yumtaufikhidayat.themovies.model.cast.MovieCastResponse
import com.yumtaufikhidayat.themovies.model.detail.MovieDetailResponse
import com.yumtaufikhidayat.themovies.model.main.MovieMainResponse
import com.yumtaufikhidayat.themovies.model.movietrailer.MovieVideoResponse
import com.yumtaufikhidayat.themovies.model.reviews.MovieReviewResponse
import com.yumtaufikhidayat.themovies.utils.Constant
import javax.inject.Inject

class RemoteDataSource @Inject constructor(
    private val apiService: ApiService
) {

    fun getMovieNowPlaying() = Pager(
        PagingConfig(
            pageSize = Constant.LOAD_PER_PAGE,
            enablePlaceholders = false
        ), pagingSourceFactory = {
            MoviePagingSource(apiService)
        }).flow

    fun getDetailMovie(movieId: Int): LiveData<NetworkResult<MovieDetailResponse>> = liveData {
        emit(NetworkResult.Loading)
        try {
            val response = apiService.getDetailMovie(movieId)
            emit(NetworkResult.Success(response))
        } catch (e: Exception) {
            emit(NetworkResult.Error(e.message.toString()))
        }
    }

    fun getMovieVideo(movieId: Int): LiveData<NetworkResult<MovieVideoResponse>> = liveData {
        emit(NetworkResult.Loading)
        try {
            val response = apiService.getMovieVideo(movieId)
            emit(NetworkResult.Success(response))
        } catch (e: Exception) {
            emit(NetworkResult.Error(e.message.toString()))
        }
    }

    fun getMovieCast(movieId: Int): LiveData<NetworkResult<MovieCastResponse>> = liveData {
        emit(NetworkResult.Loading)
        try {
            val response = apiService.getMovieCast(movieId)
            emit(NetworkResult.Success(response))
        } catch (e: Exception) {
            emit(NetworkResult.Error(e.message.toString()))
        }
    }

    fun getMovieReviews(movieId: Int): LiveData<NetworkResult<MovieReviewResponse>> = liveData {
        emit(NetworkResult.Loading)
        try {
            val response = apiService.getMovieReviews(movieId)
            emit(NetworkResult.Success(response))
        } catch (e: Exception) {
            emit(NetworkResult.Error(e.message.toString()))
        }
    }

    fun discoverMovie(query: String): LiveData<NetworkResult<MovieMainResponse>> = liveData {
        emit(NetworkResult.Loading)
        try {
            val response = apiService.getDiscoverMovie(query)
            emit(NetworkResult.Success(response))
        } catch (e: Exception) {
            emit(NetworkResult.Error(e.message.toString()))
        }
    }
}