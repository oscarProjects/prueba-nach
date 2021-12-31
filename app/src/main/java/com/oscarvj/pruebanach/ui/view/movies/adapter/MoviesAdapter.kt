package com.oscarvj.pruebanach.ui.view.movies.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.oscarvj.pruebanach.data.database.model.MovieEntity
import com.oscarvj.pruebanach.databinding.ItemMoviesBinding
import com.oscarvj.pruebanach.listener.ItemListener

class MoviesAdapter(
    private val listener: ItemListener,
) : RecyclerView.Adapter<MoviesAdapter.ViewHolder>()  {

    private val items: MutableList<MovieEntity> = ArrayList()

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val inflater = LayoutInflater.from(parent.context)
        val binding = ItemMoviesBinding.inflate(inflater)
        return ViewHolder(binding)
    }

    fun addData(items: List<MovieEntity>) {
        this.items.clear()
        this.items.addAll(items)
        notifyDataSetChanged()
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.bind(items[position])

        holder.binding.itemId.setOnClickListener {
            listener.onItemSelected(items[position])
        }
    }

    override fun getItemCount(): Int {
        return items.size
    }

    inner class ViewHolder(val binding: ItemMoviesBinding): RecyclerView.ViewHolder(binding.root) {
        fun bind(item: MovieEntity) {

            binding.textViewCategory.text = item.originalTitle

            Glide.with(itemView.context)
                .load("https://image.tmdb.org/t/p/w500${item.posterPath}")
                .circleCrop()
                .into(binding.image)
        }
    }
}