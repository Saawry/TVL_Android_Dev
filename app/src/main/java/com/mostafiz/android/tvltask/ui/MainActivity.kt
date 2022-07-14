package com.mostafiz.android.tvltask.ui

import android.annotation.SuppressLint
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import android.widget.Toast
import androidx.appcompat.app.AppCompatDelegate
import androidx.lifecycle.ViewModelProvider
import androidx.preference.PreferenceManager
import androidx.recyclerview.widget.LinearLayoutManager
import com.mostafiz.android.tvltask.R
import com.mostafiz.android.tvltask.adapter.TaskAdapter
import com.mostafiz.android.tvltask.databinding.ActivityMainBinding
import com.mostafiz.android.tvltask.models.TaskModel
import com.mostafiz.android.tvltask.viewmodel.TaskViewModel
import io.reactivex.Completable
import io.reactivex.CompletableObserver
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.Disposable
import io.reactivex.schedulers.Schedulers

class MainActivity : AppCompatActivity() {
    private lateinit var binding: ActivityMainBinding
    private lateinit var adapter: TaskAdapter
    val modelList: ArrayList<TaskModel> = ArrayList()
    lateinit var taskViewModel: TaskViewModel
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        taskViewModel = ViewModelProvider(this)[TaskViewModel::class.java]


        val sharedPref = PreferenceManager.getDefaultSharedPreferences(this)
        val dark = sharedPref.getBoolean("theme", true)

        if (dark) {
            AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_YES)
        } else {
            AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO)
        }


        binding.btnAddNewTask.setOnClickListener {
            val intent = Intent(this, NewTaskActivity::class.java)
            startActivity(intent)
        }


        initRecycler()

        retrieveDataFlow()
    }

    @SuppressLint("CheckResult", "NotifyDataSetChanged")
    private fun retrieveDataFlow() {

        taskViewModel.getTaskListFlow()?.observeOn(AndroidSchedulers.mainThread())
            ?.subscribeOn(Schedulers.io())?.subscribe { taskList ->
                if (taskList != null) {
                    if (taskList.isNotEmpty()) {
                        modelList.clear()
                        modelList.addAll(taskList as ArrayList<TaskModel>)
                        adapter.notifyDataSetChanged()
                    }
                }
            }
    }


    private fun initRecycler() {

        binding.taskRecycler.layoutManager = LinearLayoutManager(this)

        adapter = TaskAdapter(this, modelList, object : TaskAdapter.DeleteTask {
            override fun deleteTask(id: Long) {
                deleteItem(id)
            }
        })

        binding.taskRecycler.adapter = adapter

    }


    private fun deleteItem(id: Long) {
        Completable.fromAction {
            taskViewModel
                .deleteTask(id)
        }
            .observeOn(AndroidSchedulers.mainThread()).subscribeOn(Schedulers.io())
            .subscribe(object : CompletableObserver {
                override fun onSubscribe(d: Disposable) {}
                override fun onComplete() {
                    //   binding.loadingBar.visibility = View.INVISIBLE
                    Toast.makeText(
                        this@MainActivity,
                        "Deleted...",
                        Toast.LENGTH_SHORT
                    ).show()

                }

                override fun onError(e: Throwable) {

                    Toast.makeText(this@MainActivity, "Failed...", Toast.LENGTH_SHORT)
                        .show()
                }
            })
    }



    override fun onPrepareOptionsMenu(menu: Menu): Boolean {
        menu.clear() //Clear view of previous menu
        val inflater = menuInflater
        inflater.inflate(R.menu.main_menu, menu)

        return super.onPrepareOptionsMenu(menu)
    }


    override fun onOptionsItemSelected(item: MenuItem): Boolean {

        if (item.itemId == R.id.menu_settings) {
            val intent = Intent(this, SettingsActivity::class.java)
            startActivity(intent)
            return false
        }
        return super.onOptionsItemSelected(item)
    }
}