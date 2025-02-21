package com.towertex.sdk.network.service

import com.towertex.sdk.network.model.BloomApiResult
import com.towertex.sdk.network.model.UserInputPost

interface ReportingApiContract {

    suspend fun postUserInputs(
        post: UserInputPost
    ): BloomApiResult<Unit>
}