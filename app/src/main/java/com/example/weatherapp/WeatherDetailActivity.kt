package com.example.weatherapp

import android.os.Bundle
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import com.example.weatherapp.model.WeatherResponse
import com.example.weatherapp.network.RetrofitInstance
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class WeatherDetailActivity : AppCompatActivity() {
    private lateinit var detailTextView: TextView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_weather_detail)

        detailTextView = findViewById(R.id.weather_detail)
        val cityName = intent.getStringExtra("CITY_NAME") ?: return

        fetchWeatherDetails(cityName, "8bd453185c1b4e80733c92a514dc2974")
    }

    private fun fetchWeatherDetails(cityName: String, apiKey: String) {
        val call = RetrofitInstance.api.getWeatherByCity(cityName, apiKey)

        call.enqueue(object : Callback<WeatherResponse> {
            override fun onResponse(call: Call<WeatherResponse>, response: Response<WeatherResponse>) {
                if (response.isSuccessful) {
                    val weatherResponse = response.body()
                    if (weatherResponse != null) {
                        detailTextView.text = """
                            City: ${weatherResponse.name}
                            Current: ${weatherResponse.main.temp}°C, ${weatherResponse.weather[0].description}
                            Min Temp: ${weatherResponse.main.temp_min}°C
                            Max Temp: ${weatherResponse.main.temp_max}°C
                            Humidity: ${weatherResponse.main.humidity}%
                            Wind Speed: ${weatherResponse.wind.speed} m/s
                        """.trimIndent()
                    }
                } else {
                    detailTextView.text = "Failed to get weather data: ${response.message()}"
                }
            }

            override fun onFailure(call: Call<WeatherResponse>, t: Throwable) {
                detailTextView.text = "Error: ${t.message}"
            }
        })
    }
}