package com.example.weatherapp

import org.junit.Assert.assertEquals
import org.junit.Test

class WeatherUtilsTest {

    @Test
    fun testKelvinToCelsius() {
        val kelvin = 300.0
        val celsius = kelvinToCelsius(kelvin)
        assertEquals(26.85, celsius, 0.01)
    }

    private fun kelvinToCelsius(kelvin: Double): Double {
        return kelvin - 273.15
    }
}