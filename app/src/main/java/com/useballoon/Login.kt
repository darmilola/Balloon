package com.useballoon

import android.app.AlertDialog
import android.content.DialogInterface
import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.preference.PreferenceManager
import android.text.TextUtils
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import com.useballoon.Models.ActivateUser
import com.useballoon.Models.LoginUser
import com.useballoon.Models.ResponseStatus
import com.useballoon.Retrofit.API
import com.useballoon.Utils.LottieLoadingDialog
import com.useballoon.databinding.ActivityLoginBinding
import com.useballoon.viewModels.LoginViewModel
import dagger.hilt.android.AndroidEntryPoint
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.functions.Consumer
import io.reactivex.schedulers.Schedulers
import retrofit2.Retrofit
import java.sql.Timestamp
import javax.inject.Inject


@AndroidEntryPoint
class Login : AppCompatActivity() {

    @set:Inject
    lateinit var loadingDialog: LottieLoadingDialog
    @set:Inject
    lateinit var retrofit: Retrofit
    @set:Inject
    lateinit var api: API
    @set:Inject
    lateinit var compositeDisposable: CompositeDisposable
    private var loginViewModel: LoginViewModel? = null
    private var binding: ActivityLoginBinding? = null



    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        initView()
    }

    private fun initView() {

        loginViewModel = ViewModelProviders.of(this).get(LoginViewModel::class.java)
        binding = DataBindingUtil.setContentView(this, R.layout.activity_login)

        binding!!.setLifecycleOwner(this)

        binding!!.loginViewModel = loginViewModel

        //loadingDialog = LottieLoadingDialog(this)

        val intent = intent
        if (intent?.getStringExtra("fromMain") == null) {
            val action = intent.action
            val data: Uri? = intent.data
            loadingDialog?.showLoadingDialog()

            val path: String? = data?.path
            val idStr = path?.substring(path.lastIndexOf('/') + 1)
            val id = idStr!!.toInt()

            val timestamp = Timestamp(System.currentTimeMillis())
            val activateUser = ActivateUser(id, 1, timestamp.toString())
            compositeDisposable!!.add(api!!.activate(activateUser)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(
                    Consumer { responseStatus: ResponseStatus? ->
                        HandleResults(
                            responseStatus!!
                        )
                    },
                    Consumer { t: Throwable? ->
                        handleError(
                            t!!
                        )
                    }))


        }

        loginViewModel!!.user.observe(this, Observer<LoginUser> { loginUser ->
                if (TextUtils.isEmpty(loginUser.email)) {
                    binding!!.loginEmail.error = "Required"
                    binding!!.loginEmail.requestFocus()
                } else if (TextUtils.isEmpty(loginUser.password)) {
                    binding!!.loginPassword.error = "Required"
                    binding!!.loginPassword.requestFocus()
                } else if (!loginUser.isNetworkAvailable) {
                    loadingDialog!!.cancelLoadingDialog()
                    Toast.makeText(this@Login, "Network not available", Toast.LENGTH_LONG).show()
                } else if (loginUser.isLoading) {
                    loadingDialog!!.showLoadingDialog()
                } else if (loginUser.isLoginStatus) {
                    loadingDialog!!.cancelLoadingDialog()
                    val preferences = PreferenceManager.getDefaultSharedPreferences(this)
                    val edit = preferences.edit()
                    edit.putString(getString(R.string.saved_user_email), loginUser.email)
                    edit.apply()

                    val intent = Intent(this@Login, Intro::class.java)
                    startActivity(intent)
                } else if (!loginUser.isLoginStatus) {
                    loadingDialog!!.cancelLoadingDialog()
                    Toast.makeText(this@Login, "Incorrect Email or Password", Toast.LENGTH_LONG)
                        .show()
                }
             else if (!loginUser.isError) {
                loadingDialog!!.cancelLoadingDialog()
                Toast.makeText(this@Login, "Error occurred please try again", Toast.LENGTH_LONG).show()
             } else {
                    Toast.makeText(this@Login, "Error Occurred please try again", Toast.LENGTH_LONG)
                        .show()
             }
            })



    }

    fun HandleResults(responseStatus: ResponseStatus) {
        loadingDialog!!.cancelLoadingDialog()
        if (responseStatus.status.equals("success", ignoreCase = true)) {
            showAlert()
        } else if (responseStatus.status.equals("failure", ignoreCase = true)) {

            Toast.makeText(this@Login, "Error Occurred please try again", Toast.LENGTH_LONG)
                .show()
        }
    }

    private fun handleError(t: Throwable) {
        Toast.makeText(this@Login, "Error Occurred please try again", Toast.LENGTH_LONG)
            .show()
    }

    private fun showAlert() {
        AlertDialog.Builder(this@Login)
            .setTitle("Activation Successful")
            .setMessage("Your Balloon account has been successfully activated, you can now login") // Specifying a listener allows you to take an action before dismissing the dialog.
            // The dialog is automatically dismissed when a dialog button is clicked.
            .setPositiveButton("Login", DialogInterface.OnClickListener { dialog, which ->
                dialog.dismiss()
            })
            .show()
    }

}