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
import android.text.TextUtils
import android.view.*
import android.widget.ImageView
import android.widget.LinearLayout
import android.widget.TextView
import android.widget.Toast
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import com.google.android.material.button.MaterialButton
import com.useballoon.Models.Mission
import com.useballoon.Retrofit.API
import com.useballoon.Utils.LottieLoadingDialog
import com.useballoon.databinding.FragmentCreateAMissionStep1Binding
import com.useballoon.databinding.FragmentCreateAMissionStep2Binding
import com.useballoon.viewModels.Step1ViewModel
import com.useballoon.viewModels.Step2ViewModel
import io.reactivex.disposables.CompositeDisposable


@Suppress("DEPRECATION")
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
    private var step2ViewModel: Step2ViewModel? = null
    private var binding: FragmentCreateAMissionStep2Binding? = null
    private var loadingDialog: LottieLoadingDialog? = null
    private var api: API? = null
    var compositeDisposable = CompositeDisposable()

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
         binding = DataBindingUtil.inflate(
            inflater, R.layout.fragment_create_a_mission_step2, container, false
        )
        mView = binding!!.getRoot()

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


        step2ViewModel = ViewModelProviders.of(this).get(Step2ViewModel::class.java)

        binding!!.setLifecycleOwner(this)

        binding!!.step2ViewModel = step2ViewModel

        loadingDialog = LottieLoadingDialog(requireContext())

        step2ViewModel!!.mission.observe(requireActivity(), Observer<Mission> { mission ->

            loadingDialog!!.cancelLoadingDialog()
            if (TextUtils.isEmpty(mission.step1)) {
                binding!!.createAMissionStep2Step1.error = "Required"
                binding!!.createAMissionStep2Step1.requestFocus()
            } else if (TextUtils.isEmpty(mission.step2)) {
                binding!!.createAMissionStep2Step2.error = "Required"
                binding!!.createAMissionStep2Step2.requestFocus()
            }
            else if (TextUtils.isEmpty(mission.step3)) {
                binding!!.createAMissionStep2Step3.error = "Required"
                binding!!.createAMissionStep2Step3.requestFocus()
            }
            else if (!mission.isNetworkAvailable) {
                Toast.makeText(requireContext(), "Network not available", Toast.LENGTH_LONG).show()
            } else if (mission.isLoading) {
                loadingDialog!!.showLoadingDialog()
            } else if (mission.isSaved) {
                step2SuccessListener.onStep2Success()

            } else if (!mission.isSaved) {
                Toast.makeText(requireContext(), "Error Occurred", Toast.LENGTH_LONG)
                    .show()
            }
            else if (!mission.isError) {
                Toast.makeText(requireContext(), "Error occurred please try again", Toast.LENGTH_LONG).show()
            } else {
                Toast.makeText(requireContext(), "Error Occurred please try again", Toast.LENGTH_LONG)
                    .show()
            }
        })

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