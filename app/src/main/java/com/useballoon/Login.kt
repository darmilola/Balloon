package com.useballoon

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.text.TextUtils
import android.widget.Toast
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import com.useballoon.Models.LoginUser
import com.useballoon.Models.SignupUser
import com.useballoon.Utils.LottieLoadingDialog
import com.useballoon.databinding.ActivityLoginBinding
import com.useballoon.databinding.ActivitySignupBinding
import com.useballoon.viewModels.LoginViewModel
import com.useballoon.viewModels.SignupViewModel


class Login : AppCompatActivity() {

    private var loginViewModel: LoginViewModel? = null
    private var binding: ActivityLoginBinding? = null
    private var loadingDialog: LottieLoadingDialog? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        initView()
    }

    private fun initView(){

        loadingDialog = LottieLoadingDialog(this)
        loginViewModel = ViewModelProviders.of(this).get(LoginViewModel::class.java)

        binding = DataBindingUtil.setContentView(this, R.layout.activity_login)

        binding!!.setLifecycleOwner(this)

        binding!!.loginViewModel = loginViewModel


        loginViewModel!!.user.observe(this, Observer<LoginUser> { loginUser ->
            if (TextUtils.isEmpty(loginUser.email)) {
                binding!!.loginEmail.error = "Required"
                binding!!.loginEmail.requestFocus()
            }
            if (TextUtils.isEmpty(loginUser.password)) {
                binding!!.loginPassword.error = "Required"
                binding!!.loginPassword.requestFocus()
            }

            else if (loginUser.isLoading) {
                loadingDialog!!.showLoadingDialog()
            }
            else if(loginUser.isLoginStatus){
                loadingDialog!!.cancelLoadingDialog()
                startActivity(Intent(this@Login,MainActivity2::class.java))
            }
            else if(!loginUser.isLoginStatus){
                loadingDialog!!.cancelLoadingDialog()
                Toast.makeText(this@Login, "Incorrect Email or Password", Toast.LENGTH_LONG).show()
            }
        })



    }
}