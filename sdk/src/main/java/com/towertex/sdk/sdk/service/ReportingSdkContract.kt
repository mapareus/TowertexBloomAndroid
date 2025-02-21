package com.towertex.sdk.sdk.service

import com.towertex.sdk.room.model.UserInput

interface ReportingSdkContract {
    suspend fun reportUserInputs(
        inputs: List<UserInput>,
        successCallback: () -> Unit,
        failureCallback: () -> Unit,
    )
}