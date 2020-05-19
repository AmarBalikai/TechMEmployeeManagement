package com.techm.techMEmployeeManagement.view

import android.app.AlertDialog
import android.content.Context
import android.content.Intent
import android.os.Build
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.Menu
import android.view.MenuInflater
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
import com.techm.techMEmployeeManagement.model.ModelEmployeeInformation
import com.techm.techMEmployeeManagement.utils.*
import com.techm.techMEmployeeManagement.viewmodel.ViewModelEmployeeInformation
import kotlinx.android.synthetic.main.activity_main.*

class EmployeeFeatureActivity : AppCompatActivity(), View.OnClickListener {
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
        title = getString(R.string.employee_information)
        mDataViewModel = ViewModelProvider(this).get(ViewModelEmployeeInformation::class.java)
        setupProgressDialog()
        searchView.queryHint = getString(R.string.search)

        swipeToRefresh.setOnRefreshListener {
            isSwipeToRefreshCall = true
            getCountryFeaturesData(context)
        }
        searchView.setOnQueryTextListener(object : SearchView.OnQueryTextListener {
            override fun onQueryTextSubmit(query: String): Boolean {
                return false
            }

            override fun onQueryTextChange(newText: String): Boolean {
                newText.let { mAdapter.filter(it) }
                return true
            }
        })

        country_list.addItemDecoration(
            DividerItemDecoration(
                context,
                DividerItemDecoration.VERTICAL
            )
        )
        /**
         * Setting blank adapter for initialize
         */
        mAdapter = AdapterEmployeeInformation(ArrayList(), context)
        linearLayoutManager = LinearLayoutManager(this)
        country_list.layoutManager = linearLayoutManager
        country_list.adapter = mAdapter

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
                mDataViewModel.deleteEmployee(mModelEmployeeInformation.id)
            }
        }
        val itemTouchHelper = ItemTouchHelper(swipeHandler)
        itemTouchHelper.attachToRecyclerView(country_list)

        /**
         * API Live data observer
         * */
        mDataViewModel.mEmployeeInformationData.observe(this, Observer {
            if (isSwipeToRefreshCall) {
                isSwipeToRefreshCall = false
                swipeToRefresh.isRefreshing = false
            } else
                hideProgressDialog()

            when (it.status) {
                ResponseStatus.SUCCESS -> {
                    mEmployeeList.clear()
                    mEmployeeList = it.data
                    mAdapter.setList(it.data)
                }
                ResponseStatus.FAIL ->
                    toast(getString(R.string.serviceFailureError))
                ResponseStatus.LOADING ->
                    showProgressDialog()
            }
        })

        mDataViewModel.mEmployeeDeleteStatus.observe(this, Observer {
            when (it.apiStatus) {
                ResponseStatus.SUCCESS -> {
                    context?.toast(getString(R.string.employee_deleted_successfully))

                }
                ResponseStatus.FAIL -> {
                    context?.toast(getString(R.string.serviceFailureError))
                    //mAdapter.setList(mEmployeeList)
                }
                ResponseStatus.LOADING ->
                    showProgressDialog()
            }
        })
    }

    /**
     * This method for get data from the viewModel
     */
    private fun getCountryFeaturesData(context: Context?) {

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            if (NetworkConnection.isNetworkConnected()) {
                showProgressDialog()
                mDataViewModel.getEmployeeInformation()
            } else {
                if (swipeToRefresh.isRefreshing) {
                    swipeToRefresh.isRefreshing = false
                }
                context?.toast(getString(R.string.device_not_connected_to_internet))
            }
        } else {
            if (NetworkConnection.isNetworkConnectedKitkat()) {
                showProgressDialog()
                mDataViewModel.getEmployeeInformation()

            } else {
                if (swipeToRefresh.isRefreshing) {
                    swipeToRefresh.isRefreshing = false
                }
                context?.toast(getString(R.string.device_not_connected_to_internet))
            }
        }
    }


    /**
     * Showing dialog when api call
     */
    @RequiresApi(Build.VERSION_CODES.LOLLIPOP)
    private fun setupProgressDialog() {
        builder = AlertDialog.Builder(this)
        builder.setCancelable(false)
        builder.setView(R.layout.layout_loading_dialog)
        dialog = builder.create()
    }

    private fun showProgressDialog() {
        if (dialog != null && !dialog.isShowing) {
            dialog.show()
        }
    }

    private fun hideProgressDialog() {
        if (dialog != null && dialog.isShowing) {
            dialog.hide()
            dialog.dismiss()
        }
    }

    override fun onDestroy() {
        super.onDestroy()
        if (dialog != null && dialog.isShowing) {
            dialog.hide()
            dialog.dismiss()
        }
    }

    override fun onResume() {
        super.onResume()
        getCountryFeaturesData(context)
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


}
