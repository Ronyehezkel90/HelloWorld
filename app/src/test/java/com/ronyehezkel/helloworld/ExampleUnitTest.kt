package com.ronyehezkel.helloworld

import android.content.Context
import android.content.SharedPreferences
import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import com.ronyehezkel.helloworld.fake.FakeRepo
import com.ronyehezkel.helloworld.fake.FakeServerManager
import com.ronyehezkel.helloworld.model.ToDoList
import com.ronyehezkel.helloworld.viewmodel.NotesViewModel
import org.junit.After
import org.junit.Test

import org.junit.Assert.*
import org.junit.Before
import org.junit.Rule
import org.mockito.Mockito
import org.mockito.Mockito.*
import org.mockito.kotlin.doReturn
import org.mockito.kotlin.mock
import java.util.concurrent.TimeUnit

/**
 * Example local unit test, which will execute on the development machine (host).
 *
 * See [testing documentation](http://d.android.com/tools/testing).
 */
class ExampleUnitTest {

    var nowTime = 0L

    @Before
    fun a() {
        nowTime = System.currentTimeMillis()
        println("Now Time is : $nowTime")
    }

    @Test
    fun addition_isCorrect() {
        assertEquals(4, 2 + 2)
    }

    @Test
    fun checkLastLogin_isLastLoginLessThanOneHour() {
        val mockShredPref = mock(SharedPreferences::class.java)
        val THIRTHY_MIN_IN_MILLI_SEC = nowTime - TimeUnit.MINUTES.toMillis(30)
        `when`(mockShredPref.getLong("LAST_LOGIN", -1)).thenReturn(THIRTHY_MIN_IN_MILLI_SEC)

        val mockContext = mock<Context> {
            on {
                getSharedPreferences(
                    anyString(),
                    anyInt()
                )
            } doReturn mockShredPref
        }

        val result = Utils.checkIfOneHourPassed(mockContext)
        assertEquals(true, result)
    }

    @Test
    fun checkLastLogin_isLastLoginMoreThanOneHour() {
        val mockShredPref = mock(SharedPreferences::class.java)

        val THIRTHY_MIN_IN_MILLI_SEC = nowTime - TimeUnit.MINUTES.toMillis(62)
        `when`(mockShredPref.getLong("LAST_LOGIN", -1)).thenReturn(THIRTHY_MIN_IN_MILLI_SEC)

        val mockContext = mock<Context> {
            on {
                getSharedPreferences(
                    anyString(),
                    anyInt()
                )
            } doReturn mockShredPref
        }

        val result = Utils.checkIfOneHourPassed(mockContext)
        assertEquals(false, result)
    }

    @get:Rule
    val rule = InstantTaskExecutorRule()

    @Test
    fun testViewModel_LiveDataUpdate(){

        val listName = "My Songs"
        val notesViewModel = NotesViewModel(FakeServerManager(), FakeRepo())

        val toDoList = ToDoList(listName)

        notesViewModel.setCurrentToDoList(toDoList)

        assertEquals(listName, notesViewModel.toDoListLiveData.value!!.title)
    }

    @After
    fun after() {
        nowTime = System.currentTimeMillis()
        println("Now Time is : $nowTime")
    }
}