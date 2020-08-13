package com.example.otusproject.ui.screen_fav

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.bumptech.glide.request.RequestOptions
import com.example.otusproject.R
import com.example.otusproject.data.vo.MovieItem
import kotlinx.android.synthetic.main.movie_item_favorite_layout.view.*

class MovieFavRecycleAdapter(
    private val context: LayoutInflater,
    private var items: List<MovieItem>,
    val listener: (MovieItem) -> Unit
) : RecyclerView.Adapter<RecyclerView.ViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        return MovieFavViewHolder(
            LayoutInflater.from(parent.context).inflate(
                R.layout.movie_item_favorite_layout,
                parent,
                false
            )
        )
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        when (holder) {
            is MovieFavViewHolder -> {
                holder.bind(items[position]) {
                   listener(it)
                    notifyItemRemoved(position)
                    notifyDataSetChanged()
                }
            }
        }
    }

    override fun getItemCount(): Int {
        return items.size
    }

    class MovieFavViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        private val itemTitle: TextView = itemView.movieName
        private val itemPoster: ImageView = itemView.moviePoster1
        private val itemRating: TextView = itemView.movieRating
        private val itemButton: Button = itemView.deleteBtn

        fun bind(movieItem: MovieItem, listener: (MovieItem) -> Unit) {
            itemTitle.text = movieItem.title
            itemRating.text = movieItem.voteAverage.toString()
            itemButton.setOnClickListener {
                listener(movieItem)
            }

            val requestOption = RequestOptions()
                .placeholder(R.drawable.ic_launcher_background)
                .error(R.drawable.ic_launcher_background)

            Glide.with(itemView.context)
                .applyDefaultRequestOptions(requestOption)
                .load("https://image.tmdb.org/t/p/w500"+movieItem.posterPath)
                .into(itemPoster)
        }
    }
}
