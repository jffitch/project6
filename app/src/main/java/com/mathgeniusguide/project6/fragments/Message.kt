package com.mathgeniusguide.project6.fragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.navigation.Navigation
import com.mathgeniusguide.project6.dao.MoodsDao
import com.mathgeniusguide.project6.database.MoodsDatabase
import com.mathgeniusguide.project6.entity.Moods
import com.mathgeniusguide.project6.utils.OnSwipeTouchListener
import io.reactivex.Observable
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers
import kotlinx.android.synthetic.main.message.*
import java.text.SimpleDateFormat
import java.util.*
import com.mathgeniusguide.project6.R
import java.text.ParseException

private const val ARG_PARAM1 = "param1"

class Message : Fragment() {
    private var param1: Int? = null
    private var db: MoodsDatabase? = null
    private var moodsDao: MoodsDao? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        arguments?.let {
            param1 = it.getInt(ARG_PARAM1)
        }
        db = MoodsDatabase.getDataBase(context = this.requireContext())
        activity?.setTitle(resources.getString(R.string.title_note))
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        val view = inflater.inflate(R.layout.message, container, false)
        return view
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setEmotionText()
        setListeners()
        setSwipeListener(view)
    }

    private fun setEmotionText() {
        // Set the text in "Why are you feeling ___ today?" and the background color based on the selected emotion.
        val emtn = when (param1) {
            1 -> R.string.very_sad
            2 -> R.string.sad
            4 -> R.string.happy
            5 -> R.string.very_happy
            else -> R.string.neutral
        }
        emotion.text = String.format(resources.getString(R.string.emotion_string), resources.getString(emtn))
        val clr = when (param1) {
            1 -> R.color.verysad
            2 -> R.color.sad
            4 -> R.color.happy
            5 -> R.color.veryhappy
            else -> R.color.neutral
        }
        parent.setBackgroundResource(clr)
    }

    private fun setListeners() {
        back.setOnClickListener {
            // go back to previous screen
            val action = MessageDirections.actionMessageToSelection(param1!!)
            Navigation.findNavController(it).navigate(action)
        }
        save.setOnClickListener {
            // use entered date if valid, use today's date if no date or invalid date entered
            val date = validDate(dateBox.text.toString())
            // save emotion and comment in database and go back to previous screen
            Observable.fromCallable({
                db = MoodsDatabase.getDataBase(context!!)
                moodsDao = db?.moodsDao()

                with(moodsDao) {
                    this?.insertMood(Moods(date, param1!!, note.text.toString()))
                }
            }).subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe()
            back.callOnClick()
        }
        calendar.setOnClickListener {
            // remove the Calendar icon and show the field to enter the date
            calendar.visibility = View.GONE
            dateLabel.visibility = View.VISIBLE
            dateBox.visibility = View.VISIBLE
        }
    }

    private fun validDate(date: String): String {
        val sdf = SimpleDateFormat("yyyy/MM/dd", Locale.getDefault())
        sdf.isLenient = false
        val todayDate = Date()
        val today = sdf.format(todayDate)
        // if date is not parseable, return today
        try {
            sdf.parse(date)
        }
        catch (e: ParseException){
            return today
        }
        // if date is future, return today
        if (sdf.parse(date).after(todayDate)) {
            return today
        }
        // if date is real and past, return date
        // convert to date then back to string in order to insert leading 0s, because dates without leading 0s will not order properly
        return sdf.format(sdf.parse(date))
    }

    private fun setSwipeListener(view: View) {
        context?.let {
            view.setOnTouchListener(object : OnSwipeTouchListener(it) {
                override fun onSwipeRight() {
                    super.onSwipeRight()
                    // return to selection screen
                    val action = MessageDirections.actionMessageToSelection(param1!!)
                    Navigation.findNavController(view).navigate(action)
                }
            })
        }
    }
}