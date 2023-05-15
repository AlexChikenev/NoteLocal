package com.example.diploma.adapters

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.diploma.model.Project
import com.example.notelocal.R

class ProjectAdapter(var directoryToProject: List<Project>) :
    RecyclerView.Adapter<ProjectAdapter.ProjectViewHolder>() {

    private var onClickListener: OnClickListener? = null

    class ProjectViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val projectName: TextView = itemView.findViewById(R.id.projectName)
        val projectId: TextView = itemView.findViewById(R.id.projectId)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ProjectViewHolder {
        val itemView =
            LayoutInflater.from(parent.context).inflate(R.layout.item_project, parent, false)
        return ProjectViewHolder(itemView)
    }

    override fun onBindViewHolder(holder: ProjectViewHolder, position: Int) {
        val currentProject = directoryToProject[position]
        val name = "Проект"
        val id = (position + 1).toString()
        val content = name + " " + id
        holder.projectName.text = currentProject.name
        holder.projectId.text = content

        holder.itemView.setOnClickListener {
            onClickListener?.onClick(currentProject)
        }
    }

    override fun getItemCount() = directoryToProject.size

    fun setOnClickListener(onClickListener: OnClickListener) {
        this.onClickListener = onClickListener
    }

    interface OnClickListener {
        fun onClick(project: Project)
    }

}