package com.example.otusproject

import android.content.Context
import android.content.Intent
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.bumptech.glide.load.engine.DiskCacheStrategy
import com.bumptech.glide.request.RequestOptions
import kotlinx.android.synthetic.main.movie_item_favorite_layout.view.*
import kotlinx.android.synthetic.main.movie_item_layout.view.*

class MovieRecyclerAdapter(
    private val context: Context,
    private val items: List<MovieItem>,
    val listener: (MovieItem) -> Unit,
    val longListener: (MovieItem) -> Boolean
): RecyclerView.Adapter<RecyclerView.ViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        return MovieViewHolder(
            LayoutInflater.from(parent.context).inflate(R.layout.movie_item_layout, parent, false)
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


    class MovieViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        private val itemTitle: TextView? = itemView.item_movie_name_id
        private val itemPoster: ImageView = itemView.item_poster_id

        fun bind(movieItem: MovieItem, listener: (MovieItem) -> Unit, longListener: (MovieItem) -> Boolean) {
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
                .load(movieItem.moviePoster)
                .diskCacheStrategy(DiskCacheStrategy.ALL)// сохраняем загруженную картинку в кэш, должно идти после load
                .into(itemPoster)
        }
    }
}