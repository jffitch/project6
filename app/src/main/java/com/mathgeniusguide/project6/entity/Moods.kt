package com.mathgeniusguide.project6.entity

import androidx.room.*

@Entity
data class Moods(
    @PrimaryKey
    val time: String,
    val emotion: Int,
    val note: String
)