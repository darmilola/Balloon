package com.useballoon.Ui

import android.app.Dialog
import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.EditText
import com.useballoon.R
import android.view.MotionEvent


import android.view.View.OnTouchListener
import android.widget.ImageView
import android.widget.TextView
import com.google.android.material.button.MaterialButton


class CreateAMissionStep3 : Fragment() {

    private var mView: View? = null;
    private var proof: EditText? = null;
    private var importantInfo: TextView? = null;
    private var executionSample: TextView? = null;
    private var executionDialog: Dialog? = null
    private var infoDialog: Dialog? = null
    private var executionUndestand: MaterialButton? = null
    private var cancelDialog: ImageView? = null


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        mView =  inflater.inflate(R.layout.fragment_create_a_mission_step3, container, false)
        initView()

        return mView
    }

    fun initView(){
        proof = mView!!.findViewById(R.id.step3_proof_of_execution)
        importantInfo = mView!!.findViewById(R.id.create_mission_step3_important_info)
        executionSample = mView!!.findViewById(R.id.create_mission_step3_execution_sample)

        proof!!.setOnTouchListener(OnTouchListener { view, event -> // TODO Auto-generated method stub
            view.parent.requestDisallowInterceptTouchEvent(true)
            when (event.action and MotionEvent.ACTION_MASK) {
                MotionEvent.ACTION_UP -> view.parent.requestDisallowInterceptTouchEvent(false)
            }
            false
        })

        executionSample!!.setOnClickListener {
            launchInstructionsDialog()
        }

        importantInfo!!.setOnClickListener {
            LaunchImportantInfo()
        }

    }

    private fun launchInstructionsDialog() {
        executionDialog = Dialog(requireContext(), android.R.style.Theme_Dialog)
        executionDialog!!.setContentView(R.layout.create_mission_step3_proof_of_mission)
        cancelDialog = executionDialog!!.findViewById(R.id.cancel_dialog_icon);
        executionUndestand = executionDialog!!.findViewById(R.id.create_mission_setp3_execution_understand);
        executionDialog!!.setCanceledOnTouchOutside(true)
        executionDialog!!.window?.setBackgroundDrawable(ColorDrawable(Color.TRANSPARENT))
        executionDialog!!.show()
        executionUndestand!!.setOnClickListener {
            executionDialog!!.dismiss()
        }
        cancelDialog!!.setOnClickListener {
            executionDialog!!.dismiss()
        }

    }

    private fun LaunchImportantInfo() {
        infoDialog = Dialog(requireContext(), android.R.style.Theme_Dialog)
        infoDialog!!.setContentView(R.layout.create_mission_step3_important_info)
        cancelDialog = infoDialog!!.findViewById(R.id.cancel_dialog_icon);
        infoDialog!!.setCanceledOnTouchOutside(true)
        infoDialog!!.window?.setBackgroundDrawable(ColorDrawable(Color.TRANSPARENT))
        infoDialog!!.show()

        cancelDialog!!.setOnClickListener {
            infoDialog!!.dismiss()
        }
    }

}