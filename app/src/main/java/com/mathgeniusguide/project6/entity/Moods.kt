package com.mathgeniusguide.project6.entity

import android.arch.persistence.room.*
import java.util.*

@Entity
data class Moods(
    @PrimaryKey
    val time: String,
    val emotion: Int,
    val note: String
)