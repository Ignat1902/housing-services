package com.example.housing_and_communal_services.screens.bottom_bar_screen

import RequestCard
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.selection.toggleable
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import com.example.housing_and_communal_services.view_models.ServicesViewModel
import com.example.housing_and_communal_services.view_models.getRequest

@Composable
fun RequestScreen(servicesViewModel: ServicesViewModel) {
    servicesViewModel.getRequests()

    val selectedButton = remember { mutableStateOf(0) }

    val filteredWorkRequest = remember { mutableStateOf(emptyList<getRequest>()) }
    filteredWorkRequest.value = servicesViewModel.getRequest.filter { it.status == "в обработке" }

    val filteredEndRequest = remember { mutableStateOf(emptyList<getRequest>()) }
    filteredEndRequest.value = servicesViewModel.getRequest.filter { it.status == "Завершена" }

    Column() {
        Row(
            modifier = Modifier
                .padding(8.dp)
                .fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceEvenly
        ) {
            SegmentedButton(
                text = "В работе",
                isSelected = selectedButton.value == 0,
                onClick = { selectedButton.value = 0 }
            )
            SegmentedButton(
                text = "Завершенные",
                isSelected = selectedButton.value == 1,
                onClick = { selectedButton.value = 1 }
            )
        }

        if (servicesViewModel.getRequest.isEmpty()) {
            Column(
                horizontalAlignment = Alignment.CenterHorizontally,
                modifier = Modifier.fillMaxWidth()
            ) {
                Text(
                    "Вы не оставляли ни одной заявки",
                    modifier = Modifier.padding(top = 24.dp),
                    textAlign = TextAlign.Center,
                    style = MaterialTheme.typography.titleMedium,
                )
            }
        } else if (selectedButton.value == 0) {
            LazyColumn {
                items(filteredWorkRequest.value) { request ->
                    RequestCard(title = request.title, date = request.date, status = request.status)
                }
            }
        } else if (!filteredEndRequest.value.isEmpty()) {
            LazyColumn {
                items(filteredEndRequest.value) { request ->
                    RequestCard(title = request.title, date = request.date, status = request.status)
                }
            }
        }else{
            Column(
                horizontalAlignment = Alignment.CenterHorizontally,
                modifier = Modifier.fillMaxWidth()
            ) {
                Text(
                    "Нет завершенных заявок",
                    modifier = Modifier.padding(top = 24.dp),
                    textAlign = TextAlign.Center,
                    style = MaterialTheme.typography.titleMedium,
                )
            }
        }

    }
}

@Composable
fun SegmentedButton(
    text: String,
    isSelected: Boolean,
    onClick: () -> Unit
) {
    val backgroundColor =
        if (isSelected) MaterialTheme.colorScheme.surfaceVariant else MaterialTheme.colorScheme.background
    val textColor =
        if (isSelected) MaterialTheme.colorScheme.onBackground else MaterialTheme.colorScheme.onBackground

    Text(
        text = text,
        style = MaterialTheme.typography.titleMedium,
        modifier = Modifier
            .toggleable(
                value = isSelected,
                onValueChange = { onClick() }
            )
            .clip(RoundedCornerShape(50.dp))
            .background(backgroundColor)
            .padding(horizontal = 16.dp, vertical = 8.dp),
        color = textColor
    )
}
