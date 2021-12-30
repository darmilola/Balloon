package com.useballoon.Ui

import android.content.Intent
import android.icu.text.CaseMap
import android.os.Bundle
import android.preference.PreferenceManager
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.cardview.widget.CardView
import com.useballoon.CreateAMission
import com.useballoon.R
import java.text.SimpleDateFormat
import java.time.LocalDate
import java.util.*


@Suppress("DEPRECATION")
class Dashboard : Fragment() {

    lateinit var createAMissionCard:CardView
    lateinit var title: TextView
    lateinit var mView: View;
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        mView =  inflater.inflate(R.layout.fragment_dashboard, container, false)
        initView()
        return mView;
    }

    private fun initView(){

        val preferences = PreferenceManager.getDefaultSharedPreferences(requireContext())
        val userFirstname = preferences.getString(getString(R.string.saved_user_firstname), "")
        createAMissionCard = mView.findViewById(R.id.dashboard_create_a_mission)
        title = mView.findViewById(R.id.dashboard_title)
        createAMissionCard.setOnClickListener {
            startActivity(Intent(requireContext(), CreateAMission::class.java))
        }

        val calendar: Calendar = Calendar.getInstance()
        val date: Date = calendar.getTime()
        val dayOfWeek = SimpleDateFormat("EEEE", Locale.ENGLISH).format(date.getTime())

        val titleBuilder = StringBuilder()
        titleBuilder.append("Hey ")
            .append(userFirstname)
            .append(", ")
            .append("we trust your ")
            .append(dayOfWeek)
            .append(" is going very well.")
        title.text = titleBuilder.toString()

    }


}