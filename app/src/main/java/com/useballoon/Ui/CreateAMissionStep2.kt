package com.useballoon.Ui

import android.os.Bundle
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.useballoon.Adapter.AttachmentAdapter
import com.useballoon.Models.Attachments
import com.useballoon.R

import android.app.Dialog
import android.content.Context
import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.view.*
import android.widget.ImageView
import android.widget.LinearLayout
import android.widget.TextView
import com.google.android.material.button.MaterialButton


class CreateAMissionStep2 : Fragment(){
    // TODO: Rename and change types of parameters
    private lateinit var mView: View
    private lateinit var attachmentAdapter: AttachmentAdapter
    private lateinit var layoutManger: GridLayoutManager
    private var attachmentList: ArrayList<Attachments> = ArrayList()
    private lateinit var recyclerView: RecyclerView
    private lateinit var mDialog: Dialog
    private lateinit var instructions: TextView
    private lateinit var iUndestand: MaterialButton
    private lateinit var upload: MaterialButton
    private var cancelDialog: ImageView? = null
    private lateinit var step2SuccessListener: Step2SuccessListener
    private lateinit var next: LinearLayout

    interface Step2SuccessListener {
        fun onStep2Success()
    }

    override fun onAttach(context: Context) {
        super.onAttach(context)
        step2SuccessListener = context as Step2SuccessListener
    }




    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        mView =  inflater.inflate(R.layout.fragment_create_a_mission_step2, container, false)
        initView()
        return mView
    }

    private fun initView(){
        val  attachment1 = Attachments(1,"")
        val  attachment2 = Attachments(2,"")
        val  attachment5 = Attachments(5,"")
        attachmentList.add(attachment1)
        attachmentList.add(attachment2)
        attachmentList.add(attachment5)

        next = mView.findViewById(R.id.create_a_mission_step2_next)
        recyclerView = mView.findViewById(R.id.step2_attachments_recyclerview)
        instructions = mView.findViewById(R.id.create_mission_setp2_instructions);
        layoutManger = GridLayoutManager(requireContext(),2,RecyclerView.VERTICAL,false)
        attachmentAdapter = AttachmentAdapter(requireContext(),attachmentList)
        recyclerView.adapter = attachmentAdapter
        recyclerView.layoutManager = layoutManger

        instructions.setOnClickListener {
            launchInstructionsDialog()
        }

        next.setOnClickListener {
            step2SuccessListener.onStep2Success()
        }
    }

    private fun launchInstructionsDialog() {
        mDialog = Dialog(requireContext(), android.R.style.Theme_Dialog)
        mDialog.setContentView(R.layout.step2_example_dialog)
        iUndestand = mDialog.findViewById(R.id.create_mission_setp2_understand);
        mDialog.setCanceledOnTouchOutside(true)
        cancelDialog = mDialog!!.findViewById(R.id.cancel_dialog_icon);
        mDialog.window?.setBackgroundDrawable(ColorDrawable(Color.TRANSPARENT))
        mDialog.show()
        iUndestand.setOnClickListener {
            mDialog.dismiss()
        }
        cancelDialog!!.setOnClickListener {
            mDialog.dismiss()
        }

    }



}