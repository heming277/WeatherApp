package com.example.weatherapp

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.weatherapp.model.CityWeather

class CityAdapter(
    private val cities: List<CityWeather>,
    private val clickListener: (CityWeather) -> Unit
) : RecyclerView.Adapter<CityAdapter.CityViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CityViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.item_city_weather, parent, false)
        return CityViewHolder(view)
    }

    override fun onBindViewHolder(holder: CityViewHolder, position: Int) {
        val cityWeather = cities[position]
        holder.bind(cityWeather, clickListener)
    }

    override fun getItemCount(): Int {
        return cities.size
    }

    class CityViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        private val cityNameTextView: TextView = itemView.findViewById(R.id.city_name)
        private val currentTempTextView: TextView = itemView.findViewById(R.id.current_temp)
        private val weatherDescTextView: TextView = itemView.findViewById(R.id.weather_desc)

        fun bind(cityWeather: CityWeather, clickListener: (CityWeather) -> Unit) {
            cityNameTextView.text = cityWeather.name
            currentTempTextView.text = "${cityWeather.temp}°C"
            weatherDescTextView.text = cityWeather.description
            itemView.setOnClickListener { clickListener(cityWeather) }
        }
    }
}