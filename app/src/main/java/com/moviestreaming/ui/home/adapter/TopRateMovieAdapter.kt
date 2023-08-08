package com.moviestreaming.ui.home.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.widget.AppCompatImageView
import androidx.appcompat.widget.AppCompatTextView
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.moviestreaming.R
import com.moviestreaming.data.model.TopRateMovieEntity
import com.moviestreaming.data.model.base.BaseEntity
import com.moviestreaming.ui.ItemClickListener
import com.moviestreaming.utils.getImageUrl
import com.moviestreaming.utils.roundedTo2Decimal

class TopRateMovieAdapter(
    private val listOfTopRateMovie: List<TopRateMovieEntity>,
    private val itemClickListener: ItemClickListener<BaseEntity>?,
) : RecyclerView.Adapter<TopRateMovieViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): TopRateMovieViewHolder {
        return TopRateMovieViewHolder.from(parent)
    }

    override fun getItemCount(): Int {
        return 5
    }

    override fun onBindViewHolder(holder: TopRateMovieViewHolder, position: Int) {
        val item = listOfTopRateMovie[position]
        holder.bind(item, itemClickListener)
    }

}

class TopRateMovieViewHolder constructor(itemView: View) : RecyclerView.ViewHolder(itemView) {
    private val imageSlider = itemView.findViewById<AppCompatImageView>(R.id.movieImage)
    private val movieName = itemView.findViewById<AppCompatTextView>(R.id.movieName)
    private val movieGenre = itemView.findViewById<AppCompatTextView>(R.id.movieGenre)
    private val rate = itemView.findViewById<AppCompatTextView>(R.id.rate)

    fun bind(
        item: TopRateMovieEntity,
        listener: ItemClickListener<BaseEntity>?,
    ) {
        val url = item.image?.let { getImageUrl(it) }
        Glide.with(imageSlider.context)
            .load(url)
            .override(130, 160)
            .error(R.drawable.ic_image_error)
            .into(imageSlider)

        movieName.text = item.title
        movieGenre.text = item.genre.toString()
        rate.text = roundedTo2Decimal(item.rate).toString()

        itemView.setOnClickListener {
            listener?.onItemClickListener(item)
        }
    }

    companion object {
        fun from(parent: ViewGroup): TopRateMovieViewHolder {
            val layoutInflater = LayoutInflater.from(parent.context)
            val view = layoutInflater
                .inflate(R.layout.imdb_item_list, parent, false)

            return TopRateMovieViewHolder(view)
        }
    }
}