package com.example.sprawdzian

import android.os.Bundle
import android.widget.Toast
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Button
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.HorizontalDivider
import androidx.compose.ui.text.font.FontWeight

const val yellow = 0xFFFAAF32
const val forecast_bg = 0xFF3C1E82

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            WeatherScreen(
                forecastList = sampleForecasts,
                onButtonClick = {
                    Toast.makeText(this, "Pogoda będzie piękna!", Toast.LENGTH_SHORT).show()
                }
            )
        }
    }
}

@Composable
fun WeatherScreen(forecastList: List<Forecast>, onButtonClick: () -> Unit) {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .verticalScroll(rememberScrollState())
            .background(Color(0xFF6200EE))
            .padding(vertical = 20.dp),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Image(
            painter = painterResource(id = R.drawable.ic_sunny),
            contentDescription = "Sunny",
            modifier = Modifier.size(100.dp)
        )
        Text(text = "5.0°C", fontSize = 48.sp, color = Color.White, fontWeight = FontWeight.Bold)
        Text(text = "Warszawa", fontSize = 20.sp, color = Color.White)

        Spacer(modifier = Modifier.height(16.dp))

        ForecastSection(
            forecastList = forecastList,
            date = "1.12.2024"
        )

        Spacer(modifier = Modifier.height(16.dp))

        Button(
            onClick = onButtonClick,
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 16.dp),
            colors = androidx.compose.material3.ButtonDefaults.buttonColors(
                containerColor = Color(yellow)
            )
        ) {
            Text(text = "Sprawdź pozostałe dni", fontSize = 12.sp, color = Color.White)
        }

    }
}


@Composable
fun ForecastItem(forecast: Forecast) {
    Column(
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.spacedBy(4.dp)
    ) {
        Text(
            text = "${forecast.temperature}°C",
            color = Color.White,
            fontSize = 14.sp
        )

        Image(
            painter = painterResource(id = forecast.iconRes),
            contentDescription = forecast.description,
            modifier = Modifier.size(40.dp)
        )

        Text(
            text = forecast.time,
            color = Color.White,
            fontSize = 14.sp,
            textAlign = TextAlign.Center
        )
    }
}


data class Forecast(val time: String, val temperature: Double, val iconRes: Int, val description: String)

val sampleForecasts = listOf(
    Forecast("8:00", 4.0, R.drawable.ic_snow, "Cloudy"),
    Forecast("10:00", 4.5, R.drawable.ic_cloudy, "Snow"),
    Forecast("12:00", 4.7, R.drawable.ic_cloudy, "Sunny"),
    Forecast("14:00", 5.0, R.drawable.ic_sunny_and_cloudy, "Partly Cloudy"),
    Forecast("16:00", 5.0, R.drawable.ic_sunny_and_cloudy, "Sunny"),
    Forecast("18:00", 4.7, R.drawable.ic_sunny_and_cloudy, "Partly Cloudy"),
    Forecast("16:00", 5.0, R.drawable.ic_sunny_and_cloudy, "Sunny"),
    Forecast("18:00", 4.7, R.drawable.ic_sunny_and_cloudy, "Partly Cloudy"),
)

@Preview(showBackground = true)
@Composable
fun WeatherScreenPreview() {
    WeatherScreen(forecastList = sampleForecasts, onButtonClick = {})
}

@Composable
fun ForecastSection(forecastList: List<Forecast>, date: String) {
    Surface(
        modifier = Modifier
            .fillMaxWidth(),
        color = Color(forecast_bg),
        shape = RoundedCornerShape(16.dp)
    ) {
        Column(
            modifier = Modifier.padding(16.dp)
        ) {
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween
            ) {
                Text(
                    text = "Dzisiaj",
                    color = Color.White,
                    fontSize = 14.sp,
                    fontWeight = FontWeight.Bold
                )
                Text(
                    text = date,
                    color = Color.White,
                    fontSize = 14.sp,
                    fontWeight = FontWeight.Bold
                )
            }

            Spacer(modifier = Modifier.height(8.dp))
            HorizontalDivider(thickness = 1.dp, color = Color.Gray)
            Spacer(modifier = Modifier.height(16.dp))

            LazyRow(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.spacedBy(25.dp)
            ) {
                items(forecastList) { forecast ->
                    ForecastItem(forecast)
                }
            }
        }
    }
}
