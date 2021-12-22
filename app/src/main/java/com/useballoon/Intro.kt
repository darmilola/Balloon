package com.useballoon


import android.content.Intent
import android.graphics.Color
import android.graphics.Typeface
import android.os.Bundle
import android.preference.PreferenceManager
import android.text.SpannableString
import android.text.Spanned
import android.text.style.ForegroundColorSpan
import android.text.style.StyleSpan
import android.view.View
import android.view.ViewTreeObserver.OnScrollChangedListener
import android.widget.ImageView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import com.bumptech.glide.Glide
import com.useballoon.Models.IntroResponse
import com.useballoon.Models.User
import com.useballoon.databinding.ActivityIntroBinding
import com.useballoon.viewModels.IntroViewModel
import kotlinx.coroutines.*
import okhttp3.MediaType
import okhttp3.OkHttpClient
import okhttp3.Request
import okhttp3.RequestBody
import org.json.JSONException
import org.json.JSONObject
import java.text.DateFormat
import java.text.SimpleDateFormat
import java.util.*


@Suppress("DEPRECATION")
class Intro : AppCompatActivity() {
    private var introViewModel: IntroViewModel? = null
    private var binding: ActivityIntroBinding? = null
    private var users: List<User> = ArrayList()
    private var userEmail:String = ""

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        initView()
    }

    fun initView(){

        introViewModel = ViewModelProviders.of(this).get(IntroViewModel::class.java)

        binding = DataBindingUtil.setContentView(this, R.layout.activity_intro)

        binding!!.setLifecycleOwner(this)

        binding!!.introViewModel = introViewModel

        introViewModel!!.loadData()


        introViewModel!!.intro.observe(this, Observer<IntroResponse> { introResponse ->

            if(introResponse.isLoading){
                binding!!.introProgress.visibility = View.VISIBLE
                binding!!.introRoot.visibility = View.GONE
            }
            else if (!introResponse.isNetworkAvailable){
                binding!!.introProgress.visibility = View.VISIBLE
                binding!!.introRoot.visibility = View.GONE
                Toast.makeText(this@Intro, "Network not available", Toast.LENGTH_LONG).show()
            }
            else if (introResponse.isError){
                Toast.makeText(this@Intro, "Error Occurred", Toast.LENGTH_LONG).show()
            }
            else if (introResponse.status.equals("success",true)){
                  users = introResponse.data
                  if(users[0].donereg == 0){
                      BuildWelcomeString(users[0].firstname)
                      binding!!.introProgress.visibility = View.GONE
                      binding!!.introRoot.visibility = View.VISIBLE
                      userEmail = users[0].email
                      val preferences = PreferenceManager.getDefaultSharedPreferences(this)
                      val edit = preferences.edit()
                      edit.putInt(getString(R.string.saved_user_id), users[0].userId)
                      edit.apply()
                  }
                else{
                      val intent = Intent(this@Intro, CreateAMission::class.java)
                      startActivity(intent)
                      finish()
                }
            }
            else if (introResponse.status.equals("failure",true)){
                Toast.makeText(this@Intro, "Error Occurred", Toast.LENGTH_LONG).show()
            }

        })

        binding!!.introCreateAMission.setOnClickListener {

            GlobalScope.launch{
                performCoroutineTaskUpdateDoneReg(userEmail)
            }

            val intent = Intent(this@Intro, CreateAMission::class.java)
            startActivity(intent)
        }

        val imageView = findViewById<ImageView>(R.id.welcome_waves)
        Glide.with(this).load(R.drawable.wavegif).into(imageView)

        binding!!.introScrollview.viewTreeObserver.addOnScrollChangedListener(OnScrollChangedListener {
            if (binding!!.introScrollview.canScrollVertically(1)){
                binding!!.introCreateAMission.visibility = View.VISIBLE
            }
        })

    }

    fun BuildWelcomeString(firstname:String){

        val welcomeBuilder = StringBuilder()
        welcomeBuilder.append("Welcome to Balloon, ")
                      .append(firstname)
        binding!!.introWelcomeToBalloon.text = welcomeBuilder.toString()

        val beforeContinueBuilder = StringBuilder()
        beforeContinueBuilder.append("But before you continue ")
                             .append(firstname)
                             .append(",")
                             .append(" here are a few key things to understand:")
        binding!!.introWelcomeText2.text = beforeContinueBuilder.toString()

        val welcome3 =
            SpannableString(getString(R.string.welcome_1))
        welcome3.setSpan(ForegroundColorSpan(Color.BLACK),0,22,Spanned.SPAN_EXCLUSIVE_EXCLUSIVE)
        welcome3.setSpan(StyleSpan(Typeface.BOLD),0,22,Spanned.SPAN_EXCLUSIVE_EXCLUSIVE)
        welcome3.setSpan(ForegroundColorSpan(Color.BLACK),45,54,Spanned.SPAN_EXCLUSIVE_EXCLUSIVE)
        welcome3.setSpan(StyleSpan(Typeface.BOLD),45,54,Spanned.SPAN_EXCLUSIVE_EXCLUSIVE)

        val welcome4 =
            SpannableString(getString(R.string.welcome_2))
        welcome4.setSpan(ForegroundColorSpan(Color.BLACK),0,25,Spanned.SPAN_EXCLUSIVE_EXCLUSIVE)
        welcome4.setSpan(StyleSpan(Typeface.BOLD),0,25,Spanned.SPAN_EXCLUSIVE_EXCLUSIVE)
        welcome4.setSpan(ForegroundColorSpan(Color.BLACK),54,67,Spanned.SPAN_EXCLUSIVE_EXCLUSIVE)
        welcome4.setSpan(StyleSpan(Typeface.BOLD),54,67,Spanned.SPAN_EXCLUSIVE_EXCLUSIVE)

        val dateFormat: DateFormat = SimpleDateFormat("h aa")
        val dateString: String = dateFormat.format(Date()).toString()

        val finalBuilder = StringBuilder()
        finalBuilder.append(getString(R.string.welcome_3_1))
            .append(" ")
            .append(firstname)
            .append(", ")
            .append(getString(R.string.welcome_3_2))
            .append(" ")
            .append(dateString)
            .append(", ")
            .append(getString(R.string.welcome_3_3))


        binding!!.introWelcomeText3.text = welcome3
        binding!!.introWelcomeText4.text = welcome4
        binding!!.introWelcomeText5.text = finalBuilder.toString()
    }


    fun updateDoneReg(email:String){
        val jsonObject = JSONObject()
        try {
            jsonObject.put("email", email)
            jsonObject.put("donereg", 1)
        } catch (e: JSONException) {
            e.printStackTrace()
        }
        val JSON = MediaType.get("application/json; charset=utf-8")
        val body: RequestBody = RequestBody.create(JSON, jsonObject.toString())
        val client = OkHttpClient()
        val request = Request.Builder()
            .url("https://glacial-spire-23584.herokuapp.com/public/api/users/update")
            .post(body)
            .build()
        val response = client.newCall(request).execute()
        val jsonDataString = response.body()?.string()
    }


    suspend fun performCoroutineTaskUpdateDoneReg(email: String)
    {
        updateDoneReg(email)
    }






}