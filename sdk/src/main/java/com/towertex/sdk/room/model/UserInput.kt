package com.towertex.sdk.room.model

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(
    tableName = "user_inputs"
)
class UserInput(
    val key: String,
    val value: String,
    val epoch: Long,
    @PrimaryKey(autoGenerate = true)
    val id: Int = 0,
)
