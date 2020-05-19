package com.techm.techMEmployeeManagement.view

import android.app.AlertDialog
import android.os.Build
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.text.TextUtils
import android.view.View
import android.widget.AdapterView
import android.widget.ArrayAdapter
import androidx.annotation.RequiresApi
import androidx.lifecycle.Observer
import com.techm.techMEmployeeManagement.R
import com.techm.techMEmployeeManagement.model.ModelEmployeeRegistration
import com.techm.techMEmployeeManagement.utils.Constant
import com.techm.techMEmployeeManagement.utils.ResponseStatus
import com.techm.techMEmployeeManagement.utils.toast
import com.techm.techMEmployeeManagement.viewmodel.ViewModelAddEmployee
import kotlinx.android.synthetic.main.activity_add_employee.*

class AddEmployee : AppCompatActivity() {
    private lateinit var builder: AlertDialog.Builder
    private lateinit var mDataViewModel: ViewModelAddEmployee
    private lateinit var dialog: AlertDialog
    private var intentExtra: Int = 0
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_add_employee)

        intentExtra = intent.getIntExtra(Constant.openFor, 0)
        tvID.keyListener = null
        tvID.isEnabled = false
        if (intentExtra == 0)
            buttonEdit.visibility = View.GONE
        else {
            buttonEdit.visibility = View.VISIBLE
            lockAllFields()
        }
        buttonEdit.setOnClickListener {
            openAllFields()
            buttonEdit.isEnabled=false
        }
        setupProgressDialog()
        val projects = resources.getStringArray(R.array.Projects)
        val adapter = ArrayAdapter(
            this,
            android.R.layout.simple_spinner_item, projects
        )
        spinnerProject.adapter = adapter
        spinnerProject.setSelection(0)
        spinnerProject.onItemSelectedListener = object :
            AdapterView.OnItemSelectedListener {
            override fun onItemSelected(
                parent: AdapterView<*>,
                view: View, position: Int, id: Long
            ) {
                /*Toast.makeText(this@MainActivity,
                    getString(R.string.selected_item) + " " +
                            "" + languages[position], Toast.LENGTH_SHORT).show()*/
            }

            override fun onNothingSelected(parent: AdapterView<*>) {
            }
        }
        buttonSave.setOnClickListener {
            saveEmployeeRecord()
        }
        /**
         * API Live data observer
         * */
        mDataViewModel.mEmployeeResponse.observe(this, Observer {
            hideProgressDialog()
            when (it.status) {
                ResponseStatus.SUCCESS -> {
                    toast(getString(R.string.record_save_successfully))
                    clearText()
                }
                ResponseStatus.FAIL ->
                    toast(getString(R.string.serviceFailureError))
                ResponseStatus.LOADING ->
                    showProgressDialog()
            }
        })
    }

    private fun clearText() {
        textFieldName.editText!!.setText("")
        textFieldBand.editText!!.setText("")
        textFieldDesignation.editText!!.setText("")
    }

    private fun saveEmployeeRecord() {
        val strName = textFieldName.editText!!.text.toString()
        val strSalary = textFieldBand.editText!!.text.toString()
        val strAge = textFieldDesignation.editText!!.text.toString()
        when {
            TextUtils.isEmpty(strName) -> {
                textFieldName.error = getString(R.string.input_required)
                textFieldName.isErrorEnabled = true
            }
            TextUtils.isEmpty(strSalary) -> {
                textFieldBand.error = getString(R.string.input_required)
                textFieldBand.isErrorEnabled = true
            }
            TextUtils.isEmpty(strAge) -> {
                textFieldDesignation.error = getString(R.string.input_required)
                textFieldDesignation.isErrorEnabled = true
            }
            else -> {
                showProgressDialog()
                var mModelEmployeeRegistration =
                    ModelEmployeeRegistration(strName, strSalary, strAge)
                mDataViewModel.insertEmployee(mModelEmployeeRegistration)
            }
        }
    }

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

    private fun openAllFields()
    {
        textFieldName.isEnabled = true
        textFieldBand.isEnabled = true
        textFieldDesignation.isEnabled = true
        rBtnAndroid.isEnabled = true
        rBtnIos.isEnabled = true
        rBtnUX.isEnabled = true
        rBtnTester.isEnabled = true
        spinnerProject.isEnabled = true
    }
    private fun lockAllFields()
    {
        textFieldName.isEnabled = false
        textFieldBand.isEnabled = false
        textFieldDesignation.isEnabled = false
        rBtnAndroid.isEnabled = false
        rBtnIos.isEnabled = false
        rBtnUX.isEnabled = false
        rBtnTester.isEnabled = false
        spinnerProject.isEnabled = false
    }
}
