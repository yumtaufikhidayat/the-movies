package com.yumtaufikhidayat.themovies.ui.home.fragment

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.content.ContextCompat
import androidx.core.view.isVisible
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import androidx.paging.LoadState
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.StaggeredGridLayoutManager
import com.yumtaufikhidayat.themovies.R
import com.yumtaufikhidayat.themovies.databinding.FragmentHomeBinding
import com.yumtaufikhidayat.themovies.ui.home.adapter.LoadMoreAdapter
import com.yumtaufikhidayat.themovies.ui.home.adapter.MovieAdapter
import com.yumtaufikhidayat.themovies.ui.home.viewmodel.HomeViewModel
import com.yumtaufikhidayat.themovies.utils.navigateToDetail
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.launch

@AndroidEntryPoint
class HomeFragment : Fragment() {

    private var _binding: FragmentHomeBinding? = null
    private val binding get() = _binding!!

    private val homeViewModel by viewModels<HomeViewModel>()
    private val movieAdapter by lazy { MovieAdapter { navigateToDetail(it.id, it.title) }}

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentHomeBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        setToolbarHome()
        setMovieListAdapter()
        setObserver()
        navigateToSearch()
    }

    private fun setToolbarHome() {
        binding.toolbarHome.tvToolbar.text = getString(R.string.tvNowPlayingMovies)
    }

    private fun setMovieListAdapter() {
        binding.rvHome.apply {
            layoutManager = StaggeredGridLayoutManager(2, LinearLayoutManager.VERTICAL)
            setHasFixedSize(true)
            adapter = movieAdapter
        }
    }

    private fun setObserver() {
        binding.apply {
            lifecycleScope.launch {
                homeViewModel.getMovieNowPlaying().collect {
                    movieAdapter.submitData(viewLifecycleOwner.lifecycle, it)
                }
            }

            movieAdapter.apply {
                layoutError.apply {
                    addLoadStateListener { loadState ->
                        val loadStateRefresh = loadState.source.refresh
                        shimmerLoading.isVisible = loadStateRefresh is LoadState.Loading
                        rvHome.isVisible = loadStateRefresh is LoadState.NotLoading
                        tvErrorTitle.apply {
                            isVisible = loadStateRefresh is LoadState.Error
                            text = getString(R.string.tvOops)
                            setTextColor(
                                ContextCompat.getColor(
                                    requireContext(),
                                    R.color.colorOrange
                                )
                            )
                        }
                        tvError.apply {
                            isVisible = loadStateRefresh is LoadState.Error
                            text = getString(R.string.tvUnableLoadData)
                            setTextColor(
                                ContextCompat.getColor(
                                    requireContext(),
                                    R.color.colorOrange
                                )
                            )
                        }
                        btnRetry.isVisible = loadStateRefresh is LoadState.Error

                        if (loadStateRefresh is LoadState.NotLoading
                            && loadState.append.endOfPaginationReached
                            && itemCount < 1
                        ) {
                            rvHome.isVisible = false
                            tvErrorTitle.isVisible = true
                            tvError.isVisible = true
                        } else {
                            tvErrorTitle.isVisible = false
                            tvError.isVisible = false
                        }
                    }

                    btnRetry.setOnClickListener {
                        movieAdapter.retry()
                    }
                }
            }

            rvHome.adapter = movieAdapter.withLoadStateHeaderAndFooter(
                header = LoadMoreAdapter { movieAdapter.retry() },
                footer = LoadMoreAdapter { movieAdapter.retry() }
            )
        }
    }

    private fun navigateToSearch() {
        binding.fabSearch.setOnClickListener {
            findNavController().navigate(R.id.discoverMovieFragment)
        }
    }

    override fun onDestroyView() {
        _binding = null
        super.onDestroyView()
    }
}