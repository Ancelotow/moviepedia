package com.oye.moviepedia.ui.user

import android.app.Activity
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.oye.moviepedia.R

class ListPlaylistViewHolder(v: View) : RecyclerView.ViewHolder(v) {

    private val title = v.findViewById<TextView>(R.id.text_list_playlist_title)
    private val playlists = v.findViewById<RecyclerView>(R.id.item_list_recycler_playlist)

    fun setItem(item: ListPlaylistItem) {
        title.text = item.title
        val linearLayoutManager = LinearLayoutManager(itemView.context)
        linearLayoutManager.orientation = LinearLayoutManager.VERTICAL
        playlists.layoutManager = linearLayoutManager
        playlists.adapter = PlaylistListAdapter(item.playlists, null)

    }

}

class ListPlaylistListAdapter(
    val items: MutableList<ListPlaylistItem>,
    val activity: Activity?
) : RecyclerView.Adapter<ListPlaylistViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ListPlaylistViewHolder {
        val inflater = LayoutInflater.from(parent.context)
        val view = inflater.inflate(R.layout.item_list_playlist, parent, false)
        return ListPlaylistViewHolder(view)
    }

    override fun onBindViewHolder(holder: ListPlaylistViewHolder, position: Int) {
        holder.setItem(items[position])
    }

    override fun getItemCount(): Int {
        return items.size
    }

}

data class ListPlaylistItem(
    val title: String,
    val playlists: MutableList<PlaylistItem>
)