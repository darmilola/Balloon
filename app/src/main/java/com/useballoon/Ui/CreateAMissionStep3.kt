package com.useballoon.Ui

import android.app.AlertDialog
import android.app.Dialog
import android.content.DialogInterface
import android.content.Intent
import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.os.Bundle
import android.text.SpannableString
import android.text.Spanned
import android.text.TextUtils
import android.text.style.ForegroundColorSpan
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.EditText
import android.view.MotionEvent


import android.view.View.OnTouchListener
import android.widget.ImageView
import android.widget.TextView
import android.widget.Toast
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import com.google.android.material.button.MaterialButton
import com.useballoon.MissionCreationSuccess
import com.useballoon.Models.Mission
import com.useballoon.R
import com.useballoon.Retrofit.API
import com.useballoon.Signup
import com.useballoon.Utils.LottieLoadingDialog
import com.useballoon.databinding.FragmentCreateAMissionStep2Binding
import com.useballoon.databinding.FragmentCreateAMissionStep3Binding
import com.useballoon.viewModels.Step2ViewModel
import com.useballoon.viewModels.Step3ViewModel
import dagger.hilt.android.AndroidEntryPoint
import io.reactivex.disposables.CompositeDisposable
import javax.inject.Inject

@Suppress("DEPRECATION")@AndroidEntryPoint
class CreateAMissionStep3 : Fragment() {

    private var mView: View? = null;
    private var proof: EditText? = null;
    private var importantInfo: TextView? = null;
    private var executionSample: TextView? = null;
    private var executionDialog: Dialog? = null
    private var infoDialog: Dialog? = null
    private var executionUndestand: MaterialButton? = null
    private var cancelDialog: ImageView? = null
    private var step3ViewModel: Step3ViewModel? = null
    private var binding: FragmentCreateAMissionStep3Binding? = null
    @Inject
    lateinit var loadingDialog: LottieLoadingDialog
    private var api: API? = null
    var compositeDisposable = CompositeDisposable()


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment

        binding = DataBindingUtil.inflate(
            inflater, R.layout.fragment_create_a_mission_step3, container, false
        )
        mView = binding!!.getRoot()

        initView()

        return mView
    }

    fun initView(){

        step3ViewModel = ViewModelProviders.of(this).get(Step3ViewModel::class.java)

        binding!!.setLifecycleOwner(this)

        binding!!.step3ViewModel = step3ViewModel


        binding!!.createAMissionStep3EnvangelistProof.setOnTouchListener(OnTouchListener { view, event ->
            view.parent.requestDisallowInterceptTouchEvent(true)
            when (event.action and MotionEvent.ACTION_MASK) {
                MotionEvent.ACTION_UP -> view.parent.requestDisallowInterceptTouchEvent(false)
            }
            false
        })


        step3ViewModel!!.mission.observe(requireActivity(), Observer<Mission> { mission ->

            loadingDialog!!.cancelLoadingDialog()
            if (TextUtils.isEmpty(mission.compensationFee)) {
                binding!!.createAMissionStep3EnvangelistCompensation.error = "Required"
                binding!!.createAMissionStep3EnvangelistCompensation.requestFocus()
            }
            else if (mission.compensationFee.toInt() < 2000) {
                Toast.makeText(requireContext(), "Compensation fee too low minimum 2000", Toast.LENGTH_LONG).show()
            }
            else if (mission.envangelistCount == null || mission.envangelistCount.toInt() == 0) {
                Toast.makeText(requireContext(), "Envangelists too low", Toast.LENGTH_LONG).show()
            }
            else if (TextUtils.isEmpty(mission.proofOfExecution)) {
                binding!!.createAMissionStep3EnvangelistProof.error = "Required"
                binding!!.createAMissionStep3EnvangelistProof.requestFocus()
            }
            else if (!mission.isNetworkAvailable) {
                Toast.makeText(requireContext(), "Network not available", Toast.LENGTH_LONG).show()
            } else if (mission.isLoading) {
                loadingDialog!!.showLoadingDialog()
            } else if (mission.isSaved) {
                   startActivity(Intent(requireContext(), MissionCreationSuccess::class.java))
                   requireActivity().finish()
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


        binding!!.createMissionStep3ExecutionSample.setOnClickListener {
            launchInstructionsDialog()
        }

        binding!!.createMissionStep3ImportantInfo.setOnClickListener {
            LaunchImportantInfo()
        }


        binding!!.step3EnvangelistIncrease.setOnClickListener {
            var currentCountText = binding!!.createAMissionStep3EnvangelistCount.text
            if(currentCountText.contentEquals("",true)){
                currentCountText = "0"
            }
            var currentCount = currentCountText.toString().toInt()
            var increment = currentCount + 1
            var incrementedText = increment.toString()
            binding!!.createAMissionStep3EnvangelistCount.text = incrementedText

            val hiringBuilder = StringBuilder()
            hiringBuilder.append("You are hiring ")
                .append(incrementedText)
                .append(" Evangelist(s)")
            binding!!.step3EnvangelistHiringText.text = hiringBuilder.toString()
        }



        binding!!.step3EnvangelistDecrease.setOnClickListener {
            var currentCountText = binding!!.createAMissionStep3EnvangelistCount.text
            if(currentCountText.contentEquals("",true)){
                currentCountText = "0"
            }
            var currentCount = currentCountText.toString().toInt()
            if(currentCount == 0){

            }
            else {
                var decrement = currentCount - 1
                var decrementedText = decrement.toString()
                binding!!.createAMissionStep3EnvangelistCount.text = decrementedText
                val hiringBuilder = StringBuilder()
                hiringBuilder.append("You are hiring ")
                    .append(decrementedText)
                    .append(" Evangelist(s)")
                binding!!.step3EnvangelistHiringText.text = hiringBuilder.toString()
            }
        }

        val recommendation =
            SpannableString(getString(R.string.recommendation))
        recommendation.setSpan(ForegroundColorSpan(Color.parseColor("#ff6633")),0,15, Spanned.SPAN_EXCLUSIVE_EXCLUSIVE)
        binding!!.step3Recommendation.text = recommendation


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

    private fun showAlert() {
        AlertDialog.Builder(requireContext())
            .setTitle("Mission Created")
            .setMessage("Your balloon mission has been created successfully, wait while it is approved") // Specifying a listener allows you to take an action before dismissing the dialog.
            .setPositiveButton("Exit", DialogInterface.OnClickListener { dialog, which ->
                dialog.dismiss()
            })
            .show()
    }

}