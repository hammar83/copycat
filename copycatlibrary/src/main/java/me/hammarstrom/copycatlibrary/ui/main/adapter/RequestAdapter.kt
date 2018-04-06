package me.hammarstrom.copycatlibrary.ui.main.adapter

import android.support.v7.recyclerview.extensions.ListAdapter
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import me.hammarstrom.copycatlibrary.R
import me.hammarstrom.copycatlibrary.models.CopycatRequest

class RequestAdapter(
        private val listener: OnRequestClickListener
) : ListAdapter<CopycatRequest, RequestAdapter.ViewHolder>(CopycatRequest.DIFF_CALLBACK) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = LayoutInflater.from(parent.context)
                .inflate(
                        R.layout.item_request,
                        parent,
                        false
                )

        return ViewHolder(view, listener)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.bindTo(getItem(position))
    }

    class ViewHolder(
            v: View,
            private val listener: OnRequestClickListener
    ) : RecyclerView.ViewHolder(v) {

        fun bindTo(request: CopycatRequest) {
            itemView.findViewById<TextView>(R.id.hash).text = request.hash
            itemView.findViewById<TextView>(R.id.method).text = request.request.method
            itemView.findViewById<TextView>(R.id.path).text = request.request.path

            itemView.setOnClickListener {
                listener.onRequestClick(request.hash)
            }
        }

    }

    interface OnRequestClickListener {
        fun onRequestClick(hash: String)
    }
}
