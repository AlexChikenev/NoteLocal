package com.example.note.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.diploma.model.Directory
import com.example.notelocal.R

class DirectoryAdapter(var directory: List<Directory>) :
    RecyclerView.Adapter<DirectoryAdapter.DirectoryVieHolder>() {

    private var onClickListener: OnClickListener? = null

    class DirectoryVieHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val directoryName: TextView = itemView.findViewById(R.id.nameOfDirectory)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): DirectoryVieHolder {
        val itemView =
            LayoutInflater.from(parent.context).inflate(R.layout.item_directory, parent, false)
        return DirectoryVieHolder(itemView)
    }

    override fun onBindViewHolder(holder: DirectoryVieHolder, position: Int) {
        val currentDirectory = directory[position]
        holder.directoryName.text = currentDirectory.name

        holder.itemView.setOnClickListener {
            onClickListener?.onClick(currentDirectory)
        }
    }

    override fun getItemCount() = directory.size

    fun setOnClickListener(onClickListener: OnClickListener) {
        this.onClickListener = onClickListener
    }

    interface OnClickListener {
        fun onClick(directory: Directory)
    }
}