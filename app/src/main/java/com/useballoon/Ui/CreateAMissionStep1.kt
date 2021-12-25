package com.useballoon.Ui

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.preference.PreferenceManager
import android.text.TextUtils
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.LinearLayout
import android.widget.Toast
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import com.google.android.material.button.MaterialButton
import com.useballoon.Intro
import com.useballoon.Models.LoginUser
import com.useballoon.Models.Mission
import com.useballoon.Retrofit.API
import com.useballoon.Utils.LottieLoadingDialog
import com.useballoon.databinding.ActivityLoginBinding
import com.useballoon.databinding.FragmentCreateAMissionStep1Binding
import com.useballoon.viewModels.LoginViewModel
import com.useballoon.viewModels.Step1ViewModel
import io.reactivex.disposables.CompositeDisposable
import android.R.attr.data

import android.R.attr
import com.useballoon.R
import dagger.hilt.android.AndroidEntryPoint


@Suppress("DEPRECATION")@AndroidEntryPoint
class CreateAMissionStep1 : Fragment() {

    private lateinit var mView: View
    private lateinit var step1SuccessListener: Step1SuccessListener
    private var step1ViewModel: Step1ViewModel? = null
    private var binding: FragmentCreateAMissionStep1Binding? = null
    private var loadingDialog: LottieLoadingDialog? = null
    private var api: API? = null
    var compositeDisposable = CompositeDisposable()

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
         binding = DataBindingUtil.inflate(
            inflater, R.layout.fragment_create_a_mission_step1, container, false
        )
        mView = binding!!.getRoot()

        initView()
        return mView
    }

    fun initView(){
        step1ViewModel = ViewModelProviders.of(this).get(Step1ViewModel::class.java)

        binding!!.setLifecycleOwner(this)

        binding!!.step1ViewModel = step1ViewModel

        loadingDialog = LottieLoadingDialog(requireContext())

        step1ViewModel!!.mission.observe(requireActivity(), Observer<Mission> { mission ->

            loadingDialog!!.cancelLoadingDialog()
            if (TextUtils.isEmpty(mission.subject)) {
                binding!!.createMissionStep1Subject.error = "Required"
                binding!!.createMissionStep1Subject.requestFocus()
            } else if (TextUtils.isEmpty(mission.musicOrVideoUrl)) {
                binding!!.createMissionStep1Url.error = "Required"
                binding!!.createMissionStep1Url.requestFocus()
            } else if (!mission.isNetworkAvailable) {
                Toast.makeText(requireContext(), "Network not available", Toast.LENGTH_LONG).show()
            } else if (mission.isLoading) {
                loadingDialog!!.showLoadingDialog()
            } else if (mission.isSaved) {
                step1SuccessListener.onStep1Success()

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



}