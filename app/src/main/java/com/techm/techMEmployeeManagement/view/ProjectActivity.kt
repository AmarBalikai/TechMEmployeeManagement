package com.techm.techMEmployeeManagement.view

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import com.techm.techMEmployeeManagement.R
import com.techm.techMEmployeeManagement.adapter.AdapterEmployeeInformation
import com.techm.techMEmployeeManagement.adapter.AdapterProject
import com.techm.techMEmployeeManagement.application.ApplicationContext
import com.techm.techMEmployeeManagement.roomdatabase.ModelProject
import com.techm.techMEmployeeManagement.utils.ResponseStatus
import com.techm.techMEmployeeManagement.utils.toast
import com.techm.techMEmployeeManagement.viewmodel.ViewModelAddEmployee
import com.techm.techMEmployeeManagement.viewmodel.ViewModelProject
import kotlinx.android.synthetic.main.activity_main.*
import kotlinx.android.synthetic.main.activity_project.*

class ProjectActivity : AppCompatActivity(), AdapterProject.ItemClickListener {
    private lateinit var mDataViewModel: ViewModelProject
    private lateinit var mAdapter: AdapterProject
    private lateinit var linearLayoutManager: LinearLayoutManager
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_project)
        supportActionBar?.title=getString(R.string.add_project)
        mDataViewModel = ViewModelProvider(this).get(ViewModelProject::class.java)

        /**
         * Setting blank adapter for initialize
         */
        mAdapter = AdapterProject(ArrayList(), ApplicationContext.context, this)
        linearLayoutManager = LinearLayoutManager(this)
        projectList.layoutManager = linearLayoutManager
        projectList.adapter = mAdapter

        buttonAdd.setOnClickListener {
            if (projectName.text.toString().isNotEmpty()) {
                mDataViewModel.insertProject(projectName.text.toString())
                projectName.setText("")
            }
        }
        /**
         * API Live data observer
         * */
        mDataViewModel.mProjectStatus.observe(this, Observer {

            when (it.status) {
                ResponseStatus.SUCCESS -> {
                    toast(getString(R.string.record_save_successfully))

                    //clearText()
                }
                ResponseStatus.FAIL ->
                    toast(getString(R.string.serviceFailureError))

            }
        })
        mDataViewModel.mProjectData.observe(this, Observer {
            mAdapter.setList(it)
        })
    }


    override fun onItemClick(mModelProject: ModelProject, position: Int) {

    }
}
