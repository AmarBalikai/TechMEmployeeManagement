package com.techm.techMEmployeeManagement.adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.AdapterView
import android.widget.AdapterView.OnItemClickListener
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.techm.techMEmployeeManagement.R
import com.techm.techMEmployeeManagement.roomdatabase.ModelEmployeeRegistration
import kotlinx.android.synthetic.main.item_layout.view.*
import java.util.*
import kotlin.collections.ArrayList


/**
 * This class for creating items in recycler view
 * */
class AdapterEmployeeInformation : RecyclerView.Adapter<ViewHolder> {
    private var itemsList = ArrayList<ModelEmployeeRegistration>()
    private var items = ArrayList<ModelEmployeeRegistration>()
    lateinit var context: Context
    private lateinit var listener: ItemClickListener

    constructor(
        items: ArrayList<ModelEmployeeRegistration>,
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

    // Inflates the item views
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        return ViewHolder(LayoutInflater.from(context).inflate(R.layout.item_layout, parent, false))
    }

    // Binds each object in the ArrayList to a view
    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.employeeName.text = items[position].name
        holder.designation.text = items[position].designation
        holder.project.text = items[position].project
        holder.band.text = "("+items[position].band+")"
        holder.employeeId.text = items[position].employeeId
        holder.competency.text = items[position].competency

        holder.bind(items[position], position, listener)

        when (items[position].competency) {
            context.resources.getString(R.string.android) ->
                Glide.with(context).load(context.resources.getDrawable(R.drawable.android))
                    .into(holder.employeeImage);
            context.resources.getString(R.string.ios) ->
                Glide.with(context).load(context.resources.getDrawable(R.drawable.ios))
                    .into(holder.employeeImage);
            context.resources.getString(R.string.ux) ->
                Glide.with(context).load(context.resources.getDrawable(R.drawable.ux))
                    .into(holder.employeeImage);
            context.resources.getString(R.string.tester) ->
                Glide.with(context).load(context.resources.getDrawable(R.drawable.tester))
                    .into(holder.employeeImage);

        }
    }

    fun removeAt(position: Int) {
        this.items.removeAt(position)
        notifyItemRemoved(position)
    }

    fun getItemAtPosition(position: Int): ModelEmployeeRegistration {
        return this.items[position]
    }

    /**
     * setting updated list
     * */
    fun setList(dataInformation: List<ModelEmployeeRegistration>) {
        this.items = dataInformation as ArrayList<ModelEmployeeRegistration>
        itemsList.addAll(items)
        notifyDataSetChanged()
    }

    /**
     * filter employee list
     * **/
    fun filter(charText: String) {
        var charText = charText
        charText = charText.toLowerCase(Locale.getDefault())
        items.clear()
        if (charText.isEmpty()) {
            items.addAll(itemsList)
        } else {
            for (wp in itemsList) {
                if (wp.name.toLowerCase(Locale.getDefault()).contains(charText)) {
                    items.add(wp)
                }
            }
        }
        notifyDataSetChanged()
    }

    interface ItemClickListener {
        fun onItemClick(mModelEmployeeRegistration: ModelEmployeeRegistration, position: Int)
    }
}

class ViewHolder(view: View) : RecyclerView.ViewHolder(view) {
    fun bind(
        ModelEmployeeRegistration: ModelEmployeeRegistration,
        position: Int,
        listener: AdapterEmployeeInformation.ItemClickListener
    ) {
        itemView.setOnClickListener {
            listener.onItemClick(ModelEmployeeRegistration, position)
        }
    }

    val employeeName: TextView = view.employeeName
    val band: TextView = view.band
    val designation: TextView = view.designation
    val project: TextView = view.project
    val employeeImage: ImageView = view.employeeImage
    val employeeId: TextView = view.employeeId
    val competency: TextView = view.competency


}