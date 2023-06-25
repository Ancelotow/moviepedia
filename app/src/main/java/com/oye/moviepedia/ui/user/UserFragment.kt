package com.oye.moviepedia.ui.user

import android.graphics.Color
import android.graphics.LinearGradient
import android.graphics.Shader
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.EditText
import android.widget.ImageView
import android.widget.TextView
import androidx.core.view.isVisible
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import com.oye.moviepedia.R
import com.oye.moviepedia.databinding.FragmentUserBinding
import com.oye.moviepedia.domain.entities.Playlist
import com.oye.moviepedia.domain.uses_cases.LikedMovieDataError
import com.oye.moviepedia.domain.uses_cases.LikedMovieError
import com.oye.moviepedia.domain.uses_cases.LikedMovieSuccess
import com.oye.moviepedia.domain.uses_cases.NewMovieDataError
import com.oye.moviepedia.domain.uses_cases.NewMovieError
import com.oye.moviepedia.domain.uses_cases.NewMovieSuccess
import com.oye.moviepedia.domain.uses_cases.NowPlayingMovieDataError
import com.oye.moviepedia.domain.uses_cases.NowPlayingMovieError
import com.oye.moviepedia.domain.uses_cases.NowPlayingMovieSuccess
import com.oye.moviepedia.ui.home.HomeViewModel
import com.oye.moviepedia.ui.home.ListMovieItem
import com.oye.moviepedia.ui.home.ListMovieListAdapter
import com.oye.moviepedia.ui.home.MovieItem
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class UserFragment : Fragment() {

    private val viewModel: UserViewModel by viewModels()
    private var _binding: FragmentUserBinding? = null

    private val binding get() = _binding!!
    private val movieList = ArrayList<ListMovieItem>(4).apply {
        repeat(4) {
            add(ListMovieItem("", mutableListOf()))
        }
    }

    private val playlistList = ArrayList<ListPlaylistItem>(4).apply {
        repeat(4) {
            add(ListPlaylistItem("", mutableListOf()))
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentUserBinding.inflate(inflater, container, false)
        val root: View = binding.root

        val title = binding.appTitle
        val paint = title.paint
        val width = paint.measureText(title.text.toString())
        title.paint.shader = LinearGradient(
            0f, 0f, width, 0f, Color.parseColor("#9CCCA5"), Color.parseColor("#51B1DF"),
            Shader.TileMode.CLAMP
        )

        val recyclerView = binding.recyclerLikedMovies
        val linearLayoutManager = LinearLayoutManager(container?.context)
        recyclerView.layoutManager = linearLayoutManager

        val recyclerViewPlaylist = binding.recyclerPlaylist
        val playlistLinearLayoutManager = LinearLayoutManager(container?.context)
        recyclerViewPlaylist.layoutManager = playlistLinearLayoutManager

        movieList.ensureCapacity(4)
        playlistList.ensureCapacity(4)
        initLikedMovies()
        initPlaylist()
        val buttonAddPlaylist = binding.addButtonPlaylist
        buttonAddPlaylist.setOnClickListener {
            onAddButtonClick()
        }

        return root
    }

    private fun initLikedMovies() {
        viewModel.likedMoviesState.observe(viewLifecycleOwner) {
            when (it) {
                is LikedMovieSuccess -> {
                    val movies = it.movies.map { e -> MovieItem(e.title, e.posterUrl, e.director) }
                        .toMutableList()
                    movieList.add(ListMovieItem(getString(R.string.liked_new_movies), movies))
                    binding.recyclerLikedMovies.adapter = ListMovieListAdapter(movieList, activity)
                }

                is LikedMovieDataError -> {
                    Log.e("DATA ERROR", it.ex.message)
                }

                is LikedMovieError -> {
                    Log.e("ERROR", it.ex.message!!)
                }

                else -> {
                }
            }
        }
    }

    private fun initPlaylist(){
        val exemple_playlists = listOf(
            Playlist("https://cdn-icons-png.flaticon.com/512/2798/2798007.png", "Playlist 1"),
            Playlist("https://cdn-icons-png.flaticon.com/512/2798/2798007.png", "Playlist 2"),
            Playlist("https://cdn-icons-png.flaticon.com/512/2798/2798007.png", "Playlist 3"),
            Playlist("https://cdn-icons-png.flaticon.com/512/2798/2798007.png", "Playlist 4")
        )
        val playlists = exemple_playlists.map { e -> PlaylistItem(e.posterUrl, e.name, "Films : 5") }
            .toMutableList()
        playlistList.add(ListPlaylistItem(getString(R.string.user_playlist), playlists))
        binding.recyclerPlaylist.adapter = ListPlaylistListAdapter(playlistList, activity)
    }

    private fun onAddButtonClick() {
        val editText = binding.editTextPlaylist
        if (!editText.isVisible) {
            editText.isVisible = true
        } else {
            editText.isVisible = false
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

}