package com.techm.techMEmployeeManagement.view

import android.app.Application
import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import androidx.lifecycle.Observer
import com.techm.techMEmployeeManagement.application.ApplicationContext.Companion.context
import com.techm.techMEmployeeManagement.repository.RepositoryViewModel
import com.techm.techMEmployeeManagement.roomdatabase.ModelEmployeeRegistration
import com.techm.techMEmployeeManagement.viewmodel.ViewModelEmployeeInformation
import io.reactivex.Maybe
import org.junit.Assert.*
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.mockito.ArgumentMatchers
import org.mockito.Mockito
import org.mockito.MockitoAnnotations
import org.mockito.Spy
import java.net.SocketException

class EmployeeFeatureActivityTest{
    @get:Rule
    val instantTaskExecutorRule = InstantTaskExecutorRule()


    lateinit var mViewModelEmployeeInformation: ViewModelEmployeeInformation

    lateinit var mRepositoryViewModel: RepositoryViewModel

    @Before
    fun setUp() {
        MockitoAnnotations.initMocks(this)

        val application = Mockito.mock(Application::class.java)
        this.mRepositoryViewModel = RepositoryViewModel(application)
        this.mViewModelEmployeeInformation = ViewModelEmployeeInformation(application)
    }
    @Test
    fun test_getEmployeeInformationQuerySuccess() {
        Mockito.`when`(this.mRepositoryViewModel.getAllEmployeeData()).thenAnswer {
            return@thenAnswer Maybe.just(ArgumentMatchers.any<ViewModelEmployeeInformation>())
        }

        val observer = Mockito.mock(Observer::class.java) as Observer<List<ModelEmployeeRegistration>>
        this.mViewModelEmployeeInformation.mEmployeeInformationData.observeForever(observer)

        this.mViewModelEmployeeInformation.getEmployeeInformation()
        Thread.sleep(10000)
        assertNotNull(this.mViewModelEmployeeInformation.mEmployeeInformationData.value)
    }
    /**
     * This test should be fail because we will get success response from API
     * */
    @Test
    fun test_getEmployeeInformationQueryError() {
        Mockito.`when`(this.mRepositoryViewModel.getAllEmployeeData()).thenAnswer {
            return@thenAnswer Maybe.error<SocketException>(SocketException("No network here"))
        }

        val observer = Mockito.mock(Observer::class.java) as Observer<List<ModelEmployeeRegistration>>
        this.mViewModelEmployeeInformation.mEmployeeInformationData.observeForever(observer)

        this.mViewModelEmployeeInformation.getEmployeeInformation()
        Thread.sleep(10000)
        assertNull(this.mViewModelEmployeeInformation.mEmployeeInformationData.value)
    }
}