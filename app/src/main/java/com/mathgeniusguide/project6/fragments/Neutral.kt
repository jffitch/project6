package com.mathgeniusguide.project6

import android.os.Bundle
import android.os.CountDownTimer
import android.support.v4.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.navigation.Navigation
import com.mathgeniusguide.project6.utils.OnSwipeTouchListener
import kotlinx.android.synthetic.main.neutral.*

class Neutral : Fragment() {
    lateinit var timer: CountDownTimer
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        val view =  inflater.inflate(R.layout.neutral, container, false)
        return view
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setSwipeListener(view)
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

    private fun setSwipeListener(view: View) {
        context?.let {
            view.setOnTouchListener(object : OnSwipeTouchListener(it) {
                override fun onSwipeTop() {
                    super.onSwipeTop()
                    timer.cancel()
                    note.visibility = View.INVISIBLE
                    history.visibility = View.INVISIBLE
                    val action = NeutralDirections.actionNeutralToSad()
                    Navigation.findNavController(view).navigate(action)
                }
                override fun onSwipeBottom() {
                    super.onSwipeBottom()
                    timer.cancel()
                    note.visibility = View.INVISIBLE
                    history.visibility = View.INVISIBLE
                    val action = NeutralDirections.actionNeutralToHappy()
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

    companion object {
        @JvmStatic
        fun newInstance() =
            Neutral().apply {
                arguments = Bundle().apply {

                }
            }
    }
}