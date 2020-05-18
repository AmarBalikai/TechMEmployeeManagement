package com.techm.techMEmployeeManagement.view

import android.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.recyclerview.widget.LinearLayoutManager
import com.techm.techMEmployeeManagement.R
import com.techm.techMEmployeeManagement.adapter.AdapterEmployeeInformation
import com.techm.techMEmployeeManagement.viewmodel.ViewModelEmployeeInformation

class MainActivity : AppCompatActivity() {
    private lateinit var mDataViewModel: ViewModelEmployeeInformation
    private lateinit var mAdapter: AdapterEmployeeInformation
    private lateinit var builder: AlertDialog.Builder
    private lateinit var dialog: AlertDialog
    private var isSwipeToRefreshCall: Boolean = false
    private lateinit var linearLayoutManager: LinearLayoutManager
    private var mEmployeeList = ArrayList<ModelEmployeeInformation>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
    }
}
