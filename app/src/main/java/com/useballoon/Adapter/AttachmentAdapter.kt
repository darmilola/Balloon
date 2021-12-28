package com.useballoon.Adapter

import android.app.Dialog
import android.content.Context
import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.net.Uri
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import android.view.View

import com.google.android.material.chip.Chip

import android.widget.TextView


import android.view.LayoutInflater
import android.view.Window
import android.widget.EditText
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
    var fileUploadClickListener:FileUploadClickListener? = null
    private var attachmentList: ArrayList<Attachments> = ArrayList()
    val UPLOAD_URL = "https://glacial-spire-23584.herokuapp.com/public/api/artists/mission/upload"


    //Pdf request code
    private val PICK_PDF_REQUEST = 1

    //storage permission code
    private val STORAGE_PERMISSION_CODE = 123


    //Uri to store the image uri
    private val filePath: Uri? = null

     interface FileUploadClickListener {
        fun onFileUploadClicked()
    }

    init {
        this.attachmentList = attachmentList
        this.context = context
    }

    public fun setFileUploadListener(fileUploadClickListener: FileUploadClickListener){
           this.fileUploadClickListener = fileUploadClickListener
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

        if(holder is TypeSelectViewholder){
            holder.itemView.setOnClickListener {
                fileUploadClickListener!!.onFileUploadClicked()
            }
        }
    }

    override fun getItemCount(): Int {
        return attachmentList.size
    }


    override fun getItemViewType(position: Int): Int {
        return if (attachmentList[position].type.equals("png",true)) {
            TYPE_IMAGE
        }
        else if (attachmentList[position].type.equals("jpg",true)) {
            TYPE_IMAGE
        }
        else if (attachmentList[position].type.equals("mp3",true)) {
            TYPE_MUSIC
        }
        else if (attachmentList[position].type.equals("pdf",true)) {
            TYPE_PDF
        }
        else if (attachmentList[position].type.equals("mp4",true)) {
            TYPE_VIDEO
        } else if (attachmentList[position].type === "select") {
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


        init {
            ItemView.setOnClickListener {

            }
            // imageView = ItemView.findViewById(R.id.type_main_image)
            // city = ItemView.findViewById(R.id.type_main_city)
            // nameAge = ItemView.findViewById(R.id.type_main_name_age)
            // occupation = ItemView.findViewById(R.id.type_main_occupation)
        }


    }

    fun addItem(attachment: Attachments){
        attachmentList.add(attachment)
        notifyDataSetChanged()
    }

}