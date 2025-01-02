package com.example.financemanager.room

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.Transaction
import androidx.room.Update
import androidx.room.Upsert


@Dao
interface TransactionDao {
    @Query("SELECT* from Expense")
    fun getAll():List<Expense>

    @Insert
    fun insert(expense: Expense)

    @Delete
    suspend fun delete( vararg expense: Expense)

    @Query("SELECT * FROM Expense ORDER BY id DESC")
    fun getAllOrderedByLatest(): List<Expense>





}
