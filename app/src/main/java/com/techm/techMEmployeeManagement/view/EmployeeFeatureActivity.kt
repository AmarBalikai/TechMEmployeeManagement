package com.techm.techMEmployeeManagement.view

import android.app.AlertDialog
import android.content.Context
import android.content.Intent
import android.os.Build
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import android.view.View
import androidx.annotation.RequiresApi
import androidx.appcompat.widget.SearchView
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.ItemTouchHelper
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.techm.techMEmployeeManagement.R
import com.techm.techMEmployeeManagement.adapter.AdapterEmployeeInformation
import com.techm.techMEmployeeManagement.application.ApplicationContext.Companion.context
import com.techm.techMEmployeeManagement.roomdatabase.ModelEmployeeRegistration
import com.techm.techMEmployeeManagement.utils.*
import com.techm.techMEmployeeManagement.viewmodel.ViewModelEmployeeInformation
import kotlinx.android.synthetic.main.activity_main.*

class EmployeeFeatureActivity : AppCompatActivity(), View.OnClickListener,
    AdapterEmployeeInformation.ItemClickListener {
    private lateinit var mDataViewModel: ViewModelEmployeeInformation
    private lateinit var mAdapter: AdapterEmployeeInformation
    private lateinit var builder: AlertDialog.Builder
    private lateinit var dialog: AlertDialog
    private var isSwipeToRefreshCall: Boolean = false
    private lateinit var linearLayoutManager: LinearLayoutManager

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        supportActionBar?.title=getString(R.string.employee_information)
        mDataViewModel = ViewModelProvider(this).get(ViewModelEmployeeInformation::class.java)
        searchView.queryHint = getString(R.string.search)

        swipeToRefresh.setOnRefreshListener {
            mDataViewModel.getEmployeeInformation()
            swipeToRefresh.isRefreshing=false
        }
        searchView.setOnQueryTextListener(object : SearchView.OnQueryTextListener {
            override fun onQueryTextSubmit(query: String): Boolean {
                return false
            }

            override fun onQueryTextChange(newText: String): Boolean {
                newText.let { mAdapter.filter(it.trimStart()) }
                return true
            }
        })

        employeeList.addItemDecoration(
            DividerItemDecoration(
                context,
                DividerItemDecoration.VERTICAL
            )
        )
        /**
         * Setting blank adapter for initialize
         */
        mAdapter = AdapterEmployeeInformation(ArrayList(), context, this)
        linearLayoutManager = LinearLayoutManager(this)
        employeeList.layoutManager = linearLayoutManager
        employeeList.adapter = mAdapter

        val swipeHandler = object : SwipeToDeleteCallback(context) {
            override fun onMove(
                recyclerView: RecyclerView,
                viewHolder: RecyclerView.ViewHolder,
                target: RecyclerView.ViewHolder
            ): Boolean {
                TODO("Not yet implemented")
            }

            override fun onSwiped(viewHolder: RecyclerView.ViewHolder, direction: Int) {
                var mModelEmployeeInformation =
                    mAdapter.getItemAtPosition(viewHolder.adapterPosition)
                mAdapter.removeAt(viewHolder.adapterPosition)
                mDataViewModel.deleteEmployee(mModelEmployeeInformation)
            }
        }
        val itemTouchHelper = ItemTouchHelper(swipeHandler)
        itemTouchHelper.attachToRecyclerView(employeeList)

        /**
         * API Live data observer
         * */
        mDataViewModel.mEmployeeInformationData.observe(this, Observer {

            mAdapter.setList(it)
        })
    }

    override fun onClick(v: View?) {

    }

    override fun onCreateOptionsMenu(menu: Menu): Boolean {
        // Inflate the menu; this adds items to the action bar if it is present.
        menuInflater.inflate(R.menu.menu_main, menu)
        return true
    }

    /**
     *  Handle action bar item clicks here
     * */
    override fun onOptionsItemSelected(item: MenuItem): Boolean {

        return when (item.itemId) {
            R.id.action_add -> {
                var intent = Intent(this, AddEmployee::class.java)
                intent.putExtra(Constant.openFor,0)
                startActivity(intent)
                //   findNavController().navigate(R.id.action_EmployeeInformation_to_AddEmployee)
                true
            }
            else -> super.onOptionsItemSelected(item)
        }
    }

    override fun onItemClick(mModelEmployeeRegistration: ModelEmployeeRegistration, position: Int) {
        var intent = Intent(this, AddEmployee::class.java)
        intent.putExtra(Constant.openFor,1)
        intent.putExtra(Constant.data,mModelEmployeeRegistration.id)
        startActivity(intent)
    }


}
