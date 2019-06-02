package com.mathgeniusguide.project6

import android.os.Bundle
import android.support.v4.app.Fragment
import android.support.v4.content.ContextCompat
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.navigation.Navigation
import com.mathgeniusguide.project6.dao.MoodsDao
import com.mathgeniusguide.project6.database.AppDatabase
import com.mathgeniusguide.project6.entity.Moods
import com.mathgeniusguide.project6.utils.OnSwipeTouchListener
import io.reactivex.Observable
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers
import kotlinx.android.synthetic.main.message.*
import java.util.*

private const val ARG_PARAM1 = "param1"

class Message : Fragment() {
    private var param1: Int? = null
    private var db: AppDatabase? = null
    private var moodsDao: MoodsDao? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        arguments?.let {
            param1 = it.getInt(ARG_PARAM1)
        }
        db = AppDatabase.getAppDataBase(context = this.requireContext())
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

    fun setEmotionText() {
        // Set the text in "Why are you feeling ___ today?" and the background color based on the selected emotion.
        var emtn = when (param1) {
            1 -> "very sad"
            2 -> "sad"
            4 -> "happy"
            5 -> "very happy"
            else -> "neutral"
        }
        emotion.text = "Why are you feeling $emtn today?"
        var clr = when (param1) {
            1 -> R.color.verysad
            2 -> R.color.sad
            4 -> R.color.happy
            5 -> R.color.veryhappy
            else -> R.color.neutral
        }
        parent.setBackgroundColor(ContextCompat.getColor(context!!, clr))
    }

    fun setListeners() {
        // Go back to the previous screen.
        back.setOnClickListener {
            val action = MessageDirections.actionMessageToSelection(param1!!)
            Navigation.findNavController(it).navigate(action)
        }
        save.setOnClickListener {
            Observable.fromCallable({
                db = AppDatabase.getAppDataBase(context!!)
                moodsDao = db?.moodsDao()

                with(moodsDao) {
                    this?.insertMood(Moods(Date(), param1!!, note.text.toString()))
                }
            }).subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe()
            back.callOnClick()
        }
    }

    private fun setSwipeListener(view: View) {
        context?.let {
            view.setOnTouchListener(object : OnSwipeTouchListener(it) {
                override fun onSwipeTop() {
                    super.onSwipeTop()
                }

                override fun onSwipeBottom() {
                    super.onSwipeBottom()
                }

                override fun onSwipeLeft() {
                    super.onSwipeLeft()
                }

                override fun onSwipeRight() {
                    super.onSwipeRight()
                    // return to selection screen
                    val action = MessageDirections.actionMessageToSelection(param1!!)
                    Navigation.findNavController(view).navigate(action)
                }
            })
        }
    }

    companion object {
        @JvmStatic
        fun newInstance(param1: Int) =
            Message().apply {
                arguments = Bundle().apply {
                    putInt(ARG_PARAM1, param1)
                }
            }
    }
}