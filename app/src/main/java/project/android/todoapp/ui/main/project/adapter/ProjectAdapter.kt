package project.android.todoapp.ui.main.project.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import project.android.todoapp.databinding.ItemProjectBinding
import project.android.todoapp.model.Project

class ProjectAdapter() : ListAdapter<Project,ProjectAdapter.ProjectViewHolder>(DiffUtilProjectCallback) {
    lateinit var binding: ItemProjectBinding
    object DiffUtilProjectCallback : DiffUtil.ItemCallback<Project>(){
        override fun areItemsTheSame(oldItem: Project, newItem: Project): Boolean {
            return oldItem.id == newItem.id
        }

        override fun areContentsTheSame(oldItem: Project, newItem: Project): Boolean {
            return oldItem == newItem
        }

    }

    inner class ProjectViewHolder(private val binding: ItemProjectBinding) : RecyclerView.ViewHolder(binding.root){
        fun bind(project : Project){
            binding.textContentItemProj.text = project.description
            binding.textProjTitle.text = project.name
        }
    }

    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): ProjectAdapter.ProjectViewHolder {
        binding = ItemProjectBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return ProjectViewHolder(binding)
    }

    override fun onBindViewHolder(holder: ProjectAdapter.ProjectViewHolder, position: Int) {
        holder.bind(getItem(position))
    }

}