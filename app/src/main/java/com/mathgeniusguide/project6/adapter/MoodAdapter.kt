package com.mathgeniusguide.project6.adapter

import android.content.Context
import android.support.v4.content.ContextCompat
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import com.mathgeniusguide.project6.entity.Moods
import kotlinx.android.synthetic.main.saved_mood.view.*
import com.mathgeniusguide.project6.R

class MoodAdapter (val items: ArrayList<Moods>, val context: Context) : RecyclerView.Adapter<MoodAdapter.ViewHolder> () {
    override fun getItemCount(): Int {
        return items.size
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        return ViewHolder(LayoutInflater.from(context).inflate(R.layout.saved_mood, parent, false))
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        var pos = items.get(position)
        holder.date.text = pos.time.toString()
        var clr = when (pos.emotion) {
            1 -> R.color.verysad
            2 -> R.color.sad
            4 -> R.color.happy
            5 -> R.color.veryhappy
            else -> R.color.neutral
        }
        holder.parent.setBackgroundColor(ContextCompat.getColor(context!!, clr))
        if (pos.note == "") {
            holder.comment.visibility = View.GONE
        }
        holder.comment.setOnClickListener {
            Toast.makeText(context, pos.note, Toast.LENGTH_LONG).show()
        }
    }

    class ViewHolder (view : View) : RecyclerView.ViewHolder(view) {
        val date = view.moodDate
        val parent = view.parentView
        val comment = view.moodComment
    }
}