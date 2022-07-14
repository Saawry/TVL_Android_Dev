package com.mostafiz.android.tvltask.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy.REPLACE
import androidx.room.Query
import com.mostafiz.android.tvltask.models.TaskModel
import io.reactivex.Single

@Dao
interface TaskDao {

    @Insert(onConflict = REPLACE)
    fun insertTask(contactModelList: TaskModel)

    @Query("Select * from TaskModel")
    fun getTaskList(): Single<List<TaskModel?>?>?

}