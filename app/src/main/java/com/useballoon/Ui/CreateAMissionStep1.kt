package com.useballoon.Ui

import android.content.Context
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.LinearLayout
import com.google.android.material.button.MaterialButton
import com.useballoon.R

// TODO: Rename parameter arguments, choose names that match
// the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
private const val ARG_PARAM1 = "param1"
private const val ARG_PARAM2 = "param2"

/**
 * A simple [Fragment] subclass.
 * Use the [CreateAMissionStep1.newInstance] factory method to
 * create an instance of this fragment.
 */
class CreateAMissionStep1 : Fragment() {

    private lateinit var next: LinearLayout
    private lateinit var mView: View
    private lateinit var step1SuccessListener: Step1SuccessListener

    interface Step1SuccessListener {
        fun onStep1Success()
    }

    override fun onAttach(context: Context) {
        super.onAttach(context)
        step1SuccessListener = context as Step1SuccessListener
    }


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        mView =  inflater.inflate(R.layout.fragment_create_a_mission_step1, container, false)
        initView()
        return mView
    }

    fun initView(){
        next = mView.findViewById(R.id.create_a_mission_step1_next)
        next.setOnClickListener { step1SuccessListener.onStep1Success() }
    }



}