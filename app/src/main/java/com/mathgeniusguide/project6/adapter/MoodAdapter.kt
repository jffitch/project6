package com.mathgeniusguide.project6.adapter

import android.app.Activity
import android.content.Context
import android.support.v7.widget.RecyclerView
import android.util.DisplayMetrics
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.RelativeLayout
import android.widget.Toast
import com.mathgeniusguide.project6.entity.Moods
import kotlinx.android.synthetic.main.saved_mood.view.*
import com.mathgeniusguide.project6.R

class MoodAdapter (val items: ArrayList<Moods>, val context: Context) : RecyclerView.Adapter<MoodAdapter.ViewHolder> () {
    private var screenWidth = 0
    private var screenHeight = 0

    override fun getItemCount(): Int {
        return items.size
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        // get screen size and set screenWidth and screenHeight variables
        // screenHeight has 1 inch offset for app bar on top
        val displayMetrics = DisplayMetrics()
        (parent.context as Activity).windowManager.defaultDisplay.getMetrics(displayMetrics)
        screenWidth = displayMetrics.widthPixels
        screenHeight = displayMetrics.heightPixels - (.6 * displayMetrics.ydpi).toInt()
        // first argument in "inflate" is the layout for the group
        return ViewHolder(LayoutInflater.from(context).inflate(R.layout.saved_mood, parent, false))
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        // code here controls what's done to the views for each group
        var pos = items.get(position)
        // set date TextView to date in database
        holder.date.text = pos.time
        // set background color to match emotion in database
        var clr = when (pos.emotion) {
            1 -> R.color.verysad
            2 -> R.color.sad
            4 -> R.color.happy
            5 -> R.color.veryhappy
            else -> R.color.neutral
        }
        holder.parent.setBackgroundResource(clr)
        // set view width to match emotion in database
        val params = RelativeLayout.LayoutParams((screenWidth * (.25 + .15 * pos.emotion)).toInt(), screenHeight / 7)
        holder.parent.layoutParams = params
        // if no message, remove message icon
        // when message icon is clicked, show message in toast
        if (pos.note == "") {
            holder.comment.visibility = View.GONE
        }
        holder.comment.setOnClickListener {
            Toast.makeText(context, pos.note, Toast.LENGTH_LONG).show()
        }
    }

    class ViewHolder (view : View) : RecyclerView.ViewHolder(view) {
        // variables here are used in onBindViewHolder
        val date = view.moodDate
        val parent = view.parentView
        val comment = view.moodComment
    }
}