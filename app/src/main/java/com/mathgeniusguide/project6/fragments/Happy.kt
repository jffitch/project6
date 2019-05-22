package com.mathgeniusguide.project6

import android.os.Bundle
import android.support.v4.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.navigation.Navigation
import com.mathgeniusguide.project6.utils.OnSwipeTouchListener

class Happy : Fragment() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        val view =  inflater.inflate(R.layout.happy, container, false)
        return view
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setSwipeListener(view)
    }

    private fun setSwipeListener(view: View) {
        context?.let {
            view.setOnTouchListener(object : OnSwipeTouchListener(it) {
                override fun onSwipeTop() {
                    super.onSwipeTop()
                    val action = HappyDirections.actionHappyToNeutral()
                    Navigation.findNavController(view).navigate(action)
                }
                override fun onSwipeBottom() {
                    super.onSwipeBottom()
                    val action = HappyDirections.actionHappyToVeryhappy()
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
            Happy().apply {
                arguments = Bundle().apply {

                }
            }
    }
}