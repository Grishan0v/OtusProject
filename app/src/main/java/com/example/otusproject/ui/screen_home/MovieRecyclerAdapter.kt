package com.example.otusproject.ui.screen_home

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.bumptech.glide.load.engine.DiskCacheStrategy
import com.bumptech.glide.request.RequestOptions
import com.example.otusproject.R
import com.example.otusproject.data.vo.Movie
import kotlinx.android.synthetic.main.movie_item_layout.view.*

class MovieRecyclerAdapter(
    private val context: LayoutInflater,
    private val items: ArrayList<Movie>,
    val listener: (Movie) -> Unit,
    val longListener: (Movie) -> Boolean
) : RecyclerView.Adapter<RecyclerView.ViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        return MovieViewHolder(
            LayoutInflater.from(parent.context).inflate(
                R.layout.movie_item_layout,
                parent,
                false
            )
        )
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        when (holder) {
            is MovieViewHolder -> {
                holder.bind(items[position], listener, longListener)
                holder.itemView.setOnClickListener {
                    listener(items[position])
                }
                holder.itemView.setOnLongClickListener {
                    longListener(items[position])
                    return@setOnLongClickListener true
                }
            }
        }
    }

    override fun getItemCount(): Int {
        return items.size
    }

    fun setItems(movies: List<Movie>) {
        items.clear()
        items.addAll(movies)

        notifyDataSetChanged()
    }


    class MovieViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        private val itemTitle: TextView? = itemView.item_movie_name_id
        private val itemPoster: ImageView = itemView.item_poster_id

        fun bind(
            movieItem: Movie,
            listener: (Movie) -> Unit,
            longListener: (Movie) -> Boolean
        ) {
            itemTitle?.text = movieItem.title

            itemView.setOnClickListener {
                listener(movieItem)
            }

            itemView.setOnLongClickListener {
                longListener(movieItem)
            }

            val requestOption = RequestOptions()
                .placeholder(R.drawable.ic_launcher_background)
                .error(R.drawable.ic_launcher_background)

            Glide.with(itemView.context)
                .applyDefaultRequestOptions(requestOption)
                .load("https://image.tmdb.org/t/p/w500" + movieItem.posterPath)
                .into(itemPoster)
        }
    }
}