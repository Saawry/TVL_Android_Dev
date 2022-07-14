package com.mostafiz.android.tvltask.models

import androidx.room.*

@Entity
class TaskModel {
    @PrimaryKey(autoGenerate = true)
    var id: Long = 0
    @ColumnInfo(name = "title")
    var title: String? = null
    @ColumnInfo(name = "description")
    var description: String? = null
    @ColumnInfo(name = "date")
    var date: String? = null
    @ColumnInfo(name = "time")
    var time: String? = null


    @Ignore
    constructor(title: String?, description: String?, date: String?, time: String?) {
        this.title = title
        this.description = description
        this.date = date
        this.time = time
    }

    constructor(id: Long, title: String?, description: String?, date: String?, time: String?) {
        this.id = id
        this.title = title
        this.description = description
        this.date = date
        this.time = time
    }
}
