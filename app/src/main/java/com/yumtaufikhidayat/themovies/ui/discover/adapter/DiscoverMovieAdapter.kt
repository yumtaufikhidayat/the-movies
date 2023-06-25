package com.yumtaufikhidayat.themovies.ui.discover.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.yumtaufikhidayat.themovies.R
import com.yumtaufikhidayat.themovies.databinding.ItemMoviesBinding
import com.yumtaufikhidayat.themovies.model.main.MovieMainResult
import com.yumtaufikhidayat.themovies.utils.loadImage
import com.yumtaufikhidayat.themovies.utils.toRating

class DiscoverMovieAdapter(
    private val onItemClickListener: (MovieMainResult) -> Unit
) : ListAdapter<MovieMainResult, DiscoverMovieAdapter.ViewHolder>(discoverMovieDiffCallback) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        return ViewHolder(
            ItemMoviesBinding.inflate(
                LayoutInflater.from(parent.context),
                parent,
                false
            )
        )
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.bind(getItem(position))
    }

    inner class ViewHolder(private val binding: ItemMoviesBinding) :
        RecyclerView.ViewHolder(binding.root) {
        fun bind(data: MovieMainResult) {
            binding.apply {
                imgPoster.loadImage(data.posterPath)
                tvTitle.text = data.title
                tvRating.text = root.context.getString(
                    R.string.tvRatingAverageCount,
                    toRating(data.voteAverage),
                    "${data.voteCount}"
                )
                itemView.setOnClickListener {
                    onItemClickListener(data)
                }
            }
        }
    }

    companion object {
        val discoverMovieDiffCallback = object : DiffUtil.ItemCallback<MovieMainResult>() {
            override fun areItemsTheSame(
                oldItem: MovieMainResult,
                newItem: MovieMainResult
            ): Boolean = oldItem.id == newItem.id

            override fun areContentsTheSame(
                oldItem: MovieMainResult,
                newItem: MovieMainResult
            ): Boolean = oldItem == newItem
        }
    }
}