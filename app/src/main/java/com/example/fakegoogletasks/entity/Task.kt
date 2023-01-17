package com.example.fakegoogletasks.entity

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.ForeignKey
import androidx.room.PrimaryKey
import java.io.Serializable
import java.util.*


@Entity(
    foreignKeys = [ForeignKey(
        entity = Task::class,
        parentColumns = arrayOf("id"),
        childColumns = arrayOf("parent_id")
    )], tableName = "task_table"
)
data class Task(
    @PrimaryKey(autoGenerate = true)
    val id: Int,
    var title: String,
    var description: String,
    var date: Date,
    var isFinish: Boolean,
    var isFavorite: Boolean,
    val parent_id: Int?
): Serializable
