package com.techm.techMEmployeeManagement.view

import android.app.Application
import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import androidx.lifecycle.Observer
import com.techm.techMEmployeeManagement.model.ModelEmployee
import com.techm.techMEmployeeManagement.repository.RepositoryViewModel
import com.techm.techMEmployeeManagement.roomdatabase.ModelEmployeeRegistration
import com.techm.techMEmployeeManagement.roomdatabase.ModelProject
import com.techm.techMEmployeeManagement.viewmodel.ViewModelAddEmployee
import com.techm.techMEmployeeManagement.viewmodel.ViewModelEmployeeInformation
import io.reactivex.Maybe
import org.junit.Assert.*
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.mockito.ArgumentMatchers
import org.mockito.Mockito
import org.mockito.MockitoAnnotations
import java.net.SocketException

class AddEmployeeTest
{
    @get:Rule
    val instantTaskExecutorRule = InstantTaskExecutorRule()


    lateinit var mViewModelAddEmployee: ViewModelAddEmployee

    lateinit var mRepositoryViewModel: RepositoryViewModel

    @Before
    fun setUp() {
        MockitoAnnotations.initMocks(this)

        val application = Mockito.mock(Application::class.java)
        this.mRepositoryViewModel = RepositoryViewModel(application)
        this.mViewModelAddEmployee = ViewModelAddEmployee(application)
    }
    @Test
    suspend fun test_getSingleEmployeeQuerySuccess() {
        Mockito.`when`(this.mRepositoryViewModel.getEmployee("1")).thenAnswer {
            return@thenAnswer Maybe.just(ArgumentMatchers.any<ViewModelEmployeeInformation>())
        }

        val observer = Mockito.mock(Observer::class.java) as Observer<ModelEmployee>
        this.mViewModelAddEmployee.mEmployeeResponse.observeForever(observer)

        this.mViewModelAddEmployee.getEmployeeData("1")
        Thread.sleep(10000)
        assertNotNull(this.mViewModelAddEmployee.mEmployeeResponse.value)
    }
    /**
     * This test should be fail because we will get success response from API
     * */

    @Test
    suspend fun test_getSingleEmployeeQueryError() {
        Mockito.`when`(this.mRepositoryViewModel.getEmployee("1")).thenAnswer {
            return@thenAnswer Maybe.just(ArgumentMatchers.any<ViewModelEmployeeInformation>())
        }

        val observer = Mockito.mock(Observer::class.java) as Observer<ModelEmployee>
        this.mViewModelAddEmployee.mEmployeeResponse.observeForever(observer)

        this.mViewModelAddEmployee.getEmployeeData("1")
        Thread.sleep(10000)

        assertNull(this.mViewModelAddEmployee.mEmployeeResponse.value)
    }

    @Test
    suspend fun test_getProjectQuerySuccess() {
        Mockito.`when`(this.mRepositoryViewModel.getAllProjectData()).thenAnswer {
            return@thenAnswer Maybe.just(ArgumentMatchers.any<ViewModelEmployeeInformation>())
        }

        val observer = Mockito.mock(Observer::class.java) as Observer<List<ModelProject>>
        this.mViewModelAddEmployee.mProjectData.observeForever(observer)

        this.mViewModelAddEmployee.mProjectData
        Thread.sleep(10000)
        assertNotNull(this.mViewModelAddEmployee.mProjectData.value)
    }
    /**
     * This test should be fail because we will get success response from API
     * */

    @Test
    suspend fun test_getProjectQueryError() {
        Mockito.`when`(this.mRepositoryViewModel.getAllProjectData()).thenAnswer {
            return@thenAnswer Maybe.just(ArgumentMatchers.any<ViewModelEmployeeInformation>())
        }

        val observer = Mockito.mock(Observer::class.java) as Observer<List<ModelProject>>
        this.mViewModelAddEmployee.mProjectData.observeForever(observer)

        this.mViewModelAddEmployee.mProjectData
        Thread.sleep(10000)
        assertNull(this.mViewModelAddEmployee.mProjectData.value)
    }


}