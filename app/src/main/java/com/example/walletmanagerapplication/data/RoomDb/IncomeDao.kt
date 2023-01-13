package com.example.walletmanagerapplication.data.RoomDb

import androidx.lifecycle.LiveData
import androidx.room.*
import kotlinx.coroutines.flow.Flow


@Dao
interface IncomeDao {

    @Query("SELECT * from ıncome_table")
    fun getIncomes(): Flow<List<IncomeModel>>

    @Query("SELECT SUM(amount) from ıncome_table")
    fun getTotalIncome(): LiveData<Int>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertIncome(income: IncomeModel)

    @Query("DELETE FROM ıncome_table")
    suspend fun deleteIncome(income: IncomeModel)

}
