package com.oye.moviepedia.ui.user

import android.app.Activity
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.oye.moviepedia.R
import com.oye.moviepedia.ui.home.MovieListAdapter

class ListPlaylistViewHolder(
    v: View,
    private val playlistListener: PlaylistListAdapter.PlaylistListener
    ) : RecyclerView.ViewHolder(v) {

    private val playlists = v.findViewById<RecyclerView>(R.id.item_list_recycler_playlist)

    fun setItem(item: ListPlaylistItem) {
        val linearLayoutManager = LinearLayoutManager(itemView.context)
        linearLayoutManager.orientation = LinearLayoutManager.VERTICAL
        playlists.layoutManager = linearLayoutManager
        playlists.adapter = PlaylistListAdapter(item.playlists, null, playlistListener)

    }

}

class ListPlaylistListAdapter(
    val items: MutableList<ListPlaylistItem>,
    val activity: Activity?,
    private val playlistListener: PlaylistListAdapter.PlaylistListener

) : RecyclerView.Adapter<ListPlaylistViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ListPlaylistViewHolder {
        val inflater = LayoutInflater.from(parent.context)
        val view = inflater.inflate(R.layout.item_list_playlist, parent, false)
        return ListPlaylistViewHolder(view, playlistListener)
    }

    override fun onBindViewHolder(holder: ListPlaylistViewHolder, position: Int) {
        holder.setItem(items[position])
    }

    override fun getItemCount(): Int {
        return items.size
    }

}

data class ListPlaylistItem(
    val playlists: MutableList<PlaylistItem>
)