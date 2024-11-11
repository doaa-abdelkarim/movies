package com.example.movies.presentation.details.children.info

import android.content.Context
import android.os.Bundle
import android.view.View
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import com.example.movies.MoviesApp
import com.example.movies.R
import com.example.movies.databinding.FragmentInfoBinding
import com.example.movies.domain.entities.Video
import com.example.movies.presentation.details.parent.DetailsViewModel
import com.example.movies.util.AppConstants.Companion.KEY_STATE_SELECTED_VIDEO
import dagger.hilt.android.AndroidEntryPoint
import dagger.hilt.android.qualifiers.ApplicationContext
import kotlinx.coroutines.launch
import javax.inject.Inject


@AndroidEntryPoint
class InfoFragment : Fragment(R.layout.fragment_info) {
    @Inject
    @ApplicationContext
    lateinit var appContext: Context

    private lateinit var binding: FragmentInfoBinding

    private val detailsViewModel: DetailsViewModel by viewModels({ requireParentFragment() })
    private val infoViewModel: InfoViewModel by viewModels()

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding = FragmentInfoBinding.bind(view)
        binding.infoViewModel = infoViewModel
        binding.lifecycleOwner = this
        observeState()
    }

    private fun observeState() {
        if ((appContext as MoviesApp).isLargeScreen) {
            viewLifecycleOwner.lifecycleScope.launch {
                repeatOnLifecycle(Lifecycle.State.STARTED) {
                    detailsViewModel.observableSelectedVideo.collect {
                        it?.let {
                            infoViewModel.getVideoInfo(selectedVideo = it, isLargeScreen = true)
                        }
                    }
                }
            }
        }
    }

    companion object {
        fun newInstance(selectedVideo: Video?) =
            InfoFragment().apply {
                arguments = Bundle().apply {
                    putParcelable(KEY_STATE_SELECTED_VIDEO, selectedVideo)
                }
            }
    }

}