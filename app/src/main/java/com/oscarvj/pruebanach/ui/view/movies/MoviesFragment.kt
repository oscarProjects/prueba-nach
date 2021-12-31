package com.oscarvj.pruebanach.ui.view.movies

import android.content.Intent
import android.database.Cursor
import android.net.Uri
import android.os.Bundle
import android.provider.MediaStore
import android.provider.OpenableColumns
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.view.isVisible
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.GridLayoutManager
import com.oscarvj.pruebanach.R
import com.oscarvj.pruebanach.constants.Constants
import com.oscarvj.pruebanach.data.database.model.MovieEntity
import com.oscarvj.pruebanach.data.firebase.storage.FireBaseStorage
import com.oscarvj.pruebanach.data.model.ResultsModel
import com.oscarvj.pruebanach.databinding.FragmentMoviesBinding
import com.oscarvj.pruebanach.di.manager.NavigationManager
import com.oscarvj.pruebanach.listener.ItemListener
import com.oscarvj.pruebanach.ui.view.movies.adapter.MoviesAdapter
import com.oscarvj.pruebanach.ui.viewmodel.movies.MoviesViewModel
import dagger.hilt.android.AndroidEntryPoint
import javax.inject.Inject

@AndroidEntryPoint
class MoviesFragment : Fragment(), ItemListener {

    private lateinit var binding: FragmentMoviesBinding

    @Inject
    lateinit var navigation: NavigationManager

    private val moviesViewModel: MoviesViewModel by viewModels()

    private lateinit var adapter: MoviesAdapter

    private var hasInternet : Boolean = false

    @Inject
    lateinit var fireBaseStorage: FireBaseStorage

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        binding = FragmentMoviesBinding.inflate(layoutInflater)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initConfiguration()
        initListener()
    }

    private fun initConfiguration(){
        loadArguments(arguments)
        initAdapter()
        if(hasInternet) loadData()
        initObservers()
    }

    private fun initListener(){
        binding.textViewMapa.setOnClickListener {
            navigation.onNavigate(view, R.id.action_MapFragment)
        }

        binding.textViewGalery.setOnClickListener {
            val gallery = Intent(Intent.ACTION_PICK, MediaStore.Images.Media.INTERNAL_CONTENT_URI)
            gallery.putExtra(Intent.EXTRA_ALLOW_MULTIPLE,true)
            startActivityForResult(gallery, 100)
        }

        binding.textViewPhoto.setOnClickListener {
            navigation.onNavigate(view, R.id.action_PhotoFragment)
        }
    }

    private fun loadArguments(arguments: Bundle?) {
        if (arguments != null) {
            hasInternet = arguments.getBoolean(Constants.BUNDLE_INTERNET)
        }
    }

    private fun initAdapter() {
        adapter = MoviesAdapter(this)
        binding.rvMovies.layoutManager = GridLayoutManager(context, 2)
        binding.rvMovies.setHasFixedSize(true)
        binding.rvMovies.adapter = adapter
    }

    private fun loadData(){
        moviesViewModel.getAllMovies()
    }

    private fun initObservers(){
        moviesViewModel.moviesModel.observe(this, Observer {
            if(it != null){
                deleteMovies()
                saveMovies(it.results)
                binding.textViewCategory.text = it.name
            }
        })

        moviesViewModel.isLoading.observe(this, Observer {
            binding.progressBar.isVisible = it
        })

        moviesViewModel.allMovies.observe(this, Observer {
             adapter.addData(it)
        })
    }

    private fun saveMovies(results: List<ResultsModel>) {
        results.forEach {
            val movieEntity = MovieEntity(
                originalTitle = it.original_title,
                overview = it.overview,
                posterPath = it.poster_path,
                releaseDate = it.release_date
            )
            moviesViewModel.saveMovie(movieEntity)
        }
    }

    private fun deleteMovies(){
        moviesViewModel.deleteMovies()
    }

    override fun onItemSelected(objects: Any) {
        navigation.onNavigate(view, R.id.action_detailMovieFragment, buildArguments(objects as MovieEntity))
    }

    private fun buildArguments(movieEntity: MovieEntity): Bundle{
        val bundle = Bundle()
        bundle.putSerializable(Constants.BUNDLE_DETAIL_M, movieEntity)
        return bundle
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        println("DATA ${data?.clipData} ${data?.dataString}")
        for (i in 0 until data?.clipData!!.itemCount){
            val uri = data?.clipData!!.getItemAt(i).uri
            val fileName = getFileName(uri)
            println("Valor de FileName: $fileName")
            fireBaseStorage.uploadImage(fileName!!,uri, requireContext())
        }
    }

    fun getFileName(uri: Uri): String? {
        var result: String? = null
        if (uri.scheme == "content") {
            val cursor: Cursor? = context!!.contentResolver.query(uri, null, null, null, null)
            try {
                if (cursor != null && cursor.moveToFirst()) {
                    result = cursor.getString(cursor.getColumnIndex(OpenableColumns.DISPLAY_NAME))
                }
            } finally {
                cursor?.close()
            }
        }
        if (result == null) {
            result = uri.path
            val cut = result!!.lastIndexOf('/')
            if (cut != -1) {
                result = result.substring(cut + 1)
            }
        }
        return result
    }
}