package com.moviestreaming.ui.home.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.widget.AppCompatImageView
import androidx.appcompat.widget.AppCompatTextView
import androidx.viewpager.widget.PagerAdapter
import com.bumptech.glide.Glide
import com.moviestreaming.R
import com.moviestreaming.data.model.TrendingEntity
import com.moviestreaming.ui.ItemClickListener
import com.moviestreaming.utils.getImageUrl
import java.util.Objects

/*
class TrendingAdapter(private val itemClickListener: ItemClickListener<TrendingEntity>?) : RecyclerView.Adapter<TrendingViewHolder>() {
    private val lisOfTrending: MutableList<TrendingEntity> = mutableListOf()

    fun addListOfTrending(list: List<TrendingEntity>) {
        lisOfTrending.addAll(list)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): TrendingViewHolder {
        return TrendingViewHolder.from(parent)
    }

    override fun getItemCount() = lisOfTrending.size

    override fun onBindViewHolder(holder: TrendingViewHolder, position: Int) {
        val item = lisOfTrending[position]
        holder.bind(item, itemClickListener)
    }

}

class TrendingViewHolder constructor(itemView: View) : RecyclerView.ViewHolder(itemView) {
    private val imageSlider = itemView.findViewById<AppCompatImageView>(R.id.imageSlider)

    fun bind(
        item: TrendingEntity,
        listener: ItemClickListener<TrendingEntity>?,
    ) {
        Glide.with(imageSlider.context)
            .load(item.image)
//            .error(R.drawable.ic_image_error)
            .into(imageSlider)

        itemView.setOnClickListener {
            listener?.onItemClickListener(item)
        }
    }

    companion object {
        fun from(parent: ViewGroup): TrendingViewHolder {
            val layoutInflater = LayoutInflater.from(parent.context)
            val view = layoutInflater
                .inflate(R.layout.slider_item_list, parent, false)

            return TrendingViewHolder(view)
        }
    }
}*/

class TrendingAdapter(
    private val lisOfTrending: List<TrendingEntity>,
    private val itemClickListener: ItemClickListener<TrendingEntity>?,
) : PagerAdapter() {
    override fun getCount() = lisOfTrending.size

    override fun instantiateItem(container: ViewGroup, position: Int): Any {
        val itemView = LayoutInflater.from(container.context).inflate(R.layout.slider_item_list, container, false)
        val imageSlider = itemView.findViewById<AppCompatImageView>(R.id.imageSlider)
        val name = itemView.findViewById<AppCompatTextView>(R.id.name)
        val item = lisOfTrending[position]

        val url = getImageUrl(item.image)
        Glide.with(imageSlider.context)
            .load(url)
//            .error(R.drawable.ic_image_error)
            .into(imageSlider)

        name.text = item.title

        Objects.requireNonNull(container).addView(itemView);

        return itemView
    }

    override fun isViewFromObject(view: View, `object`: Any): Boolean {
        return view == `object`
    }

    override fun destroyItem(container: ViewGroup, position: Int, `object`: Any) {
        container.removeView(`object` as View)
    }

}
