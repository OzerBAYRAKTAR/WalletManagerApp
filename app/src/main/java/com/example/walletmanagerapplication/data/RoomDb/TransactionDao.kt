package com.example.walletmanagerapplication.data.RoomDb

import androidx.room.*
import kotlinx.coroutines.flow.Flow



@Dao
interface TransactionDao {

    @Query("SELECT * from transaction_table")
    fun getTransactions(): Flow<List<Transaction>>


    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insert(  transaction: Transaction)

    @Delete
    suspend fun delete(transaction: Transaction)

    @Update
    suspend fun update( transaction: Transaction)
}