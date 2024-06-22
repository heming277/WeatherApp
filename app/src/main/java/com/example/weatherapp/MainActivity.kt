package com.example.weatherapp

import android.os.Bundle
import android.view.inputmethod.EditorInfo
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.weatherapp.model.WeatherResponse
import com.example.weatherapp.network.RetrofitInstance
import com.google.android.material.textfield.TextInputEditText
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import java.text.SimpleDateFormat
import java.util.*

class MainActivity : AppCompatActivity() {
    private lateinit var searchView: TextInputEditText
    private lateinit var cityList: RecyclerView
    private lateinit var adapter: WeatherAdapter
    private val citiesWeather = mutableListOf<Pair<WeatherResponse, String>>()
    private val dateFormat = SimpleDateFormat("dd/MM/yyyy HH:mm", Locale.getDefault())

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        searchView = findViewById(R.id.search_view)
        cityList = findViewById(R.id.city_list)
        adapter = WeatherAdapter(citiesWeather) { weatherResponse -> removeCity(weatherResponse) }
        cityList.layoutManager = LinearLayoutManager(this)
        cityList.adapter = adapter

        searchView.setOnEditorActionListener { _, actionId, _ ->
            if (actionId == EditorInfo.IME_ACTION_DONE) {
                val cityName = searchView.text.toString().trim()
                if (cityName.isNotEmpty()) {
                    fetchWeatherData(cityName, BuildConfig.API_KEY)
                }
                true
            } else {
                false
            }
        }
    }

    private fun fetchWeatherData(cityName: String, apiKey: String) {
        val call = RetrofitInstance.api.getWeatherByCity(cityName, apiKey)

        call.enqueue(object : Callback<WeatherResponse> {
            override fun onResponse(call: Call<WeatherResponse>, response: Response<WeatherResponse>) {
                if (response.isSuccessful) {
                    val weatherResponse = response.body()
                    if (weatherResponse != null) {
                        val existingIndex = citiesWeather.indexOfFirst { it.first.name == weatherResponse.name }
                        val timestamp = dateFormat.format(Date())
                        if (existingIndex != -1) {
                            citiesWeather[existingIndex] = weatherResponse to timestamp
                        } else {
                            citiesWeather.add(weatherResponse to timestamp)
                        }
                        adapter.notifyDataSetChanged()
                    }
                }
            }

            override fun onFailure(call: Call<WeatherResponse>, t: Throwable) {
                // Handle failure
            }
        })
    }

    private fun removeCity(weatherResponse: WeatherResponse) {
        citiesWeather.removeIf { it.first.name == weatherResponse.name }
        adapter.notifyDataSetChanged()
    }
}