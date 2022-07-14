package viewmodel

import android.app.Application
import androidx.annotation.NonNull
import androidx.lifecycle.AndroidViewModel
import com.mostafiz.android.tvltask.models.TaskModel
import com.mostafiz.android.tvltask.storage.AppDatabase
import io.reactivex.Flowable
import io.reactivex.Single


class TaskViewModel(@NonNull application: Application?) : AndroidViewModel(application!!) {
    var appDatabase: AppDatabase? = AppDatabase.getDatabase(application!!)


    fun insertTask(contactModel: TaskModel) {
        appDatabase?.taskDao()!!.insertTask(contactModel)
    }

    fun insertTaskList(contactModelList: List<TaskModel?>?) {
        appDatabase?.taskDao()!!.insertTaskList(contactModelList)
    }


    fun getTask(id: Long): Single<TaskModel?>? {
        return appDatabase?.taskDao()!!.getTask(id)
    }


    fun getTaskList(): Single<List<TaskModel?>?>? {
        return appDatabase?.taskDao()!!.getTaskList()
    }

    fun getTaskListFlow(): Flowable<List<TaskModel?>?>? {
        return appDatabase?.taskDao()!!.getTaskListFlow()
    }

    fun deleteTask(id: Long) {
        appDatabase?.taskDao()!!.deleteTask(id)
    }

}