package com.mostafiz.android.tvltask.adapter

import android.annotation.SuppressLint
import android.content.Context
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.annotation.NonNull
import androidx.recyclerview.widget.RecyclerView
import com.mostafiz.android.tvltask.databinding.CardTaskBinding
import com.mostafiz.android.tvltask.models.TaskModel


class TaskAdapter(
    private val context: Context,
    private val modelList: ArrayList<TaskModel>,
    var deleteTask: DeleteTask
) :
    RecyclerView.Adapter<TaskAdapter.ViewHolder>() {

    @NonNull
    override fun onCreateViewHolder(
        @NonNull parent: ViewGroup,
        viewType: Int
    ): ViewHolder {
        val binding: CardTaskBinding =
            CardTaskBinding.inflate(LayoutInflater.from(context), parent, false)
        return ViewHolder(binding)
    }

    @SuppressLint("SimpleDateFormat")
    override fun onBindViewHolder(@NonNull holder: ViewHolder, position: Int) {
        val model: TaskModel = modelList[position]

        holder.binding.taskNameTv.text = model.title
        holder.binding.descriptionTv.text = model.description
        holder.binding.taskTimeTv.text = model.time
        holder.binding.taskDateTv.text = model.date

        holder.binding.removeImg.setOnClickListener{
            deleteTask.deleteTask(model.id)
        }



    }

    override fun getItemId(position: Int): Long {
        return position.toLong()
    }

    override fun getItemViewType(position: Int): Int {
        return position
    }

    override fun getItemCount(): Int {
        return modelList.size
    }

    class ViewHolder(@NonNull var binding: CardTaskBinding) :
        RecyclerView.ViewHolder(binding.root) {
    }

    interface DeleteTask {
        fun deleteTask(id: Long)
    }
}