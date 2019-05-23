package com.mathgeniusguide.project6

import android.os.Bundle
import android.support.v4.app.Fragment
import android.support.v4.content.ContextCompat
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.navigation.Navigation
import kotlinx.android.synthetic.main.message.*

private const val ARG_PARAM1 = "param1"

class Message : Fragment() {
    private var param1: Int? = null

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
        val view =  inflater.inflate(R.layout.message, container, false)
        return view
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setEmotionText()
        setListeners()
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
            val action = when (param1) {
                1 -> MessageDirections.actionMessageToVerysad()
                2 -> MessageDirections.actionMessageToSad()
                4 -> MessageDirections.actionMessageToHappy()
                5 -> MessageDirections.actionMessageToVeryhappy()
                else -> MessageDirections.actionMessageToNeutral()
            }
            Navigation.findNavController(it).navigate(action)
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