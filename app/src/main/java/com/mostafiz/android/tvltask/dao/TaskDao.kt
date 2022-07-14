package com.mostafiz.android.tvltask.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.OnConflictStrategy.REPLACE
import androidx.room.Query
import com.mostafiz.android.tvltask.models.TaskModel
import io.reactivex.Flowable
import io.reactivex.Single

@Dao
interface TaskDao {

    @Insert(onConflict = REPLACE)
    fun insertTask(contactModelList: TaskModel)

    @Insert(onConflict = OnConflictStrategy.ABORT)
    fun insertTaskList(contactModelList: List<TaskModel?>?)

    @Query("Select * from TaskModel where id=:id")
    fun getTask(id: Long): Single<TaskModel?>?

    @Query("Select * from TaskModel")
    fun getTaskList(): Single<List<TaskModel?>?>?

    @Query("Select * from TaskModel")
    fun getTaskListFlow(): Flowable<List<TaskModel?>?>?

    @Query("delete from TaskModel where id=:id ")
    fun deleteTask(id: Long)

}