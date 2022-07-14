package com.mostafiz.android.tvltask.storage

import android.annotation.SuppressLint
import android.content.Context
import androidx.room.Room
import androidx.test.core.app.ApplicationProvider
import androidx.test.ext.junit.runners.AndroidJUnit4
import com.google.common.truth.Truth.assertThat
import com.mostafiz.android.tvltask.dao.TaskDao
import com.mostafiz.android.tvltask.models.TaskModel
import junit.framework.TestCase
import kotlinx.coroutines.runBlocking
import org.junit.After
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith
import java.io.IOException


@RunWith(AndroidJUnit4::class)
class AppDatabaseTest :TestCase(){
    private lateinit var db: AppDatabase
    private lateinit var dao: TaskDao


    @Before
    public override fun setUp() {

        val context = ApplicationProvider.getApplicationContext<Context>()

        db = Room.inMemoryDatabaseBuilder(context, AppDatabase::class.java).build()
        dao = db.taskDao()
    }


    @After
    @Throws(IOException::class)
    fun closeDb() {
        db.close()
    }
    @SuppressLint("CheckResult")
    @Test
    fun insertRetrieveTask() = runBlocking {
        val task = TaskModel("DemoTask", "Description of demo task","07 Jul, 2022","7:30 pm")
        dao.insertTask(task)
        val tasks = dao.getTaskList()
        if (tasks != null) {
            assertThat(tasks.contains(task))

        }
    }
}