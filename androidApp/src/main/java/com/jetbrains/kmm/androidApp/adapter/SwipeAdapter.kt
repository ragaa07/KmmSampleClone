package com.jetbrains.kmm.androidApp.adapter

import android.view.View
import android.widget.TextView
import com.ernestoyaquello.dragdropswiperecyclerview.DragDropSwipeAdapter
import com.jetbrains.androidApp.R

class SwipeAdapter(data: MutableList<String>) :
    DragDropSwipeAdapter<String, SwipeAdapter.ViewHolder>(data) {
    class ViewHolder(itemView: View) : DragDropSwipeAdapter.ViewHolder(itemView) {
        val task: TextView = itemView.findViewById(R.id.task_text)
    }

    override fun getViewHolder(itemView: View): ViewHolder = ViewHolder(itemView)

    override fun getViewToTouchToStartDraggingItem(
        item: String,
        viewHolder: ViewHolder,
        position: Int
    ): View {
        return viewHolder.task
    }

    override fun onBindViewHolder(item: String, viewHolder: ViewHolder, position: Int) {
        viewHolder.task.text = dataSet[position]
    }

}