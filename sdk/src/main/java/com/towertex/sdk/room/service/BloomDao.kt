package com.towertex.sdk.room.service

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import com.towertex.sdk.room.model.UserInput

@Dao
interface BloomDao: BloomDaoContract {
    @Query("SELECT * FROM user_inputs")
    override suspend fun getUserInputs(): List<UserInput>

    @Query("DELETE FROM user_inputs")
    override suspend fun deleteUserInputs()

    @Query("SELECT COUNT(*) FROM user_inputs")
    override suspend fun countUserInputs(): Int

    @Insert
    override suspend fun insertUserInputs(userInputs: List<UserInput>)
}