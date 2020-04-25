package com.example.otusproject

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.bumptech.glide.request.RequestOptions
import kotlinx.android.synthetic.main.movie_item_favorite_layout.view.*

class MovieFavRecycleAdapter(
    private val context: Context,
    private var items: ArrayList<MovieItem>
): RecyclerView.Adapter<RecyclerView.ViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        return MovieFavViewHolder(
            LayoutInflater.from(parent.context).inflate(R.layout.movie_item_favorite_layout, parent, false)
        )
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        when (holder) {
            is MovieFavViewHolder -> {
                holder.bind(items[position]) {
                    items.removeAt(position)
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
        private val itemPoster: ImageView = itemView.moviePoster
        private val itemRating: TextView = itemView.movieRating
        private val itemButton: Button = itemView.deleteBtn

        fun bind(movieItem: MovieItem, listener: (View) -> Unit) {
            itemTitle.text = movieItem.title
            itemRating.text = movieItem.rating
            itemButton.setOnClickListener{
                listener(itemButton)
            }

            val requestOption = RequestOptions()
                .placeholder(R.drawable.ic_launcher_background)
                .error(R.drawable.ic_launcher_background)

            Glide.with(itemView.context)
                .applyDefaultRequestOptions(requestOption)
                .load(movieItem.moviePoster)
                .into(itemPoster)
        }
    }
}
