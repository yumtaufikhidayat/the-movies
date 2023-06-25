package com.yumtaufikhidayat.themovies.ui.discover.fragment

import android.content.Context
import android.os.Bundle
import android.text.Editable
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.inputmethod.EditorInfo
import android.view.inputmethod.InputMethodManager
import android.widget.TextView
import androidx.core.widget.addTextChangedListener
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.StaggeredGridLayoutManager
import com.yumtaufikhidayat.themovies.data.NetworkResult
import com.yumtaufikhidayat.themovies.databinding.FragmentDiscoverMovieBinding
import com.yumtaufikhidayat.themovies.ui.discover.adapter.DiscoverMovieAdapter
import com.yumtaufikhidayat.themovies.ui.discover.viewmodel.DiscoverViewModel
import com.yumtaufikhidayat.themovies.utils.navigateToDetail
import com.yumtaufikhidayat.themovies.utils.showError
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class DiscoverMovieFragment : Fragment() {

    private var _binding: FragmentDiscoverMovieBinding? = null
    private val binding get() = _binding!!

    private val discoverViewModel by viewModels<DiscoverViewModel>()
    private val discoverAdapter by lazy {
        DiscoverMovieAdapter {
            navigateToDetail(
                it.id,
                it.title
            )
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentDiscoverMovieBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        setDiscoverMovieToolbar()
        setDiscoverMovieAdapter()
        setMovieListData()
    }

    private fun setDiscoverMovieToolbar() {
        binding.toolbarDiscoverMovie.imgBack.setOnClickListener {
            findNavController().popBackStack()
        }
    }

    private fun setMovieListData() {
        binding.rvDiscoverMovie.apply {
            layoutManager = StaggeredGridLayoutManager(2, LinearLayoutManager.VERTICAL)
            setHasFixedSize(true)
            adapter = discoverAdapter
        }
    }

    private fun setDiscoverMovieAdapter() {
        binding.toolbarDiscoverMovie.etSearch.apply {
            showKeyboard()
            setOnEditorActionListener(TextView.OnEditorActionListener { _, actionId, _ ->
                if (actionId == EditorInfo.IME_ACTION_SEARCH) {
                    hideKeyboard()
                    return@OnEditorActionListener true
                }
                false
            })

            addTextChangedListener(afterTextChanged = { s ->
                showDiscoverData(s)
            })
        }
    }

    private fun showDiscoverData(s: Editable?) {
        discoverViewModel.discoverMovie(s.toString()).observe(viewLifecycleOwner) {
            when (it) {
                is NetworkResult.Loading -> {}
                is NetworkResult.Success -> discoverAdapter.submitList(it.data.results)
                is NetworkResult.Error -> showError(DISCOVER_TAG, it.error)
                is NetworkResult.ServerError -> showError(DISCOVER_TAG, it.error)
                is NetworkResult.Unauthorized -> showError(DISCOVER_TAG, it.error)
            }
        }
    }


    private fun showKeyboard() {
        binding.toolbarDiscoverMovie.apply {
            etSearch.requestFocus()
            val imm =
                requireActivity().getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
            imm.showSoftInput(etSearch, InputMethodManager.SHOW_IMPLICIT)
        }
    }

    private fun hideKeyboard() {
        binding.toolbarDiscoverMovie.apply {
            etSearch.clearFocus()
            val imm =
                requireActivity().getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
            imm.hideSoftInputFromWindow(etSearch.windowToken, 0)
        }
    }

    override fun onDestroyView() {
        _binding = null
        super.onDestroyView()
    }

    companion object {
        private const val DISCOVER_TAG = "discover_tag"
    }
}