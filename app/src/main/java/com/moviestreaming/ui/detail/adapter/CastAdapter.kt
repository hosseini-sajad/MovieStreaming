package com.moviestreaming.ui.detail.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.widget.AppCompatTextView
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.moviestreaming.R
import com.moviestreaming.data.model.CreditsEntity.CastEntity
import com.moviestreaming.ui.ItemClickListener
import com.moviestreaming.ui.detail.adapter.CastAdapter.MovieCastViewHolder
import com.moviestreaming.utils.getImageUrl
import de.hdodenhof.circleimageview.CircleImageView

class CastAdapter(
    private val listCastMovie: List<CastEntity>,
    private val itemClickListener: ItemClickListener<CastEntity>?,
) : RecyclerView.Adapter<MovieCastViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MovieCastViewHolder {
        return MovieCastViewHolder.from(parent)
    }

    override fun getItemCount() = listCastMovie.size

    override fun onBindViewHolder(holder: MovieCastViewHolder, position: Int) {
        val item = listCastMovie[position]
        holder.bind(item, itemClickListener)
    }

    class MovieCastViewHolder constructor(itemView: View) : RecyclerView.ViewHolder(itemView) {
        private val castImage = itemView.findViewById<CircleImageView>(R.id.castImage)
        private val castName = itemView.findViewById<AppCompatTextView>(R.id.castName)

        fun bind(
            item: CastEntity,
            listener: ItemClickListener<CastEntity>?,
        ) {
            val url = item.profileImage?.let { getImageUrl(it) }
            Glide.with(castImage.context)
                .load(url)
                .override(80, 80)
                .error(R.drawable.ic_image_error)
                .into(castImage)

            castName.text = item.realName

            itemView.setOnClickListener {
                listener?.onItemClickListener(item)
            }
        }

        companion object {
            fun from(parent: ViewGroup): MovieCastViewHolder {
                val layoutInflater = LayoutInflater.from(parent.context)
                val view = layoutInflater
                    .inflate(R.layout.cast_item_list, parent, false)

                return MovieCastViewHolder(view)
            }
        }
    }

}