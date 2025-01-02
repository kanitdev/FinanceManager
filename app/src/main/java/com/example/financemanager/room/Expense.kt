package com.example.financemanager.room

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "Expense")
data class Expense(
    @PrimaryKey(autoGenerate = true)
    val id:Int?=null,
    val expense: String? = null,
    val amt: Double? = null,
    val description:String? = null
)



