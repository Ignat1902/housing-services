package com.example.housing_and_communal_services.screens.detail_screen

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.BoxWithConstraints
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.housing_and_communal_services.R
import com.example.housing_and_communal_services.data.repositories.MeteringDeviceRepository
import com.example.housing_and_communal_services.view_models.MeterViewModel
import com.example.housing_and_communal_services.view_models.MeterViewModelFactory
import java.text.SimpleDateFormat
import java.util.Date
import java.util.Locale

@Composable
fun MeterReadingList(
    serialNumber: String,
    name: String
) {
    val viewModel: MeterViewModel = viewModel(
        factory = MeterViewModelFactory(
            MeteringDeviceRepository()
        )
    )
    val readings by viewModel.getMeterReadings(serialNumber)
        .observeAsState(emptyList())

    Column(
        modifier = Modifier.padding(horizontal = 16.dp),
        verticalArrangement = Arrangement.spacedBy(16.dp)
    ) {
        Card(
            shape = RoundedCornerShape(8.dp),
            colors = CardDefaults.outlinedCardColors()
        ){
            Row(
                Modifier
                    .padding(vertical = 16.dp)
                    .fillMaxWidth(),
                horizontalArrangement = Arrangement.spacedBy(16.dp)
            ) {

                Icon(
                    modifier = Modifier.size(48.dp),
                    tint =
                    when (name) {
                        "Холодная вода" -> MaterialTheme.colorScheme.onSurface
                        "Горячая вода" -> MaterialTheme.colorScheme.error
                        "Газ" -> MaterialTheme.colorScheme.onSurface
                        else -> Color.Yellow
                    },
                    painter = painterResource(
                        id = if (name == "Холодная вода" || name == "Горячая вода") {
                            R.drawable.baseline_water_drop_24
                        } else if (name == "Электричество день" || name == "Электричество ночь") {
                            R.drawable.baseline_bolt_24
                        } else R.drawable.baseline_gas_meter_24
                    ),
                    contentDescription = "Icon"
                )


                Column(
                    verticalArrangement = Arrangement.spacedBy(4.dp)
                ) {
                    Text(
                        text = name,
                        style = MaterialTheme.typography.titleMedium,
                        color = MaterialTheme.colorScheme.onBackground,
                        textAlign = TextAlign.Center
                    )
                    Row(
                        horizontalArrangement = Arrangement.Center
                    ) {
                        Text(
                            text = "Номер ИПУ: ",
                            style = MaterialTheme.typography.bodyMedium,
                            color = MaterialTheme.colorScheme.outline,
                            textAlign = TextAlign.Center
                        )
                        Text(
                            text = serialNumber,
                            style = MaterialTheme.typography.bodyMedium,
                            color = MaterialTheme.colorScheme.tertiary,
                            textAlign = TextAlign.Center
                        )
                    }
                }
            }
        }

        if (readings.isEmpty()) {
            Box(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(50.dp),
                contentAlignment = Alignment.Center
            ) {
                Text(
                    text = "По данному счетчику пока что нет истории показаний",
                    textAlign = TextAlign.Center
                )
            }
        } else {
            Scaffold(
                content = { padding ->
                    BoxWithConstraints(
                        Modifier.padding(padding),
                    ) {
                        LazyColumn {
                            items(readings) { reading ->
                                Reading(date = reading.date, value = reading.value)
                            }
                        }
                    }
                }
            )
        }
    }
}


@Composable
fun Reading(date: Date, value: Double) {

    val dateFormat = SimpleDateFormat("E MMM dd HH:mm:ss z yyyy", Locale.US)
    val dateString = dateFormat.parse(date.toString())

    val dateFormatter = SimpleDateFormat("dd MMMM yyyy", Locale("ru"))
    val timeFormatter = SimpleDateFormat("HH:mm", Locale.ENGLISH)

    val dateFormatted = dateString?.let { dateFormatter.format(it) }
    val timeFormatted = dateString?.let { timeFormatter.format(it) }

    Card(
        shape = RoundedCornerShape(8.dp),
        modifier = Modifier.padding(vertical = 16.dp)
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(vertical = 10.dp),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.SpaceBetween
        ) {
            Column(
                modifier = Modifier
                    .padding(horizontal = 16.dp, vertical = 10.dp)
            ) {
                if (dateFormatted != null) {
                    Text(
                        text = dateFormatted,
                        style = MaterialTheme.typography.titleLarge,
                        color = MaterialTheme.colorScheme.onBackground,
                    )
                }
                Row(
                    modifier = Modifier.padding(top = 12.dp),
                    horizontalArrangement = Arrangement.Center,
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Text(
                        text = "Время: ",
                        style = MaterialTheme.typography.bodyMedium,
                        color = MaterialTheme.colorScheme.outline,
                    )
                    if (timeFormatted != null) {
                        Text(
                            text = timeFormatted,
                            style = MaterialTheme.typography.bodyMedium,
                            color = MaterialTheme.colorScheme.onSecondaryContainer
                        )
                    }
                }
            }
            Column(
                modifier = Modifier
                    .padding(horizontal = 16.dp, vertical = 10.dp),
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                Text(
                    text = value.toString(),
                    style = MaterialTheme.typography.titleMedium,
                    color = MaterialTheme.colorScheme.onSecondaryContainer
                )
            }

        }
    }

}

