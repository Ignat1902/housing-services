package com.example.housing_and_communal_services.data.models

import java.util.Date

data class MeterReading(
    val serial_number: String,
    val date: Date,
    val value: Double
)
