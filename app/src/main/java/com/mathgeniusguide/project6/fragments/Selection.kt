package com.mathgeniusguide.project6

import android.os.Bundle
import android.os.CountDownTimer
import android.support.v4.app.Fragment
import android.support.v4.content.ContextCompat
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.Toast
import androidx.navigation.Navigation
import com.mathgeniusguide.project6.utils.OnSwipeTouchListener
import kotlinx.android.synthetic.main.selection.*

private const val ARG_PARAM1 = "param1"

class Selection : Fragment() {
    private var param1: Int = 5
    lateinit var timer: CountDownTimer

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        arguments?.let {
            param1 = it.getInt(ARG_PARAM1)
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        val view = inflater.inflate(R.layout.selection, container, false)
        return view
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setDisplay()
        setSwipeListener(view)
        iconCountdownTimer()
        setListeners()
    }

    private fun setSwipeListener(view: View) {
        context?.let {
            view.setOnTouchListener(object : OnSwipeTouchListener(it) {
                override fun onSwipeTop() {
                    super.onSwipeTop()
                    // if not very sad, move to sadder emotion and cancel countdown timer
                    if (param1 > 1) {
                        cancelTimer()
                        val action = SelectionDirections.actionSelectionSadder(param1 - 1)
                        Navigation.findNavController(view).navigate(action)
                    }
                }

                override fun onSwipeBottom() {
                    super.onSwipeBottom()
                    // if not very happy, move to happier emotion and cancel countdown timer
                    if (param1 < 5) {
                        cancelTimer()
                        val action = SelectionDirections.actionSelectionHappier(param1 + 1)
                        Navigation.findNavController(view).navigate(action)
                    }
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
        timer = object : CountDownTimer(1000, 1000) {
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
            val action = SelectionDirections.actionSelectionToMessage(param1)
            Navigation.findNavController(it).navigate(action)
        }
        history.setOnClickListener {
            // move to message screen and cancel countdown timer
            cancelTimer()
            val action = SelectionDirections.actionSelectionToHistory(param1)
            Navigation.findNavController(it).navigate(action)
        }
    }

    fun setDisplay() {
        var clr = when (param1) {
            1 -> R.color.verysad
            2 -> R.color.sad
            4 -> R.color.happy
            5 -> R.color.veryhappy
            else -> R.color.neutral
        }
        parent.setBackgroundColor(ContextCompat.getColor(context!!, clr))
        var img = when (param1) {
            1 -> R.drawable.smiley_verysad
            2 -> R.drawable.smiley_sad
            4 -> R.drawable.smiley_happy
            5 -> R.drawable.smiley_veryhappy
            else -> R.drawable.smiley_neutral
        }
        smiley.setImageResource(img)
    }

    companion object {
        @JvmStatic
        fun newInstance(param1: Int) =
            Selection().apply {
                arguments = Bundle().apply {
                    putInt(ARG_PARAM1, param1)
                }
            }
    }
}