package com.gabo.ramo.presentation.search

import android.net.Uri
import androidx.recyclerview.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import com.bumptech.glide.Glide
import com.gabo.ramo.R
import com.gabo.ramo.data.Movie


import kotlinx.android.synthetic.main.movie_item.view.*
import com.bumptech.glide.request.RequestOptions
import com.gabo.ramo.data.IMAGE_BASE_URL

class MovieRecyclerViewAdapter(
        private var mValues: List<Movie>,
        private val mListener: OnListInteractionListener?)
    : RecyclerView.Adapter<MovieRecyclerViewAdapter.ViewHolder>() {

    private val mOnClickListener: View.OnClickListener

    init {
        mOnClickListener = View.OnClickListener { v ->
            val item = v.tag as Movie
            // Notify the active callbacks interface (the activity, if the fragment is attached to
            // one) that an item has been selected.
            mListener?.onListItemSelected(item)
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = LayoutInflater.from(parent.context)
                .inflate(R.layout.movie_item, parent, false)

        return ViewHolder(view)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val item = mValues[position]
        val uri = item.posterPath?.let { Uri.parse("$IMAGE_BASE_URL$it") }
        Glide.with(holder.mView)
                .load(uri)
                .apply(RequestOptions().fitCenter().override(450, 600))
                .into(holder.imageView)

        with(holder.mView) {
            tag = item
            setOnClickListener(mOnClickListener)
        }
    }

    override fun getItemCount(): Int = mValues.size

    fun updateMovies(movies: List<Movie>) {
        mValues = movies
        notifyDataSetChanged()
    }

    inner class ViewHolder(val mView: View) : RecyclerView.ViewHolder(mView) {
        val imageView: ImageView = mView.movie_image
    }


    interface OnListInteractionListener {
        fun onListItemSelected(item: Movie)
    }
}
