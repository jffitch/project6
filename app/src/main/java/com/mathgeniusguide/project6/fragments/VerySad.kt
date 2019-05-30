package com.mathgeniusguide.project6

import android.os.Bundle
import android.os.CountDownTimer
import android.support.v4.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.navigation.Navigation
import com.mathgeniusguide.project6.utils.OnSwipeTouchListener
import kotlinx.android.synthetic.main.verysad.*

class VerySad : Fragment() {
    lateinit var timer: CountDownTimer
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        val view =  inflater.inflate(R.layout.verysad, container, false)
        return view
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setSwipeListener(view)
        iconCountdownTimer()
        setListeners()
    }

    private fun setSwipeListener(view: View) {
        context?.let {
            view.setOnTouchListener(object : OnSwipeTouchListener(it) {
                override fun onSwipeTop() {
                    super.onSwipeTop()
                }
                override fun onSwipeBottom() {
                    super.onSwipeBottom()
                    // move to happier emotion and cancel countdown timer
                    cancelTimer()
                    val action = VerySadDirections.actionVerysadToSad()
                    Navigation.findNavController(view).navigate(action)
                }
                override fun onSwipeLeft() {
                    super.onSwipeLeft()
                }
                override fun onSwipeRight() {
                    super.onSwipeRight()
                }
            })
        }
    }

    fun iconCountdownTimer() {
        timer = object: CountDownTimer(1000, 1000) {
            override fun onTick(millisUntilFinished: Long) {

            }
            override fun onFinish() {
                note.visibility = View.VISIBLE
                history.visibility = View.VISIBLE
            }
        }
        timer.start()
    }

    fun cancelTimer() {
        timer.cancel()
        note.visibility = View.INVISIBLE
        history.visibility = View.INVISIBLE
    }

    fun setListeners() {
        note.setOnClickListener {
            // move to message screen and cancel countdown timer
            cancelTimer()
            val action = VerySadDirections.actionVerysadToMessage(1)
            Navigation.findNavController(it).navigate(action)
        }
        history.setOnClickListener {
            // move to message screen and cancel countdown timer
            cancelTimer()
            val action = VerySadDirections.actionVerysadToHistory()
            Navigation.findNavController(it).navigate(action)
        }
    }

    companion object {
        @JvmStatic
        fun newInstance() =
            VerySad().apply {
                arguments = Bundle().apply {

                }
            }
    }
}