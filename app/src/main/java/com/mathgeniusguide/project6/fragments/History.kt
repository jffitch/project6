package com.mathgeniusguide.project6

import android.os.Bundle
import android.support.v4.app.Fragment
import android.support.v4.content.ContextCompat
import android.support.v7.widget.LinearLayoutManager
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.navigation.Navigation
import com.mathgeniusguide.project6.adapter.MoodAdapter
import com.mathgeniusguide.project6.dao.MoodsDao
import com.mathgeniusguide.project6.database.AppDatabase
import com.mathgeniusguide.project6.entity.Moods
import com.mathgeniusguide.project6.utils.OnSwipeTouchListener
import io.reactivex.Observable
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers
import kotlinx.android.synthetic.main.history.*
import java.util.*

private const val ARG_PARAM1 = "param1"

class History : Fragment() {
    private var db: AppDatabase? = null
    private var moodsDao: MoodsDao? = null
    private var param1: Int? = null
    val moodList : ArrayList<Moods> = ArrayList()

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
        val view = inflater.inflate(R.layout.history, container, false)
        return view
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        db?.moodsDao()?.getRecentMoods(7)?.observe(viewLifecycleOwner, android.arch.lifecycle.Observer {
            if(it != null) {
                // recycler view
                for (i in it) {
                    moodList.add(i)
                }
                rv.layoutManager = LinearLayoutManager(context)
                rv.adapter = MoodAdapter(moodList, context!!)
            }
        })
        setSwipeListener(view)
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
                    val action = HistoryDirections.actionHistoryToSelection(param1!!)
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