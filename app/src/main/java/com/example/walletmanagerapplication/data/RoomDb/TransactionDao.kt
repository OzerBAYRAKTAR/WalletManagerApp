package com.example.walletmanagerapplication.data.RoomDb

import androidx.lifecycle.LiveData
import androidx.room.*
import kotlinx.coroutines.flow.Flow



@Dao
interface TransactionDao {

    @Query("SELECT * from transaction_table")
    fun getTransactions(): Flow<List<Transaction>>

    @Query("SELECT * from ıncome_table")
    fun getIncomes(): Flow<List<IncomeModel>>

    @Query("SELECT SUM(amount) from transaction_table")
    fun getTotalSpent():LiveData<Int>

    @Query("SELECT SUM(amount) from ıncome_table")
    fun getTotalIncome(): LiveData<Int>


    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertTransaction(transaction: Transaction)

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertIncome(income: IncomeModel)

    @Query("DELETE FROM transaction_table")
    suspend fun deleteTransaction(transaction: Transaction)

    @Query("DELETE FROM ıncome_table")
    suspend fun deleteIncome(income: IncomeModel)

    @Update
    suspend fun update( transaction: Transaction)







}