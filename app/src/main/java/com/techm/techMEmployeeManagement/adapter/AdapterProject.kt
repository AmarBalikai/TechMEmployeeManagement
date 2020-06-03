package com.techm.techMEmployeeManagement.adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.techm.techMEmployeeManagement.R
import com.techm.techMEmployeeManagement.roomdatabase.ModelProject
import kotlinx.android.synthetic.main.item_layout.view.*
import kotlinx.android.synthetic.main.item_layout.view.project
import kotlinx.android.synthetic.main.item_project.view.*
import java.util.*
import kotlin.collections.ArrayList

class AdapterProject: RecyclerView.Adapter<ViewHolderProject> {
    private var itemsList = ArrayList<ModelProject>()
    private var items = ArrayList<ModelProject>()
    lateinit var context: Context
    private lateinit var listener: ItemClickListener

    constructor(
        items: ArrayList<ModelProject>,
        context: Context?,
        listener: ItemClickListener
    ) {
        this.items = items
        this.listener = listener;
        if (context != null) {
            this.context = context
        }
        itemsList.addAll(items)
    }

    override fun getItemCount(): Int {
        return items.size
    }

    fun removeAt(position: Int) {
        this.items.removeAt(position)
        notifyItemRemoved(position)
    }

    fun getItemAtPosition(position: Int): ModelProject {
        return this.items[position]
    }

    /**
     * setting updated list
     * */
    fun setList(dataInformation: List<ModelProject>) {
        this.items = dataInformation as ArrayList<ModelProject>
        itemsList.addAll(items)
        notifyDataSetChanged()
    }

    interface ItemClickListener {
        fun onItemClick(mModelProject: ModelProject, position: Int)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolderProject {
        return ViewHolderProject(LayoutInflater.from(context).inflate(R.layout.item_project, parent, false))
    }

    override fun onBindViewHolder(holder: ViewHolderProject, position: Int) {
        holder.projectName.text = items[position].projectName

        holder.bind(items[position], position, listener)

    }
}

class ViewHolderProject(view: View) : RecyclerView.ViewHolder(view) {
    fun bind(
        modelProject: ModelProject,
        position: Int,
        listener: AdapterProject.ItemClickListener
    ) {
        itemView.setOnClickListener {
            listener.onItemClick(modelProject, position)
        }
    }

    val projectName: TextView = view.project
}