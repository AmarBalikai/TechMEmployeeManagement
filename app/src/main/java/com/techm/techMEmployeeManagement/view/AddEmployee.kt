package com.techm.techMEmployeeManagement.view

import android.app.AlertDialog
import android.content.Intent
import android.os.Build
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.text.TextUtils
import android.util.Log
import android.view.View
import android.widget.AdapterView
import android.widget.ArrayAdapter
import android.widget.RadioButton
import androidx.annotation.RequiresApi
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import com.techm.techMEmployeeManagement.R
import com.techm.techMEmployeeManagement.adapter.SpinnerAdapter
import com.techm.techMEmployeeManagement.roomdatabase.ModelEmployeeRegistration
import com.techm.techMEmployeeManagement.roomdatabase.ModelProject
import com.techm.techMEmployeeManagement.utils.Constant
import com.techm.techMEmployeeManagement.utils.ResponseStatus
import com.techm.techMEmployeeManagement.utils.toast
import com.techm.techMEmployeeManagement.viewmodel.ViewModelAddEmployee
import kotlinx.android.synthetic.main.activity_add_employee.*

class AddEmployee : AppCompatActivity() {
    private lateinit var builder: AlertDialog.Builder
    private lateinit var mDataViewModel: ViewModelAddEmployee
    private lateinit var mSpinnerAdapter: SpinnerAdapter
    private lateinit var dialog: AlertDialog
    private var intentExtra: Int = 0
    var editProjectName = ""
    private var project = ""

    //lateinit var adapter: ArrayAdapter()
    var employeeId: Int = 0
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_add_employee)
        supportActionBar?.title=getString(R.string.add_employee)
        mDataViewModel = ViewModelProvider(this).get(ViewModelAddEmployee::class.java)
        intentExtra = intent.getIntExtra(Constant.openFor, 0)
        tvID.keyListener = null
        tvID.isEnabled = false
        if (intentExtra == 0)
            buttonEdit.visibility = View.GONE
        else {
            employeeId = intent.getIntExtra(Constant.data, 0)
            buttonEdit.visibility = View.VISIBLE
            buttonSave.isEnabled = false
            fillFeatureData(employeeId)
            lockAllFields()
        }
        addProject.setOnClickListener {
            var intent = Intent(this, ProjectActivity::class.java)
            startActivity(intent)
        }
        buttonEdit.setOnClickListener {
            openAllFields()
            buttonEdit.isEnabled = false
        }
        setupProgressDialog()

        mSpinnerAdapter = SpinnerAdapter(this, ArrayList())
        spinnerProject.adapter = mSpinnerAdapter

        mDataViewModel.mProjectData.observe(this, Observer {
            mSpinnerAdapter.setLIst(it as ArrayList<ModelProject>)
            if (intentExtra != 0) {
                var position = mSpinnerAdapter.getPosition(editProjectName)
                spinnerProject.setSelection(position)
            }
        })

        spinnerProject.onItemSelectedListener = object :
            AdapterView.OnItemSelectedListener {
            override fun onItemSelected(
                parent: AdapterView<*>,
                view: View, position: Int, id: Long
            ) {
                project = mSpinnerAdapter.getItem(position).projectName
            }

            override fun onNothingSelected(parent: AdapterView<*>) {
            }
        }
        buttonSave.setOnClickListener {
            saveEmployeeRecord()
        }

        mDataViewModel.mEmployeeResponseUpdate.observe(this, Observer {
            setInformation(it)
        })
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

    private fun fillFeatureData(employeeId: Int) {
        mDataViewModel.getEmployeeData("" + employeeId)
    }

    private fun clearText() {
        textFieldName.editText!!.setText("")
        textFieldBand.editText!!.setText("")
        textFieldDesignation.editText!!.setText("")
        textFieldEmployeeId.editText!!.setText("")
        textFieldId.editText!!.setText("")
        rBtnAndroid.isChecked = true
    }

    private fun setInformation(employee: ModelEmployeeRegistration) {
        textFieldName.editText?.setText(employee.name)
        textFieldBand.editText?.setText(employee.band)
        textFieldDesignation.editText?.setText(employee.designation)
        textFieldEmployeeId.editText?.setText(employee.employeeId)
        textFieldId.editText?.setText("" + employee.id)
        when (employee.competency) {
            getString(R.string.android) ->
                rBtnAndroid.isSelected = true
            getString(R.string.ios) ->
                rBtnIos.isSelected = true
            getString(R.string.ux) ->
                rBtnUX.isSelected = true
            getString(R.string.tester) ->
                rBtnTester.isSelected = true
        }
        editProjectName = employee.project


    }

    private fun saveEmployeeRecord() {
        val employeeName = textFieldName.editText!!.text.toString()
        val employeeBand = textFieldBand.editText!!.text.toString()
        val employeeDesignation = textFieldDesignation.editText!!.text.toString()
        val textFieldEmployeeID = textFieldEmployeeId.editText!!.text.toString()

        var employeeCompetency: String =
            findViewById<RadioButton>(rBtnGroup.checkedRadioButtonId).text.toString()

        when {
            TextUtils.isEmpty(employeeName) -> {
                textFieldName.error = getString(R.string.input_required)
                textFieldName.isErrorEnabled = true
            }
            TextUtils.isEmpty(employeeBand) -> {
                textFieldBand.error = getString(R.string.input_required)
                textFieldBand.isErrorEnabled = true
            }
            TextUtils.isEmpty(employeeDesignation) -> {
                textFieldDesignation.error = getString(R.string.input_required)
                textFieldDesignation.isErrorEnabled = true
            }
            TextUtils.isEmpty(textFieldEmployeeID) -> {
                textFieldDesignation.error = getString(R.string.input_required)
                textFieldDesignation.isErrorEnabled = true
            }

            (project == "" || project == getString(R.string.select_project)) -> {
                toast(getString(R.string.please_select_project))
            }
            else -> {
                var mModelEmployeeRegistration =
                    ModelEmployeeRegistration(
                        0,
                        employeeName,
                        employeeBand,
                        employeeDesignation,
                        textFieldEmployeeID,
                        employeeCompetency,
                        project
                    )
                if (intentExtra == 0)
                    mDataViewModel.insertEmployee(mModelEmployeeRegistration)
                else
                {
                    mModelEmployeeRegistration.id=textFieldId.editText?.text.toString().toInt()
                    mDataViewModel.updateEmployee(mModelEmployeeRegistration)
                }
                clearText()
                project = ""
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

    private fun openAllFields() {
        textFieldName.isEnabled = true
        textFieldBand.isEnabled = true
        textFieldEmployeeId.isEnabled = true
        textFieldDesignation.isEnabled = true
        rBtnAndroid.isEnabled = true
        rBtnIos.isEnabled = true
        rBtnUX.isEnabled = true
        rBtnTester.isEnabled = true
        spinnerProject.isEnabled = true
        addProject.isEnabled = true
        buttonSave.isEnabled = true
    }

    private fun lockAllFields() {
        textFieldName.isEnabled = false
        textFieldBand.isEnabled = false
        textFieldDesignation.isEnabled = false
        rBtnAndroid.isEnabled = false
        rBtnIos.isEnabled = false
        rBtnUX.isEnabled = false
        rBtnTester.isEnabled = false
        spinnerProject.isEnabled = false
        textFieldEmployeeId.isEnabled = false
        addProject.isEnabled = false
    }
}
