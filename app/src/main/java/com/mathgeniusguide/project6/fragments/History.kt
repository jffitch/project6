package com.mathgeniusguide.project6.fragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.navigation.Navigation
import androidx.recyclerview.widget.LinearLayoutManager
import com.mathgeniusguide.project6.adapter.MoodsAdapter
import com.mathgeniusguide.project6.dao.MoodsDao
import com.mathgeniusguide.project6.database.MoodsDatabase
import com.mathgeniusguide.project6.entity.Moods
import com.mathgeniusguide.project6.utils.OnSwipeTouchListener
import kotlinx.android.synthetic.main.history.*
import java.util.*
import com.mathgeniusguide.project6.R

private const val ARG_PARAM1 = "param1"

class History : Fragment() {
    private var db: MoodsDatabase? = null
    private var moodsDao: MoodsDao? = null
    private var param1: Int? = null
    private val moodList : ArrayList<Moods> = ArrayList()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        arguments?.let {
            param1 = it.getInt(ARG_PARAM1)
        }
        db = MoodsDatabase.getDataBase(context!!)
        activity?.setTitle(resources.getString(R.string.title_history))
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        val view = inflater.inflate(R.layout.history, container, false)
        return view
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        moodsDao = db?.moodsDao()
        moodsDao?.getRecentMoods(7)?.observe(viewLifecycleOwner, androidx.lifecycle.Observer {
            if(it != null) {
                // Recycler View
                // add each line from database to array list, then set up layout manager and adapter
                moodList.addAll(it)
                rv.layoutManager = LinearLayoutManager(context)
                rv.adapter = MoodsAdapter(moodList, context!!)
            }
            setSwipeListener(rv)
        })
    }

    private fun setSwipeListener(view: View) {
        context?.let {
            view.setOnTouchListener(object : OnSwipeTouchListener(it) {
                override fun onSwipeRight() {
                    super.onSwipeRight()
                    // return to selection screen
                    val action = HistoryDirections.actionHistoryToSelection(param1!!)
                    Navigation.findNavController(view).navigate(action)
                }
            })
        }
    }
}