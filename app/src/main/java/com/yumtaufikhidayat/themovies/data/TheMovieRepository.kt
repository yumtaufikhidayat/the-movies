package com.yumtaufikhidayat.themovies.data

import com.yumtaufikhidayat.themovies.data.source.RemoteDataSource
import javax.inject.Inject

class TheMovieRepository @Inject constructor(
    private val remoteDataSource: RemoteDataSource
) {
    fun getMovieNowPlaying() = remoteDataSource.getMovieNowPlaying()

    fun getDetailMovie(movieId: Int) = remoteDataSource.getDetailMovie(movieId)

    fun getMovieVideo(movieId: Int) = remoteDataSource.getMovieVideo(movieId)

    fun getMovieCast(movieId: Int) = remoteDataSource.getMovieCast(movieId)

    fun getMovieReviews(movieId: Int) = remoteDataSource.getMovieReviews(movieId)

    fun discoverMovie(query: String) = remoteDataSource.discoverMovie(query)
}