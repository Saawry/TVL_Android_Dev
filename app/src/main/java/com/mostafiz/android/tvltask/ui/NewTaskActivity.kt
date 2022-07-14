package com.mostafiz.android.tvltask.ui

import android.annotation.SuppressLint
import android.app.DatePickerDialog
import android.app.TimePickerDialog
import android.os.Bundle
import android.view.MotionEvent
import android.view.View
import android.widget.DatePicker
import android.widget.TimePicker
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.ViewModelProvider
import com.mostafiz.android.tvltask.databinding.ActivityNewTaskBinding
import com.mostafiz.android.tvltask.models.TaskModel
import io.reactivex.Completable
import io.reactivex.CompletableObserver
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.Disposable
import io.reactivex.schedulers.Schedulers
import com.mostafiz.android.tvltask.viewmodel.TaskViewModel
import java.text.SimpleDateFormat
import java.util.*


class NewTaskActivity : AppCompatActivity() {
    private lateinit var binding: ActivityNewTaskBinding

    private lateinit var title: String
    private lateinit var description: String
    private lateinit var date: String
    private lateinit var time: String
    lateinit var taskViewModel: TaskViewModel


     val myCalendar = Calendar.getInstance()

    var now = Calendar.getInstance()
    var yr = now[Calendar.YEAR]
    var mnth = now[Calendar.MONTH]
    var day = now[Calendar.DAY_OF_MONTH]
    var hour = now[Calendar.HOUR_OF_DAY]
    var minte = now[Calendar.MINUTE]


    var myTimeFormat = "hh:mm a"
    var stf: SimpleDateFormat = SimpleDateFormat(myTimeFormat, Locale.US)
    var myDateFormat = "yyyy-MM-dd"
    var sdf: SimpleDateFormat = SimpleDateFormat(myDateFormat, Locale.US)


    @SuppressLint("ClickableViewAccessibility")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding= ActivityNewTaskBinding.inflate(layoutInflater)
        setContentView(binding.root)


        taskViewModel = ViewModelProvider(this)[TaskViewModel::class.java]


        binding.insertBtn.setOnClickListener {
            if (validate()==1){
                binding.loadingBar.visibility= View.VISIBLE
                val task=TaskModel(title,description,date,time)
                insertTaskModel(task)
            }
        }


        binding.tvTaskDate.setOnTouchListener { v, event ->
            if (event.action == MotionEvent.ACTION_UP) {
                val nDate = DatePickerDialog(
                    this,
                    { view: DatePicker?, year: Int, month: Int, dayOfMonth: Int ->
                        myCalendar.set(Calendar.YEAR, year)
                        myCalendar.set(Calendar.MONTH, month)
                        myCalendar.set(Calendar.DAY_OF_MONTH, dayOfMonth)

                        binding.tvTaskDate.setText(sdf.format(myCalendar.time))
                    },
                    yr,
                    mnth,
                    day
                )
                nDate.show()
            }
            false
        }

        binding.tvTime.setOnTouchListener { v, event ->
            if (event.action == MotionEvent.ACTION_UP) {
                val nTime = TimePickerDialog(
                    this,
                    { view: TimePicker?, hourOfDay: Int, minute: Int ->
                        myCalendar[Calendar.HOUR_OF_DAY] = hourOfDay
                        myCalendar[Calendar.MINUTE] = minute
                        binding.tvTime.setText(stf.format(myCalendar.time))
                    },
                    hour,
                    minte,
                    false
                )
                nTime.show()
            }
            false
        }




    }

    private fun insertTaskModel(task: TaskModel) {
        Completable.fromAction {
            taskViewModel
                .insertTask(task)
        }
            .observeOn(AndroidSchedulers.mainThread()).subscribeOn(Schedulers.io())
            .subscribe(object : CompletableObserver {
                override fun onSubscribe(d: Disposable) {}
                override fun onComplete() {
                    //   binding.loadingBar.visibility = View.INVISIBLE
                    Toast.makeText(
                        this@NewTaskActivity,
                        "Saved Successfully...",
                        Toast.LENGTH_SHORT
                    ).show()

                    binding.tvTaskName.setText("")
                    binding.tvDescription.setText("")
                    binding.tvTaskDate.setText("--/--/----")
                    binding.tvTime.setText("--:--")

                    binding.loadingBar.visibility= View.INVISIBLE
                }

                override fun onError(e: Throwable) {
                    binding.loadingBar.visibility= View.INVISIBLE
                    Toast.makeText(this@NewTaskActivity, "Failed...", Toast.LENGTH_SHORT)
                        .show()
                }
            })
    }


    private fun validate():Int{
        title = binding.tvTaskName.text.toString()
        when {
            title.isEmpty() -> {
                binding.tvTaskName.error = "empty"
                return 0
            }
            title.length < 3 -> {
                binding.tvTaskName.error = "short, min 3 chars"
                return 0
            }
            else -> {
                binding.tvTaskName.error = null
            }
        }

        description = binding.tvDescription.text.toString()
        when {
            description.isEmpty() -> {
                binding.tvDescription.error = "empty"
                return 0
            }
            description.length < 5 -> {
                binding.tvDescription.error = "short, min 5 chars"
                return 0
            }
            else -> {
                binding.tvDescription.error = null
            }
        }

        date = binding.tvTaskDate.text.toString()
        when (date) {
            "--/--/----" -> {
                binding.tvTaskDate.error = "select date"
                return 0
            }
            else -> {
                binding.tvTaskDate.error = null
            }
        }
        time = binding.tvTime.text.toString()
        when (time) {
            "--:--" -> {
                binding.tvTime.error = "select date"
                return 0
            }
            else -> {
                binding.tvTime.error = null
            }
        }

        return 1
    }
}