package com.useballoon

import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.useballoon.viewModels.SignupViewModel
import androidx.databinding.DataBindingUtil;
import androidx.lifecycle.Observer;
import com.useballoon.databinding.ActivitySignupBinding


import androidx.lifecycle.ViewModelProviders
import android.text.TextUtils
import android.widget.Toast
import com.useballoon.Models.SignupUser
import com.useballoon.Utils.LottieLoadingDialog
import java.util.*
import kotlin.math.sign


class Signup : AppCompatActivity() {
    private var signupViewModel: SignupViewModel? = null
    private var binding: ActivitySignupBinding? = null
    private var loadingDialog: LottieLoadingDialog? = null
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        initView()
    }

    private fun initView(){

        loadingDialog = LottieLoadingDialog(this)
        signupViewModel = ViewModelProviders.of(this).get(SignupViewModel::class.java)

        binding = DataBindingUtil.setContentView(this, R.layout.activity_signup)

        binding!!.setLifecycleOwner(this)

        binding!!.signupViewModel = signupViewModel


        signupViewModel!!.user.observe(this, Observer<SignupUser> { signupUser ->
            if (TextUtils.isEmpty(signupUser.firstname)) {
                binding!!.signupFirstname.error = "Required"
                binding!!.signupFirstname.requestFocus()
            }

            else if (TextUtils.isEmpty(signupUser.lastname)) {
                binding!!.signupLastname.error = "Required"
                binding!!.signupLastname.requestFocus()
            }

            else if (TextUtils.isEmpty(signupUser.email)) {
                binding!!.signupEmail.error = "Required"
                binding!!.signupEmail.requestFocus()
            }
            else if (TextUtils.isEmpty(signupUser.password)) {
                binding!!.signupPassword.error = "Required"
                binding!!.signupPassword.requestFocus()
            }
            else if (TextUtils.isEmpty(signupUser.confirmPassword)) {
                binding!!.signupPasswordConfirm.error = "Required"
                binding!!.signupPasswordConfirm.requestFocus()
            }
            else if(!signupUser.isNetworkAvailable){
                loadingDialog!!.cancelLoadingDialog()
                Toast.makeText(this@Signup, "Network not available", Toast.LENGTH_LONG).show()
            }
            else if (signupUser.isLoading) {
                loadingDialog!!.showLoadingDialog()
            }
            else if(signupUser.signupStatus){
                loadingDialog!!.cancelLoadingDialog()
                startActivity(Intent(this@Signup,MainActivity2::class.java))
            }
            else if (!signupUser.isPasswordMatch){
                loadingDialog!!.cancelLoadingDialog()
                Toast.makeText(this@Signup, "Password does not match", Toast.LENGTH_LONG).show()
            }
            else if(!signupUser.signupStatus){
                loadingDialog!!.cancelLoadingDialog()
                Toast.makeText(this@Signup, "Email already exist", Toast.LENGTH_LONG).show()
            }
            else{
                Toast.makeText(this@Signup, "Error Occurred please try again", Toast.LENGTH_LONG).show()
            }

        })



    }
}