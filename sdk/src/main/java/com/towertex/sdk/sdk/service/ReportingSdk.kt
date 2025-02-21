package com.towertex.sdk.sdk.service

import com.towertex.sdk.network.model.Success
import com.towertex.sdk.network.model.UserInputPost
import com.towertex.sdk.network.service.ReportingApiContract
import com.towertex.sdk.room.service.BloomDaoContract
import com.towertex.sdk.room.model.UserInput
import com.towertex.sdk.sdk.model.BloomSDKConfiguration

class ReportingSdk(
    private val dao: BloomDaoContract,
    private val reportingApi: ReportingApiContract,
    private val sdkConfiguration: BloomSDKConfiguration,
): ReportingSdkContract {

    override suspend fun reportUserInputs(
        inputs: List<UserInput>,
        successCallback: () -> Unit,
        failureCallback: () -> Unit,
    ) {
        // 1# store latest user inputs
        dao.insertUserInputs(inputs)

        // 2# check if there are enough user inputs stored to send a report
        val count = dao.countUserInputs()
        if (count < sdkConfiguration.reportThreshold) {
            successCallback()
            return
        }

        // 3# send the report
        val userInputs = dao.getUserInputs()
        val result = reportingApi.postUserInputs(userInputs.toPost())
        if (result is Success) {
            dao.deleteUserInputs()
            successCallback()
        } else {
            failureCallback()
        }
    }

    private fun List<UserInput>.toPost() = UserInputPost(map {
        UserInputPost._UserInput(it.key, it.value, it.epoch)
    })
}