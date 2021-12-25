package com.useballoon

import android.app.AlertDialog
import android.content.DialogInterface
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
import com.useballoon.Retrofit.API
import com.useballoon.Utils.LottieLoadingDialog
import dagger.hilt.android.AndroidEntryPoint
import io.reactivex.disposables.CompositeDisposable
import retrofit2.Retrofit
import java.util.*
import javax.inject.Inject
import kotlin.math.sign

@AndroidEntryPoint
class Signup : AppCompatActivity() {
    @set:Inject
    lateinit var loadingDialog: LottieLoadingDialog
    private var signupViewModel: SignupViewModel? = null
    private var binding: ActivitySignupBinding? = null
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        initView()
    }

    private fun initView(){

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
                showAlert()
                Toast.makeText(this@Signup, "Signup Success", Toast.LENGTH_LONG).show()
            }
            else if (!signupUser.isPasswordMatch){
                loadingDialog!!.cancelLoadingDialog()
                Toast.makeText(this@Signup, "Password does not match", Toast.LENGTH_LONG).show()
            }
            else if(!signupUser.signupStatus){
                loadingDialog!!.cancelLoadingDialog()
                Toast.makeText(this@Signup, "Email already exist", Toast.LENGTH_LONG).show()
            }
            else if(signupUser.isError){
                Toast.makeText(this@Signup, "There seems to be an error creating your account", Toast.LENGTH_LONG).show()
            }
            else{
                Toast.makeText(this@Signup, "Error Occurred please try again", Toast.LENGTH_LONG).show()
            }

        })

    }

    private fun showAlert() {
        AlertDialog.Builder(this@Signup)
            .setTitle("Activate Account")
            .setMessage("Your Balloon account was successfully created. An activation link has now been sent to your email. Check your email to activate your account. The link expires in 10 minutes.") // Specifying a listener allows you to take an action before dismissing the dialog.
            .setPositiveButton("Okay", DialogInterface.OnClickListener { dialog, which ->
                dialog.dismiss()
            })
            .show()
    }
}