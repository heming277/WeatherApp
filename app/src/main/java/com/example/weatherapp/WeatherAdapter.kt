package com.example.weatherapp

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.weatherapp.model.WeatherResponse

class WeatherAdapter(
    private val dailyWeatherList: List<Pair<WeatherResponse, String>>,
    private val onRemoveClick: (WeatherResponse) -> Unit
) : RecyclerView.Adapter<WeatherAdapter.WeatherViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): WeatherViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.item_daily_weather, parent, false)
        return WeatherViewHolder(view)
    }

    override fun onBindViewHolder(holder: WeatherViewHolder, position: Int) {
        val (weatherResponse, timestamp) = dailyWeatherList[position]
        holder.tempTextView.text = "${weatherResponse.name}\n${weatherResponse.main.temp}°C\n${weatherResponse.weather[0].description}"
        holder.timeTextView.text = timestamp
        holder.removeButton.setOnClickListener { onRemoveClick(weatherResponse) }
    }

    override fun getItemCount(): Int {
        return dailyWeatherList.size
    }

    class WeatherViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val tempTextView: TextView = itemView.findViewById(R.id.temp_text)
        val timeTextView: TextView = itemView.findViewById(R.id.time_text)
        val removeButton: TextView = itemView.findViewById(R.id.remove_button)
    }
}