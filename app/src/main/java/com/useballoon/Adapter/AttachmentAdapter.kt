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
import android.widget.LinearLayout
import androidx.core.content.ContentProviderCompat.requireContext
import com.google.android.material.button.MaterialButton
import com.useballoon.Interfaces.AttachmentViewClickedListener
import com.useballoon.Interfaces.FileUploadClickListener
import com.useballoon.Models.Attachments
import com.useballoon.R


class AttachmentAdapter(context: Context, attachmentList: ArrayList<Attachments>): RecyclerView.Adapter<RecyclerView.ViewHolder>() {
    private val TYPE_IMAGE = 1
    private val TYPE_MUSIC = 2
    private val TYPE_PDF = 3
    private val TYPE_VIDEO = 4
    private val TYPE_SELECT = 5
    private var context:Context? = null
    var fileUploadClickListener: FileUploadClickListener? = null
    private var attachmentList: ArrayList<Attachments> = ArrayList()
    private lateinit var attachmentViewClickedListener: AttachmentViewClickedListener



    init {
        this.attachmentList = attachmentList
        this.context = context
    }

    public fun setFileUploadListener(fileUploadClickListener: FileUploadClickListener){
           this.fileUploadClickListener = fileUploadClickListener
    }

    public fun setAttachmentViewClickedlistener(attachmentViewClickedListener: AttachmentViewClickedListener){
           this.attachmentViewClickedListener = attachmentViewClickedListener
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
         var attachment = attachmentList[position]
        if(holder is TypeSelectViewholder){
            holder.itemView.setOnClickListener {
                fileUploadClickListener!!.onFileUploadClicked()
            }
        }
        if(holder is TypeImageViewholder){
            holder.itemView.setOnClickListener {
                attachmentViewClickedListener.onAttachmentClicked(attachment.attachmentUrl)
            }
            holder.attachmentName.text = attachment.shortTitle
            holder.attachmentDelete.setOnClickListener {
                attachmentList.remove(attachment)
                notifyDataSetChanged()
            }
        }
        if(holder is TypeVideoViewholder){
            holder.itemView.setOnClickListener {
                attachmentViewClickedListener.onAttachmentClicked(attachment.attachmentUrl)
            }
            holder.attachmentName.text = attachment.shortTitle
            holder.attachmentDelete.setOnClickListener {
                attachmentList.remove(attachment)
                notifyDataSetChanged()
            }
        }
        if(holder is TypePDFViewholder){
            holder.itemView.setOnClickListener {
                attachmentViewClickedListener.onAttachmentClicked(attachment.attachmentUrl)
            }
            holder.attachmentName.text = attachment.shortTitle
            holder.attachmentDelete.setOnClickListener {
                attachmentList.remove(attachment)
                notifyDataSetChanged()
            }
        }
        if(holder is TypeMusicViewholder){
            holder.itemView.setOnClickListener {
                attachmentViewClickedListener.onAttachmentClicked(attachment.attachmentUrl)
            }
            holder.attachmentName.text = attachment.shortTitle
            holder.attachmentDelete.setOnClickListener {
                attachmentList.remove(attachment)
                notifyDataSetChanged()
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
         var attachmentName: TextView
         var attachmentDelete: LinearLayout

        init {
           attachmentName = ItemView.findViewById(R.id.attachment_name)
            attachmentDelete = ItemView.findViewById(R.id.attachment_delete)
        }
    }

    class TypeVideoViewholder(ItemView: View) : RecyclerView.ViewHolder(ItemView) {
        var attachmentName: TextView
        var attachmentDelete: LinearLayout

        init {
            attachmentName = ItemView.findViewById(R.id.attachment_name)
            attachmentDelete = ItemView.findViewById(R.id.attachment_delete)
        }
    }

    class TypeMusicViewholder(ItemView: View) : RecyclerView.ViewHolder(ItemView) {
        var attachmentName: TextView
        var attachmentDelete: LinearLayout

        init {
            attachmentName = ItemView.findViewById(R.id.attachment_name)
            attachmentDelete = ItemView.findViewById(R.id.attachment_delete)
        }
    }


    class TypePDFViewholder(ItemView: View) : RecyclerView.ViewHolder(ItemView) {
        var attachmentName: TextView
        var attachmentDelete: LinearLayout

        init {
            attachmentName = ItemView.findViewById(R.id.attachment_name)
            attachmentDelete = ItemView.findViewById(R.id.attachment_delete)
        }
    }
   class TypeSelectViewholder(ItemView: View) : RecyclerView.ViewHolder(ItemView) {



        init {
            ItemView.setOnClickListener {

            }

        }


    }

    fun addItem(attachment: Attachments){
        attachmentList.add(attachment)
        notifyDataSetChanged()
    }

}