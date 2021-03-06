package com.mathgeniusguide.project6.adapter

import android.app.Activity
import android.content.Context
import android.util.DisplayMetrics
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.RelativeLayout
import android.widget.Toast
import androidx.recyclerview.widget.RecyclerView
import com.mathgeniusguide.project6.entity.Moods
import kotlinx.android.synthetic.main.saved_mood.view.*
import com.mathgeniusguide.project6.R
import java.text.SimpleDateFormat
import java.util.*

class MoodsAdapter (private val items: ArrayList<Moods>, val context: Context) : RecyclerView.Adapter<MoodsAdapter.ViewHolder> () {
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
        val pos = items[position]
        // set date TextView to date in database
        val sdf = SimpleDateFormat("yyyy/MM/dd", Locale.getDefault())
        val diff = dateDiff(pos.time, sdf.format(Date()))
        holder.date.text = when (diff) {
            0 -> context.resources.getString(R.string.today)
            1 -> context.resources.getString(R.string.yesterday)
            else -> String.format(context.resources.getString(R.string.days_ago), diff)
        }
        // set background color to match emotion in database
        val clr = when (pos.emotion) {
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

    private fun dateDiff(date1: String, date2: String) : Int {
        val MILLISECONDS_IN_DAY = 86400000
        val sdf = SimpleDateFormat("yyyy/MM/dd", Locale.getDefault())
        val d1 = sdf.parse(date1)
        val d2 = sdf.parse(date2)
        return ((Math.abs(d1.time - d2.time)) / MILLISECONDS_IN_DAY).toInt()
    }

    class ViewHolder (view : View) : RecyclerView.ViewHolder(view) {
        // variables here are used in onBindViewHolder
        val date = view.moodDate
        val parent = view.parentView
        val comment = view.moodComment
    }
}