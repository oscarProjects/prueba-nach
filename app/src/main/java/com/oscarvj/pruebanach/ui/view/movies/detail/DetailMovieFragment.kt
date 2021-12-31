package com.oscarvj.pruebanach.ui.view.movies.detail

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.bumptech.glide.Glide
import com.oscarvj.pruebanach.constants.Constants
import com.oscarvj.pruebanach.data.database.model.MovieEntity
import com.oscarvj.pruebanach.databinding.FragmentDetailMovieBinding
import com.oscarvj.pruebanach.di.manager.NavigationManager
import dagger.hilt.android.AndroidEntryPoint
import javax.inject.Inject

@AndroidEntryPoint
class DetailMovieFragment : Fragment() {

    private lateinit var binding: FragmentDetailMovieBinding

    private lateinit var detailModel: MovieEntity

    @Inject
    lateinit var navigation: NavigationManager

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        if(arguments != null){
            detailModel = requireArguments().getSerializable(Constants.BUNDLE_DETAIL_M) as MovieEntity
        }
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        binding = FragmentDetailMovieBinding.inflate(layoutInflater)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.textViewTitle.text = detailModel.originalTitle
        binding.textViewDesc.text = detailModel.overview
        Glide.with(this)
            .load("https://image.tmdb.org/t/p/w500${detailModel.posterPath}")
            .circleCrop()
            .into(binding.imageView)
    }
}