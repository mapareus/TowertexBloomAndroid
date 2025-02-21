package com.towertex.sdk.room.service

import com.towertex.sdk.room.model.UserInput

interface BloomDaoContract {
    suspend fun getUserInputs(): List<UserInput>
    suspend fun deleteUserInputs()
    suspend fun countUserInputs(): Int
    suspend fun insertUserInputs(userInputs: List<UserInput>)
}