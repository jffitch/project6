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
import io.reactivex.Observable
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers
import kotlinx.android.synthetic.main.history.*
import java.util.*

class History : Fragment() {
    private var db: AppDatabase? = null
    private var moodsDao: MoodsDao? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
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
                var text = ""
                for(i in it) {
                    text += "Feeling ${i.emotion} at ${i.time} because ${i.note}.\n"
                }
                historyText.text = text
            }
        })
    }

    companion object {
        @JvmStatic
        fun newInstance(param1: Int) =
            Message().apply {
                arguments = Bundle().apply {

                }
            }
    }
}