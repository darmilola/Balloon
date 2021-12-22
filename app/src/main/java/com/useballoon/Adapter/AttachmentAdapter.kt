package com.useballoon.Adapter

import android.app.Dialog
import android.content.Context
import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import android.view.View

import com.google.android.material.chip.Chip

import android.widget.TextView


import android.view.LayoutInflater
import android.view.Window
import android.widget.ImageView
import androidx.core.content.ContentProviderCompat.requireContext
import com.google.android.material.button.MaterialButton
import com.useballoon.Models.Attachments
import com.useballoon.R


class AttachmentAdapter(context: Context, attachmentList: ArrayList<Attachments>): RecyclerView.Adapter<RecyclerView.ViewHolder>() {
    private val TYPE_IMAGE = 1
    private val TYPE_MUSIC = 2
    private val TYPE_PDF = 3
    private val TYPE_VIDEO = 4
    private val TYPE_SELECT = 5
    private var context:Context? = null
    private var attachmentList: ArrayList<Attachments> = ArrayList()

    init {
        this.attachmentList = attachmentList
        this.context = context
    }


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        if (viewType === TYPE_IMAGE) {
            val view: View = LayoutInflater.from(parent.context)
                .inflate(R.layout.upload_item_type_image, parent, false)
            return TypeImageViewholder(view)
        }
        if (viewType === TYPE_VIDEO) {
            val view: View = LayoutInflater.from(parent.context)
                .inflate(R.layout.upload_item_type_video, parent, false)
            return TypeImageViewholder(view)
        }
        if (viewType === TYPE_MUSIC) {
            val view: View = LayoutInflater.from(parent.context)
                .inflate(R.layout.upload_item_type_music, parent, false)
            return TypeMusicViewholder(view)
        }
        if (viewType === TYPE_PDF) {
            val view: View = LayoutInflater.from(parent.context)
                .inflate(R.layout.upload_item_type_pdf, parent, false)
            return TypePDFViewholder(view)
        }
        if (viewType === TYPE_SELECT) {
            val view: View = LayoutInflater.from(parent.context)
                .inflate(R.layout.upload_item_type_select, parent, false)
            return TypeSelectViewholder(view)
        }
        else{
            val view: View = LayoutInflater.from(parent.context)
                .inflate(R.layout.upload_item_type_select, parent, false)
            return TypeImageViewholder(view)
        }

    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {

    }

    override fun getItemCount(): Int {
        return attachmentList.size
    }


    override fun getItemViewType(position: Int): Int {
        return if (attachmentList[position].type === TYPE_IMAGE) {
            TYPE_IMAGE
        } else if (attachmentList[position].type === TYPE_MUSIC) {
            TYPE_MUSIC
        }
        else if (attachmentList[position].type === TYPE_PDF) {
            TYPE_PDF
        }
        else if (attachmentList[position].type === TYPE_VIDEO) {
            TYPE_VIDEO
        } else if (attachmentList[position].type === TYPE_SELECT) {
            TYPE_SELECT
        } else {
            TYPE_IMAGE
        }
    }

    class TypeImageViewholder(ItemView: View) : RecyclerView.ViewHolder(ItemView) {
        //var imageView: ImageView
        //var city: TextView
        //var nameAge: TextView
        //var occupation: Chip

        init {
           // imageView = ItemView.findViewById(R.id.type_main_image)
           // city = ItemView.findViewById(R.id.type_main_city)
           // nameAge = ItemView.findViewById(R.id.type_main_name_age)
           // occupation = ItemView.findViewById(R.id.type_main_occupation)
        }
    }

    class TypeVideoViewholder(ItemView: View) : RecyclerView.ViewHolder(ItemView) {
        //var imageView: ImageView
        //var city: TextView
        //var nameAge: TextView
        //var occupation: Chip

        init {
            // imageView = ItemView.findViewById(R.id.type_main_image)
            // city = ItemView.findViewById(R.id.type_main_city)
            // nameAge = ItemView.findViewById(R.id.type_main_name_age)
            // occupation = ItemView.findViewById(R.id.type_main_occupation)
        }
    }

    class TypeMusicViewholder(ItemView: View) : RecyclerView.ViewHolder(ItemView) {
        //var imageView: ImageView
        //var city: TextView
        //var nameAge: TextView
        //var occupation: Chip

        init {
            // imageView = ItemView.findViewById(R.id.type_main_image)
            // city = ItemView.findViewById(R.id.type_main_city)
            // nameAge = ItemView.findViewById(R.id.type_main_name_age)
            // occupation = ItemView.findViewById(R.id.type_main_occupation)
        }
    }

    class TypePDFViewholder(ItemView: View) : RecyclerView.ViewHolder(ItemView) {
        //var imageView: ImageView
        //var city: TextView
        //var nameAge: TextView
        //var occupation: Chip

        init {
            // imageView = ItemView.findViewById(R.id.type_main_image)
            // city = ItemView.findViewById(R.id.type_main_city)
            // nameAge = ItemView.findViewById(R.id.type_main_name_age)
            // occupation = ItemView.findViewById(R.id.type_main_occupation)
        }
    }
    class TypeSelectViewholder(ItemView: View) : RecyclerView.ViewHolder(ItemView) {
        //var imageView: ImageView
        //var city: TextView
        //var nameAge: TextView
        //var occupation: Chip
        private lateinit var mDialog: Dialog
        private lateinit var upload: MaterialButton
        private lateinit var cancelDialog: ImageView

        init {
            mDialog = Dialog(ItemView.context, android.R.style.Theme_Dialog)
            ItemView.setOnClickListener {
                launchUploadDialog()
            }
            // imageView = ItemView.findViewById(R.id.type_main_image)
            // city = ItemView.findViewById(R.id.type_main_city)
            // nameAge = ItemView.findViewById(R.id.type_main_name_age)
            // occupation = ItemView.findViewById(R.id.type_main_occupation)
        }

        private fun launchUploadDialog() {
           // mDialog.requestWindowFeature(Window.FEATURE_NO_TITLE)
            mDialog.setContentView(R.layout.create_mission_step2_upload)
            upload = mDialog.findViewById(R.id.create_mission_setp2_upload);
            cancelDialog = mDialog.findViewById(R.id.cancel_dialog_icon)
            mDialog.setCanceledOnTouchOutside(true)
            mDialog.window?.setBackgroundDrawable(ColorDrawable(Color.TRANSPARENT))
            mDialog.show()
            upload.setOnClickListener {
                mDialog.dismiss()
            }

            cancelDialog.setOnClickListener { mDialog.dismiss() }

        }
    }



}