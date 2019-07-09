package com.mathgeniusguide.project6.fragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.content.ContextCompat
import androidx.fragment.app.Fragment
import androidx.navigation.Navigation
import androidx.recyclerview.widget.LinearLayoutManager
import com.mathgeniusguide.project6.adapter.MoodAdapter
import com.mathgeniusguide.project6.dao.MoodsDao
import com.mathgeniusguide.project6.database.AppDatabase
import com.mathgeniusguide.project6.entity.Moods
import com.mathgeniusguide.project6.utils.OnSwipeTouchListener
import java.util.*
import com.mathgeniusguide.project6.R
import kotlinx.android.synthetic.main.piechart.*
import lecho.lib.hellocharts.model.PieChartData
import lecho.lib.hellocharts.model.SliceValue

private const val ARG_PARAM1 = "param1"

class PieChart : Fragment() {
    private var db: AppDatabase? = null
    private var moodsDao: MoodsDao? = null
    private var param1: Int? = null
    // distribution of occurrences of each of the five emotions, initialized to 0
    private var distribution = intArrayOf(0, 0, 0, 0, 0)

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        arguments?.let {
            param1 = it.getInt(ARG_PARAM1)
        }
        db = AppDatabase.getAppDataBase(context!!)
        activity?.setTitle(resources.getString(R.string.title_piechart))
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        val view = inflater.inflate(R.layout.piechart, container, false)
        return view
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        // get database of moods, then set distribution array to number of occurrences of each emotion
        moodsDao = db?.moodsDao()
        moodsDao?.getAllMoods()?.observe(viewLifecycleOwner, androidx.lifecycle.Observer {
            if (it != null) {
                for (i in 0..4) {
                    distribution[i] = it.filter {n -> n.emotion == i + 1 }.size
                }
            }
            setPie(distribution)
        })
        setSwipeListener(pie)
    }

    private fun setPie(distribution: IntArray) {
        // colors and strings are put in an array to be accessed when setting Pie Chart using for loop
        val colors = intArrayOf(R.color.verysad, R.color.sad, R.color.neutral, R.color.happy, R.color.veryhappy)
        val strings = intArrayOf(R.string.very_sad, R.string.sad, R.string.neutral, R.string.happy, R.string.very_happy)
        val pieData = ArrayList<SliceValue>()
        for (i in 0..4) {
            // set color and label according to number of occurrences of each emotion
            // no label if emotion has 0 occurrences
            if (distribution[i] != 0) {
                pieData.add(
                    SliceValue(
                        distribution[i].toFloat(),
                        ContextCompat.getColor(context!!, colors[i])
                    ).setLabel(resources.getString(strings[i]) + " (${distribution[i]})")
                )
            }
        }
        pie.pieChartData = PieChartData(pieData).setHasLabels(true)
    }

    private fun setSwipeListener(view: View) {
        context?.let {
            view.setOnTouchListener(object : OnSwipeTouchListener(it) {
                override fun onSwipeRight() {
                    super.onSwipeRight()
                    // return to selection screen
                    val action = PieChartDirections.actionPiechartToSelection(param1!!)
                    Navigation.findNavController(view).navigate(action)
                }
            })
        }
    }
}