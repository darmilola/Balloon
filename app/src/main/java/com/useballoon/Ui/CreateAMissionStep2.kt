package com.useballoon.Ui

import android.app.Activity
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
import android.widget.*
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import com.google.android.material.button.MaterialButton
import com.useballoon.Adapter.AttachmentAdapter.FileUploadClickListener
import com.useballoon.Models.Mission
import com.useballoon.Utils.LottieLoadingDialog
import com.useballoon.databinding.FragmentCreateAMissionStep2Binding
import com.useballoon.viewModels.Step2ViewModel
import dagger.hilt.android.AndroidEntryPoint
import javax.inject.Inject
import android.content.Intent
import android.content.SharedPreferences
import android.preference.PreferenceManager

import br.com.onimur.handlepathoz.HandlePathOz
import br.com.onimur.handlepathoz.HandlePathOzListener
import br.com.onimur.handlepathoz.model.PathOz
import com.useballoon.Utils.FileUploadUtil
import java.io.File
import android.webkit.MimeTypeMap

import android.content.ContentResolver
import android.net.Uri
import androidx.lifecycle.lifecycleScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext


@Suppress("DEPRECATION")@AndroidEntryPoint
class CreateAMissionStep2 : Fragment(), HandlePathOzListener.SingleUri {
    private val REQUEST_SELECT_FILE = 0;
    private lateinit var mView: View
    private lateinit var attachmentAdapter: AttachmentAdapter
    private lateinit var layoutManger: GridLayoutManager
    private var attachmentList: ArrayList<Attachments> = ArrayList()
    private lateinit var recyclerView: RecyclerView
    private lateinit var mDialog: Dialog
    private lateinit var instructions: TextView
    lateinit var fileName: TextView
    private lateinit var iUndestand: MaterialButton
    private lateinit var upload: MaterialButton
    private lateinit var file: File
    private var cancelDialog: ImageView? = null
    private lateinit var step2SuccessListener: Step2SuccessListener
    private lateinit var next: LinearLayout
    private var step2ViewModel: Step2ViewModel? = null
    private var binding: FragmentCreateAMissionStep2Binding? = null
    @Inject
    lateinit var loadingDialog: LottieLoadingDialog
    private lateinit var handlePathOz: HandlePathOz
    private var fileType: String? = null

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

        var attachment = Attachments("select","",0,"",0)
        attachmentList.add(attachment)

        handlePathOz = HandlePathOz(requireContext(), this)
        next = mView.findViewById(R.id.create_a_mission_step2_next)
        recyclerView = mView.findViewById(R.id.step2_attachments_recyclerview)
        instructions = mView.findViewById(R.id.create_mission_setp2_instructions);
        layoutManger = GridLayoutManager(requireContext(),2,RecyclerView.VERTICAL,false)
        attachmentAdapter = AttachmentAdapter(requireContext(),attachmentList)

        attachmentAdapter.fileUploadClickListener = object : FileUploadClickListener {
            override fun onFileUploadClicked() {
                launchUploadDialog()
            }
        }

        recyclerView.adapter = attachmentAdapter
        recyclerView.layoutManager = layoutManger

        instructions.setOnClickListener {
            //launchInstructionsDialog()
            val intent = Intent()
            intent.type = "application/pdf"
            intent.action = Intent.ACTION_GET_CONTENT
            startActivityForResult(Intent.createChooser(intent, "Select File"), REQUEST_SELECT_FILE)
        }


        step2ViewModel = ViewModelProviders.of(this).get(Step2ViewModel::class.java)

        binding!!.setLifecycleOwner(this)

        binding!!.step2ViewModel = step2ViewModel


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

    private fun launchUploadDialog() {
        // mDialog.requestWindowFeature(Window.FEATURE_NO_TITLE)
        var chooseFile: MaterialButton
        var shortTitle: EditText
        lateinit var mDialog: Dialog
        lateinit var upload: MaterialButton
        lateinit var cancelDialog: ImageView
        mDialog = Dialog(requireContext(), android.R.style.Theme_Dialog)
        mDialog.setContentView(R.layout.create_mission_step2_upload)
        upload = mDialog.findViewById(R.id.create_mission_setp2_upload);
        cancelDialog = mDialog.findViewById(R.id.cancel_dialog_icon)
        chooseFile = mDialog.findViewById(R.id.mission_choose_file)
        shortTitle = mDialog.findViewById(R.id.mission_file_shorttitle)
        fileName = mDialog.findViewById(R.id.mission_file_chosen_name)
        mDialog.setCanceledOnTouchOutside(true)
        mDialog.window?.setBackgroundDrawable(ColorDrawable(Color.TRANSPARENT))
        mDialog.show()

        upload.setOnClickListener {

            if(shortTitle.text.contentEquals("",true)){
                shortTitle.error = "Required"
            }
            else {

                mDialog.dismiss()
                val preferences = PreferenceManager.getDefaultSharedPreferences(requireContext())
                val mUserId = preferences.getInt(getString(R.string.saved_user_id), 0)
                val mMissionId = preferences.getString(getString(R.string.saved_mission_id), "")
                var fileUploadUtil = FileUploadUtil(requireContext())



                     fileUploadUtil.uploadFile(
                        file,
                        shortTitle.text.toString(),
                        fileType,
                        mMissionId,
                        mUserId
                    )
                fileUploadUtil.setUploadListener {
                    attachmentAdapter.addItem(it)
                }

            }



        }


        chooseFile.setOnClickListener {
            val intent = Intent()
           // val mimeTypes = arrayOf( "application/pdf","image/","audio/","video/")
            intent.type = "*/*"
           // intent.putExtra(Intent.EXTRA_MIME_TYPES, mimeTypes)
            intent.action = Intent.ACTION_GET_CONTENT
            startActivityForResult(Intent.createChooser(intent, "Select File"), REQUEST_SELECT_FILE)
        }

        cancelDialog.setOnClickListener {
            mDialog.dismiss()

        }
    }


    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if ((requestCode == REQUEST_SELECT_FILE) and (resultCode == Activity.RESULT_OK)) {
            data?.data?.also { it ->
                //set uri to handle
                handlePathOz.getRealPath(it)
                fileType = getMimeType(it)
                //show Progress Loading
            }
        }
    }

    override fun onRequestHandlePathOz(pathOz: PathOz, tr: Throwable?) {

        //Now you can work with real path:
        Toast.makeText(requireContext(), "The real path is: ${pathOz.path} \n The type is: ${pathOz.type}", Toast.LENGTH_SHORT).show()
         file = File(pathOz.path)
         fileName.text = file.name
        //Handle any Exception (Optional)
        tr?.let {
          //  Toast.makeText(this, "${it.message}", Toast.LENGTH_SHORT).show()
        }
    }

    fun getMimeType(uri: Uri): String? {
        var mimeType: String? = null
        mimeType = if (ContentResolver.SCHEME_CONTENT == uri.getScheme()) {
            val cr: ContentResolver = requireContext().getContentResolver()
            cr.getType(uri)
        } else {
            val fileExtension = MimeTypeMap.getFileExtensionFromUrl(
                uri
                    .toString()
            )
            MimeTypeMap.getSingleton().getMimeTypeFromExtension(
                fileExtension.toLowerCase()
            )
        }
        return mimeType
    }

    override fun onDestroy() {
        handlePathOz.onDestroy()
        super.onDestroy()
    }

}