package com.techm.techMEmployeeManagement.adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.techm.techMEmployeeManagement.R
import java.util.*
import kotlin.collections.ArrayList

/**
 * This class for creating items in recycler view
 * */
class AdapterEmployeeInformation : RecyclerView.Adapter<ViewHolder> {
    private var itemsList = ArrayList<ModelEmployeeInformation>()
    private var items = ArrayList<ModelEmployeeInformation>()
    lateinit var context: Context

    constructor(items: ArrayList<ModelEmployeeInformation>, context: Context?) {
        this.items = items
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
        holder.employeeName.text = items[position].employee_name
        holder.employeeSalary.text = items[position].employee_salary
        holder.employeeAge.text = items[position].employee_age
    }

    fun removeAt(position: Int) {
        this.items.removeAt(position)
        notifyItemRemoved(position)
    }

    fun getItemAtPosition(position: Int): ModelEmployeeInformation {
        return this.items[position]
    }
    /**
     * setting updated list
     * */
    fun setList(dataInformation: ArrayList<ModelEmployeeInformation>) {
        this.items = dataInformation
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
                if (wp.employee_name.toLowerCase(Locale.getDefault()).contains(charText)) {
                    items.add(wp)
                }
            }
        }
        notifyDataSetChanged()
    }
}

class ViewHolder(view: View) : RecyclerView.ViewHolder(view) {
    val employeeName: TextView = view.employeeName
    val employeeSalary: TextView = view.employee_salary
    val employeeAge: TextView = view.employee_age
}